package ics.ci.stock.dto.inventaire;

import java.util.List;

public class InventaireEndDto {

    private InventaireNewDtos inventaire;

    private List<InventaireDetailDto> inventaireDetail;


    public InventaireEndDto() {
        super();
    }

    public InventaireEndDto(InventaireNewDtos inventaire, List<InventaireDetailDto> inventaireDetail) {
        this.inventaire = inventaire;
        this.inventaireDetail = inventaireDetail;
    }

    public InventaireNewDtos getInventaire() {
        return inventaire;
    }

    public void setInventaire(InventaireNewDtos inventaire) {
        this.inventaire = inventaire;
    }

    public List<InventaireDetailDto> getInventaireDetail() {
        return inventaireDetail;
    }

    public void setInventaireDetail(List<InventaireDetailDto> inventaireDetail) {
        this.inventaireDetail = inventaireDetail;
    }
}
