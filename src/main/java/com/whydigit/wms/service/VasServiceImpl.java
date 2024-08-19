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

import com.whydigit.wms.dto.KittingDTO;
import com.whydigit.wms.dto.KittingDetails1DTO;
import com.whydigit.wms.dto.KittingDetails2DTO;
import com.whydigit.wms.dto.VasPickDTO;
import com.whydigit.wms.dto.VasPickDetailsDTO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.KittingDetails1VO;
import com.whydigit.wms.entity.KittingDetails2VO;
import com.whydigit.wms.entity.KittingVO;
import com.whydigit.wms.entity.VasPickDetailsVO;
import com.whydigit.wms.entity.VasPickVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.KittingDetails1Repo;
import com.whydigit.wms.repo.KittingDetails2Repo;
import com.whydigit.wms.repo.KittingRepo;
import com.whydigit.wms.repo.StockDetailsRepo;
import com.whydigit.wms.repo.VasPickDetailsRepo;
import com.whydigit.wms.repo.VasPickRepo;

@Service
public class VasServiceImpl implements VasService{

	
	@Autowired
	KittingRepo kittingRepo;

	@Autowired
	KittingDetails1Repo kittingDetails1Repo;

	@Autowired
	KittingDetails2Repo kittingDetails2Repo;
	
	@Autowired
	StockDetailsRepo stockDetailsRepo;
	
	@Autowired
	VasPickRepo vasPickRepo;

	@Autowired
	VasPickDetailsRepo vasPickDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	
	
	
	// Kitting

		@Override
		public Map<String, Object> createUpdateKitting(KittingDTO kittingDTO) throws ApplicationException {
			KittingVO kittingVO;
			String message;
			String screenCode = "KT";
			if (ObjectUtils.isEmpty(kittingDTO.getId())) {
				// Creating a new KittingVO
				kittingVO = new KittingVO();
				kittingVO.setCreatedBy(kittingDTO.getCreatedBy());
				kittingVO.setUpdatedBy(kittingDTO.getCreatedBy());

		    //	GETDOCID API
				String docId = kittingRepo.getKittingInDocId(kittingDTO.getOrgId(), kittingDTO.getFinYear(),
						kittingDTO.getBranchCode(), kittingDTO.getClient(), screenCode);
				kittingVO.setDocId(docId);

				// GETDOCID LASTNO +1
				DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
						.findByOrgIdAndFinYearAndBranchCodeAndClientAndScreenCode(kittingDTO.getOrgId(),
								kittingDTO.getFinYear(), kittingDTO.getBranchCode(), kittingDTO.getClient(), screenCode);
				documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
				documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

				message = "Kitting Creation Successful";
			} else {
				// Updating an existing KittingVO
				kittingVO = kittingRepo.findById(kittingDTO.getId()).orElseThrow(() -> new ApplicationException(
						"This Id Not Found Any Information, Invalid Id: " + kittingDTO.getId()));
				kittingVO.setUpdatedBy(kittingDTO.getCreatedBy());
				message = "Kitting Update Successful";

				// Deleting existing details
				List<KittingDetails1VO> details1vos = kittingDetails1Repo.findByKittingVO(kittingVO);
				kittingDetails1Repo.deleteAll(details1vos);

				List<KittingDetails2VO> details2vos = kittingDetails2Repo.findByKittingVO(kittingVO);
				kittingDetails2Repo.deleteAll(details2vos);
			}

			// Populate KittingVO from KittingDTO
			getKittingVOFromKittingDTO(kittingVO, kittingDTO);

			// Save KittingVO to repository
			kittingRepo.save(kittingVO);

			// Prepare response map
			Map<String, Object> response = new HashMap<>();
			response.put("message", message);
			response.put("kittingVO", kittingVO);

			return response;
		}

