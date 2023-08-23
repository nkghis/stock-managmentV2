package ics.ci.stock.service.impl;

import ics.ci.stock.dto.gache.GacheProjetDTO;
import ics.ci.stock.entity.Gache;
import ics.ci.stock.repository.GacheRepository;
import ics.ci.stock.service.GacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class GacheServiceImpl implements GacheService {

    private final GacheRepository gacheRepository;

    public GacheServiceImpl(GacheRepository gacheRepository) {
        this.gacheRepository = gacheRepository;
    }

    //Sum of gache group by Projet
    @Override
    public Map<String, Integer>  getSumGacheByProject() {

        List<GacheProjetDTO> DTO = gacheRepository.findAll().stream().map(gache -> {
            GacheProjetDTO g = new GacheProjetDTO();
            g.setProjetId(gache.getOperation().getProjet().getProjet_id());
            g.setProjet(gache.getOperation().getProjet().getProjetNom());
            g.setQteGache(gache.getGacheQte());
            return g;
        }).collect(Collectors.toList());
        Map<String, Integer> collect = DTO
                .stream()
                .collect(
                        Collectors.groupingBy(
                                GacheProjetDTO::getProjet,
                                Collectors.summingInt(GacheProjetDTO::getQteGache
                                )
                        )
                );
        return collect;
    }

    @Override
    public  Map<String, Integer> getSumGacheByProject(List<Gache> gaches){

        List<GacheProjetDTO> DTO = gaches.stream().map(gache -> {
            GacheProjetDTO g = new GacheProjetDTO();
            g.setProjetId(gache.getOperation().getProjet().getProjet_id());
            g.setProjet(gache.getOperation().getProjet().getProjetNom());
            g.setQteGache(gache.getGacheQte());
            return g;
        }).collect(Collectors.toList());
        Map<String, Integer> collect = DTO
                .stream()
                .collect(
                        Collectors.groupingBy(
                                GacheProjetDTO::getProjet,
                                Collectors.summingInt(GacheProjetDTO::getQteGache
                                )
                        )
                );
        return collect;
    }

 /*   @Override
    public List<Gache> listGachesDateBetween(LocalDateTime debut, LocalDateTime fin) {
        return gacheRepository.findAllByGacheDateBetween(debut, fin);
    }*/

    @Override
    public Map<String, Integer> getSumGacheByProject(LocalDateTime debut, LocalDateTime fin) {
        List<Gache> gaches = gacheRepository.findAllByGacheDateBetween(debut, fin);
        return this.getSumGacheByProject(gaches);
    }
}
