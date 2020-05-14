package ics.ci.stock.controller;

import ics.ci.stock.entity.Ressource;
import ics.ci.stock.repository.RessourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RessourceController {

    @Autowired
    private RessourceRepository ressourceRepository;

    @RequestMapping(value = "/admin/ressources")
    public String indexRessource(Model model){

        List<Ressource> ressources = ressourceRepository.findAll();
        model.addAttribute("listressources",ressources);
        model.addAttribute("title", "Ressource - Liste");
        return "ressource/index";
    }

    @RequestMapping(value = "/admin/ressources/new", method = RequestMethod.GET)
    public String newRessource(Model model){

        model.addAttribute("maressource",new Ressource());
        model.addAttribute("title", "Ressource - Nouveau");
        return "ressource/new";

    }

    @RequestMapping(value = "/admin/ressources/save", method = RequestMethod.POST)
    public String saveRessource(@Valid Ressource ressource, Errors errors, Model model, RedirectAttributes redirectAttributes){

        if (errors.hasErrors()){
            System.out.println("error YES");
            model.addAttribute("maressource", new Ressource());
            //model.addAttribute("errors", errors);
            return "ressource/new";
        }
        ressourceRepository.save(ressource);
        redirectAttributes.addFlashAttribute("messagesucces","Opération éffectée avec succès");
        return "redirect:/admin/ressources";
    }

    @RequestMapping(value = "/admin/ressources/edit/{id}", method = RequestMethod.GET)
    public String editRessource(@PathVariable Long id, Model model){

        Ressource r = ressourceRepository.getOne(id);

        model.addAttribute("ressource", r);
        model.addAttribute("title", "Ressource - Edition");
        return "ressource/edit";
    }

    @RequestMapping(value = "/admin/ressources/delete/{id}", method = RequestMethod.GET)
    public String deleteRessource(@PathVariable Long id){

        ressourceRepository.deleteById(id);
        return "redirect:/admin/ressources";
    }

}
