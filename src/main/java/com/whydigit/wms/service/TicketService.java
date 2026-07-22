package com.whydigit.wms.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.whydigit.wms.dto.CommentsDTO;
import com.whydigit.wms.dto.TicketDTO;
import com.whydigit.wms.entity.CommentsVO;
import com.whydigit.wms.entity.NotificationVO;
import com.whydigit.wms.entity.TicketVO;
import com.whydigit.wms.exception.ApplicationException;

@Service
public interface TicketService {

	Map<String, Object> createUpdateTicket(TicketDTO ticketDTO) throws ApplicationException;

	List<TicketVO> getTicketByUser(Long orgId, Long userId);

	Optional<TicketVO> getTicketById(Long id);

	TicketVO uploadTicketScreenShotInBloob(MultipartFile file, Long id) throws IOException;

	List<TicketVO> getTicketByUserName(String userName, Long orgId);

	List<TicketVO> getTicketByOrgId(Long orgId);

	Map<String, Object> updateCreateComments(@Valid CommentsDTO commentsDTO) throws ApplicationException;

	List<CommentsVO> getCommentsByTicketId(Long ticketId, Long orgId);

	TicketVO updateTicketStatus(Long orgId, Long ticketId, String status, String userName);

	void deleteCommentsById(Long id);

	List<Map<String, Object>> getTicketNotification(Long orgId);

	TicketVO updateNotification(Long orgId, Long ticketId, String status);

	List<Map<String, Object>> getNotificationFromUser(String userName);

	TicketVO clearUserNotification(Long orgId, String userName, Long ticketId, String status);

	Map<String, Object> createComments(CommentsDTO commentDTO);

	List<CommentsVO> getAllCommentsAnotherServer(Long ticketId);

	List<CommentsVO> getAllCommentsMyServer(Long ticketId);

	CommentsVO updateComments(CommentsDTO dto);

	void deleteComments(Long id, Long sourceId);

	List<Map<String, Object>> getNotificationDetails(Long orgId, String branchCode, String client, String warehouse);

	NotificationVO updateNotificationDetails(Long orgId, Long notificationId);

}
