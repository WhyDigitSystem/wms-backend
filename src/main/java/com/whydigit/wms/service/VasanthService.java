package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.VasPickDTO;
import com.whydigit.wms.entity.VasPickVO;
import com.whydigit.wms.exception.ApplicationException;
@Service
public interface VasanthService {

	Map<String, Object> createUpdateVasPic(VasPickDTO vasPicDTO) throws ApplicationException;

	Optional<VasPickVO> getVaspickById(Long id);

	List<VasPickVO> getAllVaspick(Long orgId, String branchCode, String client, String customer);


}
