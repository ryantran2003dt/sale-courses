package com.salecoursecms.service.impl;

import com.salecoursecms.constant.AppConst;
import com.salecoursecms.constant.MessageConst;
import com.salecoursecms.constant.VariableConst;
import com.salecoursecms.dto.reponse.BaseReponse;
import com.salecoursecms.dto.request.CreateNotificationRequest;
import com.salecoursecms.entity.first.NotificationEntity;
import com.salecoursecms.mapper.NotificationMapper;
import com.salecoursecms.repository.first.NotificationRepository;
import com.salecoursecms.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final MessageSource messageSource;

    @Override
    public BaseReponse<?> getUnreadNotifications(Long userId){
        try{
            List<NotificationEntity> notificationEntityList = notificationRepository.findAllByUserIdWithStatus(userId,0);
            return new BaseReponse<>(AppConst.STATUS_SUCCESS,false,messageSource.getMessage(MessageConst.GET_DATA_SUCCESS, null,new Locale(VariableConst.LAN)),notificationEntityList);
        }catch (Exception e){
            log.error("[ERROR] "+e.getMessage());
            return new BaseReponse<>(AppConst.STATUS_FAIL,false,messageSource.getMessage(MessageConst.GET_DATA_SUCCESS, null,new Locale(VariableConst.LAN)),null);
        }
    }

    @Override
    public String sendNotification(CreateNotificationRequest req){
        try{
            NotificationEntity notificationEntity = notificationMapper.toCreateNotification(req);
            notificationRepository.save(notificationEntity);
            return "SUCCESS";
        }catch (Exception e){
            log.error("[ERROR] "+e.getMessage());
            return "FAIL";
        }
    }
    @Override
    public BaseReponse<?> markAsRead(Long id){
        try{
            Optional<NotificationEntity> notificationEntityOptional = notificationRepository.findById(id);
            notificationEntityOptional.ifPresent(notificationEntity -> {
                notificationEntity.setStatus(1);
                notificationRepository.save(notificationEntity);
            });
            return new BaseReponse<>(AppConst.STATUS_SUCCESS,false,messageSource.getMessage(MessageConst.READ_NOTI_SUCCESS, null,new Locale(VariableConst.LAN)),notificationEntityOptional.get());
        }catch (Exception e){
            log.error("[ERROR] "+e.getMessage());
            return new BaseReponse<>(AppConst.STATUS_FAIL,false,messageSource.getMessage(MessageConst.READ_NOTI_FAIL, null,new Locale(VariableConst.LAN)),null);
        }
    }
}
