package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.GatePassInDTO;
import com.whydigit.wms.dto.GrnDTO;
import com.whydigit.wms.dto.LocationMovementDTO;
import com.whydigit.wms.dto.PutAwayDTO;
import com.whydigit.wms.dto.SalesReturnDTO;
import com.whydigit.wms.entity.CarrierVO;
import com.whydigit.wms.entity.GatePassInVO;
import com.whydigit.wms.entity.GrnVO;
import com.whydigit.wms.entity.LocationMovementVO;
import com.whydigit.wms.entity.PutAwayVO;
import com.whydigit.wms.entity.SalesReturnVO;
import com.whydigit.wms.exception.ApplicationException;

@Service
public interface InwardTransactionService {

	// Grn

	List<GrnVO> getAllGrn(Long orgId,String finYear,String branch,String branchCode,String client,String warehouse);

	GrnVO getGrnById(Long id);
	
	String getGRNdocid(Long orgId,String finYear,String branchCode,String client,String screencode);

	Map<String, Object> createUpdateGrn(GrnDTO grnDTO) throws ApplicationException;

//	Set<Object[]> getAllGatePassNumberByClientAndBranch(Long orgId, String client, String customer, String branchcode);

	Set<Object[]> getGatePassDetailsByGatePassNo(Long orgId, String client, String entryno, Long docid,
			String branchcode);

	// GatePassIn

	List<GatePassInVO> getAllGatePassIn();

	Optional<GatePassInVO> getGatePassInById(Long id);

	Map<String, Object> createUpdateGatePassIn(GatePassInDTO gatePassInDTO) throws ApplicationException;

	Optional<GatePassInVO> updateGatePassIn(GatePassInVO gatePassInVO);

	List<CarrierVO> getAllModeOfShipment();

	void deleteGatePassIn(Long id);

//	Put Away

	List<PutAwayVO> getAllPutAway();

	Optional<PutAwayVO> getPutAwayById(Long id);

	PutAwayVO createPutAway(PutAwayDTO PutAwayDTO);

	Optional<PutAwayVO> updatePutAway(PutAwayVO PutAwayVO);

	void deletePutAway(Long idLong);

	Set<Object[]> getGrnNoForPutAway(Long orgId, String client, String branch, String finyr, String branchcode);

	List<CarrierVO> getActiveShipment(String shipmentMode);

//	SalesReturn
	List<SalesReturnVO> getAllSalesReturn(Long orgId, String finYear, String branch, String branchCode, String client,
			String warehouse);

	SalesReturnVO getAllSalesReturnById(Long id);

	Map<String, Object> createUpdateSalesReturn(@Valid SalesReturnDTO salesReturnDTO) throws ApplicationException;

	List<Map<String, Object>> getSalesReturnFillGridDetails(String docId, String client, Long orgId, String branchCode);

//	LocationMovement
	List<LocationMovementVO> getAllLocationMovement(Long orgId, String finYear, String branch, String branchCode, String client,
			String warehouse);

	LocationMovementVO getAllLocationMovementById(Long id);

	Map<String, Object> createUpdateLocationMovement(@Valid LocationMovementDTO locationMovementDTO) throws ApplicationException;

}
