package ics.ci.stock.service;

import ics.ci.stock.dto.gache.GacheProjetDTO;
import ics.ci.stock.entity.Gache;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface GacheService {

    Map<String, Integer> getSumGacheByProject();

    Map<String, Integer> getSumGacheByProject(List<Gache> gaches);

   /* List<Gache> listGachesDateBetween(LocalDateTime debut, LocalDateTime fin);*/

    Map<String, Integer> getSumGacheByProject (LocalDateTime debut, LocalDateTime fin);
}
