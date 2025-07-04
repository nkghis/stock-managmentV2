package ics.ci.stock.controller;

import ics.ci.stock.dto.DateSearchINPUT;
import ics.ci.stock.entity.*;
import ics.ci.stock.entity.custom.GacheDto;
import ics.ci.stock.entity.custom.StockBeforeCustom;
import ics.ci.stock.entity.custom.StockBetween;
import ics.ci.stock.repository.*;
import ics.ci.stock.service.GacheService;
import ics.ci.stock.utils.DateConvert;
import ics.ci.stock.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private final ProjetRepository projetRepository;

    private final GacheService gacheService;

    private final VstockRepository vstockRepository;

    private final VoperationRepository voperationRepository;

    private final VmouvementRepository vmouvementRepository;

    private final VgacheRepository vgacheRepository;

    private final VgacheMouvementRepository vgacheMouvementRepository;

    public MainController(GacheService gacheService, VstockRepository vstockRepository, VoperationRepository voperationRepository, VmouvementRepository vmouvementRepository, VgacheRepository vgacheRepository, VgacheMouvementRepository vgacheMouvementRepository, ProjetRepository projetRepository) {
        this.gacheService = gacheService;
        this.vstockRepository = vstockRepository;
        this.voperationRepository = voperationRepository;
        this.vmouvementRepository = vmouvementRepository;
        this.vgacheRepository = vgacheRepository;
        this.vgacheMouvementRepository = vgacheMouvementRepository;
        this.projetRepository = projetRepository;
    }

    /*@Autowired
    private  stockBeforeCustom;*/



    @RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
    public String welcomePage(Model model, Principal principal) {

        //Check if user is connected
        if (null == principal){
            model.addAttribute("title", "Authentification");
            return "main/loginPage";
        }


        return "redirect:/dashboard";

    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model, Principal principal) {

        User loginedUser = (User) ((Authentication) principal).getPrincipal();



        String userInfo = WebUtils.toString(loginedUser);


        model.addAttribute("userInfo", userInfo);


        return "main/adminPage";
    }




    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {
        //Check if user is connected
        if (null == principal){
            model.addAttribute("title", "Authentification");
            return "main/loginPage";
        }

        //List<Reception> receptions = receptionRepository.findByEstDisponibleTrue();

        //String test = "test";

        // After user login successfully.
        String userName = principal.getName();


        System.out.println("User Name: " + userName);

        User loginedUser = (User) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);

        List<Vstock> stocks = vstockRepository.findAll();
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("title", "Tableau de bord - Etat de stock");
        model.addAttribute("stocks", stocks);
        model.addAttribute("search", new Search());




        return "main/dashboard";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {

        if (principal != null) {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();

            String userInfo = WebUtils.toString(loginedUser);

            model.addAttribute("userInfo", userInfo);

            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);

        }

        return "main/403Page";
    }

    @RequestMapping(value = "/admin/test", method = RequestMethod.GET)
    public String loginPage(Model model) {
        model.addAttribute("title", "admin page");
        return "main/adminPage";
    }

    //Liste des stock en Json
    @RequestMapping(value = "/stocks")
    @ResponseBody
    public List<Vstock> LivrerJson(Model model){

        return vstockRepository.findAll();
    }


    @RequestMapping(value = "/dashboard/operation", method = RequestMethod.POST)
    public String allOperation( @Valid Search search , Errors errors, Model model, RedirectAttributes redirectAttributes) throws ParseException {

        if (errors.hasErrors()){
            System.out.println("error YES");
            model.addAttribute("search", new Search());

            return "redirect:/dashboard";
        }

        //Date ss = search;
        Search ss = search;

        //Date d = new Date();
        Date ldebut = new SimpleDateFormat("yyyy-MM-dd").parse(search.getDebut());
        LocalDateTime debut = ldebut.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        Date lfin = new SimpleDateFormat("yyyy-MM-dd").parse(search.getFin());
        LocalDateTime fin = lfin.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().withHour(23);

        Date d = new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01");
        LocalDateTime da = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();







        //List de stock par projet avant la date entrée.
        List<IStockBeforeCustom> stockBefore = voperationRepository.stockBeforeCustom(debut);


        //Recherche d'une interface IStockBefore en fonction du nom de projet.
        IStockBeforeCustom j = getIStockBeforeCustom(stockBefore,"OMIS");
        //List des operation en fonction des dates entrées.
        //List<Voperation> listOperation = voperationRepository.findAllByDateBetween(debut,fin);

        //List de stock par projet en fonction des dates entrées.
        //List<IStockBeforeCustom> stockBetween = voperationRepository.stockBetween(debut,fin);
        List<IStockBetweenCustom> stockBetween = voperationRepository.stockBetween(debut,fin);

        //Initialisation de la liste des StockBetween. Liste des stocks pendant une periode
        List<StockBetween> listStockBetween = new ArrayList<>();;


        //Iteration de la liste des StockBetween
        for (IStockBetweenCustom var: stockBetween) {

            //Recuperation du nom du projet
            String nomProjet = var.getProjet();

            //Recherche de l'objet IStockBeforeCustom dans la liste des IStockBeforeCustom ayant pour non de projet "nomProjet".
            IStockBeforeCustom before = getIStockBeforeCustom(stockBefore, nomProjet);

            //initialisation du stock initial
            int stockInitial;

            //Test
            if (before == null)
            {
                stockInitial = 0;

            }


            else {
                stockInitial = before.getStock();
            }

            //Calcul du stock final                                                                         //Ajouter en faveur ajustement
            int stockFinal = stockInitial + var.getEntreposage() + var.getRetour() - var.getEnlevement() + var.getAjustement();

            //Creation du Stock between.
            StockBetween a = new StockBetween();
            a.setProjet(var.getProjet());
            a.setClient(var.getClient());
            a.setProduit(var.getProduit());
            a.setEmetteur(var.getEmetteur());
            a.setStockInitial(stockInitial);
            a.setEntreposage(var.getEntreposage());
            a.setEnlevement(var.getEnlevement());
            a.setRetour(var.getRetour());
            a.setGache(var.getGache());
            //Ajouter en faveur ajustement
            a.setAjustement(var.getAjustement());
            //a retire
            a.setLivraison(a.getEnlevement()-a.getRetour()-a.getGache());
            a.setStockFinal(stockFinal);


            a.setEntrepot(var.getEntrepot());



            //Ajout du stock between dans la list des stock StockBetween.
            listStockBetween.add(a);

        }

        //Liste de stock a jours avant la periode
        List<StockBetween> STOCKBEFORE = this.stockBeforeHaventStockBetween(listStockBetween, stockBefore);

        //Ajout de la liste de stock avant periode à la liste de stock pendant la periode
        listStockBetween.addAll(STOCKBEFORE);

       /* List<StockBetween> last = listStockBetween;
        String aa ="";*/

       //convertion de la date en chaine de caractere et formatage.
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String datedebut = formatter.format(ldebut);
        String datefin = formatter.format(lfin);

        model.addAttribute("title", "etat de stock par projet  - du " +datedebut+ " au " +datefin);
        model.addAttribute("operations", listStockBetween);

        return "main/operation";
    }

    @RequestMapping(value = "/dashboard/mouvement", method = RequestMethod.POST)
    public String allMouvement( @Valid Search search , Errors errors, Model model) throws ParseException {

        if (errors.hasErrors()){
            System.out.println("error YES");
            model.addAttribute("search", new Search());
            //model.addAttribute("errors", errors);
            return "redirect:/dashboard";
        }




        Date ldebut = new SimpleDateFormat("yyyy-MM-dd").parse(search.getDebut());
        LocalDateTime debut = ldebut.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        Date lfin = new SimpleDateFormat("yyyy-MM-dd").parse(search.getFin());
        LocalDateTime fin = lfin.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().withHour(23);

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String datedebut = formatter.format(ldebut);
        String datefin = formatter.format(lfin);




        List<Vmouvement> listOperation = vmouvementRepository.findAllByDateBetween(debut,fin);


        model.addAttribute("title", "Liste des mouvements - du " +datedebut+ " au " +datefin);
        model.addAttribute("mouvements", listOperation);

        return "main/mouvement";
    }

    @RequestMapping(value = {"/dashboard/gache"}, method = {RequestMethod.GET})
    public String sumGacheGroupByProjet( Model model){

        Map<String, Integer> gaches = gacheService.getSumGacheByProject();
        model.addAttribute("title", "Somme des gaches par projet");
        model.addAttribute("gaches", gaches);
        model.addAttribute("search", new DateSearchINPUT());
        return "gache/index";

    }

    @RequestMapping(value = "/dashboard/gache/periode", method = RequestMethod.POST)
    public String gachePeriode(@Valid @ModelAttribute("search") Search search , BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) throws ParseException {

        if (bindingResult.hasErrors()) {
            System.out.println("error YES");
            model.addAttribute("search", new Search());

            return "redirect:/dashboard/gache";
        }

        LocalDateTime datedebut = DateConvert.getDateTime(search.getDebut());
        LocalDateTime datefin = DateConvert.getDateTime(search.getFin());
        Map<String, Integer> gaches = gacheService.getSumGacheByProject(datedebut, datefin);

        model.addAttribute("title", "Liste et somme des gaches en fonction des projets - du " +search.getDebut()+ " au " +search.getFin());
        model.addAttribute("gaches", gaches);
        return "gache/periode";
    }

    @RequestMapping(value = "/dashboard/gache/mouvement", method = RequestMethod.POST)
    public String gacheMouvement(@Valid DateSearchINPUT search , Errors errors, Model model, RedirectAttributes redirectAttributes) throws ParseException {

        if (errors.hasErrors()) {
            System.out.println("error YES");
            model.addAttribute("search", new DateSearchINPUT());
            return "redirect:/dashboard/gache";
        }

       /* Date debut = new SimpleDateFormat("yyyy-MM-dd").parse(search.getDebut());
        Date fin = new SimpleDateFormat("yyyy-MM-dd").parse(search.getFin());*/

        //List<GacheDto> gacheDtos = vgacheMouvementRepository.gachesBetween(debut, fin);

        String s = "";


        return "gache/mouvement";
    }


    //Method who return interface IStockBeforeCustom.
    private IStockBeforeCustom getIStockBeforeCustom(List<IStockBeforeCustom> list, String s)
    {
        IStockBeforeCustom j = list.stream()
                .filter(c->s.equals(c.getProjet()))
                .findAny()
                .orElse(null);
        return j;
    }

    //Method who return StockBetween en fonction d'une liste de de stock et nom de projet.
    private StockBetween getStockBetween(List<StockBetween> list, String s)
    {
        StockBetween j = list.stream()
                .filter(c->s.equals(c.getProjet()))
                .findAny()
                .orElse(null);
        return j;
    }




    private List<StockBetween> stockBeforeHaventStockBetween(List<StockBetween> betweens, List<IStockBeforeCustom> befores ){

       /* List<StockBetween> stocks = new ArrayList<>();

        for ( StockBetween between : betweens){

            IStockBeforeCustom j = this.getIStockBeforeCustom(befores, between.getProjet());

            if (j != null){
                StockBetween stock = new StockBetween();
                stock.setProjet(between.getProjet());
                stock.setClient(between.getClient());
                stock.setProduit(between.getProduit());
                stock.setEmetteur(between.getEmetteur());
                stock.setStockInitial(j.getStock());
                stock.setEntreposage(0);
                stock.setEnlevement(0);
                *//*  a.setEnlevement(var.getEnlevement()-var.getRetour());*//*
                stock.setRetour(0);
                stock.setGache(0);
                //a retire
                stock.setLivraison(0);
                stock.setStockFinal(j.getStock());
                stocks.add(stock);
            }
        }
*/
        List<StockBetween> stocks = new ArrayList<>();

        for ( IStockBeforeCustom before : befores){

            StockBetween j = this.getStockBetween(betweens, before.getProjet());

            if (j == null){
                StockBetween stock = new StockBetween();
                Projet projet = projetRepository.findByProjetNom(before.getProjet());
                stock.setProjet(projet.getProjetNom());
                stock.setClient(projet.getClient().getClient_nom());
                stock.setProduit(projet.getProduit().getProduit_nom());
                stock.setEmetteur(projet.getEmetteur().getEmetteurNom());
                stock.setEntrepot(before.getEntrepot());
                stock.setStockInitial(before.getStock());
                stock.setEntreposage(0);
                stock.setEnlevement(0);
                /*  a.setEnlevement(var.getEnlevement()-var.getRetour());*/
                stock.setRetour(0);
                stock.setGache(0);
                //a retire
                stock.setLivraison(0);
                stock.setStockFinal(before.getStock());
                stocks.add(stock);
            }
        }

        return stocks;


    }

    private Boolean checkProjectNameInList(String projetNom, List<IStockBeforeCustom> befores ){

        IStockBeforeCustom j = this.getIStockBeforeCustom(befores, projetNom);

        if (j == null){
            return false;
        }else {
            return true;
        }
    }
}
