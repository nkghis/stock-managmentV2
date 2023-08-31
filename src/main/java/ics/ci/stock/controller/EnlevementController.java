package ics.ci.stock.controller;

import ics.ci.stock.entity.*;
import ics.ci.stock.repository.*;
import ics.ci.stock.service.NotificationService;
import ics.ci.stock.service.StockService;
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



    private final NotificationService notificationService;

    private final EnlevementRepository enlevementRepository;

    private final VentreposageTrueLivrableRepository livrableRepository;

    private final RessourceRepository ressourceRepository;

    private final MotifRepository motifRepository;

    private final EntreposerRepository entreposerRepository;
    //private EntreposageRepository entreposageRepository;

    private final UserRepository userRepository;

    private final StockRepository stockRepository;

    private final VenlevementGacheRepository venlevementGacheRepository;

    private final TypegacheRepository typegacheRepository;

    private final GacheRepository gacheRepository;

    private final StockService stockService;

    public EnlevementController(EnlevementRepository enlevementRepository, VentreposageTrueLivrableRepository livrableRepository, RessourceRepository ressourceRepository, MotifRepository motifRepository, EntreposerRepository entreposerRepository, UserRepository userRepository, StockRepository stockRepository, VenlevementGacheRepository venlevementGacheRepository, TypegacheRepository typegacheRepository, GacheRepository gacheRepository, StockService stockService, NotificationService notificationService) {
        this.enlevementRepository = enlevementRepository;
        this.livrableRepository = livrableRepository;
        this.ressourceRepository = ressourceRepository;
        this.motifRepository = motifRepository;
        this.entreposerRepository = entreposerRepository;
        this.userRepository = userRepository;
        this.stockRepository = stockRepository;
        this.venlevementGacheRepository = venlevementGacheRepository;
        this.typegacheRepository = typegacheRepository;
        this.gacheRepository = gacheRepository;
        this.stockService = stockService;
        this.notificationService = notificationService;
    }

    @RequestMapping(value = "/agent/livraisons", method = RequestMethod.GET)
    public String indexLivraison(Model model){

        /*List<VentreposageTrueLivrable> livrab = livrableRepository.findAll();
        int test = 1;*/

        model.addAttribute("title", "Sortie, Gache, Retour  - Liste");
        return "enlevement/index";
    }

    @RequestMapping(value = "/agent/livraisons/save", method = RequestMethod.POST)
    public String saveDistribution(@Valid Enlevement enlevement, BindingResult bindingResult, Model model, Principal principal, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError());
            return "redirect:/agent/livraisons";
        }






        //Get id entreposage since form
        String d = request.getParameter("entreposer");

        //Parse String to Long
        Long ditribuerId = Long.parseLong(d);

        //Get Reception Object
        //Entreposage entreposage = entreposageRepository.getOne(ditribuerId);
        Entreposer entreposage = entreposerRepository.getOne(ditribuerId);

        Projet pro = entreposage.getProjet();
        Entrepot ent = entreposage.getEntrepot();
        int qte = enlevement.getOperationQte();

        // Check if Stock quantite is available
        Boolean result = stockService.checkIfStockAvailable(pro, ent, qte);

        String messageValue = null;
        String messageVariable = null;

        if (result == false){

            messageVariable = "messagestock";
            messageValue = "Le stock du project : " + entreposage.getProjet().getProjetNom()  +" ayant pour entrepot " + entreposage.getEntrepot().getEntrepotNom()+" ne dispose pas assez de stock pour effectué cette opération";

        }else {

            messageVariable = "messageenlevement";
            messageValue = "Sortie éffectuée avec succès";
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
            enlevement.setOperationDateSaisie(enlevement.getOperationDateSaisie());

            //Produit produit = entreposage.getReception().getProduit();
            //Recherche dans la table stock en fonction de produit, projet, entrepot
            Stock stock = stockRepository.findByProjetAndEntrepot(/*produit,*/ projet, entrepot);
            int quantite = stock.getStockQuantite() - enlevement.getOperation_qte();

            enlevement.setStockInitial(stock.getStockQuantite());
            enlevement.setStockFinal(quantite);

            stock.setUser(user);
            stock.setStockDate(date);
            stock.setStockQuantite(quantite);

            enlevementRepository.save(enlevement);
            stockRepository.save(stock);

           //Check if quantité stock est superieur à seuil de securité Si false, Envoyé message de notification
            Boolean checkSeuil = stockService.seuilSecuriteDisponible(stock.getProjet());
            if (checkSeuil == false){

                String projetNom = projet.getProjetNom().toUpperCase();
                String entrepotNom = entrepot.getEntrepotNom().toUpperCase();
                int stockQuantite = stockService.totalStockByProjet(stock.getProjet());
                String sujet = "Notification Stock | Seuil de sécurité atteint | Enlevement | Projet : " + projetNom;
                String message = "A tous, " + System.lineSeparator() +
                        "Le stock relatif au projet : " + projetNom + " emmagasiné a l'entrepôt : " + entrepotNom +" a depassé le seuil de sécurité." + System.lineSeparator()+
                        "  - Quantité seuil: " + stock.getProjet().getSeuilProjet() + System.lineSeparator() +
                        "  - Stock disponible: " +stockQuantite + System.lineSeparator() +
                        System.lineSeparator() +
                        "L'Administrateur" + System.lineSeparator()+
                        System.lineSeparator() +
                        "Ceci est un message generé automatiquement. Nous vous prions de ne pas repondre à ce message";

                try {
                    notificationService.sendEmail( sujet, message, user, projet);
                }catch( Exception e ){
                    System.out.println(e.getMessage());
                }

            }
        }




        redirectAttributes.addFlashAttribute(messageVariable,messageValue);
        //redirectAttributes.addFlashAttribute("messageenlevement","Sortie éffectée avec succès");

        return "redirect:/agent/livraisons";
    }






        //Affiche un formulaire de enlevement.
    @RequestMapping(value = "/agent/livraisons/distributions/{id}", method = RequestMethod.GET)
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
    @RequestMapping(value = "/agent/livraisons/all")
    @ResponseBody
    public List<VentreposageTrueLivrable> allDistributionTrueLivrableJson(Model model){

        List<VentreposageTrueLivrable> y = livrableRepository.findAll();
        return y;

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
