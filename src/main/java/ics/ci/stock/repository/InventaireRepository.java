package ics.ci.stock.repository;

import ics.ci.stock.entity.Inventaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventaireRepository  extends JpaRepository < Inventaire, Long> {

}
