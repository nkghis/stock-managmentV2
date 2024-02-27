package ics.ci.stock.service.impl;

import ics.ci.stock.entity.AppRole;
import ics.ci.stock.entity.AppUser;
import ics.ci.stock.repository.RoleRepository;
import ics.ci.stock.repository.UserRepository;
import ics.ci.stock.repository.UserRoleRepository;
import ics.ci.stock.service.RoleService;
import ics.ci.stock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final RoleService roleService;
    private final UserRepository userRepository;



    private final RoleRepository roleRepository;

    private final UserRoleRepository userRoleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserRoleRepository userRoleRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.roleService = roleService;
    }


    @Override
    public List<AppUser> getUsersByRoleName(String roleName) {

        List<AppUser> users = userRepository.findAll();
        List<AppUser> us = new ArrayList<>();
        for (AppUser user : users){
            Boolean hasrole = this.hasRole(user, roleName);
            if (hasrole){
                us.add(user);
            }
        }

        return us;
    }

    @Override
    public AppRole getRoleByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    @Override
    public Boolean hasRole(AppUser user, String roleName) {
        List<AppRole> roles = roleService.getRolesByUser(user);
        List<String> stringRoles = new ArrayList<String>();
        for (AppRole r : roles){
            String rname = r.getRoleName();
            stringRoles.add(rname);
        }
        Boolean role = false;
        Boolean roleContain = stringRoles.contains(roleName);
        if (roleContain){
            role = true;
        }else {
            role = false;
        }

        return role;
    }

    @Override
    public String[] getEmails(List<AppUser> users) {

        List<String> emailsList = new ArrayList<>();
        for (AppUser user : users){
            emailsList.add(user.getEmail());
        }

        return emailsList.toArray(new String[0]);
    }

    @Override
    public String[] getEmails(String roleName) {

        List<AppUser> users = this.getUsersByRoleName(roleName);
        return this.getEmails(users);
    }
}
