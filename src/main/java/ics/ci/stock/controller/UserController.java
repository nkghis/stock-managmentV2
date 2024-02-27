package ics.ci.stock.controller;

import ics.ci.stock.entity.*;
import ics.ci.stock.repository.RoleRepository;
import ics.ci.stock.repository.UserRepository;
import ics.ci.stock.repository.UserRoleRepository;
import ics.ci.stock.utils.EncrytedPasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final UserRoleRepository userRoleRepository;

    public UserController(UserRepository userRepository, RoleRepository roleRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @RequestMapping(value = "/acces/users")
    public String indexUser(Model model, HttpServletRequest request/*, Principal principal*/){


        //Get List of Rolename form a user
        /*
        AppUser user = userRepository.findByUserName(principal.getName());

        List<String> kk = user.getRoles().stream().map(AppRole::getRoleName).collect(Collectors.toList());

         */



        List<AppUser> users = new ArrayList<AppUser>();

        //String roles = "";


        if (request.isUserInRole("ROLE_ADMIN")) {

            /*List<AppRole> ros = new ArrayList<AppRole>();
            Collection<AppRole> appRoles;*/

             users = userRepository.findAll(Sort.by(Sort.Direction.DESC,"userId"));
             users = this.getUserListWithRoleInString(users);

             /*for (AppUser u : users){
                 appRoles = u.getRoles();
                 ArrayList<String> arrayList = new ArrayList<>();
                 for (AppRole a : appRoles){

                     String s = a.getRoleName();
                     arrayList.add(s);
                 }


                 String role = Arrays.toString(arrayList.toArray()).replace("[", "").replace("]", "");
                 u.setMesroles(role);
             }*/

        }

        else {
            String r = "ROLE_ADMIN";
            AppRole role = roleRepository.findByRoleName(r);
            List<UserRole> listUsers = userRoleRepository.findByAppRoleIsNot(role);
            List<AppUser> allusers = new ArrayList<AppUser>();

            for (UserRole userrole:listUsers) {

                AppUser myuser = userrole.getAppUser();
                allusers.add(myuser);
            }

             users = allusers;
             users = this.getUserListWithRoleInString(users);
        }
        //List<AppUser> users = userRepository.findAll();

        model.addAttribute("listusers",users);
        model.addAttribute("title", "Utilisateur - Liste");
        return "user/index";
    }

    @RequestMapping(value = "/acces/users/new", method = RequestMethod.GET)
    public String newUser(Model model, HttpServletRequest request){

        List<AppRole> roles = new ArrayList<AppRole>();

        String r = "ROLE_ADMIN";

        if (request.isUserInRole(r)) {
            roles = roleRepository.findAll();
        }
        else {

            roles = roleRepository.findByRoleNameIsNot(r);
        }

        //List<AppRole> roles = roleRepository.findAll();
        model.addAttribute("monuser",new AppUser());
        model.addAttribute("title", "Utilisateur - Nouveau");
        model.addAttribute("roles", roles);
        return "user/new";

    }

    @RequestMapping(value = "/acces/users/save", method = RequestMethod.POST)
    public String saveUser(@Valid AppUser user, Errors errors, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){

        if (errors.hasErrors()){
            System.out.println("error YES");
            model.addAttribute("monuser", new AppUser());
            //model.addAttribute("errors", errors);
            return "user/new";
        }
        //Encrypt Password
        String password = EncrytedPasswordUtils.encrytePassword(user.getEncrytedPassword());
        user.setEncrytedPassword(password);;
        user.setEnabled(true);

        // Save User
        AppUser u = userRepository.save(user);

        //Get role form Select multiple value
        String[] selected = request.getParameterValues("role");

        //Add role
        for (String s : selected){

            Long id = Long.parseLong(s);
            AppRole role = roleRepository.getOne(id);
            UserRole userRole = new UserRole();
            userRole.setAppUser(u);
            userRole.setAppRole(role);

            userRoleRepository.save(userRole);


        }
        redirectAttributes.addFlashAttribute("messagesucces","Opération éffectée avec succès");
        return "redirect:/acces/users";
    }

    @RequestMapping(value = "/acces/users/edit/{id}", method = RequestMethod.GET)
    public String editUser(@PathVariable Long id, Model model){

        AppUser u = userRepository.getOne(id);

        List<AppRole>  roles = roleRepository.findAll();

        Collection<AppRole> selected = u.getRoles();

        //selected = u.

        model.addAttribute("user", u);
        model.addAttribute("roles", roles);
        model.addAttribute("selected", selected);
        model.addAttribute("title", "Utilisateur - Edition");
        return "user/edit";
    }

    @RequestMapping(value = "/admin/users/delete/{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes){

        AppUser appUser = userRepository.getOne(id);
        List<UserRole> userroles = userRoleRepository.findByAppUser(appUser);

        for (UserRole userrole:userroles) {

            Long userroleId = userrole.getId();
            userRoleRepository.deleteById(userroleId);
        }

        userRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("messagesucces","Utilisateur "+ appUser.getUserName() +" a été supprimé avec succès ");
        return "redirect:/acces/users";
    }

    @RequestMapping(value = "/acces/users/finByUsername/{name}", method = RequestMethod.GET)
    @ResponseBody
    //public String findUser(@PathVariable String name){
    public Boolean findUser(@PathVariable String name){
        String username = name;
        //AppUser user = userRepository.findByUserName(name);
        Boolean u = userRepository.existsByUserName(name);
        /*if (user == null){

            System.out.println("not found");
            String msg = "<span style=\"color:green;\">Nom d'utilisateur disponible</span>";
            *//*String msg = "<p>Username unavailable</p>";*//*
            //String mp = "/";
            return msg;
            //return "redirect:/admin/users/new";
        }
        else{
            System.out.println("trouve");
            *//*String msg = "<span style="+"color:red;"+">Username unavailable</span>";*//*
            String msg = "<span style=\"color:red;\">Nom d'utilisateur indisponible</span>";
            *//*String msg = "<p>Username available</p>";*//*
            //return "redirect:/admin/users/new";
            return msg;
        }*/
        //return "redirect:/admin/users/new";
        return u;
    }

    @RequestMapping(value = "/acces/users/update", method = RequestMethod.POST)
    public String saveUser(@Valid AppUser user, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){

        if (bindingResult.hasErrors()){
            System.out.println("error YES");
            model.addAttribute("monuser", new AppUser());
            //model.addAttribute("errors", errors);
            return "user/new";
        }

        //Get muser by id
        AppUser myuser = userRepository.getOne(user.getUserId());

        String p = myuser.getEncrytedPassword();

        //init role
        String[] selected = request.getParameterValues("role");

        //Get roles by muser
        Collection<AppRole> mesroles = myuser.getRoles();

        String asPassword = user.getEncrytedPassword();
        String password = "";
        if (asPassword.isEmpty())
        {
             password = myuser.getEncrytedPassword();
        }
        else{

            password =  EncrytedPasswordUtils.encrytePassword(user.getEncrytedPassword());
        }

        user.setUserName(myuser.getUserName());
        user.setEmail(myuser.getEmail());
        user.setEnabled(true);
        user.setEncrytedPassword(password);
        userRepository.save(user);

        //Add role
        for (String s : selected){
            Long id = Long.parseLong(s);
            AppRole role = roleRepository.getOne(id);
            UserRole userRole = new UserRole();
            userRole.setAppUser(myuser);
            userRole.setAppRole(role);
            userRoleRepository.save(userRole);
        }
        redirectAttributes.addFlashAttribute("messagesucces","Utilisateur [" + user.getUserName()+"] mis à jour avec succès");
        return "redirect:/acces/users";
    }

    /*
    * Parametre Listes Utilisateurs
    * Retour Listes Utilisateurs
    *
    * Recupere la liste des utilisateurs puis pour chaque utilisateur recupere la liste des roles et la convertie en chaine de caracteres attribue
    * cette chaine de caratere au champs @Transient "mesroles" et la remet l'utilisateur dans la liste
    * */
    private List<AppUser> getUserListWithRoleInString(List<AppUser> Users){


        //Init Collection
        Collection<AppRole> appRoles;
        for (AppUser u : Users){
            //Get roles for a user
            appRoles = u.getRoles();
            //ini array list
            ArrayList<String> arrayList = new ArrayList<>();

            for (AppRole a : appRoles){
                // Get User Role Name
                String s = a.getRoleName();

                //Add in list array
                arrayList.add(s);
            }

            // Convert Array to string without bracket
            String role = Arrays.toString(arrayList.toArray()).replace("[", "").replace("]", "");

            //Set String to mesroles;
            u.setMesroles(role);
        }

        return Users;
    }




}