		private KittingVO getKittingVOFromKittingDTO(KittingVO kittingVO, KittingDTO kittingDTO) {
			// Populate kittingVO fields from kittingDTO
			kittingVO.setScreenName(kittingDTO.getScreenName());
			kittingVO.setOrgId(kittingDTO.getOrgId());
			kittingVO.setCustomer(kittingDTO.getCustomer());
			kittingVO.setClient(kittingDTO.getClient());
			kittingVO.setFinYear(kittingDTO.getFinYear());
			kittingVO.setBranch(kittingDTO.getBranch());
			kittingVO.setBranchCode(kittingDTO.getBranchCode());
			kittingVO.setWarehouse(kittingDTO.getWarehouse());
			kittingVO.setActive(kittingDTO.isActive());
			kittingVO.setCancel(kittingDTO.isCancel());
			kittingVO.setCancelRemarks(kittingDTO.getCancelRemarks());
			kittingVO.setFreeze(kittingDTO.isFreeze());
			kittingVO.setRefNo(kittingDTO.getRefNo());
			kittingVO.setSku(kittingDTO.getSku());

			// Handle KittingDetails1VO
			List<KittingDetails1VO> kittingDetails1VOs = new ArrayList<>();
			for (KittingDetails1DTO details1dto : kittingDTO.getKittingDetails1DTO()) {
				KittingDetails1VO kittingDetails1VO = new KittingDetails1VO();
				kittingDetails1VO.setPallet(details1dto.getPallet());
				kittingDetails1VO.setPartNo(details1dto.getPartNo());
				kittingDetails1VO.setPartDescription(details1dto.getPartDescription());
				kittingDetails1VO.setBatchNo(details1dto.getBatchNo());
				kittingDetails1VO.setLotNo(details1dto.getLotNo());
				kittingDetails1VO.setGrnNo(details1dto.getGrnNo());
				kittingDetails1VO.setGrnDate(details1dto.getGrnDate());
				kittingDetails1VO.setSku(details1dto.getSku());
				kittingDetails1VO.setAvlQty(details1dto.getAvlQty());
				kittingDetails1VO.setQty(details1dto.getQty());
				kittingDetails1VO.setUnitRate(details1dto.getUnitRate());
				kittingDetails1VO.setAmount(details1dto.getAmount());
				kittingDetails1VO.setQcflag(details1dto.isQcflag());

				// Avoid recursive reference to kittingVO in KittingDetails1VO
				kittingDetails1VO.setKittingVO(kittingVO);
				kittingDetails1VOs.add(kittingDetails1VO);
			}
			kittingVO.setKittingDetails1VO(kittingDetails1VOs);

			// Handle KittingDetails2VO
			List<KittingDetails2VO> kittingDetails2VOs = new ArrayList<>();
			for (KittingDetails2DTO details2dto : kittingDTO.getKittingDetails2DTO()) {
				KittingDetails2VO kittingDetails2VO = new KittingDetails2VO();
				kittingDetails2VO.setPPartDescription(details2dto.getPPartDescription());
				kittingDetails2VO.setPPartNo(details2dto.getPPartNo());
				kittingDetails2VO.setPBatchNo(details2dto.getPBatchNo());
				kittingDetails2VO.setPLotNo(details2dto.getPLotNo());
				kittingDetails2VO.setPGrnNo(details2dto.getPGrnNo());
				kittingDetails2VO.setPGrnDate(details2dto.getPGrnDate());
				kittingDetails2VO.setQQcflag(details2dto.isQQcflag());
				kittingDetails2VO.setPQty(details2dto.getPQty());
				kittingDetails2VO.setPExpDate(details2dto.getPExpDate());

				// Avoid recursive reference to kittingVO in KittingDetails2VO
				kittingDetails2VO.setKittingVO(kittingVO);
				kittingDetails2VOs.add(kittingDetails2VO);
			}
			kittingVO.setKittingDetails2VO(kittingDetails2VOs);

			return kittingVO;
		}

		@Override
		public List<KittingVO> getAllKitting(Long orgId, String branchCode, String client, String customer) {
			return kittingRepo.findAllKiting(orgId, branchCode, client, customer);
		}

		@Override
		public Optional<KittingVO> getKittingById(Long id) {
			return kittingRepo.findKittingById(id);
		}

		@Override
		public String getKittingInDocId(Long orgId, String finYear, String branch, String branchCode, String client) {
			String ScreenCode = "KT";
			String result = kittingRepo.getKittingInDocId(orgId, finYear, branchCode, client, ScreenCode);
			return result;
		}

		@Override
		public List<Map<String, Object>> getPartNOByChild(Long orgId, String bin, String branch, String branchCode,
		        String client) {
		    Set<Object[]> getPartNo = kittingRepo.getPartNOByChild(orgId, bin, branch, branchCode, client);
		    return gateChildPartNo(getPartNo);        
		}

		private List<Map<String, Object>> gateChildPartNo(Set<Object[]> getPartNo) {
		    List<Map<String, Object>> gridDetails = new ArrayList<>();  // Correct the type here
		    for (Object[] child : getPartNo) {
		        Map<String, Object> details = new HashMap<>();
		        details.put("partNo", child[0] != null ? Integer.parseInt(child[0].toString()) : 0);
		        details.put("partDesc", child[1] != null ? child[1].toString() : "");
		        details.put("Sku", child[2] != null ? child[2].toString() : "");
		        gridDetails.add(details);
		    }
		    return gridDetails;
		}

