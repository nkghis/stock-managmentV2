package ics.ci.stock.service;

import ics.ci.stock.dto.projet.ProjetDtoOut;
import ics.ci.stock.entity.Entrepot;
import ics.ci.stock.entity.Projet;
import ics.ci.stock.entity.Stock;

import java.util.List;

public interface StockService {

    Boolean checkIfStockAvailable(Projet projet, Entrepot entrepot, int quantite);
    Stock getStockByProjetAndEntrepot(Long idProjet, Long idEntrepot);
    Stock getStockByProjetAndEntrepot(Projet projet, Entrepot entrepot);
    List<Projet> getListProjetsHaveStockByEntrepot(Long idEntrepot);
    //List<Projet> getListProjetByEntrepot(Long idEntrepot);
    List<ProjetDtoOut> getListProjetByEntrepot(Long idEntrepot);
    Boolean checkIfStockIsGreaterThanZero(Stock stock, int qte);

    void updateStock(Stock stock);

    Boolean seuilSecuriteDisponible(Stock stock);

}
