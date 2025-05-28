package com.salecourseweb.repository.second;

import com.salecourseweb.entity.second.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    @Query("select r from RoleEntity r where r.id = :id and r.status = 1")
    Optional<RoleEntity> findRoleById(@Param("id") Long id);
}
