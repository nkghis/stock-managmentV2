package ics.ci.stock.repository;

import ics.ci.stock.entity.Projet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetRepository extends JpaRepository<Projet, Long> {
    public Projet findByProjetNom(String projet);
    //public Projet findByProjetNomAndProjet_id

    //Projet findBySeuilProjetGreaterThan
}
