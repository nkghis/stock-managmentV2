package ics.ci.stock.controller;

import ics.ci.stock.entity.*;
import ics.ci.stock.repository.*;
import ics.ci.stock.service.NotificationService;
import ics.ci.stock.utils.ConfigProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class ProjetController {
    private final ProjetRepository projetRepository;

    private final ClientRepository clientRepository;

    private final EmetteurRepository emetteurRepository;

    private final ProduitRepository produitRepository;

    private final ProviderRepository providerRepository;

    private final NotificationService notificationService;

    private final UserRepository userRepository;

    private final ConfigProperties configProperties;

    public ProjetController(ProjetRepository projetRepository, ClientRepository clientRepository, EmetteurRepository emetteurRepository, ProduitRepository produitRepository, ProviderRepository providerRepository, NotificationService notificationService, UserRepository userRepository, ConfigProperties configProperties) {
        this.projetRepository = projetRepository;
        this.clientRepository = clientRepository;
        this.emetteurRepository = emetteurRepository;
        this.produitRepository = produitRepository;
        this.providerRepository = providerRepository;
        this.notificationService = notificationService;
        this.userRepository = userRepository;
        this.configProperties = configProperties;
    }

    @RequestMapping(value = "/auth/projets")
    public String index(Model model){

        List<Projet> projets = projetRepository.findAll();
        model.addAttribute("listprojets",projets);
        model.addAttribute("title", "Projet - Liste");
        return "projet/index";
    }


    @RequestMapping(value = "/auth/projets/save", method = RequestMethod.POST)
    public String saveProjet(@Valid Projet projet,Principal principal, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) throws InterruptedException {

        if(bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError());
            model.addAttribute("monprojet", new Projet());
            return "projet/new";
        }



        Projet t = projetRepository.findByProjetNom(projet.getProjetNom());

        if (t == null)
        {

           projet.setProjetNom(projet.getProjetNom().toUpperCase());
         Projet pro =   projetRepository.save(projet);

            AppUser user = userRepository.findByUserName(principal.getName());

            String to = configProperties.getConfigValue("stock.managment.email.projet");
            String subject ="[Projet :" + pro.getProjetNom()+ "] Notification création de projet ";
            String messageEmail = "Hello Teams, \n" +
                    "Nous vous informons qu 'un nouveau projet vient d'etre creé. Vous trouverez ci-dessous les details : \n" +
                    "Nom Projet : " +pro.getProjetNom()+ "\n"+
                    "Emetteur : " +pro.getEmetteur().getEmetteurNom()+ "\n"+
                    "Produit : " +pro.getProduit().getProduit_nom()+ "\n"+
                    "Client : " +pro.getClient().getClient_nom()+ "\n"+
                    "Fournisseur : " +pro.getProvider().getProviderNom()+ "\n"+
                    "Seuil : " + pro.getSeuilProjet() + "\n"+
                    "Créer par : " + user.toNomComplet() + "\n"+
                    "Merci.\n" +
                    "L'administrateur.\n" +
                    "Ceci est un message generé automatiquement. Nous vous prions de ne pas repondre à ce message";

            notificationService.sendEmailValidation(subject, messageEmail, user, projet, to);

            redirectAttributes.addFlashAttribute("messagesucces","Opération éffectuée avec succès");
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
    public String updateProjet(@Valid Projet projet, Principal principal, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) throws InterruptedException {

        if(bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError());
            model.addAttribute("monprojet", new Projet());
            return "projet/new";
        }

        Projet p = projetRepository.findByProjetNom(projet.getProjetNom());

       // String projetName = "";

        //Projet pro ;

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
           p.setProvider(projet.getProvider());

          projetRepository.save(p);
            redirectAttributes.addFlashAttribute("messagesucces","Projet [" + p.getProjetNom()+"] mis à jour avec succès");
        }

        //Get User



        return "redirect:/auth/projets";
    }

}
