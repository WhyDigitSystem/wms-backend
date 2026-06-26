package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;

import com.whydigit.wms.dto.RolesPermissionHeaderDTO;
import com.whydigit.wms.entity.RolesPermissionHeaderVO;
import com.whydigit.wms.exception.ApplicationException;

public interface RolesResponsibilitiesService {

	List<RolesPermissionHeaderVO> getRolesPermissionHeaderByRoleandOrgid(String role, Long orgid);

	Map<String, Object> createUpdateRoleScreenPermission(RolesPermissionHeaderDTO rolesPermissionHeaderDTO) throws ApplicationException;

}
