package com.sys.ims.repository;

import com.sys.ims.model.Notification;
import com.sys.ims.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {


    List<Notification> findTop100ByCreatedForOrderByCreatedAtDesc(User user);
    Long countAllByCreatedForAndStatus(User user,String status);
    List<Notification> findAllByCreatedForAndStatus(User user,String status);

}
