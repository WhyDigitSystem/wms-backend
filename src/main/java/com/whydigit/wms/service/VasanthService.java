package com.whydigit.wms.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.VasPickDTO;
import com.whydigit.wms.exception.ApplicationException;
@Service
public interface VasanthService {

	Map<String, Object> createUpdateVasPic(VasPickDTO vasPicDTO) throws ApplicationException;

}
