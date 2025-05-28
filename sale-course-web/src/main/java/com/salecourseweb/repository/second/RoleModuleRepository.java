package com.salecourseweb.repository.second;

import com.salecourseweb.entity.second.RoleModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleModuleRepository extends JpaRepository<RoleModuleEntity, Long> {
    List<RoleModuleEntity> findByRoleId(Long roleId);
}
