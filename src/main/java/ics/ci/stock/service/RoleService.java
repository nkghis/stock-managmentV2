package ics.ci.stock.service;

import ics.ci.stock.entity.AppRole;
import ics.ci.stock.entity.AppUser;

import java.util.List;

public interface RoleService {

    List<AppRole> getRolesByUser(AppUser user);
}
