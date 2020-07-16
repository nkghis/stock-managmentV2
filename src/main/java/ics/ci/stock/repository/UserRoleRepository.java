package ics.ci.stock.repository;

import ics.ci.stock.entity.AppRole;
import ics.ci.stock.entity.AppUser;
import ics.ci.stock.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    List<UserRole> findByAppUser(AppUser user);

    //List<User> findByRoles_NameIn(List<String> roles);
    List<UserRole> findByAppRoleIsNot(AppRole role);


}
