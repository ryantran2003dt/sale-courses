package com.salecoursecms.service;

import com.salecoursecms.dto.reponse.BaseReponse;
import com.salecoursecms.dto.request.CreateNotificationRequest;

public interface NotificationService {
    BaseReponse<?> getUnreadNotifications(Long id);
    BaseReponse<?> markAsRead(Long id);
    String sendNotification(CreateNotificationRequest req);
}
