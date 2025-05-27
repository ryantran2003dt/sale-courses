package com.salecoursecms.repository.first;

import com.salecoursecms.entity.first.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    @Query("select r from RoleEntity r where r.id = :id and r.status = 1")
    Optional<RoleEntity> findRoleById(@Param("id") Long id);
}
