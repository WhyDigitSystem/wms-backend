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

import com.whydigit.wms.dto.BuyerOrderDTO;
import com.whydigit.wms.dto.BuyerOrderDetailsDTO;
import com.whydigit.wms.entity.BuyerOrderDetailsVO;
import com.whydigit.wms.entity.BuyerOrderVO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.HandlingStockOutVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.BuyerOrderDetailsRepo;
import com.whydigit.wms.repo.BuyerOrderRepo;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.HandlingStockOutRepo;
import com.whydigit.wms.repo.StockDetailsRepo;

@Service
public class BuyerOrderServiceImpl implements BuyerOrderService {

	@Autowired
	BuyerOrderRepo buyerOrderRepo;

	@Autowired
	BuyerOrderDetailsRepo buyerOrderDetailsRepo;

	@Autowired
	StockDetailsRepo stockDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;
	
	@Autowired
	HandlingStockOutRepo handlingStockOutRepo;

	// BuyerOrder

	@Override
	public Map<String, Object> createUpdateBuyerOrder(BuyerOrderDTO buyerOrderDTO) throws ApplicationException {
		String screenCode = "BO";
		BuyerOrderVO buyerOrderVO;
		String message = null;

		if (ObjectUtils.isEmpty(buyerOrderDTO.getId())) {

			if (buyerOrderRepo.existsByOrderNoAndOrgIdAndClientAndCustomer(buyerOrderDTO.getOrderNo(),
					buyerOrderDTO.getOrgId(), buyerOrderDTO.getClient(),
					buyerOrderDTO.getCustomer())) {
				String errorMessage = String.format("This orderNo:%s Already Exists This Client.",
						buyerOrderDTO.getOrderNo());
				throw new ApplicationException(errorMessage);
			}

			buyerOrderVO = new BuyerOrderVO();

			// GETDOCID API
			String docId = buyerOrderRepo.getbuyerOrderDocId(buyerOrderDTO.getOrgId(), buyerOrderDTO.getFinYear(),
					buyerOrderDTO.getBranchCode(), buyerOrderDTO.getClient(), screenCode);
			buyerOrderVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndClientAndScreenCode(buyerOrderDTO.getOrgId(),
							buyerOrderDTO.getFinYear(), buyerOrderDTO.getBranchCode(), buyerOrderDTO.getClient(),
							screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			buyerOrderVO.setCreatedBy(buyerOrderDTO.getCreatedBy());
			buyerOrderVO.setUpdatedBy(buyerOrderDTO.getCreatedBy());  

			message = "BuyerOrder Creation Successfully";
		} else {
			buyerOrderVO = buyerOrderRepo.findById(buyerOrderDTO.getId()).orElseThrow(() -> new ApplicationException(
					"This Id Not Found Any Informations,Invalid Id" + buyerOrderDTO.getId()));
			buyerOrderVO.setUpdatedBy(buyerOrderDTO.getCreatedBy());

			if (!buyerOrderVO.getOrderNo().equalsIgnoreCase(    buyerOrderDTO.getOrderNo())) {

				if (buyerOrderRepo.existsByOrderNoAndOrgIdAndClientAndCustomer(buyerOrderDTO.getOrderNo(),
						buyerOrderDTO.getOrgId(), buyerOrderDTO.getClient(),
						buyerOrderDTO.getCustomer())) {
					String errorMessage = String.format("This orderNo:%s Already Exists This Client.",
							buyerOrderDTO.getOrderNo());
					throw new ApplicationException(errorMessage);
				}
				buyerOrderVO.setOrderNo(buyerOrderDTO.getOrderNo());
			}
			message = "BuyerOrder Updation Successfully";
		}

		getBuyerOrderVOfromBuyerOrderDTO(buyerOrderVO, buyerOrderDTO);
		BuyerOrderVO buyerOrderVO2= buyerOrderRepo.save(buyerOrderVO);
		List<BuyerOrderDetailsVO>buyerOrderDetailsVO2=buyerOrderVO2.getBuyerOrderDetailsVO();
		
		for(BuyerOrderDetailsVO buyerOrderDetailsVOs2:buyerOrderDetailsVO2)
		{
			HandlingStockOutVO handlingStockOutVO=new HandlingStockOutVO();
			
			handlingStockOutVO.setOrgId(buyerOrderVO2.getOrgId());
			handlingStockOutVO.setBranch(buyerOrderVO2.getBranch());
			handlingStockOutVO.setBranchCode(buyerOrderVO2.getBranchCode());
			handlingStockOutVO.setWarehouse(buyerOrderVO2.getWarehouse());
			handlingStockOutVO.setCustomer(buyerOrderVO2.getCustomer());
			handlingStockOutVO.setClient(buyerOrderVO2.getClient());
			handlingStockOutVO.setRefNo(buyerOrderVO2.getOrderNo());
			handlingStockOutVO.setRefDate(buyerOrderVO2.getOrderDate());	
			handlingStockOutVO.setPartNo(buyerOrderDetailsVOs2.getPartNo());
			handlingStockOutVO.setPartDesc(buyerOrderDetailsVOs2.getPartDesc());
			handlingStockOutVO.setSku(buyerOrderDetailsVOs2.getSku());
			handlingStockOutVO.setBuyerOrderNo(buyerOrderVO2.getOrderNo());
			handlingStockOutVO.setBuyerOrderDate(buyerOrderVO2.getOrderDate());
			handlingStockOutVO.setBuyerOrdNo(buyerOrderVO.getDocId());
			handlingStockOutVO.setSDocid(buyerOrderVO2.getDocId());
			handlingStockOutVO.setRpQty(buyerOrderDetailsVOs2.getQty());
			handlingStockOutVO.setSQty(buyerOrderDetailsVOs2.getQty());
			handlingStockOutVO.setPickQty(0);
			handlingStockOutVO.setScreenCode(buyerOrderVO.getScreenCode());
			handlingStockOutVO.setBuyerOrdDate(buyerOrderVO.getDocDate());
			handlingStockOutRepo.save(handlingStockOutVO);
		}		
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", message);
		response.put("buyerOrderVO", buyerOrderVO);
		return response;

	}

	private BuyerOrderVO getBuyerOrderVOfromBuyerOrderDTO(BuyerOrderVO buyerOrderVO, BuyerOrderDTO buyerOrderDTO) {
		buyerOrderVO.setOrderNo(buyerOrderDTO.getOrderNo());
		buyerOrderVO.setOrgId(buyerOrderDTO.getOrgId());
		buyerOrderVO.setOrderDate(buyerOrderDTO.getOrderDate());
		buyerOrderVO.setInvoiceNo(buyerOrderDTO.getInvoiceNo());
		buyerOrderVO.setRefDate(buyerOrderDTO.getRefDate());
		buyerOrderVO.setBuyer(buyerOrderDTO.getBuyer());
		buyerOrderVO.setBuyerShortName(buyerOrderDTO.getBuyerShortName());
		buyerOrderVO.setBillToShortName(buyerOrderDTO.getBillToShortName());
		buyerOrderVO.setBillToName(buyerOrderDTO.getBillToName());
		buyerOrderVO.setShipToShortName(buyerOrderDTO.getShipToShortName());
		buyerOrderVO.setShipToName(buyerOrderDTO.getShipToName());
		buyerOrderVO.setInvoiceDate(buyerOrderDTO.getInvoiceDate());
		buyerOrderVO.setRefNo(buyerOrderDTO.getRefNo());
		buyerOrderVO.setCustomer(buyerOrderDTO.getCustomer());
		buyerOrderVO.setClient(buyerOrderDTO.getClient());
		buyerOrderVO.setFinYear(buyerOrderDTO.getFinYear());
		buyerOrderVO.setBranch(buyerOrderDTO.getBranch());
		buyerOrderVO.setBranchCode(buyerOrderDTO.getBranchCode());
		buyerOrderVO.setWarehouse(buyerOrderDTO.getWarehouse());
		
		if (buyerOrderDTO.getId() != null) {
			List<BuyerOrderDetailsVO> detailsVOs = buyerOrderDetailsRepo.findByBuyerOrderVO(buyerOrderVO);
			buyerOrderDetailsRepo.deleteAll(detailsVOs);
		}

		int orderQty = 0;
		int avilQty = 0;

		List<BuyerOrderDetailsVO> detailsVOList = new ArrayList<BuyerOrderDetailsVO>();
		for (BuyerOrderDetailsDTO buyerOrderDetailsDTO : buyerOrderDTO.getBuyerOrderDetailsDTO()) {

			BuyerOrderDetailsVO detailsVO = new BuyerOrderDetailsVO();
			detailsVO.setPartNo(buyerOrderDetailsDTO.getPartNo());
			detailsVO.setPartDesc(buyerOrderDetailsDTO.getPartDesc());
			detailsVO.setQty(buyerOrderDetailsDTO.getQty());
			detailsVO.setBatchNo(buyerOrderDetailsDTO.getBatchNo());
			detailsVO.setAvailQty(buyerOrderDetailsDTO.getAvailQty());
			detailsVO.setSku(buyerOrderDetailsDTO.getSku());
			detailsVO.setExpDate(buyerOrderDetailsDTO.getExpDate());
			
			avilQty = avilQty + buyerOrderDetailsDTO.getAvailQty();
			orderQty = orderQty + buyerOrderDetailsDTO.getQty();

			detailsVO.setBuyerOrderVO(buyerOrderVO);
			detailsVOList.add(detailsVO);
		}
		buyerOrderVO.setTotalOrderQty(orderQty);
		buyerOrderVO.setTotalAvailQty(avilQty);
		buyerOrderVO.setBuyerOrderDetailsVO(detailsVOList);
		return buyerOrderVO;
	}

	@Override
	public Optional<BuyerOrderVO> getAllBuyerOrderById(Long id) {

		return buyerOrderRepo.findAllBuyerOrderById(id);
	}

	@Override
	public String getBuyerOrderDocId(Long orgId, String finYear, String branch, String branchCode, String client) {
		String ScreenCode = "BO";
		String result = buyerOrderRepo.getbuyerOrderDocId(orgId, finYear, branchCode, client, ScreenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getBoSkuDetails(Long orgId, String branchCode, String client, String batch,
			String warehouse) {
		Set<Object[]> result = buyerOrderRepo.getBoSku(orgId, branchCode, client, batch, warehouse);
		return getAllSkuDetails(result);
	}

	private List<Map<String, Object>> getAllSkuDetails(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("partNo", fs[0] != null ? fs[0].toString() : "");
			part.put("partDesc", fs[1] != null ? fs[1].toString() : "");
			part.put("batch", fs[2] != null ? fs[2].toString() : "");
			part.put("sqty", fs[3] != null ? Integer.parseInt(fs[3].toString()) : 0);  
			part.put("id", fs[4] != null ? Integer.parseInt(fs[4].toString()) : 0);

			details1.add(part);
		}
		return details1;

	}


	@Override
	public int getAvlQtyByBO(Long orgId, String client, String branchCode, String warehouse,
			String branch, String partNo, String partDesc, String batch) {
		int result = buyerOrderRepo.getAvilableQty(orgId, client, branchCode, warehouse, branch, partNo,
				partDesc, batch);
		return result;
	}


	
	@Override
	public List<BuyerOrderVO> getAllBuyerOrderByOrgId(Long orgId) {
		return buyerOrderRepo.findByBo(orgId);
	}

	@Override
	public List<Map<String, Object>> getBatchByBuyerOrder(Long orgId, String branchCode, String client,
			String warehouse, String partNo) {
		Set<Object[]> result = stockDetailsRepo.getDetails(orgId, branchCode, client, warehouse, partNo);
		return getBatch1(result);
	}

	private List<Map<String, Object>> getBatch1(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("batch", fs[0] != null ? fs[0].toString() : "");
			

			details1.add(part);
		}
		return details1;

	}

}
