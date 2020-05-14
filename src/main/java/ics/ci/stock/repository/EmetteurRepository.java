package ics.ci.stock.repository;


import ics.ci.stock.entity.Emetteur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmetteurRepository extends JpaRepository <Emetteur, Long> {
}
