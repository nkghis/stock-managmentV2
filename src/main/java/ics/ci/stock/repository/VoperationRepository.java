package ics.ci.stock.repository;

import ics.ci.stock.entity.Voperation;
import ics.ci.stock.entity.custom.StockBeforeCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface VoperationRepository extends JpaRepository <Voperation, Long> {

    List<Voperation> findAllByDateBetween(LocalDateTime debut, LocalDateTime fin);
    List<Voperation> findAllByDateBefore(LocalDateTime dateTime);


    @Query( value = "SELECT\n" +
            "dbo.v_operation.operation_id,\n" +
            "dbo.v_operation.client,\n" +
            "dbo.v_operation.projet,\n" +
            "dbo.v_operation.produit,\n" +
            "dbo.v_operation.emetteur,\n" +
            "dbo.v_operation.initial,\n" +
            "dbo.v_operation.quantite,\n" +
            "dbo.v_operation.[date],\n" +
            "dbo.v_operation.operation,\n" +
            "dbo.v_operation.gache,\n" +
            "dbo.v_operation.retour,\n" +
            "dbo.v_operation.final,\n" +
            "dbo.v_operation.entrepot,\n" +
            "dbo.v_operation.projet_id\n" +
            "\n" +
            "FROM\n" +
            "dbo.v_operation\n" +
            "WHERE\n" +
            "dbo.v_operation.[date] < ?1",
            nativeQuery = true)
    List<Voperation> dateBefore(LocalDateTime dateTime);

    @Query("select a from Voperation  a where a.date < :date")
    List<Voperation> dateQuery (@Param("date") LocalDateTime localDateTime);

    //Ajouter en faveur ajustement (ligne ajustement et calcul ajustement)
    @Query(value = "SELECT\n" +
            "dbo.v_operation.projet_id,\n" +
            "dbo.v_operation.projet,\n" +
            "dbo.v_operation.entrepot,\n" +
            "Sum(CASE WHEN v_operation.operation = 'dis' then v_operation.quantite else 0 end) AS entreposage,\n" +
            "Sum(CASE WHEN v_operation.operation = 'enl' then v_operation.quantite else 0 end) AS enlevement,\n" +
            "Sum(CASE WHEN v_operation.operation = 'inv' then v_operation.quantite else 0 end) AS ajustement,\n" +
            "COALESCE(Sum(v_operation.retour),0) AS retour,\n" +
            "COALESCE(Sum(v_operation.gache),0) AS gache,\n" +
            "Sum(CASE WHEN v_operation.operation = 'dis' then v_operation.quantite else 0 end) + COALESCE(Sum(v_operation.retour),0)  - Sum(CASE WHEN v_operation.operation = 'enl' then v_operation.quantite else 0 end) + Sum(CASE WHEN v_operation.operation = 'inv' then v_operation.quantite else 0 end)AS stock\n" +
            "\n" +
            "\n" +
            "FROM\n" +
            "dbo.v_operation\n" +
            "WHERE\n" +
            "dbo.v_operation.[date] < ?1\n" +
            "GROUP BY\n" +
            "dbo.v_operation.projet,\n" +
            "dbo.v_operation.projet_id,\n"+
            "dbo.v_operation.entrepot"
            ,nativeQuery = true)
    //List<StockBeforeCustom> stockBeforeCustom(LocalDateTime dateTime);
    List<IStockBeforeCustom> stockBeforeCustom(LocalDateTime dateTime);

    //Ajouter en faveur ajustement (ligne ajustement et calcul ajustement)

    @Query(value = "SELECT\n" +
            "dbo.v_operation.projet_id,\n" +
            "dbo.v_operation.projet,\n" +
            "dbo.v_operation.client,\n" +
            "dbo.v_operation.produit,\n" +
            "dbo.v_operation.emetteur,\n" +
            "dbo.v_operation.entrepot,\n" +
            "Sum(CASE WHEN v_operation.operation = 'dis' then v_operation.quantite else 0 end) AS entreposage,\n" +
            "Sum(CASE WHEN v_operation.operation = 'enl' then v_operation.quantite else 0 end) AS enlevement,\n" +
            "Sum(CASE WHEN v_operation.operation = 'inv' then v_operation.quantite else 0 end) AS ajustement,\n" +
            "COALESCE(Sum(v_operation.retour),0) AS retour,\n" +
            "COALESCE(Sum(v_operation.gache),0) AS gache,\n" +
            "Sum(CASE WHEN v_operation.operation = 'dis' then v_operation.quantite else 0 end) + COALESCE(Sum(v_operation.retour),0)  - Sum(CASE WHEN v_operation.operation = 'enl' then v_operation.quantite else 0 end) + Sum(CASE WHEN v_operation.operation = 'inv' then v_operation.quantite else 0 end) AS stock\n" +
            "\n" +
            "\n" +
            "FROM\n" +
            "dbo.v_operation\n" +
            "WHERE\n" +
            "dbo.v_operation.[date] BETWEEN ?1 AND ?2\n" +
            "GROUP BY\n" +
            "dbo.v_operation.projet_id,\n" +
            "dbo.v_operation.projet,\n" +
            "dbo.v_operation.client,\n" +
            "dbo.v_operation.produit,\n" +
            "dbo.v_operation.emetteur,\n"+
            "dbo.v_operation.entrepot\n"
            ,nativeQuery = true)
    List<IStockBetweenCustom> stockBetween(LocalDateTime start, LocalDateTime end);

}
