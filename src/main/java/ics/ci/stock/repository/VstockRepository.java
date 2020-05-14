package ics.ci.stock.repository;

import ics.ci.stock.entity.Vstock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VstockRepository extends JpaRepository<Vstock, Long> {
}
