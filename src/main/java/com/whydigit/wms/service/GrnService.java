package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.whydigit.wms.dto.CustomerAttachmentType;
import com.whydigit.wms.dto.GrnDTO;
import com.whydigit.wms.entity.GatePassInVO;
import com.whydigit.wms.entity.GrnVO;
import com.whydigit.wms.exception.ApplicationException;

@Service
public interface GrnService {

	
	List<GrnVO> getAllGrn(Long orgId, String finYear, String branch, String branchCode, String client,
			String warehouse);

	GrnVO getGrnById(Long id);

	String getGRNdocid(Long orgId, String finYear, String branchCode, String client, String screencode);

	Map<String, Object> createUpdateGrn(GrnDTO grnDTO) throws ApplicationException;
	
	List<GatePassInVO> getGatepassInNoForPendingGRN(Long orgId, String branchCode, String finYear, String client);
	
	List<Map<String, Object>> getGatepassInGridDetailsForPendingGRN(Long orgId, String finYear, String branchCode,
			String client, String gatePassDocId);

	List<Map<String, Object>> getGatePassDetailsByGatePassNo(Long orgId, String client, String entryno, Long docid,
			String branchcode);


	int getSuccessfulUploads();

	int getTotalRows();

	void ExcelUploadForGrn(MultipartFile[] files, CustomerAttachmentType type, Long orgId, String createdBy,
			String customer, String client, String finYear, String branch, String branchCode, String warehouse) throws ApplicationException;

	List<Map<String, Object>> getGrnStatusForDashBoard(Long orgId, String finYear, String branchCode, String client,String warehouse);

}
