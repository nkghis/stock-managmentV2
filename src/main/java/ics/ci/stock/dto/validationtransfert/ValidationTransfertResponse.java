package ics.ci.stock.dto.validationtransfert;

public class ValidationTransfertResponse {
    private Long id;

    private String dateSaisie;
    private String entrepotSource;

    private String entrepotDestination;
    private String projet;

    private int qte;
/*    private int stockInitialSource;
    private int stockInitialDestination;
    private int stockFinalSource;
    private int stockFinalDestination;*/

    private String statut;

    private String userName;

    public ValidationTransfertResponse() {
        super();
    }

    public String getEntrepotDestination() {
        return entrepotDestination;
    }

    public void setEntrepotDestination(String entrepotDestination) {
        this.entrepotDestination = entrepotDestination;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntrepotSource() {
        return entrepotSource;
    }

    public void setEntrepotSource(String entrepotSource) {
        this.entrepotSource = entrepotSource;
    }

    public String getProjet() {
        return projet;
    }

    public void setProjet(String projet) {
        this.projet = projet;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /* public int getStockInitialSource() {
        return stockInitialSource;
    }

    public void setStockInitialSource(int stockInitialSource) {
        this.stockInitialSource = stockInitialSource;
    }

    public int getStockInitialDestination() {
        return stockInitialDestination;
    }

    public void setStockInitialDestination(int stockInitialDestination) {
        this.stockInitialDestination = stockInitialDestination;
    }

    public int getStockFinalSource() {
        return stockFinalSource;
    }

    public void setStockFinalSource(int stockFinalSource) {
        this.stockFinalSource = stockFinalSource;
    }

    public int getStockFinalDestination() {
        return stockFinalDestination;
    }

    public void setStockFinalDestination(int stockFinalDestination) {
        this.stockFinalDestination = stockFinalDestination;
    }*/

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getDateSaisie() {
        return dateSaisie;
    }

    public void setDateSaisie(String dateSaisie) {
        this.dateSaisie = dateSaisie;
    }
}
