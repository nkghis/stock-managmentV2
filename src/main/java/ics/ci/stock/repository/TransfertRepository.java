package ics.ci.stock.repository;


import ics.ci.stock.entity.Transfert;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Transactional
public interface TransfertRepository extends OperationBaseRepository<Transfert> {


}
