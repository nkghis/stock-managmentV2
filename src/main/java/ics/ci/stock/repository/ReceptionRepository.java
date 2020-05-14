package ics.ci.stock.repository;

//import org.springframework.transaction.annotation.Transactional;
import ics.ci.stock.entity.Reception;

        import javax.transaction.Transactional;

@Transactional
public interface ReceptionRepository extends OperationBaseRepository<Reception> {
    //

}
