package com.whydigit.wms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.TicketDTO;
import com.whydigit.wms.entity.TicketVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.TicketRepo;

@Service
public class TicketServiceImpl implements TicketService {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(TicketServiceImpl.class);
	
	@Autowired
	TicketRepo ticketRepo;

	@Override
	public Map<String, Object> createUpdateTicket(TicketDTO ticketDTO) throws ApplicationException {
		TicketVO ticketVO;
	    String message;

	    if (ObjectUtils.isEmpty(ticketDTO.getId())) {
	        // Create new region
	    	ticketVO = new TicketVO();
	    	ticketVO.setCreatedBy(ticketDTO.getCreatedBy());
	    	ticketVO.setUpdatedBy(ticketDTO.getCreatedBy());
	        message = "Ticket Created Successfully";
	    } else {
	        // Update existing region
	    	ticketVO = ticketRepo.findById(ticketDTO.getId())
	                .orElseThrow(() -> new ApplicationException("This Id Is Not Found Any Information: " + ticketDTO.getId()));
	    	ticketVO.setUpdatedBy(ticketDTO.getCreatedBy());
	        message = "Region Updated Successfully";
	    }

	    getTicketVOFromTicketDTO(ticketVO, ticketDTO);
	    ticketRepo.save(ticketVO);

	    Map<String, Object> response = new HashMap<>();
	    response.put("message", message);
	    response.put("ticketVO", ticketVO);
	    return response;
	}

	private void getTicketVOFromTicketDTO(TicketVO ticketVO, TicketDTO ticketDTO) {
		
		ticketVO.setOrgId(ticketDTO.getOrgId());
		ticketVO.setBranchCode(ticketDTO.getBranchCode());
		ticketVO.setBranch(ticketDTO.getBranch());
		ticketVO.setClient(ticketDTO.getClient());
		ticketVO.setCustomer(ticketDTO.getCustomer());
		ticketVO.setWarehouse(ticketDTO.getWarehouse());
		ticketVO.setFinYear(ticketDTO.getFinYear());
		ticketVO.setName(ticketDTO.getName());
		ticketVO.setIssueDesc(ticketDTO.getIssueDesc());
		ticketVO.setEmail(ticketDTO.getEmail());
		ticketVO.setTicketStatus("Open");
	}

	

	@Override
	public TicketVO getTicketById(Long id) {
		return ticketRepo.findById(id).get();
	}

	@Override
	public List<TicketVO> getTicketByUser(Long orgId, Long userId) {
		
		return ticketRepo.getByUserId(orgId, userId);
	}

}
