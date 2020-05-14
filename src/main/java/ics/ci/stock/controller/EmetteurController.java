package ics.ci.stock.controller;


import ics.ci.stock.entity.Emetteur;
import ics.ci.stock.repository.EmetteurRepository;
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
public class EmetteurController {

    @Autowired
    private EmetteurRepository emetteurRepository;

    @RequestMapping(value = "/admin/emetteurs")
    public String index(Model model){



        List<Emetteur> emetteurs = emetteurRepository.findAll();
        model.addAttribute("listEmetteurs", emetteurs);
        model.addAttribute("title", "Emetteur - Liste");
        return "emetteur/index";
    }


    @RequestMapping(value = "/admin/emetteurs/new", method = RequestMethod.GET)
    public String newEmetteur(Model model){

        model.addAttribute("monemetteur",new Emetteur());
        model.addAttribute("title", "Emetteur - Nouveau");
        return "emetteur/new";

    }


    @RequestMapping(value = "/admin/emetteurs/save", method = RequestMethod.POST)
    public String saveEmetteur(@Valid Emetteur emetteur, Errors errors, Model model, RedirectAttributes redirectAttributes){

        if (errors.hasErrors()){
            System.out.println("error YES");
            model.addAttribute("monemetteur", new Emetteur());
            //model.addAttribute("errors", errors);
            return "emetteur/new";
        }
        emetteurRepository.save(emetteur);
        redirectAttributes.addFlashAttribute("messagesucces","Opération éffectée avec succès");
        return "redirect:/admin/emetteurs";
    }

    @RequestMapping(value = "/admin/emetteurs/edit/{id}", method = RequestMethod.GET)
    public String editEmetteur(@PathVariable Long id, Model model){

        Emetteur e = emetteurRepository.getOne(id);

        model.addAttribute("emetteur", e);
        model.addAttribute("title", "Emetteur - Edition");
        return "emetteur/edit";
    }


    @RequestMapping(value = "/admin/emetteurs/delete/{id}", method = RequestMethod.GET)
    public String deleteEmetteur(@PathVariable Long id){

        emetteurRepository.deleteById(id);
        return "redirect:/admin/emetteurs";
    }



}
