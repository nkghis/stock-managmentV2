package ics.ci.stock.service;

import ics.ci.stock.dto.historiquevalidation.HistoriqueValidationRequest;
import ics.ci.stock.dto.historiquevalidation.HistoriqueValidationResponse;
import ics.ci.stock.entity.AppUser;
import ics.ci.stock.entity.HistoriqueValidationTransfert;
import ics.ci.stock.entity.ValidationTransfert;
import ics.ci.stock.enums.Statut;

import java.util.List;

public interface HistoriqueValidationService {

    HistoriqueValidationResponse toDTO(HistoriqueValidationTransfert historiqueValidationTransfert);

    List<HistoriqueValidationResponse> TODTO(List<HistoriqueValidationTransfert> historiqueValidationTransferts);

    List<HistoriqueValidationResponse> all();

    List<HistoriqueValidationTransfert> findByStatut(Statut statut);


    HistoriqueValidationResponse createHistoriqueValidation(HistoriqueValidationRequest historiqueValidationRequest);
}
