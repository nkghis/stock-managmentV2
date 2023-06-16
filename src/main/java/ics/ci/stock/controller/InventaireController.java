package ics.ci.stock.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ics.ci.stock.dto.entrepot.EntrepotDtoOut;
import ics.ci.stock.dto.inventaire.InventaireDetailDtoOut;
import ics.ci.stock.dto.inventaire.InventaireDtoOut;
import ics.ci.stock.dto.inventaire.InventaireEndDto;
import ics.ci.stock.dto.inventaire.InventaireNewDto;
import ics.ci.stock.dto.projet.ProjetDtoOut;
import ics.ci.stock.entity.AppUser;
import ics.ci.stock.entity.Inventaire;
import ics.ci.stock.entity.Inventairedetail;
import ics.ci.stock.repository.UserRepository;
import ics.ci.stock.service.EntrepotService;
import ics.ci.stock.service.InventaireService;
import ics.ci.stock.service.StockService;
import org.apache.catalina.User;
import org.apache.poi.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.TextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class InventaireController {

    private final StockService stockService;

    private final EntrepotService entrepotService;
    private final InventaireService inventaireService;
    private final UserRepository userRepository;

    public InventaireController(StockService stockService, EntrepotService entrepotService, InventaireService inventaireService, UserRepository userRepository) {
        this.stockService = stockService;
        this.entrepotService = entrepotService;
        this.inventaireService = inventaireService;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/inventaire/inventaires")
    public String index(Model model){

        List<InventaireDtoOut> dtos = inventaireService.listInventairesToDto();
                model.addAttribute("title", "Inventaire - Liste des Ajustements de stock");
                model.addAttribute("dtos", dtos);
        return "inventaire/index";
    }

    @RequestMapping(value = "/inventaire/inventaires/newold", method = RequestMethod.GET)
    public String newAjustementStockOld(Model model) throws JsonProcessingException {

        List<ProjetDtoOut> projets = stockService.getListProjetByEntrepot(1L);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonProjet = objectMapper.writeValueAsString(projets);
        model.addAttribute("mesprojets",jsonProjet);
        model.addAttribute("title", "Inventaire Adjustment Stock - Nouveau");
        return "inventaire/new";

    }

    @RequestMapping(value = "/inventaire/inventaires/new/step1", method = RequestMethod.GET)
    public String newAjustementStockStep1(Model model)  {

        List<EntrepotDtoOut> entrepots = entrepotService.listEntrepots();
        model.addAttribute("dto", new InventaireNewDto());
        model.addAttribute("entrepots", entrepots);
        model.addAttribute("title", "Inventaire Adjustment Stock - Nouveau Etape 1");

        return "inventaire/mynew";
    }

    @RequestMapping(value = "/inventaire/inventaires/new/step2", method = RequestMethod.POST)
    public String newAjustementStockStep2(Model model, @Valid @ModelAttribute("dto") InventaireNewDto dto,  BindingResult bindingResult) throws JsonProcessingException  {
        String s = "";
        if (bindingResult.hasErrors()){
            System.out.println("error");
            model.addAttribute("title", "Inventaire Adjustment Stock - Nouveau Etape 1");
            List<EntrepotDtoOut> entrepots = entrepotService.listEntrepots();
            model.addAttribute("entrepots", entrepots);
            //model.addAttribute("dto", dto);

            return "inventaire/mynew";
        }

        List<ProjetDtoOut> projets = stockService.getListProjetByEntrepot(dto.getEntrepot().getEntrepotId());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonProjet = objectMapper.writeValueAsString(projets);
        //String jsonDto = objectMapper.writeValueAsString(dto);
        model.addAttribute("mesprojets",jsonProjet);
        model.addAttribute("title", "Inventaire Adjustment Stock - Nouveau Etape 2");
        model.addAttribute("dto", dto);
        //model.addAttribute("jsonDto", jsonDto);



        return "inventaire/new";
    }

   /* @RequestMapping(value = "/inventaire/inventaires/save", method = RequestMethod.POST)
    @ResponseBody
    public String saveAjustement(@RequestBody InventaireEndDto dto, Principal principal, Errors errors, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){

        String s = "aa";

        List<String> errorDetail = inventaireService.checkIfStockGreaterThanZero(dto);
        if (!errorDetail.isEmpty()){
            String result = String.join(", ", errorDetail);
            //redirectAttributes.addFlashAttribute("messageerror","Merci de verifier les quantités des projets suivants : " + result);
        }else {

            AppUser user = userRepository.findByUserName(principal.getName());
            inventaireService.createInventaire(dto, user );
            //redirectAttributes.addFlashAttribute("messagesucces","Opération éffectuée avec succès");
        }

        return s;
        //return "redirect:/inventaire/inventaires";
    }*/


    @RequestMapping(value="/inventaire/inventaires/{idProject}/{idEntrepot}", method=RequestMethod.GET)
    @ResponseBody
    public String getStockByProjectAndByEntrepot(@PathVariable Long idProject, @PathVariable Long idEntrepot) {

        int result = inventaireService.getStockQuantityByProjetAndEntrepot(idProject,idEntrepot);
        return Integer.toString(result);
    }

    @RequestMapping(value="/inventaire/inventaires/{idInventaire}", method=RequestMethod.GET)
    public String getInventaireDetailByInventaire(@PathVariable Long idInventaire, Model model) {


        Inventaire inventaire = inventaireService.getInventaireById(idInventaire);
        InventaireDtoOut dto = inventaireService.inventaireToDto(inventaire);
        List<InventaireDetailDtoOut> dtos = inventaireService.listInventairesDetailToDtoByInventaireId(idInventaire);

        String message = "Detail de l'inventaire avec intitulé : " + dto.getIntitule().toUpperCase();
        model.addAttribute("dtos",dtos);
        model.addAttribute("dto",dto);
        model.addAttribute("title", message);

        return "inventaire/detail";

    }
}
