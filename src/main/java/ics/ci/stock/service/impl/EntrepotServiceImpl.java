package ics.ci.stock.service.impl;

import ics.ci.stock.dto.entrepot.EntrepotDtoOut;
import ics.ci.stock.entity.Entrepot;
import ics.ci.stock.repository.EntrepotRepository;
import ics.ci.stock.service.EntrepotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EntrepotServiceImpl  implements EntrepotService {

    private final EntrepotRepository entrepotRepository;

    public EntrepotServiceImpl(EntrepotRepository entrepotRepository) {
        this.entrepotRepository = entrepotRepository;
    }

    @Override
    public EntrepotDtoOut entrepotToDtoOut(Entrepot entrepot) {

        EntrepotDtoOut dto = new EntrepotDtoOut();
        dto.setEntrepotId(entrepot.getEntrepotId());
        dto.setEntrepotNom(entrepot.getEntrepotNom());

        return dto;
    }

    @Override
    public List<EntrepotDtoOut> listsEntrepotToDtoOut(List<Entrepot> entrepots) {

        List<EntrepotDtoOut> dtos = new ArrayList<>();

        for (Entrepot entrepot : entrepots){

           EntrepotDtoOut dto =  this.entrepotToDtoOut(entrepot);

            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<EntrepotDtoOut> listEntrepots() {

        List<Entrepot> entrepots = entrepotRepository.findAll();

        List<EntrepotDtoOut> dtos = this.listsEntrepotToDtoOut(entrepots);
        return dtos;
    }
}
