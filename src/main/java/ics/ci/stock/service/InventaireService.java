package ics.ci.stock.service;

import ics.ci.stock.dto.inventaire.InventaireDetailDtoOut;
import ics.ci.stock.dto.inventaire.InventaireDtoOut;
import ics.ci.stock.dto.inventaire.InventaireEndDto;
import ics.ci.stock.entity.*;

import java.util.List;

public interface InventaireService {

    InventaireDtoOut inventaireToDto(Inventaire inventaire);
    List<InventaireDtoOut> listInventairesToDto(List<Inventaire> inventaires);
    List<InventaireDtoOut> listInventairesToDto();
    void createInventaire(InventaireEndDto dto, AppUser user);

    String getReference ();
    List<String> checkIfStockGreaterThanZero(InventaireEndDto dto);
    int getStockQuantityByProjetAndEntrepot(Long projetId, Long entrepotId);

    InventaireDetailDtoOut inventaireDetailToDto(Inventairedetail inventairedetail);
    List<InventaireDetailDtoOut> listInventairesDetailToDto(List<Inventairedetail> inventairedetails);
    List<InventaireDetailDtoOut> listInventairesDetailToDto();
    List<InventaireDetailDtoOut> listInventairesDetailToDtoByInventaireId(Long inventaireId);

    Inventaire getInventaireById(Long inventaireId);

}
