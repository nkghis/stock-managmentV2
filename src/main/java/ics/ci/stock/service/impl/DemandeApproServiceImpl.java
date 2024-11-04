package ics.ci.stock.service.impl;

import ics.ci.stock.dto.demandeappro.DemandeApproRequest;
import ics.ci.stock.dto.demandeappro.DemandeApproResponse;
import ics.ci.stock.dto.demandeappro.DetailDemandeApproRequest;
import ics.ci.stock.dto.demandeappro.DetailDemandeApproResponse;
import ics.ci.stock.entity.DemandeAppro;
import ics.ci.stock.entity.DetailDemandeAppro;
import ics.ci.stock.entity.Entrepot;
import ics.ci.stock.enums.Statut;
import ics.ci.stock.repository.DemandeApproRepository;
import ics.ci.stock.repository.DetailDemandeApproRepository;
import ics.ci.stock.repository.EntrepotRepository;
import ics.ci.stock.repository.ProjetRepository;
import ics.ci.stock.service.DemandeApproService;
import ics.ci.stock.service.UserService;
import ics.ci.stock.utils.DateConvert;
import ics.ci.stock.utils.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DemandeApproServiceImpl implements DemandeApproService {


    private final DemandeApproRepository demandeApproRepository;
    private final DetailDemandeApproRepository detailDemandeApproRepository;

    private final UserService userService;

    private final EntrepotRepository entrepotRepository;

    private final ProjetRepository projetRepository;


    public DemandeApproServiceImpl(DemandeApproRepository demandeApproRepository, DetailDemandeApproRepository detailDemandeApproRepository, UserService userService, EntrepotRepository entrepotRepository, ProjetRepository projetRepository) {
        this.demandeApproRepository = demandeApproRepository;
        this.detailDemandeApproRepository = detailDemandeApproRepository;
        this.userService = userService;
        this.entrepotRepository = entrepotRepository;
        this.projetRepository = projetRepository;
    }

    @Override
    public List<DemandeApproResponse> all() {
        return this.TODTO(demandeApproRepository.findAll());
    }

    @Override
    public List<DemandeApproResponse> TODTO(List<DemandeAppro> demandeAppros) {
        List<DemandeApproResponse> responses = new ArrayList<>();
        for (DemandeAppro demandeAppro : demandeAppros){
            responses.add(this.toDTO(demandeAppro));
        }
        return responses;
    }

    @Override
    public DemandeApproResponse toDTO(DemandeAppro demandeAppro) {

        //get all detailDemande by DemandeAppro
        List<DetailDemandeAppro> details = detailDemandeApproRepository.findByDemandeAppro(demandeAppro);

        //convert all detailDemande to all detailDemande Response
        List<DetailDemandeApproResponse> detailResponse = this.TODTOS(details);

        //Set Detail response
        DemandeApproResponse response = new DemandeApproResponse();
        response.setDate(DateConvert.getStringDate(demandeAppro.getDate()));
        response.setStatut(demandeAppro.getStatut().name());
        response.setReference(demandeAppro.getReference());
        response.setEntrepotSource(demandeAppro.getEntrepotDestination().getEntrepotNom());
        response.setUtilisateur(demandeAppro.getUser().toNomComplet());
        response.setDetails(detailResponse);
        return response;
    }

    @Override
    public void createDemandeAppro(DemandeApproRequest request) {

        DemandeAppro demandeAppro = new DemandeAppro();
        demandeAppro.setDate(LocalDateTime.now());
        demandeAppro.setUser(userService.getUserByPrincipalName(request.getUserPrincipalName()));
        demandeAppro.setReference(Reference.getReferenceDemandeAppro());
        demandeAppro.setEntrepotDestination(entrepotRepository.getOne(request.getEntrepotDestination()));
        demandeAppro.setStatut(Statut.EN_COURS);
        demandeApproRepository.save(demandeAppro);

        for (DetailDemandeApproRequest detail :request.getDetailDemandeAppro() ){
            DetailDemandeAppro d = new DetailDemandeAppro();
            d.setDemandeAppro(demandeAppro);
            d.setQte(detail.getQte());
            d.setProjet(projetRepository.getOne(detail.getProjet()));
            detailDemandeApproRepository.save(d);
        }

    }

    @Override
    public DemandeAppro byId(Long id) {
        return demandeApproRepository.getOne(id);
    }

    @Override
    public List<DemandeApproResponse> findByStatut(Statut statut) {
        return this.TODTO(demandeApproRepository.findByStatut(statut));
    }

    @Override
    public List<DetailDemandeApproResponse> allDetails(DemandeAppro demandeAppro) {
        List <DetailDemandeAppro> details = detailDemandeApproRepository.findByDemandeAppro(demandeAppro);
        return this.TODTOS(details);
    }

    @Override
    public DetailDemandeApproResponse toDTOS(DetailDemandeAppro details) {
        DetailDemandeApproResponse response = new DetailDemandeApproResponse();
        response.setQte(details.getQte());
        response.setProjet(details.getProjet().getProjetNom());
        return response;
    }

    @Override
    public List<DetailDemandeApproResponse> TODTOS(List<DetailDemandeAppro> details) {

        List<DetailDemandeApproResponse> responses = new ArrayList<>();
        for (DetailDemandeAppro detail : details){
            responses.add(this.toDTOS(detail));
        }
        return responses;
    }

    @Override
    public DetailDemandeAppro byIds(Long id) {
        return detailDemandeApproRepository.getOne(id);
    }

    @Override
    public List<DetailDemandeAppro> findAllDetailDemandeByDemandeAppro(DemandeAppro demandeAppro) {
        return detailDemandeApproRepository.findByDemandeAppro(demandeAppro);
    }
}
