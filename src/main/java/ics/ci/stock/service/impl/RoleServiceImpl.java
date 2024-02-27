package ics.ci.stock.service.impl;

import ics.ci.stock.entity.AppRole;
import ics.ci.stock.entity.AppUser;
import ics.ci.stock.entity.UserRole;
import ics.ci.stock.repository.RoleRepository;
import ics.ci.stock.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Transactional
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;


    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<AppRole> getRolesByUser(AppUser user) {
        Collection<UserRole> userroles = user.getUserRoles();
        List<AppRole> roles = new ArrayList<AppRole>();
        for (UserRole userrole : userroles){
            AppRole r = userrole.getAppRole();
            roles.add(r);
        }
        return roles;
    }
}
