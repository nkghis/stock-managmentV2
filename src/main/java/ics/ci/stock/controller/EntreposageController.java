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
public class EntreposageController {

    @Autowired
    //private EntreposageRepository entreposageRepository;
    private EntreposerRepository entreposerRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private VreceptionTrueDispoRepository trueDispoRepository;

    @Autowired
    private EntrepotRepository entrepotRepository;

    @Autowired
    private ReceptionRepository receptionRepository;

    @Autowired
    private StockRepository stockRepository;

    @RequestMapping(value = "/agent/distributions", method = RequestMethod.GET)
    public String indexEntreposage(Model model){

        model.addAttribute("title", "Entrée - Liste");
        return "entreposage/index";
    }

    @RequestMapping(value = "/agent/distributions/new", method = RequestMethod.GET)
    public String newEntreposage(Model model){


        model.addAttribute("title", "Entrée - Nouveau");
        return "entreposage/new";
    }


    @RequestMapping(value = "/agent/distributions/save", method = RequestMethod.POST)
    public String saveDistribution(@Valid Entreposer entreposage, BindingResult bindingResult, Model model, Principal principal, HttpServletRequest request, Stock stk, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError());
            return "redirect:/agent/distributions/new";
        }
        //Get id Operation since form
        String s = request.getParameter("reception");

        //Parse String to Long
        Long operationId = Long.parseLong(s);

        //Get Reception Object
        Reception reception = receptionRepository.getOne(operationId);

        //Get Local date Time
        LocalDateTime date = LocalDateTime.now();

        //Get User AUth
        AppUser user = userRepository.findByUserName(principal.getName());

        //Declaration des quantités depuis le formulaire
        int qteVerseForm = entreposage.getQteVerse();
        int qteRestanteForm = entreposage.getQteRestante();

        //Declaration de qte restante dans Reception
        int qteR = qteRestanteForm - qteVerseForm;

        //Mise à jour de Reception
        if (qteR == 0){
            reception.setEstDisponible(false);
        }else {
            reception.setEstDisponible(true);
        }
        reception.setDispo(qteR);
        receptionRepository.save(reception);



        //Declaration variables
        //Produit produit = reception.getProduit();
        Projet projet = reception.getProjet();
        Entrepot entrepot = entreposage.getEntrepot();

        //Recherche dans la table stock en fonction de produit, projet, entrepot
        //Stock stock = stockRepository.findByProduitAndAndProjetAndEntrepot(produit, projet, entrepot);
        Stock stock = stockRepository.findByProjetAndEntrepot(projet, entrepot);





        //Enregistrement de entreposage.
        entreposage.setProjet(reception.getProjet());
        entreposage.setOperation_qte(entreposage.getQteVerse());
        entreposage.setQteRestante(entreposage.getQteVerse());
        entreposage.setEstLivrable(true);
        entreposage.setOperation_date(date);
        entreposage.setUser(user);
        entreposage.setReception(reception);




        //Debut enregistrement du stock.

        if (stock == null){

            //Persistence (insert) table stocks si nouveau stock en fonction du projet, produit et entrepot exist
            //La variable stk a ete declaré et instancié par injection dans RequestMapping.
            //stk.setProduit(produit);
            stk.setProjet(projet);
            stk.setEntrepot(entrepot);
            stk.setUser(user);
            stk.setStockDate(date);
            stk.setStockQuantite(entreposage.getQteVerse());

            //affectection de stock initial et final
            //entreposage.setStockInitial(entreposage.getQteVerse());
            entreposage.setStockInitial(0);
            entreposage.setStockFinal(entreposage.getQteVerse());

            //perssist stock
            stockRepository.save(stk);
        }
        else {

            int quantite = stock.getStockQuantite() + entreposage.getQteVerse();

            //affectection de stock initial et final
            entreposage.setStockInitial(stock.getStockQuantite());
            entreposage.setStockFinal(quantite);

            //persistence (update) table stock si le stock en fonction du projet, produit, entrepot  exist


            stock.setUser(user);
            stock.setStockDate(date);
            stock.setStockQuantite(quantite);



            //perssist stock
            stockRepository.save(stock);
        }


        //persist entreposage
        entreposerRepository.save(entreposage);
        //Notification et redirection
        redirectAttributes.addFlashAttribute("messagedistribution","Entréé éffectée avec succès");
        return "redirect:/agent/distributions";
    }

    @RequestMapping(value = "/admin/distributions/edit/{id}", method = RequestMethod.GET)
    public String editDistribution(@PathVariable Long id, Model model){
        model.addAttribute("title", "Entreposage - Edition");
        return "entreposage/edit";
    }

    @RequestMapping(value = "/admin/distributions/update", method = RequestMethod.POST)
    public String updateDistribution(@Valid Entreposer entreposage, BindingResult bindingResult, Model model, Principal principal){

        return "";
    }

    @RequestMapping(value = "/agent/distributions/all")
    @ResponseBody
    public List<VreceptionTrueDispo> allReceptionTrueJson(Model model) {


        return  trueDispoRepository.findAll();

    }

    @RequestMapping(value = "/agent/distributions/entrepots/{id}", method = RequestMethod.GET)
    public String DistribuerEntrepot(@PathVariable Long id, Model model){

        List<Entrepot> entrepots = entrepotRepository.findAll();
        Reception reception = receptionRepository.getOne(id);

        int qterestant = reception.getDispo();

        //Get reference.
        String distributionReference = getReference();




        //Entreposage entreposage = new Entreposage();
        Entreposer entreposage = new Entreposer();

        entreposage.setOperationReference(distributionReference);
        entreposage.setQteRestante(qterestant);

        model.addAttribute("madistribution", entreposage);
        model.addAttribute("entrepots",entrepots);
        model.addAttribute("reception",reception);
        model.addAttribute("title", "Entrée - Affectation d'une réception à un entrepôt ");
        return "entreposage/new";
    }

    @RequestMapping(value = "/admin/distributions/delete/{id}", method = RequestMethod.GET)
    public String deleteDistribuer(@PathVariable Long id, Model model){

        entreposerRepository.deleteById(id);
        return "redirect:/admin/distributions";
    }





    private String getReference(){

        Entreposer lastEntreposage = entreposerRepository.findTopByOrderByOperationIdDesc();
        LocalDate date = LocalDate.now();
        String d = date.toString().replaceAll("-","");
        String da = d.substring(2, d.length());
        if (lastEntreposage == null)
        {
            String masque = "POT-";
            int id = 1;
            String distributionReference = masque + da + "-" + id;
            return distributionReference;
        }
        else {
            Long l = lastEntreposage.getOperationId();
            int id = l.intValue()+1;
            String masque = "POT-";
            String distributionReference = masque + da + "-" + id;
            return distributionReference;
        }


    }




}
