package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.TicketDTO;
import com.whydigit.wms.entity.TicketVO;
import com.whydigit.wms.exception.ApplicationException;

@Service
public interface TicketService {
	
	Map<String,Object>createUpdateTicket(TicketDTO ticketDTO) throws ApplicationException;
	
	List<TicketVO> getTicketByUser(Long orgId,Long userId);
	
	TicketVO getTicketById(Long id);



	


}
