package ics.ci.stock.repository;

import ics.ci.stock.entity.Conditionnement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConditionnementRepository extends JpaRepository<Conditionnement, Long> {
}
