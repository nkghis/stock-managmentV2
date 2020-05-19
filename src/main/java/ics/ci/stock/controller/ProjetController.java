package ics.ci.stock.controller;

import ics.ci.stock.entity.Client;
import ics.ci.stock.entity.Emetteur;
import ics.ci.stock.entity.Produit;
import ics.ci.stock.entity.Projet;
import ics.ci.stock.repository.ClientRepository;
import ics.ci.stock.repository.EmetteurRepository;
import ics.ci.stock.repository.ProduitRepository;
import ics.ci.stock.repository.ProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProjetController {
    @Autowired
    private ProjetRepository projetRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EmetteurRepository emetteurRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @RequestMapping(value = "/admin/projets")
    public String index(Model model){

        List<Projet> projets = projetRepository.findAll();
        model.addAttribute("listprojets",projets);
        model.addAttribute("title", "Projet - Liste");
        return "projet/index";
    }


    @RequestMapping(value = "/admin/projets/save", method = RequestMethod.POST)
    public String saveProjet(@Valid Projet projet, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError());
            model.addAttribute("monprojet", new Projet());
            return "projet/new";
        }
        Projet p = projet;
        //String aa = "";

        Projet t = projetRepository.findByProjetNom(projet.getProjetNom());

        if (t == null)
        {
           String s ="save";
            projetRepository.save(projet);
            redirectAttributes.addFlashAttribute("messagesucces","Opération éffectée avec succès");
        }

        else {
            String s = "notSave";
            redirectAttributes.addFlashAttribute("messagedanger","Echec, Le nom du projet [" +projet.getProjetNom() + " ] existe déjà dans la base de données");
        }
        //boolean t = projetRepository.findByProjetNom(projet.getProjetNom());

        //String aa = "";


        return "redirect:/admin/projets";
    }

    @RequestMapping(value = "/admin/projets/new", method = RequestMethod.GET)
    public String newProjet (Model model){
        List<Client> clients =clientRepository.findAll();
        List<Emetteur> emetteurs =emetteurRepository.findAll();
        List<Produit> produits =produitRepository.findAll();
        model.addAttribute("monprojet", new Projet());
        model.addAttribute("clients", clients);
        model.addAttribute("emetteurs", emetteurs);
        model.addAttribute("produits", produits);
        model.addAttribute("title", "Projet - Nouveau");
        return "projet/new";
    }

    @RequestMapping(value = "/admin/projets/edit/{id}", method = RequestMethod.GET)
    public String editProjet(@PathVariable Long id, Model model){

        Projet p = projetRepository.getOne(id);
        List<Client> c = clientRepository.findAll();
        List<Produit> produits = produitRepository.findAll();
        List<Emetteur> emetteurs = emetteurRepository.findAll();
        model.addAttribute("projet", p);
        model.addAttribute("clients", c);
        model.addAttribute("produits", produits);
        model.addAttribute("emetteurs", emetteurs);
        model.addAttribute("title", "Projet - Edition");
        return "projet/edit";
    }

    @RequestMapping(value = "/admin/projets/delete/{id}", method = RequestMethod.GET)
    public String deleteClient(@PathVariable Long id){

        projetRepository.deleteById(id);
        return "redirect:/admin/projets";
    }
}
