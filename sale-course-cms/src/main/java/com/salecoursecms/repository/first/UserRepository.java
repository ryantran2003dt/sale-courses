package com.salecoursecms.repository.first;


import com.salecoursecms.entity.first.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u WHERE u.status = :status AND u.id = :id ")
    UserEntity findUserByStatus(@Param("id") Long id, @Param("status") int status);

    @Query("SELECT a FROM UserEntity a WHERE a.username = :username")
    Optional<UserEntity> findByUsernameWithoutStatus(String username);

    @Query("select u from UserEntity u where u.username =:username and u.status = 1")
    Optional<UserEntity> findByUsername(String username);

    @Query("select u from UserEntity u " +
            "where (:search is null or :search = '' or" +
            " LOWER(u.username) like concat('%', LOWER(:search), '%') or" +
            " LOWER(u.fullName) like concat('%', LOWER(:search), '%') or" +
            " LOWER(u.email) like concat('%', LOWER(:search), '%'))" +
            "and u.status = 1 ")
    Page<UserEntity> findAllWithPagin(String search ,Pageable pageable);
}

