package ics.ci.stock.repository;

import ics.ci.stock.entity.Entrepot;
import ics.ci.stock.entity.Projet;
import ics.ci.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {

    //Stock findByProduitAndAndProjetAndEntrepot(Produit produit, Projet projet, Entrepot entrepot);
    Stock findByProjetAndEntrepot(Projet projet, Entrepot entrepot);
    List<Stock> findByEntrepot(Entrepot entrepot);
    List<Stock> findByProjet(Projet projet);
    List<Stock> findByEntrepotNotInAndProjet(List<Entrepot> entrepots, Projet projet);
}
