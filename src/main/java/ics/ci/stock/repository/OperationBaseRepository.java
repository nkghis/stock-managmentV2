package ics.ci.stock.repository;

import ics.ci.stock.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface OperationBaseRepository<T extends Operation> extends JpaRepository<T, Long> {


    public T findByOperationReference(String ref);
    public T findTopByOrderByOperationIdDesc();
    public List<Reception> findByEstDisponibleTrue();
    //Iterable<T> findAllByTypeoperation()
    //public List<T> findByProjetAndEntrepot(Projet projet, Entrepot entrepot);
    //public List<Entreposer> findByProjetAndEntrepot(Projet projet, Entrepot entrepot);
    //public T findOne(Long id);


    /*@Query("select u from #{#entityName} as u where u.projet_id = ?1 and  u.entrepot_id = ?2")
    List<Entreposer> findTranfert (Long projetId, Long entrepotId);*/

/*    @Query("select u from operations u where u.projet_id = ?1 and u.entrepot_id = ?2 and u.typeoperation= ?3")
    List<Entreposer> findTranfert (Long projetId, Long entrepotId, String typeOperation);*/

    //public T find
    //T findAllByDispo_operation(int i);
}
