package com.afrivera.security.persistence.repository;

import com.afrivera.security.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

    List<RoleEntity> findRoleEntitiesByRoleEnumIn(List<String> roles);
}
