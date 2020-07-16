package ics.ci.stock.controller;

import ics.ci.stock.entity.Entrepot;
import ics.ci.stock.repository.EntrepotRepository;
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
public class EntrepotController {

    @Autowired
    private EntrepotRepository entrepotRepository;

    @RequestMapping(value = "/auth/entrepots")
    public String indexEntrepot(Model model){



        List<Entrepot> entrepots = entrepotRepository.findAll();
        model.addAttribute("listEntrepots", entrepots);
        model.addAttribute("title", "Entrepot - Liste");
        return "entrepot/index";
    }

    @RequestMapping(value = "/auth/entrepots/new", method = RequestMethod.GET)
    public String newEntrepot(Model model){

        model.addAttribute("monentrepot",new Entrepot());
        model.addAttribute("title", "Entrepôt - Nouveau");
        return "entrepot/new";

    }


    @RequestMapping(value = "/auth/entrepots/save", method = RequestMethod.POST)
    public String saveEntrepot(@Valid Entrepot entrepot, Errors errors, Model model, RedirectAttributes redirectAttributes){

        if (errors.hasErrors()){
            System.out.println("error YES");
            model.addAttribute("monentrepot", new Entrepot());
            //model.addAttribute("errors", errors);
            return "entrepot/new";
        }

        entrepot.setEntrepotNom(entrepot.getEntrepotNom().toUpperCase());
        entrepotRepository.save(entrepot);
        redirectAttributes.addFlashAttribute("messagesucces","Opération éffectée avec succès");
        return "redirect:/auth/entrepots";
    }



    @RequestMapping(value = "/auth/entrepots/edit/{id}", method = RequestMethod.GET)
    public String editEntrepot(@PathVariable Long id, Model model){

        Entrepot e = entrepotRepository.getOne(id);

        model.addAttribute("entrepot", e);
        model.addAttribute("title", "Entrepôt - Edition");
        return "entrepot/edit";
    }

    @RequestMapping(value = "/auth/entrepots/delete/{id}", method = RequestMethod.GET)
    public String deleteEntrepot(@PathVariable Long id){

        entrepotRepository.deleteById(id);
        return "redirect:/auth/entrepots";
    }
}

