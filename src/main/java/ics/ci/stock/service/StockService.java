package ics.ci.stock.service;

import ics.ci.stock.entity.Entrepot;
import ics.ci.stock.entity.Projet;

public interface StockService {

    Boolean checkIfStockAvailable(Projet projet, Entrepot entrepot, int quantite);
}
