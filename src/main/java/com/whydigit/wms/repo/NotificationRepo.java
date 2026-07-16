package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.NotificationVO;


@Repository
public interface NotificationRepo extends JpaRepository<NotificationVO, Long>{

}
