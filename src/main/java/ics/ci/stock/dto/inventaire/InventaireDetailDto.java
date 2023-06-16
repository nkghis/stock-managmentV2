package ics.ci.stock.dto.inventaire;

public class InventaireDetailDto {

    private Long projet;
    private int stockInitial;
    private int stockFinal;


    public InventaireDetailDto() {
        super();
    }

    public InventaireDetailDto(Long projet, int stockInitial, int stockFinal) {
        this.projet = projet;
        this.stockInitial = stockInitial;
        this.stockFinal = stockFinal;
    }

    public Long getProjet() {
        return projet;
    }

    public void setProjet(Long projet) {
        this.projet = projet;
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
}
