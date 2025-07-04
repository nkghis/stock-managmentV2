package ics.ci.stock.entity.custom;

import java.util.Objects;

public class StockBetween {

    private String projet;
    private String client;
    private String produit;
    private String emetteur;
    private String entrepot;
    private int stockInitial;
    private int entreposage;
    private int enlevement;
    private int retour;
    private int gache;
    private int stockFinal;
    private int livraison;
    private int ajustement;


    public StockBetween() {
        super();
    }

    public StockBetween(String projet, String client, String produit, String emetteur, String entrepot,int stockInitial, int entreposage, int enlevement, int retour, int gache, int stockFinal, int ajustement) {
        this.projet = projet;
        this.client = client;
        this.produit = produit;
        this.emetteur = emetteur;
        this.entrepot = entrepot;
        this.stockInitial = stockInitial;
        this.entreposage = entreposage;
        this.enlevement = enlevement;
        this.retour = retour;
        this.gache = gache;
        this.stockFinal = stockFinal;
        this.ajustement = ajustement;
    }

    public String getProjet() {
        return projet;
    }

    public void setProjet(String projet) {
        this.projet = projet;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

    public String getEmetteur() {
        return emetteur;
    }

    public void setEmetteur(String emetteur) {
        this.emetteur = emetteur;
    }

    public int getStockInitial() {
        return stockInitial;
    }

    public void setStockInitial(int stockInitial) {
        this.stockInitial = stockInitial;
    }

    public int getEntreposage() {
        return entreposage;
    }

    public void setEntreposage(int entreposage) {
        this.entreposage = entreposage;
    }

    public int getEnlevement() {
        return enlevement;
    }

    public void setEnlevement(int enlevement) {
        this.enlevement = enlevement;
    }

    public int getRetour() {
        return retour;
    }

    public void setRetour(int retour) {
        this.retour = retour;
    }

    public int getGache() {
        return gache;
    }

    public void setGache(int gache) {
        this.gache = gache;
    }

    public int getStockFinal() {
        return stockFinal;
    }

    public void setStockFinal(int stockFinal) {
        this.stockFinal = stockFinal;
    }

    public int getLivraison() {
        return livraison;
    }

    public void setLivraison(int livraison) {
        this.livraison = livraison;
    }

    public String getEntrepot() {
        return entrepot;
    }

    public void setEntrepot(String entrepot) {
        this.entrepot = entrepot;
    }

    public int getAjustement() {
        return ajustement;
    }

    public void setAjustement(int ajustement) {
        this.ajustement = ajustement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockBetween that = (StockBetween) o;
        return stockInitial == that.stockInitial &&
                entreposage == that.entreposage &&
                enlevement == that.enlevement &&
                retour == that.retour &&
                gache == that.gache &&
                livraison == that.livraison&&
                stockFinal == that.stockFinal &&
                Objects.equals(projet, that.projet) &&
                Objects.equals(client, that.client) &&
                Objects.equals(produit, that.produit) &&
                Objects.equals(emetteur, that.emetteur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projet, client, produit, emetteur, stockInitial, entreposage, enlevement, retour, gache, stockFinal, livraison);
    }
}
