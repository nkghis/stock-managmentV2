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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class RetourController {

    @Autowired
    private EnlevementRepository enlevementRepository;

    @Autowired
    private VenlevementRetourRepository venlevementRetourRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RetourRepository retourRepository;

    @Autowired
    private StockRepository stockRepository;



    //Affiche un formulaire de Retour.
    @RequestMapping(value = "/agent/retours/{id}", method = RequestMethod.GET)
    public String newRetour(@PathVariable Long id, Model model) {

        Enlevement enlevement = enlevementRepository.getOne(id);

        //model.addAttribute("typegaches", typegaches);
        model.addAttribute("enlevement", enlevement);
        model.addAttribute("monretour", new Retour());
        model.addAttribute("title", "  Retour - Attribution");
        return "retour/new";

    }


    //Attribuer retour
    @RequestMapping(value = "/agent/retours/save", method = RequestMethod.POST)
    public String attribuerRetour(@Valid Retour retour, BindingResult bindingResult, Model model, Principal principal, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError());
            return "redirect:/agent/livraisons";
        }

        //Get id distribuer since form
        String d = request.getParameter("enlevement");

        //Parse String to Long
        Long enlevementId = Long.valueOf(Long.parseLong(d));

        //Get enlevement
        Enlevement enlevement = enlevementRepository.getOne(enlevementId);

        //Get Local date Time
        LocalDateTime date = LocalDateTime.now();

        //Get User AUth
        AppUser user = userRepository.findByUserName(principal.getName());

        //persistence de gache
        retour.setRetourDate(date);
        retour.setOperation(enlevement);
        retour.setUser(user);
        retourRepository.save(retour);

        //Mise à jour de la table stock.

        //Produit produit = enlevement.getProduit();
        Projet projet = enlevement.getProjet();
        Entrepot entrepot = enlevement.getEntreposer().getEntrepot();

        //Recherche dans la table stock en fonction de produit, projet, entrepot
        //Stock stock = stockRepository.findByProduitAndAndProjetAndEntrepot(produit, projet, entrepot);
        Stock stock = stockRepository.findByProjetAndEntrepot(projet, entrepot);

        //Get quantite restante stock
        int quantite = stock.getStockQuantite() + retour.getRetourQte();

        //Persistence de la table stock
        stock.setUser(user);
        stock.setStockDate(date);
        stock.setStockQuantite(quantite);
        stockRepository.save(stock);

        //Mise a jour du champs "est_retour" de la table "enlevement" qui hertite de la table "operation".

        enlevement.setEstRetour(true);
        enlevementRepository.save(enlevement);

        //Notification et redirection
        redirectAttributes.addFlashAttribute("messageretour","Attribution du retour éffectée avec succès");
        return "redirect:/agent/livraisons";
    }

    //Liste des stock a attribuer des retour en Json
    @RequestMapping(value = "/agent/retours")
    @ResponseBody
    public List<VenlevementRetour> EnlevementRetourJson(Model model){

        return venlevementRetourRepository.findAll();
    }


    @RequestMapping(value = {"/agent/retours/pas/{id}"}, method = {RequestMethod.GET})
    public String pasRetour(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Enlevement enlevement = (Enlevement)this.enlevementRepository.getOne(id);
        enlevement.setEstRetour(Boolean.valueOf(true));
        this.enlevementRepository.save(enlevement);
        redirectAttributes.addFlashAttribute("messageretour", "Pas du retour éffectée avec succès");
        return "redirect:/agent/livraisons";
     }
}
