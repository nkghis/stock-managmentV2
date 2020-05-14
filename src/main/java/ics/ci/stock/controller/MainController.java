package ics.ci.stock.controller;

import ics.ci.stock.entity.*;
import ics.ci.stock.entity.custom.StockBeforeCustom;
import ics.ci.stock.repository.IStockBeforeCustom;
import ics.ci.stock.repository.VmouvementRepository;
import ics.ci.stock.repository.VoperationRepository;
import ics.ci.stock.repository.VstockRepository;
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








        List<IStockBeforeCustom> st = voperationRepository.stockBeforeCustom(debut);
        IStockBeforeCustom j = getIStockBeforeCustom(st,"OMIS");






        List<Voperation> listOperation = voperationRepository.findAllByDateBetween(debut,fin);





        return "";
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




        List<Vmouvement> listOperation = vmouvementRepository.findAllByDateBetween(debut,fin);


        model.addAttribute("title", "Liste des mouvements");
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
