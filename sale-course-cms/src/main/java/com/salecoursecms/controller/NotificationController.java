package com.salecoursecms.controller;

import com.salecoursecms.constant.UrlConst;
import com.salecoursecms.dto.request.CreateNotificationRequest;
import com.salecoursecms.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(UrlConst.NOTIFI)
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUnread(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getUnreadNotifications(userId));
    }

    @PostMapping("/{id}/read")
    public ResponseEntity<?> markAsRead(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.markAsRead(id));
    }
    @PostMapping(UrlConst.CREATE)
    public ResponseEntity<?> markAsRead(@RequestBody CreateNotificationRequest req) {
        return ResponseEntity.ok(notificationService.sendNotification(req));
    }
}
