package ics.ci.stock.repository;

import ics.ci.stock.entity.Entrepot;
import ics.ci.stock.entity.Projet;
import ics.ci.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {

    //Stock findByProduitAndAndProjetAndEntrepot(Produit produit, Projet projet, Entrepot entrepot);
    Stock findByProjetAndEntrepot(Projet projet, Entrepot entrepot);
}
