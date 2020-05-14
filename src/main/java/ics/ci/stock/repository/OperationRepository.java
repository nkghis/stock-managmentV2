package ics.ci.stock.repository;

//import org.springframework.transaction.annotation.Transactional;
import ics.ci.stock.entity.Operation;

import javax.transaction.Transactional;

//@NoRepositoryBean
@Transactional
public interface OperationRepository extends OperationBaseRepository<Operation> {

}
