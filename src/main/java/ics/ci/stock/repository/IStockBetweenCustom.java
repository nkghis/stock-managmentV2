package ics.ci.stock.repository;

public interface IStockBetweenCustom {

    Long getProjet_id();
    String getProjet();
    String getClient();
    String getProduit();
    String getEmetteur();
    int getEntreposage();
    int getEnlevement();
    int getRetour();
    int getGache();
    int getStock();
}
