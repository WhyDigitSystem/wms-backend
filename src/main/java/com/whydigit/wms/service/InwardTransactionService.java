package com.whydigit.wms.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.GrnDTO;
import com.whydigit.wms.dto.PutAwayDTO;
import com.whydigit.wms.entity.GatePassInVO;
import com.whydigit.wms.entity.GrnVO;
import com.whydigit.wms.entity.PutAwayVO;

@Service
public interface InwardTransactionService {

	// Grn

	List<GrnVO> getAllGrn();

	Optional<GrnVO> getGrnById(Long id);

	GrnVO createGrn(GrnDTO grnDTO);

	Optional<GrnVO> updateGrn(GrnVO grnVO);

	void deleteGrn(Long id);

	Set<Object[]> getAllGatePassNumberByClientAndBranch(Long orgId, String client, String customer, String branchcode);

	Set<Object[]> getGatePassDetailsByGatePassNo(Long orgId, String client, String entryno, Long docid, String branchcode);

	// GatePassIn

	List<GatePassInVO> getAllGatePassIn();

	Optional<GatePassInVO> getGatePassInById(Long id);

	GatePassInVO createGatePassIn(GatePassInVO gatePassInVO);

	Optional<GatePassInVO> updateGatePassIn(GatePassInVO gatePassInVO);

	void deleteGatePassIn(Long id);

	Set<Object[]> getAllPartnoByCustomer(Long orgId, String client, String customer, String cbranch);

//	Putaway
	List<PutAwayVO> getAllPutAway();

	Optional<PutAwayVO> getPutAwayById(Long id);

	PutAwayVO createPutAway(PutAwayDTO PutAwayDTO);

	Optional<PutAwayVO> updatePutAway(PutAwayVO PutAwayVO);

	void deletePutAway(Long idLong);
	
	Set<Object[]> getGrnNoForPutAway(Long orgId, String client, String branch, String finyr, String branchcode);

}
