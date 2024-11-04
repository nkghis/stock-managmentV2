package ics.ci.stock.repository;

import ics.ci.stock.entity.DemandeAppro;
import ics.ci.stock.enums.Statut;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeApproRepository extends JpaRepository<DemandeAppro, Long> {

    List<DemandeAppro> findByStatut(Statut statut);
}
