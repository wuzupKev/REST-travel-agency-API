package org.githubwuzupkev.repositories;

import org.githubwuzupkev.models.auth.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    List<RoleEntity> findRoleEntitiesByRoleEnumIn(List<String> roleNames);
}
