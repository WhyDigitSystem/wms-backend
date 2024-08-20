package com.whydigit.wms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.VasPickDTO;
import com.whydigit.wms.dto.VasPickDetailsDTO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.VasPickDetailsVO;
import com.whydigit.wms.entity.VasPickVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.StockDetailsRepo;
import com.whydigit.wms.repo.VasPickDetailsRepo;
import com.whydigit.wms.repo.VasPickRepo;

@Service
public class VasPickServiceImpl implements VasPickService{
	

	@Autowired
	StockDetailsRepo stockDetailsRepo;
	
	@Autowired
	VasPickRepo vasPickRepo;

	@Autowired
	VasPickDetailsRepo vasPickDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	
	
	
	

			//VASPICK
			
			@Override
			public Map<String, Object> createUpdateVasPic(VasPickDTO vasPicDTO) throws ApplicationException {
				VasPickVO vasPickVO;
				String message = null;
				String screenCode = "VPR";
				if (ObjectUtils.isEmpty(vasPicDTO.getId())) {
					vasPickVO = new VasPickVO();
					vasPickVO.setCreatedBy(vasPicDTO.getCreatedBy());
					vasPickVO.setUpdatedBy(vasPicDTO.getCreatedBy());

					// GETDOCID API
					String docId = vasPickRepo.getVasPickDocId(vasPicDTO.getOrgId(), vasPicDTO.getFinYear(),
							vasPicDTO.getBranchCode(), vasPicDTO.getClient(), screenCode);
					vasPickVO.setDocId(docId);

					// GETDOCID LASTNO +1
					DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
							.findByOrgIdAndFinYearAndBranchCodeAndClientAndScreenCode(vasPicDTO.getOrgId(),
									vasPicDTO.getFinYear(), vasPicDTO.getBranchCode(), vasPicDTO.getClient(), screenCode);
					documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
					documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
					
//					if("confirm".equals(vasPicDTO.getStatus())) {
//						vasPickVO.setFreeze(true);
//					}else {
//						vasPickVO.setFreeze(false);
//					}
					message = "VasPicK Creation Successfully";
				} else {
					vasPickVO = vasPickRepo.findById(vasPicDTO.getId()).orElseThrow(() -> new ApplicationException(
							"This Id Is Not Found Any Information, Invalid Id: " + vasPicDTO.getId()));

					vasPickVO.setUpdatedBy(vasPicDTO.getCreatedBy());
					message = "VasPicK Update Successfully";

					// Remove existing details if updating
					List<VasPickDetailsVO> detailsVOs = vasPickDetailsRepo.findByVasPickVO(vasPickVO);
					vasPickDetailsRepo.deleteAll(detailsVOs);
				}

				vasPickVO = getVasPickVOFromVasPickDTO(vasPickVO, vasPicDTO);

				// Save parent entity along with its details
				vasPickRepo.save(vasPickVO);

				Map<String, Object> response = new HashMap<>();
				response.put("message", message);
				response.put("vasPickVO", vasPickVO);
				return response;
			}

			private VasPickVO getVasPickVOFromVasPickDTO(VasPickVO vasPickVO, VasPickDTO vasPicDTO) {
				vasPickVO.setPicBin(vasPicDTO.getPicBin());
				vasPickVO.setOrgId(vasPicDTO.getOrgId());
				vasPickVO.setStockState(vasPicDTO.getStockState());
				vasPickVO.setStatus(vasPicDTO.getStatus());
				vasPickVO.setStateStatus(vasPicDTO.getStateStatus());
				vasPickVO.setCustomer(vasPicDTO.getCustomer());
				vasPickVO.setClient(vasPicDTO.getClient());
				vasPickVO.setFinYear(vasPicDTO.getFinYear());
				vasPickVO.setBranch(vasPicDTO.getBranch());
				vasPickVO.setBranchCode(vasPicDTO.getBranchCode());
				vasPickVO.setWarehouse(vasPicDTO.getWarehouse());
				vasPickVO.setCancelRemarks(vasPicDTO.getCancelRemarks());
				vasPickVO.setPicBin(vasPicDTO.getPicBin());

				int totalOrderQty = 0;
				int pickedQty = 0;

				List<VasPickDetailsVO> vasPickDetailsVOs = new ArrayList<>();
				for (VasPickDetailsDTO vasPickDTO : vasPicDTO.getVasPickDetailsDTO()) {
					VasPickDetailsVO detailsVO = new VasPickDetailsVO();
					detailsVO.setPartDescription(vasPickDTO.getPartDescription());
					detailsVO.setPartNo(vasPickDTO.getPartNo());
					detailsVO.setSku(vasPickDTO.getSku());
					detailsVO.setBin(vasPickDTO.getBin());
					detailsVO.setBatchNo(vasPickDTO.getBatchNo());
					detailsVO.setGrnNo(vasPickDTO.getGrnNo());
					detailsVO.setBinType(vasPickDTO.getBinType());
					detailsVO.setBatchDate(vasPickDTO.getBatchDate());
					detailsVO.setAvlQty(vasPickDTO.getAvlQty());
					detailsVO.setPicQty(vasPickDTO.getPicQty());
					detailsVO.setRemaningQty(vasPickDTO.getRemaningQty());
					detailsVO.setQcflag(vasPickDTO.getQcflag());
					detailsVO.setGrnDate(vasPickDTO.getGrnDate());
					detailsVO.setBinClass(vasPickDTO.getBinClass());
					detailsVO.setCellType(vasPickDTO.getCellType());
					detailsVO.setCore(vasPickDTO.getCore());
					detailsVO.setExpDate(vasPickDTO.getExpDate());
					
					totalOrderQty = totalOrderQty + vasPickDTO.getAvlQty();
					pickedQty = pickedQty + vasPickDTO.getPicQty();

					detailsVO.setVasPickVO(vasPickVO); // Set the parent reference
					vasPickDetailsVOs.add(detailsVO);
				}
				vasPickVO.setVasPickDetailsVO(vasPickDetailsVOs);
				vasPickVO.setTotalOrderQty(totalOrderQty);
				vasPickVO.setPickedQty(pickedQty);
				return vasPickVO;
			}

