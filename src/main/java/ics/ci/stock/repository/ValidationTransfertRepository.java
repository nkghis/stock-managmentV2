package ics.ci.stock.repository;

import ics.ci.stock.entity.ValidationTransfert;
import ics.ci.stock.enums.Statut;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ValidationTransfertRepository extends JpaRepository<ValidationTransfert, Long> {

    List<ValidationTransfert> findByStatut(Statut statut);

}
