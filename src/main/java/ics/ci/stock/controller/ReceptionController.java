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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ReceptionController {

    @Autowired
    private ReceptionRepository receptionRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private ProjetRepository projetRepository;

    @Autowired
    private RessourceRepository ressourceRepository;

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired VreceptionRepository vreceptionRepository;

    /*@RequestMapping(value = "/admin/receptions")
    @ResponseBody
    public List<Vreception> index(Model model) {

        return  vreceptionRepository.findAll();
    }*/
    @RequestMapping(value = "/agent/receptions")
    public String indexReception(Model model) {


        model.addAttribute("title", "Réception - Liste");

        return "reception/index";
    }

    @RequestMapping(value = "/agent/receptions/new", method = RequestMethod.GET)
    public String newReception(Model model){


        List<Projet> projets = projetRepository.findAll();
        //List<Produit> produits = produitRepository.findAll();
        List<Fournisseur> fournisseurs = fournisseurRepository.findAll();
        List<Ressource> ressources = ressourceRepository.findAll();

        //Get reference.
       String operationReference = getReference();
        System.out.println(operationReference);

        //Add reference value in Object
        Reception r = new Reception();
        r.setOperationReference(operationReference);


        model.addAttribute("mareception",r);
        model.addAttribute("projets",projets);
        //model.addAttribute("produits",produits);
        model.addAttribute("fournisseurs",fournisseurs);
        model.addAttribute("ressources",ressources);

        model.addAttribute("title", "Réception - Nouveau");
        return "reception/new";

    }

    @RequestMapping(value = "/agent/receptions/save", method = RequestMethod.POST)
    public String saveReception(@Valid Reception reception, BindingResult bindingResult, Model model, Principal principal, RedirectAttributes redirectAttributes) {

        //Validation du formulaire
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError());

            /*//Get reference.
            String operationReference = getReference();

            //Add reference value in Object
            Reception r = new Reception();
            r.setOperationReference(operationReference);

            model.addAttribute("mareception", r);
            return "reception/new";*/
            return "redirect:/agent/receptions/new";
        }
        LocalDateTime date = LocalDateTime.now();
        AppUser user = userRepository.findByUserName(principal.getName());

        reception.setEstDisponible(true);
        reception.setOperation_date(date);
        reception.setUser(user);
        reception.setDispo(reception.getOperation_qte());
        receptionRepository.save(reception);

        System.out.println("bien enrole");

        //Notification et redirection
        redirectAttributes.addFlashAttribute("messagereception","Reception éffectée avec succès");
        return "redirect:/agent/receptions";


    }


    private String getReference(){

        Reception lastReception = receptionRepository.findTopByOrderByOperationIdDesc();
        LocalDate date = LocalDate.now();
        String masque = "REC-";
        String d = date.toString().replaceAll("-","");
        String da = d.substring(2, d.length());
        if (lastReception == null)
        {
            int id = 1;
            String operationReference = masque + da + "-" + id;
            return operationReference;
        }
        else {
            Long l = lastReception.getOperationId();
            int id = l.intValue()+1;
            String operationReference = masque + da + "-" + id;
            return operationReference;
        }


    }

    @RequestMapping(value = "/admin/receptions/edit/{id}", method = RequestMethod.GET)
    public String editReception(@PathVariable Long id, Model model){

        Reception r = receptionRepository.getOne(id);
        List<Projet> projets = projetRepository.findAll();
        List<Produit> produits = produitRepository.findAll();
        List<Fournisseur> fournisseurs = fournisseurRepository.findAll();
        List<Ressource> ressources = ressourceRepository.findAll();

        model.addAttribute("reception",r);
        model.addAttribute("projets",projets);
        model.addAttribute("produits",produits);
        model.addAttribute("fournisseurs",fournisseurs);
        model.addAttribute("ressources",ressources);

        model.addAttribute("title", "Réception - Edition");
        return "reception/edit";
    }

    @RequestMapping(value = "/agent/receptions/update", method = RequestMethod.POST)
    public String updateReception(@Valid Reception reception, BindingResult bindingResult, Model model, Principal principal){

        return "";
    }

    @RequestMapping(value = "/agent/receptions/all")
    @ResponseBody
    public List<Vreception> allReceptionJson(Model model) {

        return  vreceptionRepository.findAll();
    }
}
