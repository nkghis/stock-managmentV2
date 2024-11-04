package ics.ci.stock.service;

import ics.ci.stock.dto.appro.ApproRequest;
import ics.ci.stock.entity.AppUser;
import ics.ci.stock.entity.Appro;
import ics.ci.stock.entity.DemandeAppro;
import ics.ci.stock.entity.DetailDemandeAppro;

import java.util.List;

public interface ApproService {

    void createAppro(ApproRequest request, AppUser user);

    void createAppros(List<ApproRequest> requests, AppUser user);

    List<Appro> findByDetailDemandeAppro(DetailDemandeAppro detailDemandeAppro);

    List<Appro> findAllApproByDemandeAppro(DemandeAppro demandeAppro);
}
