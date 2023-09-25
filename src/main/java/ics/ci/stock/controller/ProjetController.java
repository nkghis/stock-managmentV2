package ics.ci.stock.controller;

import ics.ci.stock.entity.*;
import ics.ci.stock.repository.*;
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
    private final ProjetRepository projetRepository;

    private final ClientRepository clientRepository;

    private final EmetteurRepository emetteurRepository;

    private final ProduitRepository produitRepository;

    private final ProviderRepository providerRepository;

    public ProjetController(ProjetRepository projetRepository, ClientRepository clientRepository, EmetteurRepository emetteurRepository, ProduitRepository produitRepository, ProviderRepository providerRepository) {
        this.projetRepository = projetRepository;
        this.clientRepository = clientRepository;
        this.emetteurRepository = emetteurRepository;
        this.produitRepository = produitRepository;
        this.providerRepository = providerRepository;
    }

    @RequestMapping(value = "/auth/projets")
    public String index(Model model){

        List<Projet> projets = projetRepository.findAll();
        model.addAttribute("listprojets",projets);
        model.addAttribute("title", "Projet - Liste");
        return "projet/index";
    }


    @RequestMapping(value = "/auth/projets/save", method = RequestMethod.POST)
    public String saveProjet(@Valid Projet projet, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError());
            model.addAttribute("monprojet", new Projet());
            return "projet/new";
        }



        Projet t = projetRepository.findByProjetNom(projet.getProjetNom());

        if (t == null)
        {

           projet.setProjetNom(projet.getProjetNom().toUpperCase());
            projetRepository.save(projet);
            redirectAttributes.addFlashAttribute("messagesucces","Opération éffectée avec succès");
        }

        else {

            redirectAttributes.addFlashAttribute("messagedanger","Echec, Le nom du projet [" +projet.getProjetNom() + " ] existe déjà dans la base de données");
        }



        return "redirect:/auth/projets";
    }

    @RequestMapping(value = "/auth/projets/new", method = RequestMethod.GET)
    public String newProjet (Model model){
        List<Client> clients =clientRepository.findAll();
        List<Emetteur> emetteurs =emetteurRepository.findAll();
        List<Produit> produits =produitRepository.findAll();
        List<Provider> providers = providerRepository.findAll();
        model.addAttribute("monprojet", new Projet());
        model.addAttribute("clients", clients);
        model.addAttribute("emetteurs", emetteurs);
        model.addAttribute("produits", produits);
        model.addAttribute("providers", providers);
        model.addAttribute("title", "Projet - Nouveau");
        return "projet/new";
    }

    @RequestMapping(value = "/auth/projets/edit/{id}", method = RequestMethod.GET)
    public String editProjet(@PathVariable Long id, Model model){

        Projet p = projetRepository.getOne(id);
        List<Client> c = clientRepository.findAll();
        List<Produit> produits = produitRepository.findAll();
        List<Emetteur> emetteurs = emetteurRepository.findAll();
        List<Provider> providers = providerRepository.findAll();
        model.addAttribute("projet", p);
        model.addAttribute("clients", c);
        model.addAttribute("produits", produits);
        model.addAttribute("emetteurs", emetteurs);
        model.addAttribute("providers", providers);
        model.addAttribute("title", "Projet - Edition");
        return "projet/edit";
    }

    @RequestMapping(value = "/auth/projets/delete/{id}", method = RequestMethod.GET)
    public String deleteClient(@PathVariable Long id){

        projetRepository.deleteById(id);
        return "redirect:/auth/projets";
    }


    @RequestMapping(value = "/auth/projets/update", method = RequestMethod.POST)
    public String updateProjet(@Valid Projet projet, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError());
            model.addAttribute("monprojet", new Projet());
            return "projet/new";
        }

        Projet p = projetRepository.findByProjetNom(projet.getProjetNom());

        if(p == null)
        {
            projet.setProjetNom(projet.getProjetNom().toUpperCase());
            projetRepository.save(projet);

            redirectAttributes.addFlashAttribute("messagesucces","Projet [" + projet.getProjetNom()+"] mis à jour avec succès");
        }

        else {
           p.setProjetNom(projet.getProjetNom().toUpperCase());
           p.setSeuilProjet(projet.getSeuilProjet());
           p.setClient(projet.getClient());
           p.setEmetteur(projet.getEmetteur());
           p.setProduit(projet.getProduit());

           projetRepository.save(p);
            redirectAttributes.addFlashAttribute("messagesucces","Projet [" + p.getProjetNom()+"] mis à jour avec succès");
        }
        return "redirect:/auth/projets";
    }

}
