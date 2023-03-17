package ics.ci.stock.repository;

import ics.ci.stock.entity.Destruction;

import java.util.List;

public interface DestructionRepository extends OperationBaseRepository<Destruction> {

    @Override
    List<Destruction> findAll();
}
