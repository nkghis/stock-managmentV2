package ics.ci.stock.dto.inventaire;


public class InventaireDetailDtoOut {
    private Long id;
    private String projet;
    private int stockInitial;
    private int stockFinal;
    private String  dateOperation;

    public InventaireDetailDtoOut() {
        super();
    }

    public InventaireDetailDtoOut(Long id, String projet, int stockInitial, int stockFinal, String dateOperation) {
        this.id = id;
        this.projet = projet;
        this.stockInitial = stockInitial;
        this.stockFinal = stockFinal;
        this.dateOperation = dateOperation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStockInitial() {
        return stockInitial;
    }

    public void setStockInitial(int stockInitial) {
        this.stockInitial = stockInitial;
    }

    public int getStockFinal() {
        return stockFinal;
    }

    public void setStockFinal(int stockFinal) {
        this.stockFinal = stockFinal;
    }

    public String getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(String dateOperation) {
        this.dateOperation = dateOperation;
    }

    public String getProjet() {
        return projet;
    }

    public void setProjet(String projet) {
        this.projet = projet;
    }
}
