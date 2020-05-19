package ics.ci.stock.controller;

import ics.ci.stock.entity.*;
import ics.ci.stock.entity.custom.StockBeforeCustom;
import ics.ci.stock.entity.custom.StockBetween;
import ics.ci.stock.repository.*;
import ics.ci.stock.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
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

@Controller
public class MainController {

    @Autowired
    private VstockRepository vstockRepository;

    @Autowired
    private VoperationRepository voperationRepository;

    @Autowired
    private VmouvementRepository vmouvementRepository;

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
        model.addAttribute("title", "Tableau de bord");
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


    @RequestMapping(value = "/admin/dashboard/operation", method = RequestMethod.POST)
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
        LocalDateTime fin = lfin.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

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

        //Initialisation de la liste des StockBetween.
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

            //Calcul du stock final
            int stockFinal = stockInitial + var.getEntreposage() + var.getRetour() - var.getEnlevement();

            //Creation du Stock between.
            StockBetween a = new StockBetween();
            a.setProjet(var.getProjet());
            a.setClient(var.getClient());
            a.setProduit(var.getProduit());
            a.setEmetteur(var.getEmetteur());
            a.setStockInitial(stockInitial);
            a.setEntreposage(var.getEntreposage());
            a.setEnlevement(var.getEnlevement());
          /*  a.setEnlevement(var.getEnlevement()-var.getRetour());*/
            a.setRetour(var.getRetour());
            a.setGache(var.getGache());
            //a retire
            a.setLivraison(a.getEnlevement()-a.getRetour());
            a.setStockFinal(stockFinal);
            //a.setStockFinal(var.getEntreposage() - a.getLivraison() + stockInitial);


            //Ajout du stock between dans la list des stock StockBetween.
            listStockBetween.add(a);

        }

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

    @RequestMapping(value = "/admin/dashboard/mouvement", method = RequestMethod.POST)
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
        LocalDateTime fin = lfin.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String datedebut = formatter.format(ldebut);
        String datefin = formatter.format(lfin);




        List<Vmouvement> listOperation = vmouvementRepository.findAllByDateBetween(debut,fin);


        model.addAttribute("title", "Liste des mouvements - du " +datedebut+ " au " +datefin);
        model.addAttribute("mouvements", listOperation);

        return "main/mouvement";
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
}
