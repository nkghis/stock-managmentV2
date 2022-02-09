package ics.ci.stock.controller;


import ics.ci.stock.entity.*;
import ics.ci.stock.entity.custom.EntrepotCriteria;
import ics.ci.stock.repository.*;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
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

    @Autowired
    private TransfertRepository transfertRepository;



    @Autowired
    private ProjetRepository projetRepository;

    @Autowired
    private EntrepotRepository entrepotRepository;

    @Autowired
    private EntreposerRepository entreposerRepository;

   /* @Autowired
    private ReceptionRepository receptionRepository;*/

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntreposageController entreposageController;



    public  long pro;
    public  long ent;
    public  String cre = "CREAPUB";
    public  String nat = "NATHAN";

    //public Entrepot creapub ;/*= entrepotRepository.findByEntrepotNom(cre);*/
    //public Entrepot nathan ;/*= entrepotRepository.findByEntrepotNom(nat);*/

    @RequestMapping(value = "/auth/transfert", method = RequestMethod.GET)
    public String indexTransfert(Model model){

        model.addAttribute("title", "Transfert de stocks  - Liste");
        return "transfert/index";
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
    public String saveTransfert( @Valid Transfert transfert, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, Principal principal, HttpServletRequest request) {


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

        ////Get entrepot by id
        Entrepot entrepotDestination = entrepotRepository.getOne(eDestination);

        //Init Stock
        Stock stock;

        ////Get Stock by entrepot and Projet
        Stock sSource = stockRepository.findByProjetAndEntrepot(entreposer.getProjet(),entreposer.getEntrepot());
        Stock sDestination = stockRepository.findByProjetAndEntrepot(entreposer.getProjet(),entrepotDestination);


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

            //Creaete new stock
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


        ////Intitialise new transfert and create.
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

            String message = "Transfert effectué avec succès | Quantité : " +qte + "| Entrepot Source : " + entrepotSource.getEntrepotNom() + "| Entrepot Destination : " + entrepotDestination.getEntrepotNom();
        //redirectAttributes.addFlashAttribute("messagesucces","Opération éffectée avec succès");
        redirectAttributes.addFlashAttribute("messagesucces",message);
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
