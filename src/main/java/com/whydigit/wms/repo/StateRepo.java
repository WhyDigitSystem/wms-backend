package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.StateVO;


@Repository
public interface StateRepo extends JpaRepository<StateVO, Integer> {
	@Query(value = "select s from StateVO s where s.userid=?1")
	List<StateVO> getStateByUserId(long userid);

}
