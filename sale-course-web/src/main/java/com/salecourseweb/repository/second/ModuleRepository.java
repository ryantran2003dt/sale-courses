package com.salecourseweb.repository.second;

import com.salecourseweb.entity.second.ModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends JpaRepository<ModuleEntity, Long> {
}
