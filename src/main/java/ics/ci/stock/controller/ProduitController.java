package ics.ci.stock.controller;

import ics.ci.stock.entity.Operation;
import ics.ci.stock.entity.Produit;
import ics.ci.stock.entity.Reception;
import ics.ci.stock.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProduitController {
    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReceptionRepository receptionRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private EnlevementRepository enlevementRepository;


    @RequestMapping(value = "/admin/produits")
    public String index(Model model) {
        List<Produit> produit = produitRepository.findAll();
        model.addAttribute("produits", produit);
        model.addAttribute("title", "Produit - Liste");

        //Long i = receptionRepository.count();
        List<Reception> receptions = receptionRepository.findAll();
        //Reception receptionss   = receptionRepository.
        //Reception r = receptionRepository.
        List<Operation> reception = operationRepository.findAll();
        Operation r = operationRepository.findByOperationReference("test2");
        Reception reception1 = receptionRepository.findTopByOrderByOperationIdDesc();
        //Operation o = operationRepository.findByOperation_ref("test2");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        //System.out.println(r);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        /*Reception o = userRepository.operation();
        String r = Long.toString(o.getOperationId());
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(r);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");*/
        return "produit/index";
    }

    @RequestMapping(value = "/admin/produits/new", method = RequestMethod.GET)
    public String newProduit(Model model){

        model.addAttribute("monproduit",new Produit());
        model.addAttribute("title", "Produit - Nouveau");
        return "produit/new";

    }


    @RequestMapping(value = "/admin/produits/save", method = RequestMethod.POST)
        public String saveProduit(@Valid Produit produit, Errors errors, Model model, RedirectAttributes redirectAttributes){

        if (errors.hasErrors()){
            System.out.println("error YES");
            model.addAttribute("monproduit", new Produit());
            //model.addAttribute("errors", errors);
            return "produit/new";
        }
        produitRepository.save(produit);
        redirectAttributes.addFlashAttribute("messagesucces","Opération éffectée avec succès");
        return "redirect:/admin/produits";
    }

    @RequestMapping(value = "/admin/produits/edit/{id}", method = RequestMethod.GET)
    public String editProduit(@PathVariable Long id, Model model){

        Produit c = produitRepository.getOne(id);
        model.addAttribute("produit", c);

        System.out.println(c.getProduit_nom());
        model.addAttribute("title", "Produit - Edition");
        return "produit/edit";
    }


    @RequestMapping(value = "/admin/produits/delete/{id}", method = RequestMethod.GET)
    public String deleteProduit(@PathVariable Long id){

        produitRepository.deleteById(id);
        return "redirect:/admin/produits";
    }

}
