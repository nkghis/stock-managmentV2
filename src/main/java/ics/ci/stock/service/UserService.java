package ics.ci.stock.service;

import ics.ci.stock.entity.AppRole;
import ics.ci.stock.entity.AppUser;

import java.util.List;

public interface UserService {

    List<AppUser> getUsersByRoleName(String roleName);

    AppRole getRoleByRoleName(String roleName);

    Boolean hasRole(AppUser user, String roleName);

    String[] getEmails (List<AppUser> users);
    String[] getEmails (String roleName);




}
