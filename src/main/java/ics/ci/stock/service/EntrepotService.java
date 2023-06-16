package ics.ci.stock.service;

import ics.ci.stock.dto.entrepot.EntrepotDtoOut;
import ics.ci.stock.entity.Entrepot;

import java.util.List;

public interface EntrepotService {

    EntrepotDtoOut entrepotToDtoOut(Entrepot entrepot);

    List<EntrepotDtoOut> listsEntrepotToDtoOut(List<Entrepot> entrepots);

    List<EntrepotDtoOut> listEntrepots();
}
