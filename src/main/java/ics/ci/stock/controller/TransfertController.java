package ics.ci.stock.controller;


import ics.ci.stock.dto.historiquevalidation.HistoriqueValidationRequest;
import ics.ci.stock.dto.historiquevalidation.HistoriqueValidationResponse;
import ics.ci.stock.dto.validationtransfert.ValidationTransfertResponse;
import ics.ci.stock.entity.*;
import ics.ci.stock.entity.custom.EntrepotCriteria;
import ics.ci.stock.enums.Statut;
import ics.ci.stock.repository.*;
import ics.ci.stock.service.*;
import ics.ci.stock.utils.WebUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TransfertController {

    private final TransfertRepository transfertRepository;
    private final ProjetRepository projetRepository;
    private final EntrepotRepository entrepotRepository;
    private final EntreposerRepository entreposerRepository;
    private final StockRepository stockRepository;
    private final UserRepository userRepository;
    private final EntreposageController entreposageController;
    private final StockService stockService;

    private final ValidationTransfertService validationTransfertService;

    private final HistoriqueValidationService historiqueValidationService;
    private final NotificationService notificationService;
    private final UserService userService;



    public  long pro;
    public  long ent;
    public  String cre = "CREAPUB";
    public  String nat = "NATHAN";

    public TransfertController(TransfertRepository transfertRepository, ProjetRepository projetRepository, EntrepotRepository entrepotRepository, EntreposerRepository entreposerRepository, StockRepository stockRepository, UserRepository userRepository, EntreposageController entreposageController, StockService stockService, NotificationService notificationService, ValidationTransfertService validationTransfertService, HistoriqueValidationService historiqueValidationService, UserService userService) {
        this.transfertRepository = transfertRepository;
        this.projetRepository = projetRepository;
        this.entrepotRepository = entrepotRepository;
        this.entreposerRepository = entreposerRepository;
        this.stockRepository = stockRepository;
        this.userRepository = userRepository;
        this.entreposageController = entreposageController;
        this.stockService = stockService;
        this.notificationService = notificationService;
        this.validationTransfertService = validationTransfertService;
        this.historiqueValidationService = historiqueValidationService;
        this.userService = userService;
    }


    @RequestMapping(value = "/auth/transfert", method = RequestMethod.GET)
    public String indexTransfert(Model model){

        model.addAttribute("title", "Transfert de stocks  - Liste");
        return "transfert/index";
    }


    @RequestMapping(value = "/auth/transfert/demande")
    public String indexDemandeTransfert(Model model){


        //List<ValidationTransfertResponse> demandes = validationTransfertService.all();

        List<ValidationTransfertResponse> demandes = validationTransfertService.allByStatut(Statut.EN_COURS);
        model.addAttribute("demandes", demandes);
        model.addAttribute("title", "Demande de transfert en attente de validation - Liste");
        return "transfert/demande";
    }


    @RequestMapping(value = "/auth/transferts/new", method = RequestMethod.GET)
    public String newProjet (Model model){

        List<Projet> projets = projetRepository.findAll();
        List<Entrepot> entrepots = entrepotRepository.findAll();
        Entrepot creapub = entrepotRepository.findByEntrepotNom(cre);
        Entrepot nathan = entrepotRepository.findByEntrepotNom(nat);

    //Remove  entrepot Creapub and Nathan on Destination list Entrepot
        entrepots.remove(creapub);
        entrepots.remove(nathan);


        model.addAttribute("projets",projets);
        model.addAttribute("entrepots",entrepots);
        model.addAttribute("title", "Transfert - Nouveau | Sources");

        return "transfert/new";
    }


    @RequestMapping(value = "/auth/transferts/edit/{id}", method = RequestMethod.GET)
    public String editReception(@PathVariable Long id, Model model){

        Entreposer entreposer = entreposerRepository.getOne(id);
        String projetNom = entreposer.getProjet().getProjetNom();
        String entrepotNom = entreposer.getEntrepot().getEntrepotNom();
        Projet p = entreposer.getProjet();
        Entrepot e = entreposer.getEntrepot();
        Stock stock = stockRepository.findByProjetAndEntrepot(p,e);



        int dispoTransfert = entreposer.getTransfertDispo();
        int dispoStock = stock.getStockQuantite();
        List<Entrepot> entrepots = entrepotRepository.findAll();
        Entrepot creapub = entrepotRepository.findByEntrepotNom(cre);
        Entrepot nathan = entrepotRepository.findByEntrepotNom(nat);

        //Remove Source Entrepot, entrepot Creapub and Nathan on Destination list Entrepot
        entrepots.remove(entreposer.getEntrepot());
        entrepots.remove(creapub);
        entrepots.remove(nathan);

        Transfert montransfert = new Transfert();
        montransfert.setOperationId(id);

        model.addAttribute("title", "Liste des stocks transferables | SOURCE PROJET : "+projetNom+" | SOURCE ENTREPOT: "+entrepotNom);
        model.addAttribute("montransfert", montransfert);
        model.addAttribute("entrepots", entrepots);
        model.addAttribute("dispoStock", dispoStock);
        model.addAttribute("dispoTransfert", dispoTransfert);
        model.addAttribute("id", id);

        return "transfert/destination";
    }

    @RequestMapping(value = "/auth/transferts/save", method = RequestMethod.POST)
    public String saveTransfert( @Valid Transfert transfert, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, Principal principal, HttpServletRequest request) throws InterruptedException {


        if(bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError());
            model.addAttribute("montransfert", transfert);
            return "redirect:auth/transferts/edit/"+transfert.getOperationId();
        }




        ////Get id number of operation 'dis' from form
        String s = request.getParameter("id");

        ////Get id number of entrepot  from form
        String ss = request.getParameter("entrepot");

        //// Parse id number String operation 'dis' to Long
        Long id = Long.parseLong(s);

        ////Get qte operation from form
        int qte = transfert.getOperationQte();




        ////Parse String to Long
        Long eDestination = Long.parseLong(ss);

        ////Get Operation by operation id where type Operation = 'dis'
        Entreposer entreposer = entreposerRepository.getOne(id);

        //Get Projet
        Projet projet = entreposer.getProjet();

        ////Get entrepot from operation
        Entrepot entrepotSource = entreposer.getEntrepot();

        // Check if Stock quantite is available
        Boolean result = stockService.checkIfStockAvailable(projet, entrepotSource, qte);

        String messageValue = null;
        String messageVariable = null;



        if (result == false){

            //stock unavailable
            messageVariable = "messagestock";
            messageValue = "Le stock du project : " + projet.getProjetNom()  +" ayant pour entrepot " + entrepotSource.getEntrepotNom()+" ne dispose pas assez de stock pour effectué cette opération";

        }else {

            //stock available

            ////Get entrepot by id
            Entrepot entrepotDestination = entrepotRepository.getOne(eDestination);

            //Get User
            AppUser user = userRepository.findByUserName(principal.getName());

            Transfert t = new Transfert();

            t.setOperationDateSaisie(transfert.getOperationDateSaisie());
            t.setEntreposer(entreposer);
            t.setEntrepotSource(entrepotSource);
            t.setEntrepotDestination(entrepotDestination);
            t.setProjet(entreposer.getProjet());
            t.setOperationQte(qte);

            //Create validation Transfert
            ValidationTransfert responseValidation = validationTransfertService.createValidationTransfert(t, user);

            //Create Historique validation transfert
            HistoriqueValidationRequest historiqueValidationRequest = new HistoriqueValidationRequest();
            historiqueValidationRequest.setValidationTransfert(responseValidation);
            historiqueValidationRequest.setCommentaires("");
            historiqueValidationRequest.setStatut(Statut.EN_COURS);
            historiqueValidationRequest.setUser(user);
            HistoriqueValidationResponse historiqueValidationResponse = historiqueValidationService.createHistoriqueValidation(historiqueValidationRequest);


            //Send email

            String roleName = "ROLE_RESPONSABLESTOCK";

            //Get users emails have roleName;
            String[] emails = userService.getEmails(roleName);

            //String subject = "test notification email";

            //String messageEmail = "test notification email";


            String subject ="[Ref :" + responseValidation.getReference()+ "] Notification demande de transfert | Demande de transfert en attente de validation";

            String baseUrl = WebUtils.getBaseUrl(request);

            String url = baseUrl + "/responsablestock/validations/"+ responseValidation.getId();

            String messageEmail = "Hello Teams, " +  System.lineSeparator() +
                    "Nous vous informons qu'une demande de transfert du projet : " + projet.getProjetNom()+ " vous a été soumis par l'utilisateur :  " + user.getUserName() + "." + System.lineSeparator() +
                    "La demande concerne un transfert qui se fera depuis l'entrepot : " + entrepotSource.getEntrepotNom() + " vers : " + entrepotDestination.getEntrepotNom()+ "."+ System.lineSeparator()+
                    "La quantité est : " + qte + "."+ System.lineSeparator() +
                    "Nous vous prions de cliquer sur le lien ci-dessous pour Valider ou Refuser la demande" + System.lineSeparator()+
                    url + System.lineSeparator()+

                    "Merci." + System.lineSeparator() +
                    "L'administrateur." + System.lineSeparator()+
                    System.lineSeparator() +
                    "Ceci est un message generé automatiquement. Nous vous prions de ne pas repondre à ce message";


            notificationService.sendEmailValidation(subject, messageEmail, user, projet, emails);




            String message = "Demande de Transfert effectué avec succès | Quantité : " +qte + "| Entrepot Source : " + entrepotSource.getEntrepotNom() + "| Entrepot Destination : " + entrepotDestination.getEntrepotNom();

            messageVariable = "messagesucces";
            messageValue = message;


            //redirectAttributes.addFlashAttribute("messagesucces","Opération éffectée avec succès");
           // redirectAttributes.addFlashAttribute("messagesucces",message);


        }

        redirectAttributes.addFlashAttribute(messageVariable,messageValue);

        return "redirect:/auth/transfert";

    }


    @RequestMapping(value = "/auth/transferts/source", method = RequestMethod.POST)
    public String sourceTransfert( Model model, Principal principal, RedirectAttributes redirectAttributes, HttpServletRequest request) {

         pro = Long.parseLong(request.getParameter("projet"));
         ent = Long.parseLong(request.getParameter("entrepot"));
        Projet projet = projetRepository.getOne(pro);
        Entrepot entrepot = entrepotRepository.getOne(ent);
        String projetNom = projet.getProjetNom();
        String entrepotNom = entrepot.getEntrepotNom();

        model.addAttribute("projetNom",projetNom);
        model.addAttribute("entrepotNom",entrepotNom);
        model.addAttribute("title", "Liste des stocks transferables | SOURCE PROJET : "+projetNom+" | SOURCE: "+entrepotNom);

        return "transfert/source";
    }


    @RequestMapping(value = "/auth/transferts/all")
    @ResponseBody
    public List<Vtransfert> allTransfertJson(Model model) {


        List<Transfert> transferts = transfertRepository.findAll();
        List<Vtransfert> vtransferts = new ArrayList<Vtransfert>();

        for (Transfert t : transferts) {

            Vtransfert vtransfert = new Vtransfert();
            vtransfert.setOperationId(t.getOperationId());
            vtransfert.setOperationReference(t.getOperationReference());
            vtransfert.setProjetNom(t.getProjet().getProjetNom());
            vtransfert.setQteOperation(t.getOperationQte());
            vtransfert.setInitialSource(t.getStockInitialSource());
            vtransfert.setFinalSource(t.getStockFinalSource());
            vtransfert.setInitialDestination(t.getStockInitialDestination());
            vtransfert.setFinalDestination(t.getStockFinalDestination());
            vtransfert.setEntrepotSource(t.getEntrepotSource().getEntrepotNom());
            vtransfert.setEntrepotDestination(t.getEntrepotDestination().getEntrepotNom());
            vtransfert.setUserName(t.getUser().getUserName());
            vtransfert.setOperationDate(t.getOperation_date());
            vtransferts.add(vtransfert);
        }
        return  vtransferts;
    }

    @RequestMapping(value = "/auth/transfertsSource/all")
    @ResponseBody
    public List<Ventreposage> allTransfertsSourceJson(Model model) {

        Projet projet = projetRepository.getOne(pro);
        Entrepot entrepot = entrepotRepository.getOne(ent);

        List<Entreposer> entreposerLists = entreposerRepository.findByProjetAndEntrepot(projet, entrepot);
        List<Entreposer> entreposerList = new ArrayList<Entreposer>();
        for (Entreposer u: entreposerLists) {

            if (u.getTransfertDispo() > 0)
            {
                entreposerList.add(u);
            }

        }



        //List<Entreposer> entreposerList = entreposerRepository.findByProjetAndEntrepotAAndTransfertDispoGreaterThan(projet, entrepot, "0");

        List<Ventreposage> ventreposages = new ArrayList<Ventreposage>();

        for (Entreposer e :entreposerList) {

            Ventreposage ventreposage = new Ventreposage();
            ventreposage.setEntreposageId(e.getOperationId());
            ventreposage.setEntreposageReference(e.getOperationReference());
            ventreposage.setOperationQte(e.getOperationQte());
           /* ventreposage.setProjetNom(e.getProjet().getProjetNom());*/
            ventreposage.setTransfertDispo(e.getTransfertDispo());
            ventreposage.setClientNom(e.getProjet().getClient().getClient_nom());
            ventreposage.setProduitNom(e.getProjet().getProduit().getProduit_nom());
            ventreposage.setEmetteurNom(e.getProjet().getEmetteur().getEmetteurNom());
            /*ventreposage.setEntrepotNom(e.getEntrepot().getEntrepotNom());*/
            ventreposage.setEntreposageDate(e.getOperation_date());
            ventreposages.add(ventreposage);
        }

        return  ventreposages;
    }

    @RequestMapping(value = "/auth/transfertsDestination/stock" , method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public int getStockQteAvailabeByDestinationEntrepot(@RequestBody EntrepotCriteria entrepotCriteria){

        //Stock stock;
        Projet p = projetRepository.getOne(pro);
        Long idEntrepot = new Long(entrepotCriteria.getEntrepotId());
        Entrepot e = entrepotRepository.getOne(idEntrepot);
        Stock s = stockRepository.findByProjetAndEntrepot(p,e);
        int qteStock;

        if (s == null){
            qteStock = 0;
        }
        else{
            qteStock = s.getStockQuantite();
        }

        //int i = 0;
        return qteStock;

    }


    @RequestMapping(value = "/responsablestock/validations/{id}", method = RequestMethod.GET)
    public String viewValidation(@PathVariable Long id, Model model){


        String s = "";
        String message = "";

        if (!validationTransfertService.isPresent(id)){

            message = "Désolé cette validation est introuvable ";

            model.addAttribute("message", message);

            s = "error/validation";

        } else {

            ValidationTransfert validationTransfert = validationTransfertService.byId(id);

            if (validationTransfert.getStatut().equals(Statut.EN_COURS)){

                ValidationTransfertResponse validationTransfertResponse = validationTransfertService.toDTO(validationTransfert);
                model.addAttribute("validationTransfertResponse", validationTransfertResponse);
                s = "transfert/validation";



            } else {
                message = "Désolé cette validation ne dispose pas de statut autorisant à faire valider la demande";
                model.addAttribute("message", message);
                s = "error/validation";

            }

        }






        model.addAttribute("title", "Validation de transfert");
        return s;
    }

    @RequestMapping(value = "/responsablestock/validations/accept/{id}", method = RequestMethod.GET)
    public String acceptValidation( @PathVariable Long id, Model model, RedirectAttributes redirectAttributes, Principal principal) throws InterruptedException {

        ValidationTransfert validationTransfert = validationTransfertService.byId(id);
        Entreposer entreposer = validationTransfert.getEntreposer();
        Projet projet = entreposer.getProjet();
        Entrepot entrepotSource = entreposer.getEntrepot();
        //Transfert transfert = new Transfert();
        int qte = validationTransfert.getTransfertQte();

        // Check if Stock quantite is available
        Boolean result = stockService.checkIfStockAvailable(projet, entrepotSource, qte);

        String messageValue = null;
        String messageVariable = null;

        if (!result){

            //stock unavailable
            messageVariable = "messagestock";
            messageValue = "Le stock du project : " + projet.getProjetNom()  +" ayant pour entrepot " + entrepotSource.getEntrepotNom()+" ne dispose pas assez de stock pour effectué cette opération";

        }else {

            //stock available

            ////Get entrepot by id
            //Entrepot entrepotDestination = entrepotRepository.getOne(eDestination);

            Entrepot entrepotDestination = validationTransfert.getEntrepotDestination();

            //Init Stock
            Stock stock;

            ////Get Stock by entrepot and Projet
            Stock sSource = stockRepository.findByProjetAndEntrepot(entreposer.getProjet(), entreposer.getEntrepot());
            Stock sDestination = stockRepository.findByProjetAndEntrepot(entreposer.getProjet(), entrepotDestination);

            ////Get Transfert reference
            String reference = this.getReference();

            ////Get DateTime at now
            LocalDateTime date = LocalDateTime.now();

            //Get User
            AppUser user = userRepository.findByUserName(principal.getName());

            ////Get Qte Stock Source & Destination
            //Source
            int stockInitialSource = sSource.getStockQuantite();
            int stockFinalSource = sSource.getStockQuantite() - qte ;

            //Initialisation
            int stockInitialDestination;
            int stockFinalDestination;
            // Check if Destination stock exist if exist use it or create one new
            if (sDestination == null)
            {
                //Init Initial & final Destination
                stockInitialDestination = 0;
                stockFinalDestination = qte;

                //Create new stock
                stock = new Stock();
                stock.setProjet(projet);
                stock.setEntrepot(entrepotDestination);


            }
            else {
                stockInitialDestination = sDestination.getStockQuantite();
                stockFinalDestination = sDestination.getStockQuantite() + qte;
                stock = sDestination;
            }
            //Destination

            int dispoTransfert = entreposer.getTransfertDispo() - qte;


            //Intitialise new transfert and create.
            Transfert t = new Transfert();

            t.setOperationReference(reference);
            t.setOperationQte(qte);
            t.setOperation_date(date);
            t.setEstDisponible(false);
            t.setProjet(entreposer.getProjet());
            t.setUser(user);
            t.setStockInitialSource(stockInitialSource);
            t.setStockFinalSource(stockFinalSource);
            t.setStockInitialDestination(stockInitialDestination);
            t.setStockFinalDestination(stockFinalDestination);
            t.setEntreposer(entreposer);
            t.setEntrepotSource(entrepotSource);
            t.setEntrepotDestination(entrepotDestination);
            t.setOperationDateSaisie(validationTransfert.getDateSaisie());

            //Creer le transfert
            transfertRepository.save(t);

            ////Mettre à jour dispoTransfert et entreproser.
            entreposer.setTransfertDispo(dispoTransfert);
            entreposerRepository.save(entreposer);

            ////Create new Entreposage
            //Get entreposage reference
            String eReference = entreposageController.getReference();
            //Initialise
            Entreposer e = new Entreposer();
            //Set
            e.setOperationReference(eReference);
            e.setOperationQte(qte);
            e.setOperation_date(date);
            e.setEstDisponible(false);
            e.setProjet(entreposer.getProjet());
            e.setUser(user);
            e.setQteVerse(qte);
            e.setQteRestante(qte);
            e.setEstLivrable(true);
            e.setTransfertDispo(qte);
            e.setReception(entreposer.getReception());
            e.setEntrepot(entrepotDestination);
            //Persist
            entreposerRepository.save(e);

            // Create or Update Stock de destination if exist or not exist
            stock.setStockQuantite(stockFinalDestination);
            stock.setStockDate(date);
            stock.setUser(user);
            stockRepository.save(stock);

            // Update Stock Source
            sSource.setStockQuantite(stockFinalSource);
            sSource.setStockDate(date);
            sSource.setUser(user);
            stockRepository.save(sSource);


            /*            //Check if quantité stock est superieur à seuil de securité Si false, Envoyé message de notification
            Boolean checkSeuil = stockService.seuilSecuriteDisponible(sSource);

            if (checkSeuil == false){

                String projetNom = sSource.getProjet().getProjetNom().toUpperCase();
                String entrepotNom = sSource.getEntrepot().getEntrepotNom().toUpperCase();
                String sujet = "Notification Stock | Seuil de sécurité atteint | Transfert | Projet : " + projetNom;
                String message = "A tous" + System.lineSeparator() +
                        "Le stock relatif au projet : " + projetNom + " emmagasiné a l'entrepôt : " + entrepotNom +" a depassé le seuil de sécurité." + System.lineSeparator()+
                        "  - Quantité seuil: " + sSource.getProjet().getSeuilProjet() +"  - Stock disponible" +
                        "  - Stock disponible: " +sSource.getStockQuantite() + System.lineSeparator() +
                        "  - Action : ENLEVEMENT " + System.lineSeparator() +
                        System.lineSeparator() +
                        "L'Administrateur" + System.lineSeparator()+
                        System.lineSeparator() +
                        "Ceci est un message generé automatiquement. Nous vous prions de ne pas repondre à ce message";

                try {
                    notificationService.sendEmail( sujet, message, user, sSource.getProjet());
                }catch (Exception exception){
                    System.out.println(exception.getMessage());
                }


            }*/



            //Mettre a jour le validation de  transfert

            validationTransfertService.updateStatutValidationTransfert(validationTransfert, Statut.VALIDER);

            //Enregistrer le transfert dans avec statut accepter

            //Mettre a jour  le stock


            //Creer l'historique
            String commentaire = "";
            HistoriqueValidationRequest request = new HistoriqueValidationRequest();
            request.setValidationTransfert(validationTransfert);
            request.setStatut(Statut.VALIDER);
            request.setCommentaires(commentaire);
            request.setUser(user);
            historiqueValidationService.createHistoriqueValidation(request);

            //Notifier l'utilisateur qui a emis la demande

            String subject ="[Ref :" + validationTransfert.getReference()+ "] Notification Transfert | Votre demande de transfert a été validée";
            String messageEmail = "Hello " + validationTransfert.getUser().getUserName()+ ","+System.lineSeparator() +
                    "Nous vous informons que votre demande de transfert du projet : " + projet.getProjetNom()+ " a été validée par :  " + user.getUserName() + "." + System.lineSeparator() +
                    "Le transfert se fera depuis l'entrepot : " + entrepotSource.getEntrepotNom() + " vers : " + entrepotDestination.getEntrepotNom()+ "."+ System.lineSeparator()+
                    "La quantité est : " + qte + "."+ System.lineSeparator() +
                    "Merci." + System.lineSeparator() +
                    "L'administrateur." + System.lineSeparator()+
                    System.lineSeparator() +
                    "Ceci est un message generé automatiquement. Nous vous prions de ne pas repondre à ce message";

            String to = validationTransfert.getUser().getEmail();


            notificationService.sendEmailValidation(subject, messageEmail, user, projet, to);

            String message = "Transfert effectué avec succès | Quantité : " +qte + "| Entrepot Source : " + entrepotSource.getEntrepotNom() + "| Entrepot Destination : " + entrepotDestination.getEntrepotNom();

            messageVariable = "messagesucces";
            messageValue = message;
        }


        redirectAttributes.addFlashAttribute(messageVariable,messageValue);

        return "redirect:/auth/transfert";
    }

    @RequestMapping(value = "/responsablestock/validations/refuse/{id}", method = RequestMethod.GET)
    public String refuseValidation( @PathVariable Long id,  Model model, RedirectAttributes redirectAttributes, Principal principal) throws InterruptedException {


        //Mettre à jour le statut de la demande

        ValidationTransfert validationTransfert = validationTransfertService.byId(id);

        validationTransfertService.updateStatutValidationTransfert(validationTransfert, Statut.REFUSER);

        //Get User
        AppUser user = userRepository.findByUserName(principal.getName());

        //Creer l'historique
        String commentaire = "";
        HistoriqueValidationRequest request = new HistoriqueValidationRequest();
        request.setValidationTransfert(validationTransfert);
        request.setStatut(Statut.REFUSER);
        request.setCommentaires(commentaire);
        request.setUser(user);
        historiqueValidationService.createHistoriqueValidation(request);




        //Notifier l'utilisateur qui a emis la demande

        String subject ="[Ref :" + validationTransfert.getReference()+ "] Notification Transfert | Votre demande de transfert a été refusée";
        String messageEmail = "Hello " + validationTransfert.getUser().getUserName()+ ","+System.lineSeparator() +
                "Nous vous informons que votre demande de transfert du projet : " + validationTransfert.getProjet().getProjetNom()+ " a été refusée par :  " + user.getUserName() + "." + System.lineSeparator() +
                "Le transfert devait se faire depuis l'entrepot : " + validationTransfert.getEntrepotSource().getEntrepotNom() + " vers : " + validationTransfert.getEntrepotDestination().getEntrepotNom()+ "."+ System.lineSeparator()+
                "La quantité est : " + validationTransfert.getTransfertQte() + "."+ System.lineSeparator() +
                "Merci." + System.lineSeparator() +
                "L'administrateur." + System.lineSeparator()+
                System.lineSeparator() +
                "Ceci est un message generé automatiquement. Nous vous prions de ne pas repondre à ce message";

        String to = validationTransfert.getUser().getEmail();

        notificationService.sendEmailValidation(subject, messageEmail, user, validationTransfert.getProjet(), to);

        String message = "Demande de transfert refusée avec succès | Quantité : " +validationTransfert.getTransfertQte() + "| Entrepot Source : " + validationTransfert.getEntrepotSource().getEntrepotNom() + "| Entrepot Destination : " + validationTransfert.getEntrepotDestination().getEntrepotNom();

        redirectAttributes.addFlashAttribute("messagesucces",message);
        return "redirect:/auth/transfert";
    }


    @RequestMapping(value = "/auth/historiquevalidationtransferts/all")
    @ResponseBody
    public List<HistoriqueValidationResponse> allHistoriqueValidationTransfertJson() {

        return historiqueValidationService.all();

    }






    private String getReference(){

        Transfert lastTransfert = transfertRepository.findTopByOrderByOperationIdDesc();
        LocalDate date = LocalDate.now();
        String masque = "TRA-";
        String d = date.toString().replaceAll("-","");
        String da = d.substring(2, d.length());
        if (lastTransfert == null)
        {
            int id = 1;
            String operationReference = masque + da + "-" + id;
            return operationReference;
        }
        else {
            Long l = lastTransfert.getOperationId();
            int id = l.intValue()+1;
            String operationReference = masque + da + "-" + id;
            return operationReference;
        }


    }

}
