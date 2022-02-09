package ics.ci.stock.repository;

import ics.ci.stock.entity.Entrepot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntrepotRepository extends JpaRepository<Entrepot, Long> {
  Entrepot findByEntrepotNom(String entrepotNom);
}
