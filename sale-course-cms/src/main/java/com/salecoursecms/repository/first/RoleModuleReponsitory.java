package com.salecoursecms.repository.first;

import com.salecoursecms.entity.first.RoleModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleModuleReponsitory extends JpaRepository<RoleModuleEntity, Long> {
    List<RoleModuleEntity> findByRoleId(Long roleId);
}
