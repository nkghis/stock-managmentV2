package ics.ci.stock.repository;


import ics.ci.stock.entity.Entreposer;

import javax.transaction.Transactional;

@Transactional
public interface EntreposerRepository extends OperationBaseRepository<Entreposer> {

    //public Entreposer findTopByOperationIdDesc();
}
