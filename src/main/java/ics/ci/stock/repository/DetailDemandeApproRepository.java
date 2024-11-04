package ics.ci.stock.repository;

import ics.ci.stock.entity.DemandeAppro;
import ics.ci.stock.entity.DetailDemandeAppro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetailDemandeApproRepository extends JpaRepository<DetailDemandeAppro, Long> {

    List<DetailDemandeAppro> findByDemandeAppro(DemandeAppro demandeAppro);
}
