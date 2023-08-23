package ics.ci.stock.controller;

import ics.ci.stock.entity.*;
import ics.ci.stock.entity.custom.DestructionDto;
import ics.ci.stock.repository.*;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class DestructionController {

    @Autowired
    private DestructionRepository  destructionRepository;

    @Autowired
    private EntreposerRepository entreposerRepository;

    @Autowired
    private RessourceRepository ressourceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockService stockService;

    @RequestMapping(value = "/destruction/destructions", method = RequestMethod.GET)
    public String indexDestruction(Model model){

        //retrive data by json see method on allDestructionDtoJson()

        model.addAttribute("title", "Destruction de stocks  - Liste");
        return "destruction/index";
    }


    @RequestMapping(value = "/destruction/destructions/save", method = RequestMethod.POST)
    public String saveTransfert(@Valid Destruction destruction, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, Principal principal, HttpServletRequest request) {


        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError());
            model.addAttribute("madestruction", destruction);
            return "redirect:/destruction/destructions/" + destruction.getOperationId();
        }

        //Get id entreposage since form
        String d = request.getParameter("entreposer");

        //Parse String to Long
        Long ditribuerId = Long.parseLong(d);

        //Get Reception Object

        Entreposer entreposage = entreposerRepository.getOne(ditribuerId);

        //Declaration variables
        Projet projet = entreposage.getProjet();
        Entrepot entrepot = entreposage.getEntrepot();
        int qte = destruction.getOperation_qte();

        // Check if Stock quantite is available
        Boolean result = stockService.checkIfStockAvailable(projet, entrepot, qte);

        String messageValue = null;
        String messageVariable = null;



        if (result == false){

            //stock unavailable
            messageVariable = "messagestock";
            messageValue = "Le stock du project : " + projet.getProjetNom()  +" ayant pour entrepot " + entrepot.getEntrepotNom()+" ne dispose pas assez de stock pour effectué cette opération";

        }else {

            //Get Local date Time
            LocalDateTime date = LocalDateTime.now();

            //Get User AUth
            AppUser user = userRepository.findByUserName(principal.getName());

            //Declaration des quantités depuis le formulaire
            int qteVerseForm = destruction.getOperation_qte();
            int qteRestanteForm = destruction.getEnlevementDispo();

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


            //Enregistrement de destruction.

            destruction.setEnlevementDispo(destruction.getOperation_qte());
            destruction.setOperation_date(date);
            destruction.setUser(user);
            destruction.setEntreposer(entreposage);
            destruction.setEstDisponible(true);
            destruction.setProjet(projet);
            destruction.setEntrepot(entrepot);
            destruction.setOperationDateSaisie(destruction.getOperationDateSaisie());

            //Recherche dans la table stock en fonction de produit, projet, entrepot
            Stock stock = stockRepository.findByProjetAndEntrepot(/*produit,*/ projet, entrepot);
            int quantite = stock.getStockQuantite() - destruction.getOperation_qte();
            destruction.setStockInitial(stock.getStockQuantite());
            destruction.setStockFinal(quantite);
            stock.setUser(user);
            stock.setStockDate(date);
            stock.setStockQuantite(quantite);
            stockRepository.save(stock);
            destructionRepository.save(destruction);
            System.out.println("save Destruction");

            messageVariable = "messagedestruction";
            messageValue = "Destruction éffectuée avec succès";


        }


        //redirectAttributes.addFlashAttribute("messagedestruction","Destruction éffectuée avec succès");
        redirectAttributes.addFlashAttribute(messageVariable,messageValue);

        return "redirect:/destruction/destructions/";
    }

    @RequestMapping(value = "/destruction/destructions/{id}", method = RequestMethod.GET)
    public String destructionNew(@PathVariable Long id, Model model){

        String reference = getReference();
        Entreposer entreposage = entreposerRepository.getOne(id);
        Destruction destruction = new Destruction();
        destruction.setOperationReference(reference);
        //enlevement.setOperation_qte(entreposage.getQteVerse());
        destruction.setEnlevementDispo(entreposage.getQteRestante());
        destruction.setEntreposer(entreposage);

        //Liste des Objets Ressources & Motifs
        List<Ressource> ressources = ressourceRepository.findAll();

        model.addAttribute("madestruction", destruction);
        model.addAttribute("ressources",ressources);
        model.addAttribute("entreposage", entreposage);
        model.addAttribute("title", "Sortie - Enregistrer une Destruction");

        return "destruction/new";
    }





    @RequestMapping(value = "/destruction/destructions/all")
    @ResponseBody
    public List<DestructionDto> allDestructionDtoJson(Model model){

        List<Destruction> destructions = destructionRepository.findAll();
        List<DestructionDto> dtos = destructionDtoList(destructions);
        return dtos;
    }

    private List<DestructionDto> destructionDtoList (List<Destruction> destructions){

        List<DestructionDto>  dtos = new ArrayList<>();

        for (Destruction destruction : destructions){

            DestructionDto dto = new DestructionDto();
            dto.setOperationId(destruction.getOperationId());
            dto.setOperationReference(destruction.getOperationReference());
            dto.setOperationQte(destruction.getOperationQte());
            dto.setOperationDateSaisie(dateSaisieToString(destruction.getOperationDateSaisie()));
            dto.setOperationDate(dateSystemeToString(destruction.getOperation_date()));

            dto.setProjet(destruction.getProjet().getProjetNom());
            dto.setUser(destruction.getUser().getUserName());
            dto.setEnlevementDispo(destruction.getEnlevementDispo());
            dto.setStockInitial(destruction.getStockInitial());
            dto.setStockFinal(destruction.getStockFinal());
            dto.setRessource(destruction.getRessource().getRessource_nom()+ " " + destruction.getRessource().getRessource_prenoms());
            dto.setEntreposer(destruction.getEntreposer().getOperationId());
            dto.setEntrepot(destruction.getEntrepot().getEntrepotNom());
            dtos.add(dto);

        }

        return dtos;
    }

    private String dateSaisieToString(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        String strDate = formatter.format(date);
        return strDate;
    }

    private String dateSystemeToString(LocalDateTime date){

        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    //Fonction qui formate et retourne la reference
    public String getReference(){

        Destruction lastDestruction = destructionRepository.findTopByOrderByOperationIdDesc();
        LocalDate date = LocalDate.now();
        String d = date.toString().replaceAll("-","");
        String da = d.substring(2, d.length());
        if (lastDestruction == null)
        {
            String masque = "DES-";
            int id = 1;
            String livraisonReference = masque + da + "-" + id;
            return livraisonReference;
        }
        else {
            Long l = lastDestruction.getOperationId();
            int id = l.intValue()+1;
            String masque = "DES-";
            String livraisonReference = masque + da + "-" + id;
            return livraisonReference;
        }


    }

}
