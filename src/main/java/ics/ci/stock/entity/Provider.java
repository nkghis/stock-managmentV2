package ics.ci.stock.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "providers")
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long providerId;

    @NotEmpty
    @Column(name = "provider_nom", unique = true)
    private String providerNom;


    public Provider() {
        super();
    }

    public Provider(Long providerId, String providerNom) {
        this.providerId = providerId;
        this.providerNom = providerNom;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public String getProviderNom() {
        return providerNom;
    }

    public void setProviderNom(String providerNom) {
        this.providerNom = providerNom;
    }
}
