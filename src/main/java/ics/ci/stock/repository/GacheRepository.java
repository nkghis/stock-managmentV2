package ics.ci.stock.repository;

import ics.ci.stock.entity.Gache;
import ics.ci.stock.entity.custom.GacheDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GacheRepository extends JpaRepository<Gache, Long> {

/*    @Query("SELECT\n" +
            "dbo.projets.projet_id AS id,\n" +
            "dbo.projets.projet_nom AS projet,\n" +
            "Sum(dbo.gaches.gache_qte) AS qte\n" +
            "\n" +
            "FROM\n" +
            "dbo.gaches\n" +
            "INNER JOIN dbo.operations ON dbo.gaches.operation_id = dbo.operations.operation_id\n" +
            "INNER JOIN dbo.projets ON dbo.operations.projet_id = dbo.projets.projet_id\n" +
            "GROUP BY\n" +
            "dbo.projets.projet_nom,dbo.projets.projet_id\n" +
            "ORDER BY\n" +
            "qte DESC")
    public List<Gache> totalGacheGroupByProject();*/
}
