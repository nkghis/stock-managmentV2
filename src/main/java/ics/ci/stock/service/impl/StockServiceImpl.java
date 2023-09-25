package ics.ci.stock.service.impl;

import ics.ci.stock.dto.projet.ProjetDtoOut;
import ics.ci.stock.entity.Entrepot;
import ics.ci.stock.entity.Projet;
import ics.ci.stock.entity.Stock;
import ics.ci.stock.repository.EntrepotRepository;
import ics.ci.stock.repository.ProjetRepository;
import ics.ci.stock.repository.StockRepository;
import ics.ci.stock.service.StockService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    private final ProjetRepository projetRepository;

    private final EntrepotRepository entrepotRepository;

    public StockServiceImpl(StockRepository stockRepository, ProjetRepository projetRepository, EntrepotRepository entrepotRepository) {
        this.stockRepository = stockRepository;
        this.projetRepository = projetRepository;
        this.entrepotRepository = entrepotRepository;
    }

    @Override
    public Boolean checkIfStockAvailable(Projet projet, Entrepot entrepot, int quantite) {

        Stock stock = stockRepository.findByProjetAndEntrepot(projet, entrepot);

        int  stockQuantite = stock.getStockQuantite();
        boolean result;
        if (stockQuantite >= quantite){
            result = true;
        }else {
            result = false;
        }
        return result;
    }

    @Override
    public Stock getStockByProjetAndEntrepot(Long idProjet, Long idEntrepot) {

        Projet projet = projetRepository.getOne(idProjet);
        Entrepot entrepot = entrepotRepository.getOne(idEntrepot);
        return stockRepository.findByProjetAndEntrepot(projet, entrepot);
    }

    @Override
    public Stock getStockByProjetAndEntrepot(Projet projet, Entrepot entrepot) {
        return stockRepository.findByProjetAndEntrepot(projet, entrepot);
    }

    @Override
    public List<Projet> getListProjetsHaveStockByEntrepot(Long idEntrepot) {

        Entrepot entrepot = entrepotRepository.getOne(idEntrepot);
        List<Stock> stocks = stockRepository.findByEntrepot(entrepot);

        List<Projet> projectList = new ArrayList<>();

        for (Stock stock : stocks){

            Projet projet = stock.getProjet();
            boolean exist = projectList.contains(projet);
            if (!exist){
                projectList.add(projet);
            }
        }
        return projectList;
    }

    /*@Override
    public List<Projet> getListProjetByEntrepot(Long idEntrepot) {
        Entrepot entrepot = entrepotRepository.getOne(idEntrepot);
        List<Stock> stocks = stockRepository.findByEntrepot(entrepot);
        List<Projet> projectList = new ArrayList<>();
        for (Stock stock : stocks){
            Projet projet = stock.getProjet();
            projectList.add(projet);
        }
        return projectList;
    }*/
    @Override
    public List<ProjetDtoOut> getListProjetByEntrepot(Long idEntrepot) {
        Entrepot entrepot = entrepotRepository.getOne(idEntrepot);
        List<Stock> stocks = stockRepository.findByEntrepot(entrepot);
        List<ProjetDtoOut> projectList = new ArrayList<>();
        for (Stock stock : stocks){
            Projet projet = stock.getProjet();
            ProjetDtoOut dto = new ProjetDtoOut();
            dto.setProjetId(projet.getProjet_id());
            dto.setProjetName(projet.getProjetNom());
            projectList.add(dto);
        }
        return projectList;
    }

    @Override
    public Boolean checkIfStockIsGreaterThanZero(Stock stock, int qte) {

        int quantite = stock.getStockQuantite() + qte;

        if (quantite >= 0){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public void updateStock(Stock stock) {

        stockRepository.save(stock);
    }

    @Override
    public Boolean seuilSecuriteDisponible(Stock stock) {

        Projet projet = stock.getProjet();

        if (stock.getStockQuantite() > projet.getSeuilProjet()){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public Boolean seuilSecuriteDisponible(int stock, Projet projet) {
        if (stock > projet.getSeuilProjet()){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Boolean seuilSecuriteDisponible(Projet projet) {

        //List<Stock> stocks = this.getListStockByProjet(projet);
        List<Stock> stocks = this.getListStockByProjetWithoutEntrepot(projet);
        int stock = this.totalStockByProjet(stocks);
        if (stock > projet.getSeuilProjet()){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public int totalStockByProjet(List<Stock> stocks) {

        int quantiteTotal = 0;

        for (Stock stock : stocks){
            quantiteTotal = quantiteTotal + stock.getStockQuantite();
        }
        return quantiteTotal;
    }

/*    @Override
    public int totalStockByProjet(Projet projet) {
        List<Stock> stocks = this.getListStockByProjet(projet);
        int quantiteTotal = 0;
        for (Stock stock : stocks){
            quantiteTotal = quantiteTotal + stock.getStockQuantite();
        }
        return quantiteTotal;
    }
    */

    @Override
    public int totalStockByProjet(Projet projet) {
        List<Stock> stocks = this.getListStockByProjetWithoutEntrepot(projet);
        int quantiteTotal = 0;
        for (Stock stock : stocks){
            quantiteTotal = quantiteTotal + stock.getStockQuantite();
        }
        return quantiteTotal;
    }


    @Override
    public List<Stock> getListStockByProjet(Projet projet) {
        return stockRepository.findByProjet(projet);
    }

    @Override
    public List<Stock> getListStockByProjetWithoutEntrepot(List<Entrepot> entrepots, Projet projet) {
        return stockRepository.findByEntrepotNotInAndProjet(entrepots, projet);
    }

    @Override
    public List<Stock> getListStockByProjetWithoutEntrepot(Projet projet) {

        Entrepot entrepot = entrepotRepository.findByEntrepotNom("CP ICS STOCK");
        List<Entrepot> entrepots = new ArrayList<>();
        entrepots.add(entrepot);
        return stockRepository.findByEntrepotNotInAndProjet(entrepots, projet);
    }


}
