package com.salecoursecms.repository.first;

import com.salecoursecms.entity.first.AuthProvidersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthProvidersRepository extends JpaRepository<AuthProvidersEntity, Long> {
}
