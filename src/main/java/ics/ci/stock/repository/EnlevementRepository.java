package ics.ci.stock.repository;

import ics.ci.stock.entity.Enlevement;

import javax.transaction.Transactional;
//import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface EnlevementRepository extends OperationBaseRepository<Enlevement> {

    //public Enlevement findTopByOrderByOperationIdDesc();
}
