package ics.ci.stock.repository;

import ics.ci.stock.entity.HistoriqueValidationTransfert;
import ics.ci.stock.enums.Statut;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoriqueValidationTransfertRepository extends JpaRepository<HistoriqueValidationTransfert, Long> {

    List<HistoriqueValidationTransfert> findByStatut(Statut statut);
}
