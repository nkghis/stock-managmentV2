package ics.ci.stock.service;

import ics.ci.stock.dto.validationtransfert.ValidationTransfertResponse;
import ics.ci.stock.entity.AppUser;
import ics.ci.stock.entity.Transfert;
import ics.ci.stock.entity.ValidationTransfert;
import ics.ci.stock.enums.Statut;

import java.util.List;

public interface ValidationTransfertService {

    List<ValidationTransfertResponse> all();
    List<ValidationTransfertResponse> allByStatut(Statut statut);
    ValidationTransfertResponse toDTO(ValidationTransfert validationTransfert);
    List<ValidationTransfertResponse> TODTO (List<ValidationTransfert> validationTransferts);
    ValidationTransfert createValidationTransfert(Transfert transfert, AppUser user);

    ValidationTransfert transfertToValidation(Transfert transfert);

    ValidationTransfert byId(Long id);

    void updateStatutValidationTransfert(ValidationTransfert validationTransfert, Statut statut);

    List<ValidationTransfert> findByStatut(Statut statut);

    boolean isPresent(Long id);

    String getReference();
}
