package ics.ci.stock.service.impl;

import ics.ci.stock.entity.Entrepot;
import ics.ci.stock.entity.Projet;
import ics.ci.stock.entity.Stock;
import ics.ci.stock.repository.StockRepository;
import ics.ci.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public Boolean checkIfStockAvailable(Projet projet, Entrepot entrepot, int quantite) {

        Stock stock = stockRepository.findByProjetAndEntrepot(projet, entrepot);

        int  stockQuantite = stock.getStockQuantite();
        boolean result;
        if (stockQuantite >= quantite){
            result = true;
        }else {
            result = false;
        }
        return result;
    }
}
