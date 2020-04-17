package ics.ci.authentification.controller;

import ics.ci.authentification.entity.*;
import ics.ci.authentification.repository.*;
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
public class GacheController {

    @Autowired
    private TypegacheRepository typegacheRepository;

    @Autowired
    private VenlevementGacheRepository venlevementGacheRepository;

    @Autowired
    private EnlevementRepository enlevementRepository;

    @Autowired
    private GacheRepository gacheRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private EntreposageRepository entreposageRepository;

    @Autowired
    private EnlevementController enlevementController;

    //Affiche un formulaire de Gache.
    @RequestMapping(value = "/admin/gaches/{id}", method = RequestMethod.GET)
    public String newGache(@PathVariable Long id, Model model) {

        List<Typegache> typegaches = typegacheRepository.findAll();
        Enlevement enlevement = enlevementRepository.getOne(id);

        model.addAttribute("typegaches", typegaches);
        model.addAttribute("enlevement", enlevement);
        model.addAttribute("magache", new Gache());
        model.addAttribute("title", "  Gache - Attribution");

        return "gache/new";

    }

    //Attribuer Gache
    @RequestMapping(value = "/admin/gaches/save", method = RequestMethod.POST)
    public String attribuerGache(@Valid Gache gache, BindingResult bindingResult, Model model, Principal principal, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError());
            return "redirect:/admin/livraisons";
        }

        //Get id distribuer since form
        String d = request.getParameter("enlevement");

        //Parse String to Long
        Long livraisonId = Long.parseLong(d);

        //Get enlevement
        Enlevement enlevement = enlevementRepository.getOne(livraisonId);

        //Get Local date Time
        LocalDateTime date = LocalDateTime.now();

        //Get User AUth
        AppUser user = userRepository.findByUserName(principal.getName());

        //persistence de gache
        gache.setGacheDate(date);
        gache.setOperation(enlevement);
        gache.setUser(user);
        gacheRepository.save(gache);


        Entreposage entreposage = enlevement.getEntreposage();

        int qteVerseFrom = gache.getGacheQte().intValue();
        int qteRestanteForm = entreposage.getQteRestante();


        int qteR = qteRestanteForm - qteVerseFrom;

        if (qteR == 0) {
            entreposage.setEstLivrable(Boolean.valueOf(false));
        } else {
            entreposage.setEstLivrable(Boolean.valueOf(true));
        }

        entreposage.setQteRestante(qteR);
        entreposageRepository.save(entreposage);

        /* Creation d'un nouvel enlevement du au gache */

        Enlevement en = new Enlevement();
        en.setOperationReference(enlevementController.getReference());
        en.setOperationQte(gache.getGacheQte().intValue());
        en.setEnlevementDispo(gache.getGacheQte());
        en.setOperation_date(date);
        en.setUser(user);
        en.setEntreposage(entreposage);
        en.setEstGache(Boolean.valueOf(false));
        en.setEstRetour(Boolean.valueOf(false));
        en.setGache(Integer.valueOf(0));
        en.setEstDisponible(true);
        en.setRessource(enlevement.getRessource());
        en.setMotif(enlevement.getMotif());
        //en.setProduit(enlevement.getProduit());
        en.setProjet(enlevement.getProjet());
        enlevementRepository.save(en);


        /*Mise à jour de la gache*/

        Projet projet = entreposage.getReception().getProjet();
        Entrepot entrepot = entreposage.getEntrepot();


        Stock stock = stockRepository.findByProjetAndEntrepot(projet, entrepot);
        int quantite = stock.getStockQuantite() - gache.getGacheQte().intValue();
        stock.setUser(user);
        stock.setStockDate(date);
        stock.setStockQuantite(quantite);
        this.stockRepository.save(stock);
        enlevement.setEstGache(Boolean.valueOf(true));
        enlevementRepository.save(enlevement);

        String mes = "Attribution de gache éffectée avec succès & Creation de l'enlevement N° : " + en.getOperationReference();



        //Notification et redirection
        redirectAttributes.addFlashAttribute("messagegache", mes);
        return "redirect:/admin/livraisons";
    }

    //Liste des stock livrer en Json
    @RequestMapping(value = "/admin/gaches")
    @ResponseBody
    public List<VenlevementGache> LivrerJson(Model model){

        return venlevementGacheRepository.findAll();
    }



    @RequestMapping(value = {"/admin/gaches/pas/{id}"}, method = {RequestMethod.GET})
      public String pasGache(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
            Enlevement enlevement = (Enlevement)this.enlevementRepository.getOne(id);
            enlevement.setEstGache(Boolean.valueOf(true));
            this.enlevementRepository.save(enlevement);
            redirectAttributes.addFlashAttribute("messagegache", "Pas de gache éffectée avec succès");
            return "redirect:/admin/livraisons";

          }
}