			@Override
			public Optional<VasPickVO> getVaspickById(Long id) {
				return vasPickRepo.findById(id);
			}

			@Override
			public String getVasPickDocId(Long orgId, String finYear, String branch, String branchCode, String client) {
				String ScreenCode = "VPR";
				String result = vasPickRepo.getVasPickDocId(orgId, finYear, branchCode, client, ScreenCode);
				return result;
			}

			@Override
			public List<VasPickVO> getAllVaspick(Long orgId, String branchCode, String client, String branch, String finYear,
					String warehouse) {
				return vasPickRepo.findALLVasPick(orgId, branchCode, client, branch, finYear, warehouse);
			}
			

			private List<Map<String, Object>> getVaspickFullGrids(Set<Object[]> result) {
				List<Map<String, Object>> details1 = new ArrayList<>();
				for (Object[] fs : result) {
					Map<String, Object> part = new HashMap<>();

					part.put("avlQty", fs[0] != null ? Integer.parseInt(fs[0].toString()) : 0);
					part.put("partDesc", fs[1] != null ? fs[1].toString() : "");
					part.put("partNo", fs[2] != null ? fs[2].toString() : "");
					part.put("sku", fs[3] != null ? fs[3].toString() : "");
					part.put("bin", fs[4] != null ? fs[4].toString() : "");
					part.put("batch", fs[5] != null ? fs[5].toString() : "");
					part.put("grnNo", fs[6] != null ? fs[6].toString() : "");
					part.put("lotNo", fs[8] != null ? fs[7].toString() : "");

					details1.add(part);
				}
				return details1;

			}

			@Override
			public List<Map<String, Object>> getVasPicGridDetails(Long orgId, String branchCode, String client, String warehouse,
					String stateStatus, String branch, String bintype){
			Set<Object[]> result = vasPickRepo.getVasPicGrid(orgId, branchCode, client, warehouse, stateStatus,branch,bintype);
			return getVasPicGridDetails(result);
		}

		private List<Map<String, Object>> getVasPicGridDetails(Set<Object[]> result) {
			List<Map<String, Object>> details1 = new ArrayList<>();
			for (Object[] fs : result) {
				Map<String, Object> part = new HashMap<>();

				part.put("partDesc", fs[0] != null ? fs[0].toString() : "");
				part.put("partNo", fs[1] != null ? fs[1].toString() : "");
				part.put("sku", fs[2] != null ? fs[2].toString() : "");
				part.put("bin", fs[3] != null ? fs[3].toString() : "");
				part.put("batch", fs[4] != null ? fs[4].toString() : "");
				part.put("batchDate", fs[5] != null ? fs[5].toString() : "");
				part.put("grnDate", fs[6] != null ? fs[6].toString() : "");
				part.put("grnNo", fs[7] != null ? fs[7].toString() : "");
				part.put("binType", fs[8] != null ? fs[8].toString() : "");
				part.put("core", fs[9] != null ? fs[9].toString() : "");
				part.put("expDate", fs[10] != null ? fs[10].toString() : "");
				part.put("qcFlag", fs[11] != null ? fs[11].toString() : "");
				part.put("binClass", fs[12] != null ? fs[12].toString() : "");
				part.put("status", fs[13] != null ? fs[13].toString() : "");
				part.put("cellType", fs[14] != null ? fs[14].toString() : "");
				part.put("avalQty", fs[15] != null ?Integer.parseInt(fs[15].toString()) : 0);
				part.put("pickQty", fs[15] != null ?Integer.parseInt(fs[15].toString()) : 0);
				part.put("id", fs[16] != null ?Integer.parseInt(fs[16].toString()) : 0);				
				details1.add(part);
			}
			return details1;

		}

}