		@Override
		public List<Map<String, Object>> getGrnNOByChild(Long orgId, String bin, String branch, String branchCode,
		        String client, String partNo, String partDesc, String sku) {
		    Set<Object[]> getGrnData = kittingRepo.getGrnNOByChild(orgId, bin, branch, branchCode, client, partNo, partDesc, sku);
		    
		    return processGrnData(getGrnData);
		}

		private List<Map<String, Object>> processGrnData(Set<Object[]> getGrnData) {
		    List<Map<String, Object>> grnDetails = new ArrayList<>();
		    for (Object[] record : getGrnData) {
		        Map<String, Object> details = new HashMap<>();
		        details.put("grnnNo", record[0] != null ? record[0].toString() : "");
		        details.put("GrnDate", record[1] != null ? record[1].toString() : "");
		        details.put("batch", record[2] != null ? record[2].toString() : "");
		        details.put("batchDate", record[3] != null ? record[3].toString() : "");
		        grnDetails.add(details);
		    }
		    return grnDetails;
		}

		@Override
		public List<Map<String, Object>> getSqtyByKitting(Long orgId, String branchCode, String client, String partNo, String warehouse,String grnno) {
			   Set<Object[]> getQty = stockDetailsRepo.getQtyDetais(orgId, branchCode,  client,  partNo,  warehouse, grnno);
			    return getQtys(getQty);        
			}

			private List<Map<String, Object>> getQtys(Set<Object[]> getPartNo) {
			    List<Map<String, Object>> gridDetails = new ArrayList<>();  // Correct the type here
			    for (Object[] child : getPartNo) {
			        Map<String, Object> details = new HashMap<>();
			        details.put("sQTY", child[0] != null ? Integer.parseInt(child[0].toString()) : 0);
			        gridDetails.add(details);
			    }
			    return gridDetails;
			}
		
		

		@Override
		public List<Map<String, Object>> getPartNOByParent(Long orgId,String branchCode,
				String client) {
		    Set<Object[]> getPartNo = kittingRepo.getPartNOByParent(orgId, branchCode, client);
		    return gateParentPartNo(getPartNo);        
		}

		private List<Map<String, Object>> gateParentPartNo(Set<Object[]> getPartNo) {
		    List<Map<String, Object>> gridDetails = new ArrayList<>();  // Correct the type here
		    for (Object[] child : getPartNo) {
		        Map<String, Object> details = new HashMap<>();
		        details.put("partNo", child[0] != null ? Integer.parseInt(child[0].toString()) : 0);
		        details.put("partDesc", child[1] != null ? child[1].toString() : "");
		        details.put("Sku", child[2] != null ? child[2].toString() : "");
		        gridDetails.add(details);
		    }
		    return gridDetails;
		}

		@Override
		public List<Map<String, Object>> getGrnNOByParent(Long orgId, String bin, String branch, String branchCode,
				String client, String partNo, String partDesc, String sku) {
			 Set<Object[]> getGrnData = kittingRepo.getGrnNOByParent(orgId, bin, branch, branchCode, client, partNo, partDesc, sku);
			    
			    return processParentGrnData(getGrnData);
			}

			private List<Map<String, Object>> processParentGrnData(Set<Object[]> getGrnData) {
			    List<Map<String, Object>> grnDetails = new ArrayList<>();
			    for (Object[] record : getGrnData) {
			        Map<String, Object> details = new HashMap<>();
			        details.put("grnnNo", record[0] != null ? record[0].toString() : "");
			        details.put("GrnDate", record[1] != null ? record[1].toString() : "");
			        details.put("batch", record[2] != null ? record[2].toString() : "");
			        details.put("batchDate", record[3] != null ? record[3].toString() : "");
			        grnDetails.add(details);
			    }
			    return grnDetails;
			}

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
				part.put("stockDate", fs[12] != null ? fs[12].toString() : "");
				part.put("lotNo", fs[13] != null ? fs[13].toString() : "");
				part.put("binClass", fs[14] != null ? fs[14].toString() : "");
				part.put("avalQty", fs[15] != null ?Integer.parseInt(fs[15].toString()) : 0);
				part.put("pickQty", fs[15] != null ?Integer.parseInt(fs[15].toString()) : 0);
				
				details1.add(part);
			}
			return details1;

		}
}
