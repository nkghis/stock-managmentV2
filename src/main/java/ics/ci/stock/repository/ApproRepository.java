package ics.ci.stock.repository;

import ics.ci.stock.entity.Appro;
import ics.ci.stock.entity.DetailDemandeAppro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApproRepository extends JpaRepository<Appro, Long> {

    List<Appro> findByDetailDemandeAppro(DetailDemandeAppro detailDemandeAppro);
}
