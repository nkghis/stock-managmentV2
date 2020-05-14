package ics.ci.stock.repository;

import ics.ci.stock.entity.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<AppRole, Long> {
   /* @Query("Select ur.appRole.roleName from " + UserRole.class.getName() + " ur " + " where ur.appUser.userId = :userId")
    List<String>getRoleNames(@Param("userId")Long userId);*/

   AppRole findByRoleId (Long id);
}
