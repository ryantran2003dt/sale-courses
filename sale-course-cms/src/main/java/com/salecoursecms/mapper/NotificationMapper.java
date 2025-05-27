package com.salecoursecms.mapper;

import com.salecoursecms.dto.request.CreateNotificationRequest;
import com.salecoursecms.entity.first.NotificationEntity;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class NotificationMapper {
    public NotificationEntity toCreateNotification(CreateNotificationRequest request) {
        NotificationEntity notification = new NotificationEntity();
        notification.setUserId(request.getUserId());
        notification.setTitle(request.getTitle());
        notification.setContent(request.getContent());
        notification.setCreateDate(new Date());
        notification.setCreateBy(request.getCreateBy());
        notification.setType(request.getType());
        notification.setRedirectId(request.getRedirectId());
        notification.setStatus(request.getStatus());
        return notification;
    }
}
