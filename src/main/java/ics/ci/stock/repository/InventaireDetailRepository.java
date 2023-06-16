package ics.ci.stock.repository;

import ics.ci.stock.entity.Inventaire;
import ics.ci.stock.entity.Inventairedetail;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface InventaireDetailRepository extends OperationBaseRepository<Inventairedetail> {

    List<Inventairedetail> findByInventaire(Inventaire inventaire);
}
