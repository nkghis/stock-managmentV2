package ics.ci.stock.entity;

//import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.Date;

public class Search {


    private String debut;
    private String fin;

    public Search() {
        super();
    }

    public String getDebut() {
        return debut;
    }

    public void setDebut(String debut) {
        this.debut = debut;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }
}


