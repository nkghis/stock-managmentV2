package ics.ci.stock.service.impl;

import ics.ci.stock.dto.inventaire.InventaireDetailDto;
import ics.ci.stock.dto.inventaire.InventaireDetailDtoOut;
import ics.ci.stock.dto.inventaire.InventaireDtoOut;
import ics.ci.stock.dto.inventaire.InventaireEndDto;
import ics.ci.stock.entity.*;
import ics.ci.stock.repository.EntrepotRepository;
import ics.ci.stock.repository.InventaireDetailRepository;
import ics.ci.stock.repository.InventaireRepository;
import ics.ci.stock.repository.ProjetRepository;
import ics.ci.stock.service.InventaireService;
import ics.ci.stock.service.StockService;
import ics.ci.stock.utils.DateConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class InventaireServiceImpl implements InventaireService {

private final InventaireRepository inventaireRepository;
private final InventaireDetailRepository inventaireDetailRepository;
private final ProjetRepository projetRepository;
private final StockService stockService;
private final EntrepotRepository entrepotRepository;


    public InventaireServiceImpl(InventaireRepository inventaireRepository, InventaireDetailRepository inventaireDetailRepository, ProjetRepository projetRepository, StockService stockService, EntrepotRepository entrepotRepository) {
        this.inventaireRepository = inventaireRepository;
        this.inventaireDetailRepository = inventaireDetailRepository;
        this.projetRepository = projetRepository;
        this.stockService = stockService;
        this.entrepotRepository = entrepotRepository;
    }

    @Override
    public InventaireDtoOut inventaireToDto(Inventaire inventaire) {

        InventaireDtoOut dto = new InventaireDtoOut();
        dto.setInventaireId(inventaire.getInventaireId());
        dto.setEntrepot(inventaire.getEntrepot().getEntrepotNom());
        dto.setIntitule(inventaire.getLibelle());
        dto.setDateInventaire(DateConvert.getStringDate(inventaire.getDateInventaire()));
        dto.setDateDebut(DateConvert.getStringDate(inventaire.getDateDebut()));
        dto.setDateFin(DateConvert.getStringDate(inventaire.getDateFin()));
        return dto;
    }

    @Override
    public List<InventaireDtoOut> listInventairesToDto(List<Inventaire> inventaires) {
        List<InventaireDtoOut> dtos = new ArrayList<InventaireDtoOut>();
        for (Inventaire inventaire : inventaires){
       InventaireDtoOut dto =  this.inventaireToDto(inventaire);
       dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<InventaireDtoOut> listInventairesToDto() {
        List<Inventaire> inventaires = inventaireRepository.findAll();
       return  this.listInventairesToDto(inventaires);
    }

    @Override
    public void createInventaire(InventaireEndDto dto, AppUser user) {

        //Add Inventaire
        Inventaire inventaire = new Inventaire();
        inventaire.setLibelle(dto.getInventaire().getIntitule());
        inventaire.setDateDebut(dto.getInventaire().getDateDebut());
        inventaire.setDateFin(dto.getInventaire().getDateFin());
        inventaire.setEntrepot(entrepotRepository.getOne(dto.getInventaire().getEntrepot()));
        inventaire.setDateInventaire(LocalDateTime.now());
        Inventaire i =  inventaireRepository.save(inventaire);


        //Get Inventaire detail List
        List<InventaireDetailDto> detailDtos = dto.getInventaireDetail();

        //Loop on list
         for (InventaireDetailDto detailDto : detailDtos){

             // Init
             Inventairedetail inventairedetail = new Inventairedetail();
             //Get Projet
             Projet projet = projetRepository.getOne(detailDto.getProjet());

             //Get quantity
             int stockInitial = detailDto.getStockInitial();
             int stockFinal = detailDto.getStockFinal();

             //Get Reference
             String reference = this.getReference();

             //Get Entrepot
             Entrepot entrepot = i.getEntrepot();
             LocalDateTime date = LocalDateTime.now();

             //Set Inventaire detail
             inventairedetail.setOperationReference(reference);
             inventairedetail.setOperation_qte(stockFinal);
             inventairedetail.setOperation_date(date);
             inventairedetail.setProjet(projet);
             inventairedetail.setUser(user);
             inventairedetail.setStockPrecedent(stockInitial);
             inventairedetail.setStockInitial(stockInitial);
             inventairedetail.setStockSuivant(stockFinal);
             inventairedetail.setStockFinal(stockFinal);
             inventairedetail.setEntrepot(entrepot);
             inventairedetail.setInventaire(i);
             inventaireDetailRepository.save(inventairedetail);

             //update stock
             Stock stock = stockService.getStockByProjetAndEntrepot(projet, entrepot);
             stock.setStockQuantite(stockFinal);
             stockService.updateStock(stock);

         }
    }

    @Override
    public String getReference() {

        Inventairedetail inventairedetail = inventaireDetailRepository.findTopByOrderByOperationIdDesc();
        LocalDate date = LocalDate.now();
        String d = date.toString().replaceAll("-","");
        String da = d.substring(2, d.length());
        String masque = "INV-";
        int id;
        if (inventairedetail == null)
        {

            id = 1;

        }
        else {
            Long l = inventairedetail.getOperationId();
            id = l.intValue()+1;

        }
        return masque + da + "-" + id;

    }

    @Override
    public List<String> checkIfStockGreaterThanZero(InventaireEndDto dto) {

        Entrepot entrepot = entrepotRepository.getOne(dto.getInventaire().getEntrepot());

        List<InventaireDetailDto> detailDtos = dto.getInventaireDetail();

        List<String> result = new ArrayList<String>();

        for (InventaireDetailDto detailDto : detailDtos){

            Projet projet = projetRepository.getOne(detailDto.getProjet());
            //Stock stock = stockService.getStockByProjetAndEntrepot(projet, entrepot);
            //Boolean check = stockService.checkIfStockIsGreaterThanZero(stock, detailDto.getStockFinal());
            
            if (detailDto.getStockFinal() < 0 ){
                String projetName = projet.getProjetNom();
                result.add(projetName);
            }
        }
        return result;
    }

    @Override
    public int getStockQuantityByProjetAndEntrepot(Long projetId, Long entrepotId) {
        return stockService.getStockByProjetAndEntrepot(projetId, entrepotId).getStockQuantite();
    }

    @Override
    public InventaireDetailDtoOut inventaireDetailToDto(Inventairedetail inventairedetail) {

        InventaireDetailDtoOut dto = new InventaireDetailDtoOut();
        dto.setId(inventairedetail.getProjet().getProjet_id());
        dto.setStockInitial(inventairedetail.getStockPrecedent());
        dto.setStockFinal(inventairedetail.getStockSuivant());
        dto.setDateOperation(DateConvert.getStringDate(inventairedetail.getOperation_date()));
        dto.setProjet(inventairedetail.getProjet().getProjetNom());
        return dto;
    }

    @Override
    public List<InventaireDetailDtoOut> listInventairesDetailToDto(List<Inventairedetail> inventairedetails) {

        List<InventaireDetailDtoOut> dtos = new ArrayList<>();
        for (Inventairedetail inventairedetail : inventairedetails ){
            InventaireDetailDtoOut dto = this.inventaireDetailToDto(inventairedetail);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<InventaireDetailDtoOut> listInventairesDetailToDto() {
        List<Inventairedetail> inventairedetails = inventaireDetailRepository.findAll();
             return this.listInventairesDetailToDto(inventairedetails);
    }

    @Override
    public List<InventaireDetailDtoOut> listInventairesDetailToDtoByInventaireId(Long inventaireId) {
        Inventaire inventaire = inventaireRepository.getOne(inventaireId);
        List<Inventairedetail> inventairedetails = inventaireDetailRepository.findByInventaire(inventaire);
        return this.listInventairesDetailToDto(inventairedetails);
    }

    @Override
    public Inventaire getInventaireById(Long inventaireId) {
        return inventaireRepository.getOne(inventaireId);
    }
}
