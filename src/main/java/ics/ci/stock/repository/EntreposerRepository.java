package ics.ci.stock.repository;


import ics.ci.stock.entity.Entreposer;
import ics.ci.stock.entity.Entrepot;
import ics.ci.stock.entity.Projet;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface EntreposerRepository extends OperationBaseRepository<Entreposer> {

    //public Entreposer findTopByOperationIdDesc();
    public List<Entreposer> findByProjetAndEntrepot(Projet projet, Entrepot entrepot);
    //public List<Entreposer> findByProjetAndEntrepotAAndTransfertDispoGreaterThan(Projet projet, Entrepot entrepot, String RollNumber);
}
