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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class EnlevementController {

    @Autowired
    private EnlevementRepository enlevementRepository;

    @Autowired
    private VentreposageTrueLivrableRepository livrableRepository;

    @Autowired
    private RessourceRepository ressourceRepository;

    @Autowired
    private MotifRepository motifRepository;

    @Autowired
    private EntreposerRepository entreposerRepository;
    //private EntreposageRepository entreposageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private VenlevementGacheRepository venlevementGacheRepository;

    @Autowired
    private TypegacheRepository typegacheRepository;

    @Autowired
    private GacheRepository gacheRepository;

    @RequestMapping(value = "/admin/livraisons", method = RequestMethod.GET)
    public String indexLivraison(Model model){

        /*List<VentreposageTrueLivrable> livrab = livrableRepository.findAll();
        int test = 1;*/

        model.addAttribute("title", "Sortie, Gache, Retour  - Liste");
        return "enlevement/index";
    }

    @RequestMapping(value = "/admin/livraisons/save", method = RequestMethod.POST)
    public String saveDistribution(@Valid Enlevement enlevement, BindingResult bindingResult, Model model, Principal principal, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError());
            return "redirect:/admin/livraisons";
        }

        //Get id entreposage since form
        String d = request.getParameter("entreposer");

        //Parse String to Long
        Long ditribuerId = Long.parseLong(d);

        //Get Reception Object
        //Entreposage entreposage = entreposageRepository.getOne(ditribuerId);
        Entreposer entreposage = entreposerRepository.getOne(ditribuerId);

        //Get Local date Time
        LocalDateTime date = LocalDateTime.now();

        //Get User AUth
        AppUser user = userRepository.findByUserName(principal.getName());

        //Declaration des quantités depuis le formulaire
        int qteVerseForm = enlevement.getOperation_qte();
        int qteRestanteForm = enlevement.getEnlevementDispo();

        //Declaration de qte restante dans Distribution
        int qteR = qteRestanteForm - qteVerseForm;

        //Mise à jour de Entreposage
        if (qteR == 0){
            entreposage.setEstLivrable(false);
        }else{
            entreposage.setEstLivrable(true);
        }

        entreposage.setQteRestante(qteR);
        entreposerRepository.save(entreposage);

        //Declaration variables
        Projet projet = entreposage.getProjet();
        Entrepot entrepot = entreposage.getEntrepot();

        //Enregistrement de Enlevement.
        //enlevement.setOperationReferenceFournisseur(null);
        enlevement.setEnlevementDispo(enlevement.getOperation_qte());
        enlevement.setOperation_date(date);
        enlevement.setUser(user);
        enlevement.setEntreposer(entreposage);
        enlevement.setEstGache(false);
        enlevement.setEstRetour(false);
        enlevement.setGache(0);
        enlevement.setEstDisponible(true);
        //enlevement.setProduit(entreposage.getReception().getProduit());
        enlevement.setProjet(projet);
        enlevement.setEntrepot(entrepot);



        //Produit produit = entreposage.getReception().getProduit();



        //Recherche dans la table stock en fonction de produit, projet, entrepot
        Stock stock = stockRepository.findByProjetAndEntrepot(/*produit,*/ projet, entrepot);
        int quantite = stock.getStockQuantite() - enlevement.getOperation_qte();

        enlevement.setStockInitial(stock.getStockQuantite());
        enlevement.setStockFinal(quantite);




        stock.setUser(user);
        stock.setStockDate(date);
        stock.setStockQuantite(quantite);




        stockRepository.save(stock);

        enlevementRepository.save(enlevement);

        redirectAttributes.addFlashAttribute("messageenlevement","Sortie éffectée avec succès");
        return "redirect:/admin/livraisons";
    }






        //Affiche un formulaire de enlevement.
    @RequestMapping(value = "/admin/livraisons/distributions/{id}", method = RequestMethod.GET)
    public String LivraisonDistribuer(@PathVariable Long id, Model model){

        //Get reference.
        String reference = getReference();
        Entreposer entreposage = entreposerRepository.getOne(id);

        //Affectation des valeur a l'objet enlevement
        Enlevement enlevement = new Enlevement();
        enlevement.setOperationReference(reference);
        //enlevement.setOperation_qte(entreposage.getQteVerse());
        enlevement.setEnlevementDispo(entreposage.getQteRestante());
        enlevement.setEntreposer(entreposage);

        //Liste des Objets Ressources & Motifs
        List<Ressource> ressources = ressourceRepository.findAll();
        List<Motif> motifs = motifRepository.findAll();

        model.addAttribute("malivraison", enlevement);
        model.addAttribute("ressources",ressources);
        model.addAttribute("motifs",motifs);
        model.addAttribute("entreposage", entreposage);
        model.addAttribute("title", "Sortie - Enregistrer une Sortie");
        return "enlevement/new";

    }


    //Liste des stock livrables en Json
    @RequestMapping(value = "/admin/livraisons/all")
    @ResponseBody
    public List<VentreposageTrueLivrable> allDistributionTrueLivrableJson(Model model){

        List<VentreposageTrueLivrable> y = livrableRepository.findAll();
        return y;
               //return livrableRepository.findAll();
    }





    //Fonction qui formate et retourne la reference
    public String getReference(){

        Enlevement lastEnlevement = enlevementRepository.findTopByOrderByOperationIdDesc();
        LocalDate date = LocalDate.now();
        String d = date.toString().replaceAll("-","");
        String da = d.substring(2, d.length());
        if (lastEnlevement == null)
        {
            String masque = "ENL-";
            int id = 1;
            String livraisonReference = masque + da + "-" + id;
            return livraisonReference;
        }
        else {
            Long l = lastEnlevement.getOperationId();
            int id = l.intValue()+1;
            String masque = "ENL-";
            String livraisonReference = masque + da + "-" + id;
            return livraisonReference;
        }


    }
}
