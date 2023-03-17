package ics.ci.stock.repository;

import ics.ci.stock.entity.Vgachemouvement;
import ics.ci.stock.entity.custom.GacheDto;
import ics.ci.stock.entity.custom.GacheProjetDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface VgacheMouvementRepository extends JpaRepository<Vgachemouvement, Long> {


    @Query(value = "SELECT\n" +
            "dbo.gaches.gache_id AS id,\n" +
            "dbo.projets.projet_nom AS projet,\n" +
            "dbo.typegaches.typegache_nom AS typedegache,\n" +
            "dbo.gaches.gache_qte AS qte,\n" +
            "dbo.gaches.gache_date AS dategache,\n" +
            "dbo.gaches.operation_date_saisie AS datesaisie\n" +
            "\n" +
            "FROM\n" +
            "dbo.gaches\n" +
            "INNER JOIN dbo.operations ON dbo.gaches.operation_id = dbo.operations.operation_id\n" +
            "INNER JOIN dbo.projets ON dbo.operations.projet_id = dbo.projets.projet_id\n" +
            "INNER JOIN dbo.typegaches ON dbo.gaches.typegache_id = dbo.typegaches.typegache_id\n" +
            "WHERE\n" +
            "dbo.gaches.operation_date_saisie BETWEEN ?1 AND ?2 \n", nativeQuery = true)
    List<GacheDto> gachesBetween(Date start, Date end);



    @Query(value = "SELECT\n" +
            "dbo.projets.projet_id AS id,\n" +
            "dbo.projets.projet_nom AS projet,\n" +
            "Sum(dbo.gaches.gache_qte) AS qte\n" +
            "\n" +
            "FROM\n" +
            "dbo.gaches\n" +
            "INNER JOIN dbo.operations ON dbo.gaches.operation_id = dbo.operations.operation_id\n" +
            "INNER JOIN dbo.projets ON dbo.operations.projet_id = dbo.projets.projet_id\n" +
            "WHERE\n" +
            "dbo.gaches.operation_date_saisie BETWEEN ?1 AND ?2 \n" +
            "GROUP BY\n" +
            "dbo.projets.projet_nom,\n" +
            "dbo.projets.projet_id\n" +
            "ORDER BY\n" +
            "qte DESC", nativeQuery = true)
    List<GacheProjetDto> gachesProjetBetween(Date start, Date end);

}
