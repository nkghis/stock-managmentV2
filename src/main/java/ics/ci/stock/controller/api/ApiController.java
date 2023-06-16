package ics.ci.stock.controller.api;

import ics.ci.stock.dto.inventaire.InventaireEndDto;
import ics.ci.stock.entity.AppUser;
import ics.ci.stock.repository.UserRepository;
import ics.ci.stock.service.InventaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RestController
public class ApiController {

    private final InventaireService inventaireService;
    private final UserRepository userRepository;

    public ApiController(InventaireService inventaireService ,UserRepository userRepository) {
        this.inventaireService = inventaireService;
        this.userRepository = userRepository;
    }

    @PostMapping("/inventaire/inventaires/save")
    public String saveAjustement(@RequestBody InventaireEndDto dto, Principal principal, Errors errors, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){

        //String s = "aa";

        List<String> errorDetail = inventaireService.checkIfStockGreaterThanZero(dto);
        if (!errorDetail.isEmpty()){
            String result = String.join(", ", errorDetail);
            //redirectAttributes.addFlashAttribute("messageerror","Merci de verifier les quantités des projets suivants : " + result);
        }else {

            AppUser user = userRepository.findByUserName(principal.getName());
            inventaireService.createInventaire(dto, user );
            //redirectAttributes.addFlashAttribute("messagesucces","Opération éffectuée avec succès");
        }

        return "test";
    }
}


