package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.GatePassInDTO;
import com.whydigit.wms.dto.GrnDTO;
import com.whydigit.wms.entity.GatePassInVO;
import com.whydigit.wms.entity.GrnVO;
import com.whydigit.wms.entity.PutAwayVO;
import com.whydigit.wms.exception.ApplicationException;

@Service
public interface InwardTransactionService {

	// Grn

	List<GrnVO> getAllGrn(Long orgId, String finYear, String branch, String branchCode, String client,
			String warehouse);

	GrnVO getGrnById(Long id);

	String getGRNdocid(Long orgId, String finYear, String branchCode, String client, String screencode);

	Map<String, Object> createUpdateGrn(GrnDTO grnDTO) throws ApplicationException;

//	Set<Object[]> getAllGatePassNumberByClientAndBranch(Long orgId, String client, String customer, String branchcode);

	Set<Object[]> getGatePassDetailsByGatePassNo(Long orgId, String client, String entryno, Long docid,
			String branchcode);
	

	// GatePassIn

	List<GatePassInVO> getAllGatePassIn(Long orgId, String branchCode,String finYear,String client);

	Optional<GatePassInVO> getGatePassInById(Long id);
	
	List<GatePassInVO>getGatepassInDetailsForPendingGRN(Long orgId, String branchCode,String finYear,String client);

	Map<String, Object> createUpdateGatePassIn(GatePassInDTO gatePassInDTO) throws ApplicationException;

	Optional<GatePassInVO> updateGatePassIn(GatePassInVO gatePassInVO);
	
	String getGatePassInDocId (Long orgId, String finYear, String branch, String branchCode, String client);

	void deleteGatePassIn(Long id);

//	Put Away

	List<PutAwayVO> getAllPutAway(Long orgId, String finYear, String branch, String branchCode, String client,
			String warehouse);
	
	PutAwayVO getPutAwayById(Long id);
	
	String getPutAwayDocId(Long orgId, String finYear, String branch, String branchCode, String client);


//	PutAwayVO createPutAway(PutAwayDTO PutAwayDTO);

	Optional<PutAwayVO> updatePutAway(PutAwayVO PutAwayVO);

	void deletePutAway(Long idLong);

	Set<Object[]> getGrnNoForPutAway(Long orgId, String client, String branch, String finyr, String branchcode);


	

	


}
