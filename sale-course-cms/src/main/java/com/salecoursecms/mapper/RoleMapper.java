package com.salecoursecms.mapper;

import com.salecoursecms.dto.reponse.ModuleReponse;
import com.salecoursecms.dto.reponse.RoleReponse;
import com.salecoursecms.entity.first.RoleEntity;
import com.salecoursecms.repository.first.ModuleRepository;
import com.salecoursecms.repository.first.RoleModuleReponsitory;
import com.salecoursecms.repository.first.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RoleMapper {
    private final RoleRepository roleRepository;
    private final ModuleRepository moduleRepository;
    private final RoleModuleReponsitory roleModuleReponsitory;

    public RoleReponse toRoleReponse(RoleEntity roleEntity, List<Long> moduleId) {
        RoleReponse roleReponse = new RoleReponse();
        roleReponse.setId(roleEntity.getId());
        roleReponse.setRoleName(roleEntity.getRoleName());
        roleReponse.setDescription(roleEntity.getDescription());
        roleReponse.setStatus(roleEntity.getStatus());
        List<ModuleReponse> moduleEntities = new ArrayList<>();
        for (Long module: moduleId) {
            moduleRepository.findById(module).ifPresent(moduleEntity -> {
                ModuleReponse moduleReponse = new ModuleReponse();
                moduleReponse.setId(moduleEntity.getId());
                moduleReponse.setIcon(moduleEntity.getIcon());
                moduleReponse.setLink(moduleEntity.getLink());
                moduleReponse.setTitle(moduleEntity.getModuleName());
                moduleEntities.add(moduleReponse);
            });
        }
        roleReponse.setModules(moduleEntities);
        return roleReponse;
    }
}
