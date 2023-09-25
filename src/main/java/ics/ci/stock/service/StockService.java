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
    Boolean seuilSecuriteDisponible(int stock, Projet projet);
    Boolean seuilSecuriteDisponible(Projet projet);
    int totalStockByProjet(List<Stock> stocks);
    int totalStockByProjet(Projet projet);
    List<Stock> getListStockByProjet(Projet projet);


    List<Stock> getListStockByProjetWithoutEntrepot(List<Entrepot> entrepots, Projet projet);

    List<Stock> getListStockByProjetWithoutEntrepot(Projet projet);
}
