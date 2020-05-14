package ics.ci.stock.repository;

import ics.ci.stock.entity.Vmouvement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface VmouvementRepository extends JpaRepository<Vmouvement, Long> {
    List<Vmouvement> findAllByDateBetween(LocalDateTime debut, LocalDateTime fin);
}
