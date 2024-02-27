package ics.ci.stock.service.impl;

import ics.ci.stock.dto.historiquevalidation.HistoriqueValidationRequest;
import ics.ci.stock.dto.historiquevalidation.HistoriqueValidationResponse;
import ics.ci.stock.entity.AppUser;
import ics.ci.stock.entity.HistoriqueValidationTransfert;
import ics.ci.stock.entity.ValidationTransfert;
import ics.ci.stock.enums.Statut;
import ics.ci.stock.repository.HistoriqueValidationTransfertRepository;
import ics.ci.stock.service.HistoriqueValidationService;
import ics.ci.stock.utils.DateConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class HistoriqueValidationServiceImpl implements HistoriqueValidationService {

    private final HistoriqueValidationTransfertRepository repository;

    public HistoriqueValidationServiceImpl(HistoriqueValidationTransfertRepository repository) {
        this.repository = repository;
    }


    @Override
    public HistoriqueValidationResponse toDTO(HistoriqueValidationTransfert historiqueValidationTransfert) {

        HistoriqueValidationResponse h = new HistoriqueValidationResponse();
        h.setReference(historiqueValidationTransfert.getValidationTransfert().getReference());
        h.setId(historiqueValidationTransfert.getId());
        h.setCommentaires(historiqueValidationTransfert.getCommentaires());
        h.setDate(DateConvert.getStringDate(historiqueValidationTransfert.getDate()));
        h.setUser(historiqueValidationTransfert.getUser().getUserName());
        h.setStatut(historiqueValidationTransfert.getStatut().name());
        h.setEntrepotSource(historiqueValidationTransfert.getValidationTransfert().getEntrepotSource().getEntrepotNom());
        h.setEntrepotDestination(historiqueValidationTransfert.getValidationTransfert().getEntrepotDestination().getEntrepotNom());
        h.setProjet(historiqueValidationTransfert.getValidationTransfert().getProjet().getProjetNom());
        h.setQte(historiqueValidationTransfert.getValidationTransfert().getTransfertQte());
        return h;
    }

    @Override
    public List<HistoriqueValidationResponse> TODTO(List<HistoriqueValidationTransfert> historiqueValidationTransferts) {

        List<HistoriqueValidationResponse> DTO = new ArrayList<>();
        for (HistoriqueValidationTransfert historiqueValidationTransfert : historiqueValidationTransferts){
            HistoriqueValidationResponse dto = this.toDTO(historiqueValidationTransfert);
            DTO.add(dto);
        }
        return DTO;
    }

    @Override
    public List<HistoriqueValidationResponse> all() {
        return this.TODTO(repository.findAll());
    }

    @Override
    public List<HistoriqueValidationTransfert> findByStatut(Statut statut) {
        return repository.findByStatut(statut);
    }

    @Override
    public HistoriqueValidationResponse createHistoriqueValidation(HistoriqueValidationRequest historiqueValidationRequest) {

        HistoriqueValidationTransfert h = new HistoriqueValidationTransfert();
        h.setCommentaires(historiqueValidationRequest.getCommentaires());
        h.setDate(LocalDateTime.now());
        h.setValidationTransfert(historiqueValidationRequest.getValidationTransfert());
        h.setStatut(historiqueValidationRequest.getStatut());
        h.setUser(historiqueValidationRequest.getUser());
        return this.toDTO(repository.save(h));
    }
}

