package com.salecoursecms.service.impl;

import com.salecoursecms.config.security.service.JwtService;
import com.salecoursecms.constant.AppConst;
import com.salecoursecms.constant.MessageConst;
import com.salecoursecms.constant.VariableConst;
import com.salecoursecms.dto.reponse.*;
import com.salecoursecms.dto.request.*;
import com.salecoursecms.entity.first.CourseEntity;
import com.salecoursecms.entity.first.CourseSessionEntity;
import com.salecoursecms.exception.ResourceNotFoundException;
import com.salecoursecms.mapper.CourseMapper;
import com.salecoursecms.mapper.CourseSessionMapper;
import com.salecoursecms.mapper.PagingMapper;
import com.salecoursecms.repository.first.CourseRepository;
import com.salecoursecms.repository.first.CourseSessionRepository;
import com.salecoursecms.repository.first.UserRepository;
import com.salecoursecms.service.CourseService;
import com.salecoursecms.service.CourseSessionService;
import com.salecoursecms.service.NotificationService;
import com.salecoursecms.utils.PageableInit;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final MessageSource messageSource;
    private final PagingMapper pagingMapper;
    private final CourseSessionService courseSessionService;
    private final CourseSessionRepository courseSessionRepository;
    private final CourseSessionMapper courseSessionMapper;
    private final NotificationService notificationService;

    @Override
    public BaseReponse<?> findAllCourse(String search, PagingRequest pagingRequest){
        try{
            Pageable pageable = PageableInit.getPageable(pagingRequest);
            Page<CourseEntity> courseEntityPage = courseRepository.findAllWithPagin(search,pageable);

            PagingResponse pagingResponse = pagingMapper.createPagingResponse(courseEntityPage.getTotalPages(), courseEntityPage.getNumber(), courseEntityPage.getSize());

            return new BaseReponse<>(AppConst.STATUS_SUCCESS,false,messageSource.getMessage(MessageConst.GET_DATA_SUCCESS, null,new Locale(VariableConst.LAN)),pagingResponse,courseEntityPage.getContent());
        }catch (Exception e){
            log.error("[ERROR] "+e.getMessage());
            return new BaseReponse<>(AppConst.STATUS_FAIL,true,messageSource.getMessage(MessageConst.GET_DATA_FAIL, null,new Locale(VariableConst.LAN)),null);
        }
    }

    @Override
    public BaseReponse<?> createCourse(CreateCourseRequest req, HttpServletRequest httpServletRequest) {
        try{
            String authorizationHeader = httpServletRequest.getHeader("Authorization");
            String accessToken = authorizationHeader.substring(7);
            Long createBy = jwtService.extractIdFromToken(accessToken);
            boolean existTeacherId = userRepository.existsById(req.getTeacherId());
            if(createBy == null || !existTeacherId) {
                throw new ResourceNotFoundException(MessageConst.ACCOUNT_NOT_FOUND);
            }else {
                req.setCreateBy(createBy);
            }
            CourseEntity courseEntity = courseMapper.toCreateCourse(req);
            courseRepository.save(courseEntity);
            if(courseEntity.getStatus() == 0){
                CreateNotificationRequest notificationRequest = new CreateNotificationRequest(21L,"",courseEntity.getDescription(),createBy,"CREATE_COURSE",courseEntity.getId(),0);
                String saveNoti = notificationService.sendNotification(notificationRequest);
                log.info("Create noti: "+saveNoti);
            }
            CourseReponse reponse = courseMapper.toReponse(courseEntity);
            return new BaseReponse<>(AppConst.STATUS_SUCCESS,false,messageSource.getMessage(MessageConst.CREATE_SUCCESS, null,new Locale(VariableConst.LAN)),reponse);
        } catch (Exception e) {
            log.error("[ERRRO] "+e.getMessage());
            return new BaseReponse<>(AppConst.STATUS_FAIL,false,messageSource.getMessage(MessageConst.CREATE_FAIL, null,new Locale(VariableConst.LAN)),null);
        }
    }

    @Override
    public BaseReponse<?> updateStatusCourse(UpdateStatusRequest req){
        try{
            CourseDetailReponse reponse = null;
            CourseEntity courseEntity = courseRepository.findById(req.getId()).orElse(null);
            if(courseEntity == null) {
                throw new ResourceNotFoundException(MessageConst.COURSE_NOT_FOUND);
            }
            courseEntity.setStatus(req.getStatus());
            courseRepository.save(courseEntity);
            if(req.getStatus() == 1){
                List<CourseSessionReponse> courseSessionEntities = courseSessionService.generatorCourseSession(courseEntity);

                reponse = courseMapper.toDetailReponse(courseEntity,courseSessionEntities);
            }
            return new BaseReponse<>(AppConst.STATUS_SUCCESS,false,messageSource.getMessage(MessageConst.UPDATE_SUCCESS, null,new Locale(VariableConst.LAN)),reponse);
        }catch (Exception e) {
            log.error("[ERROR] "+e.getMessage());
            return new BaseReponse<>(AppConst.STATUS_FAIL,false,messageSource.getMessage(MessageConst.UPDATE_FAIL, null,new Locale(VariableConst.LAN)),null);
        }
    }

    @Override
    public BaseReponse<?> updateCourse(UpdateCourseRequest req, HttpServletRequest httpServletRequest) {
        try{
            String authorizationHeader = httpServletRequest.getHeader("Authorization");

            String accessToken = authorizationHeader.substring(7);

            req.setUpdateBy(jwtService.extractIdFromToken(accessToken));


            CourseEntity courseEntity = courseMapper.toUpdateCourse(req);

            if(courseEntity.getStatus() == 0){
                courseSessionRepository.deleteCourseSessionByCourseId(req.getId());
            }
            if(courseEntity.getStatus() == 0){
                CreateNotificationRequest notificationRequest = new CreateNotificationRequest(21L,"",courseEntity.getDescription(),req.getUpdateBy(),"UPDATE_COURSE",courseEntity.getId(),0);
                String saveNoti = notificationService.sendNotification(notificationRequest);
                log.info("Create noti: "+saveNoti);
            }

            CourseReponse reponse = courseMapper.toReponse(courseEntity);

            return new BaseReponse<>(AppConst.STATUS_SUCCESS,false,messageSource.getMessage(MessageConst.UPDATE_SUCCESS, null,new Locale(VariableConst.LAN)),reponse);
        }catch (Exception e) {
            log.error("[ERROR] "+e.getMessage());
            return new BaseReponse<>(AppConst.STATUS_FAIL,false,messageSource.getMessage(MessageConst.UPDATE_FAIL, null,new Locale(VariableConst.LAN)),null);
        }
    }

    @Override
    public BaseReponse<?> findCourseById(Long id){
        try{

            CourseEntity courseEntity = courseRepository.findById(id).orElse(null);
            if(courseEntity == null){
                throw new ResourceNotFoundException(MessageConst.COURSE_NOT_FOUND);
            }
            List<CourseSessionEntity> courseSessionEntity = courseSessionRepository.findByCourseId(id);
            List<CourseSessionReponse> courseSessionReponses = courseSessionMapper.toReponseList(courseSessionEntity);
            CourseDetailReponse res = courseMapper.toDetailReponse(courseEntity,courseSessionReponses);
            return new BaseReponse<>(AppConst.STATUS_SUCCESS,false,messageSource.getMessage(MessageConst.GET_DATA_SUCCESS, null,new Locale(VariableConst.LAN)),res);

        }catch (Exception e){
            log.error("[ERROR] "+e.getMessage());
            return new BaseReponse<>(AppConst.STATUS_FAIL,true,messageSource.getMessage(MessageConst.GET_DATA_FAIL, null,new Locale(VariableConst.LAN)),null);
        }
    }

}
