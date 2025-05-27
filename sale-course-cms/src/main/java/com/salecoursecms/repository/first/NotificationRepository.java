package com.salecoursecms.repository.first;

import com.salecoursecms.entity.first.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    @Query("select n from NotificationEntity n where n.userId=:userId and n.status=:status")
    List<NotificationEntity> findAllByUserIdWithStatus(Long userId, int status);
}
