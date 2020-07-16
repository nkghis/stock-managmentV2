package ics.ci.stock.controller;

import ics.ci.stock.entity.Fournisseur;
import ics.ci.stock.repository.FournisseurRepository;
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
public class FournisseurController {

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @RequestMapping(value = "/access/fournisseurs")
    public String indexFournisseur(Model model){

        List<Fournisseur> fournisseurs = fournisseurRepository.findAll();
        model.addAttribute("listfournisseurs",fournisseurs);
        model.addAttribute("title", "Fournisseur - Liste");
        return "fournisseur/index";
    }

    @RequestMapping(value = "/access/fournisseurs/new", method = RequestMethod.GET)
    public String newFournisseur(Model model){

        model.addAttribute("monfournisseur",new Fournisseur());
        model.addAttribute("title", "Fournisseur - Nouveau");
        return "fournisseur/new";

    }

    @RequestMapping(value = "/access/fournisseurs/save", method = RequestMethod.POST)
    public String saveFournisseur(@Valid Fournisseur fournisseur, Errors errors, Model model, RedirectAttributes redirectAttributes){

        if (errors.hasErrors()){
            System.out.println("error YES");
            model.addAttribute("monfournisseur", new Fournisseur());
            //model.addAttribute("errors", errors);
            return "fournisseur/new";
        }
        fournisseur.setFournisseur_nom(fournisseur.getFournisseur_nom().toUpperCase());
        fournisseurRepository.save(fournisseur);
        redirectAttributes.addFlashAttribute("messagesucces","Opération éffectée avec succès");
        return "redirect:/access/fournisseurs";
    }

    @RequestMapping(value = "/access/fournisseurs/edit/{id}", method = RequestMethod.GET)
    public String editFournisseur(@PathVariable Long id, Model model){

        Fournisseur r = fournisseurRepository.getOne(id);

        model.addAttribute("fournisseur", r);
        model.addAttribute("title", "Fournisseur - Edition");
        return "fournisseur/edit";
    }

    @RequestMapping(value = "/access/fournisseurs/delete/{id}", method = RequestMethod.GET)
    public String deleteFournisseur(@PathVariable Long id){

        fournisseurRepository.deleteById(id);
        return "redirect:/access/fournisseurs";
    }
}
