package ics.ci.stock.service.impl;

import ics.ci.stock.dto.appro.ApproRequest;
import ics.ci.stock.entity.*;
import ics.ci.stock.enums.Etat;
import ics.ci.stock.repository.ApproRepository;
import ics.ci.stock.repository.DetailDemandeApproRepository;
import ics.ci.stock.repository.EntreposerRepository;
import ics.ci.stock.repository.EntrepotRepository;
import ics.ci.stock.service.ApproService;
import ics.ci.stock.service.DemandeApproService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ApproServiceImpl implements ApproService {

    private final ApproRepository approRepository;

    private final EntreposerRepository entreposerRepository;

    private final DetailDemandeApproRepository detailDemandeApproRepository;
    private final DemandeApproService demandeApproService;

    public ApproServiceImpl(ApproRepository approRepository, EntreposerRepository entreposerRepository, DetailDemandeApproRepository detailDemandeApproRepository, DemandeApproService demandeApproService) {
        this.approRepository = approRepository;
        this.entreposerRepository = entreposerRepository;
        this.detailDemandeApproRepository = detailDemandeApproRepository;
        this.demandeApproService = demandeApproService;
    }


    @Override
    public void createAppro(ApproRequest request, AppUser user) {

        Entreposer entreposer   = entreposerRepository.getOne(request.getEntreposer());
        DetailDemandeAppro detailDemandeAppro = detailDemandeApproRepository.getOne(request.getDemandeApproDetail());
        Appro appro = new Appro();
        appro.setQte(request.getQte());
        appro.setEntreposer(entreposer);
        appro.setDetailDemandeAppro(detailDemandeAppro);
        appro.setDate(LocalDateTime.now());
        appro.setEtat(Etat.AFFECTER);
        appro.setUser(user);
        approRepository.save(appro);
    }

    @Override
    public void createAppros(List<ApproRequest> requests, AppUser user) {

        for (ApproRequest approRequest : requests){
            this.createAppro(approRequest, user);
        }
    }

    @Override
    public List<Appro> findByDetailDemandeAppro(DetailDemandeAppro detailDemandeAppro) {
        return approRepository.findByDetailDemandeAppro(detailDemandeAppro);
    }

    @Override
    public List<Appro> findAllApproByDemandeAppro(DemandeAppro demandeAppro) {

        List<DetailDemandeAppro> details = demandeApproService.findAllDetailDemandeByDemandeAppro(demandeAppro);
        List<Appro> appros = new ArrayList<>();

        for (DetailDemandeAppro detail : details){

            List<Appro> apps = this.findByDetailDemandeAppro(detail);
            appros.addAll(apps);
        }

        return appros;
    }
}
