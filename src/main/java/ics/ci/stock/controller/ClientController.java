package ics.ci.stock.controller;

import ics.ci.stock.entity.Client;
import ics.ci.stock.repository.ClientRepository;
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
public class ClientController {

    private final ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @RequestMapping(value = "/auth/clients")
    public String index(Model model){



        List<Client> clients = clientRepository.findAll();
        model.addAttribute("listClients", clients);
        model.addAttribute("title", "Client - Liste");
        return "client/index";
    }

    @RequestMapping(value = "/auth/clients/new", method = RequestMethod.GET)
    public String newClient(Model model){

        model.addAttribute("monclient",new Client());
        model.addAttribute("title", "Client - Nouveau");
        return "client/new";

    }


    @RequestMapping(value = "/auth/clients/save", method = RequestMethod.POST)
    public String saveClient(@Valid Client client, Errors errors, Model model, RedirectAttributes redirectAttributes){

        if (errors.hasErrors()){
            System.out.println("error YES");
            model.addAttribute("monclient", new Client());
            //model.addAttribute("errors", errors);
            return "client/new";
        }
        client.setClient_nom(client.getClient_nom().toUpperCase());
        clientRepository.save(client);
        redirectAttributes.addFlashAttribute("messagesucces","Opération éffectée avec succès");
        return "redirect:/auth/clients";
    }



    @RequestMapping(value = "/auth/clients/edit/{id}", method = RequestMethod.GET)
    public String editClient(@PathVariable Long id, Model model){

        Client c = clientRepository.getOne(id);

        model.addAttribute("client", c);
        model.addAttribute("title", "Client - Edition");
        return "client/edit";
    }

    @RequestMapping(value = "/auth/clients/delete/{id}", method = RequestMethod.GET)
    public String deleteClient(@PathVariable Long id){

        clientRepository.deleteById(id);
        return "redirect:/auth/clients";
    }
}
