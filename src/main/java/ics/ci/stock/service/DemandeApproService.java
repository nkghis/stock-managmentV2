package ics.ci.stock.service;

import ics.ci.stock.dto.demandeappro.DemandeApproRequest;
import ics.ci.stock.dto.demandeappro.DemandeApproResponse;
import ics.ci.stock.dto.demandeappro.DetailDemandeApproResponse;
import ics.ci.stock.entity.DemandeAppro;
import ics.ci.stock.entity.DetailDemandeAppro;
import ics.ci.stock.enums.Statut;

import java.util.List;

public interface DemandeApproService {

    List<DemandeApproResponse> all();
    List<DemandeApproResponse> TODTO(List<DemandeAppro> demandeAppros);
    DemandeApproResponse toDTO (DemandeAppro demandeAppro);

    void createDemandeAppro(DemandeApproRequest request);

    DemandeAppro byId (Long id);

    List<DemandeApproResponse> findByStatut(Statut statut);


    List<DetailDemandeApproResponse> allDetails(DemandeAppro appro);

    DetailDemandeApproResponse toDTOS(DetailDemandeAppro details);
    List<DetailDemandeApproResponse> TODTOS (List<DetailDemandeAppro> details);

    DetailDemandeAppro byIds(Long id);

    List<DetailDemandeAppro> findAllDetailDemandeByDemandeAppro(DemandeAppro demandeAppro);
}
