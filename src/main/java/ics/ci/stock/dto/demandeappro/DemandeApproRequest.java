package ics.ci.stock.dto.demandeappro;

import java.util.List;

public class DemandeApproRequest {

    private String userPrincipalName;
    private Long entrepotDestination;
    private List<DetailDemandeApproRequest> detailDemandeAppro;

    public DemandeApproRequest() {
        super();
    }

    public Long getEntrepotDestination() {
        return entrepotDestination;
    }

    public void setEntrepotDestination(Long entrepotDestination) {
        this.entrepotDestination = entrepotDestination;
    }

    public List<DetailDemandeApproRequest> getDetailDemandeAppro() {
        return detailDemandeAppro;
    }

    public void setDetailDemandeAppro(List<DetailDemandeApproRequest> detailDemandeAppro) {
        this.detailDemandeAppro = detailDemandeAppro;
    }

    public String getUserPrincipalName() {
        return userPrincipalName;
    }

    public void setUserPrincipalName(String userPrincipalName) {
        this.userPrincipalName = userPrincipalName;
    }
}
