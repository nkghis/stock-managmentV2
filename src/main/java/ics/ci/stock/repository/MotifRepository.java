package ics.ci.stock.repository;

import ics.ci.stock.entity.Motif;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotifRepository extends JpaRepository<Motif, Long> {
}
