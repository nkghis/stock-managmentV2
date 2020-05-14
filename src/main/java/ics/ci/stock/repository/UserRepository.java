package ics.ci.stock.repository;

import ics.ci.stock.entity.AppUser;
import ics.ci.stock.entity.Reception;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUserName(String userName);

    Boolean existsByUserName(String userName);

    @Query(value = "SELECT TOP 1\n" +
            "dbo.operations.typeoperation,\n" +
            "dbo.operations.operation_id,\n" +
            "dbo.operations.dispo_operation,\n" +
            "dbo.operations.operation_date,\n" +
            "dbo.operations.operation_qte,\n" +
            "dbo.operations.produit_id,\n" +
            "dbo.operations.projet_id,\n" +
            "dbo.operations.appuser_id,\n" +
            "dbo.operations.fournisseur_id,\n" +
            "dbo.operations.ressource_id\n" +
            "\n" +
            "FROM\n" +
            "dbo.operations\n" +
            "WHERE\n" +
            "dbo.operations.typeoperation = 'rec'\n" +
            "ORDER BY\n" +
            "dbo.operations.operation_id DESC\n",  nativeQuery = true)

  /*  @Query(value = )*/
    Reception operation();
}
