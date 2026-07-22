package com.whydigit.wms.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.whydigit.wms.dto.CommentsDTO;
import com.whydigit.wms.dto.TicketDTO;
import com.whydigit.wms.entity.CommentsVO;
import com.whydigit.wms.entity.NotificationVO;
import com.whydigit.wms.entity.TicketVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.CommentsRepo;
import com.whydigit.wms.repo.NotificationRepo;
import com.whydigit.wms.repo.TicketRepo;

@Service
public class TicketServiceImpl implements TicketService {

	public static final Logger LOGGER = LoggerFactory.getLogger(TicketServiceImpl.class);

	@Autowired
	TicketRepo ticketRepo;

	@Autowired
	CommentsRepo commentsRepo;

	@Autowired
	NotificationRepo notificationRepo;

	@Autowired
	EmailService emailService;

	@Value("${app.mail.adminEmail}")
	private String adminEmail;

	@Value("${app.mail.noreplay}")
	private String noReplayEmail;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	CommentSyncService commentSyncService;

	@Autowired
	private AsyncService asyncService;

	@Override
	@Transactional
	public Map<String, Object> createUpdateTicket(@Valid TicketDTO ticketDTO) {

		TicketVO ticketVO = new TicketVO();

		ticketVO.setCreatedBy(ticketDTO.getCreatedBy());
		ticketVO.setUpdatedBy(ticketDTO.getCreatedBy());

		mapDtoToVo(ticketVO, ticketDTO);

		ticketVO = ticketRepo.save(ticketVO);

		Long generatedId = ticketVO.getId();
		ticketVO.setSourceId(generatedId);
		ticketRepo.saveAndFlush(ticketVO);

		asyncService.externalApiCallAsync(ticketVO);
		asyncService.sendEmailAsync(ticketVO);

		Map<String, Object> response = new HashMap<>();
		response.put("ticketId", ticketVO.getId());
		response.put("sourceId", ticketVO.getSourceId());
		response.put("ticketVO", ticketVO);
		response.put("message", "Ticket created successfully. Email & sync in progress.");

		return response;
	}

	private void mapDtoToVo(TicketVO vo, TicketDTO dto) {
		vo.setSubject(dto.getSubject());
		vo.setDescription(dto.getDescription());
		vo.setUserName(dto.getUserName());
		vo.setOrgId(dto.getOrgId());
		vo.setStatus(dto.getStatus());
		vo.setEmail(dto.getEmail());
		vo.setBranch(dto.getBranch());
		vo.setBranchCode(dto.getBranchCode());
		vo.setCompanyName(dto.getCompanyName());
		vo.setTicketStatus(dto.getTicketStatus());
	}

	@Override
	public TicketVO uploadTicketScreenShotInBloob(MultipartFile file, Long id) throws IOException {

		TicketVO ticketVO = ticketRepo.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found"));

		ticketVO.setScreenShot(file.getBytes());
		ticketVO = ticketRepo.save(ticketVO);

		callExternalImageAPI(file, id);

		return ticketVO;
	}

	private void callExternalImageAPI(MultipartFile file, Long sourceId) {

		try {

			String url = "http://139.5.190.244:8061/api/ticket/uploadTicketBySourceId";

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);

			MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

			// 🔥 file convert
			body.add("file", new ByteArrayResource(file.getBytes()) {
				@Override
				public String getFilename() {
					return file.getOriginalFilename();
				}
			});

			// 🔥 send sourceId
			body.add("sourceId", sourceId.toString());

			HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

			ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

			if (response.getStatusCode().is2xxSuccessful()) {
				LOGGER.info("✅ Image synced to external server");
			} else {
				LOGGER.error("❌ External image upload failed");
			}

		} catch (Exception e) {
			LOGGER.error("❌ External API Exception: ", e);
		}
	}

	@Override
	public Optional<TicketVO> getTicketById(Long id) {
		return ticketRepo.findById(id);
	}

	@Override
	public List<TicketVO> getTicketByUserName(String userName, Long orgId) {

		return ticketRepo.findByUserName(userName, orgId);
	}

	@Override
	public List<TicketVO> getTicketByOrgId(Long orgId) {
		// TODO Auto-generated method stub
		return ticketRepo.getByOrgId(orgId);
	}

	@Override
	public Map<String, Object> updateCreateComments(@Valid CommentsDTO commentsDTO) throws ApplicationException {

		CommentsVO commentsVO;

		String message = null;

		if (ObjectUtils.isEmpty(commentsDTO.getId())) {

			commentsVO = new CommentsVO();

			commentsVO.setCreatedBy(commentsDTO.getCreatedBy());
			commentsVO.setUpdatedBy(commentsDTO.getCreatedBy());

			message = "Comments Creation Successfully";
		}

		else {
			commentsVO = commentsRepo.findById(commentsDTO.getId()).orElseThrow(
					() -> new ApplicationException("CommentsVO  Not Found with id: " + commentsDTO.getId()));
			commentsVO.setUpdatedBy(commentsDTO.getCreatedBy());

			message = "Comments Updation Successfully";
		}

		commentsVO = getCommentsVOFromCommentsDTO(commentsVO, commentsDTO);
		commentsRepo.save(commentsVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("commentsVO", commentsVO);
		return response;
	}

	private CommentsVO getCommentsVOFromCommentsDTO(CommentsVO commentsVO, @Valid CommentsDTO commentsDTO) {

		commentsVO.setComments(commentsDTO.getComments());
		commentsVO.setOrgId(commentsDTO.getOrgId());
		commentsVO.setUserName(commentsDTO.getUserName());
		commentsVO.setTicketId(commentsDTO.getTicketId());
		return commentsVO;
	}

	@Override
	public List<CommentsVO> getCommentsByTicketId(Long ticketId, Long orgId) {
		// TODO Auto-generated method stub
		return commentsRepo.getComments(ticketId, orgId);
	}

	@Override
	public TicketVO updateTicketStatus(Long orgId, Long ticketId, String status, String userName) {

		TicketVO ticketVO = ticketRepo.findByTicketIdAndorgId(ticketId, orgId);

		if (ticketVO == null) {
			throw new RuntimeException("Ticket Records not found This Id");
		}

		ticketVO.setStatus(status);
		ticketVO.setUpdatedBy(userName);
		ticketVO.setNotificationFlag(true);
		ticketRepo.save(ticketVO);

		boolean mailSent = false;

		String Ticketstatus = ticketVO.getSubject() + " - Ticket Status Changed";

		try {
			String htmlContent = loadHtmlTemplateUpdateMail(ticketVO.getId(), Ticketstatus, ticketVO.getStatus(),
					ticketVO.getDescription());
			emailService.sendHtmlEmail(noReplayEmail, ticketVO.getEmail(), Ticketstatus, htmlContent);
			mailSent = true;

		} catch (Exception e) {
			System.err.println("❌ Failed to send mail for ticket ID " + ticketVO.getId() + ": " + e.getMessage());
			e.printStackTrace();
		}

		return ticketVO;

	}

	@Override
	public void deleteCommentsById(Long id) {

		commentsRepo.deleteById(id);
	}

	// Notification

	@Override
	public List<Map<String, Object>> getTicketNotification(Long orgId) {

		Set<Object[]> subGroupDetails = ticketRepo.getTicketNotification(orgId);
		return getTicket(subGroupDetails);
	}

	private List<Map<String, Object>> getTicket(Set<Object[]> subGroupDetails) {
		List<Map<String, Object>> subgroup = new ArrayList<>();
		for (Object[] sub : subGroupDetails) {
			Map<String, Object> mp = new HashMap<>();
			mp.put("id", sub[0] != null ? sub[0].toString() : 0);
			mp.put("ticketId", sub[1] != null ? sub[1].toString() : 0);
			mp.put("createdBy", sub[2] != null ? sub[2].toString() : "");
			mp.put("description", sub[3] != null ? sub[3].toString() : " ");
			mp.put("orgId", sub[4] != null ? sub[4].toString() : 0);
			mp.put("status", sub[5] != null ? sub[5].toString() : "");
			mp.put("subject", sub[6] != null ? sub[6].toString() : " ");
			mp.put("userName", sub[7] != null ? sub[7].toString() : " ");

			subgroup.add(mp);
		}
		return subgroup;
	}

	@Override
	public TicketVO updateNotification(Long orgId, Long ticketId, String status) {

		if ("Clear".equalsIgnoreCase(status)) {
			TicketVO ticketVO1 = ticketRepo.findByTicketIdAndorgId(ticketId, orgId);
			if (ticketVO1 != null) {
				ticketVO1.setStatusFlag(false);
				ticketRepo.save(ticketVO1);
				return ticketVO1;
			}
		}

		if ("ClearAll".equalsIgnoreCase(status)) {
			List<TicketVO> ticketList = ticketRepo.findByOrgId(orgId);
			for (TicketVO ticket : ticketList) {
				ticket.setStatusFlag(false);
			}
			ticketRepo.saveAll(ticketList);
			return ticketList.isEmpty() ? null : ticketList.get(0);
		}

		return null;
	}

	@Override
	public List<Map<String, Object>> getNotificationFromUser(String userName) {

		Set<Object[]> subGroupDetails = ticketRepo.getNotificationFromUser(userName);
		return getNotificationUser(subGroupDetails);
	}

	private List<Map<String, Object>> getNotificationUser(Set<Object[]> subGroupDetails) {
		List<Map<String, Object>> subgroup = new ArrayList<>();
		for (Object[] sub : subGroupDetails) {
			Map<String, Object> mp = new HashMap<>();
			mp.put("id", sub[0] != null ? sub[0].toString() : 0);
			mp.put("ticketId", sub[1] != null ? sub[1].toString() : 0);
			mp.put("updatedBy", sub[2] != null ? sub[2].toString() : "");
			mp.put("status", sub[3] != null ? sub[3].toString() : " ");
			mp.put("orgId", sub[4] != null ? sub[4].toString() : 0);
			mp.put("comments", sub[5] != null ? sub[5].toString() : "");

			subgroup.add(mp);
		}
		return subgroup;
	}

	@Override
	public TicketVO clearUserNotification(Long orgId, String userName, Long ticketId, String status) {

		if ("Clear".equalsIgnoreCase(status)) {
			// Clear notification flag for a specific TicketVO
			TicketVO ticketVO1 = ticketRepo.findByTicketIdAndorgId(ticketId, orgId);
			if (ticketVO1 != null) {
				ticketVO1.setNotificationFlag(false);
				ticketRepo.save(ticketVO1);

				// Clear notification flag for all CommentsVOs associated with this ticket
				List<CommentsVO> commentsList = commentsRepo.findByTicketIdAndorgId(ticketId, orgId);
				for (CommentsVO commentsVO1 : commentsList) {
					commentsVO1.setNotificationFlag(false);
				}
				commentsRepo.saveAll(commentsList); // Save all updated comments

				return ticketVO1;
			}
		}

		if ("ClearAll".equalsIgnoreCase(status)) {
			// Clear notification flags for all TicketVOs
			List<TicketVO> ticketList = ticketRepo.findByUserName(userName);
			for (TicketVO ticket : ticketList) {
				ticket.setNotificationFlag(false);
			}
			ticketRepo.saveAll(ticketList);

			// Clear notification flags for all CommentsVOs
			List<CommentsVO> commentList = commentsRepo.findByUserName(userName);
			for (CommentsVO commentsVO : commentList) {
				commentsVO.setNotificationFlag(false);
			}
			commentsRepo.saveAll(commentList);

			// Return the first ticket or null
			return ticketList.isEmpty() ? null : ticketList.get(0);
		}

		return null;
	}

	public String loadHtmlTemplate(Long ticketId, String subject, String status, String description, String CreatedBy,
			String Email, String createdOn) {
		try {
			ClassPathResource resource = new ClassPathResource("template/email_template.html");
			String content = new String(resource.getInputStream().readAllBytes());

			return content.replace("${ticketId}", ticketId.toString()).replace("${subject}", subject)
					.replace("${status}", status).replace("${description}", description)
					.replace("${raisedBy}", CreatedBy).replace("${raisedEamil}", Email)
					.replace("${raisedOn}", createdOn);

		} catch (Exception e) {
			e.printStackTrace();
			return "<p>Default email content</p>";
		}
	}

	public String loadHtmlTemplateUpdateMail(Long ticketId, String subject, String status, String description) {
		try {
			ClassPathResource resource = new ClassPathResource("template/Updates_mail.html");
			String content = new String(resource.getInputStream().readAllBytes());

			return content.replace("${ticketId}", ticketId.toString()).replace("${subject}", subject)
					.replace("${status}", status).replace("${description}", description);

		} catch (Exception e) {
			e.printStackTrace();
			return "<p>Default email content</p>";
		}
	}

	@Override
	public Map<String, Object> createComments(CommentsDTO commentDTO) {

		Map<String, Object> response = new HashMap<>();

		try {
			System.out.println("📥 Incoming SourceId: " + commentDTO.getSourceId());

			CommentsVO vo = new CommentsVO();

			vo.setComments(commentDTO.getComments());
			vo.setUserName(commentDTO.getUserName());
			vo.setTicketId(commentDTO.getTicketId());
			vo.setSourceUserName(commentDTO.getSourceUserName());
			vo.setOrgId(commentDTO.getOrgId());
			vo.setSourceTicketId(commentDTO.getSourceTicketId());
			vo.setCreatedBy(commentDTO.getCreatedBy());
			vo.setUpdatedBy(commentDTO.getCreatedBy());

			// 🔥 VERY IMPORTANT
			vo.setSourceId(commentDTO.getSourceId());

			commentsRepo.saveAndFlush(vo);

			System.out.println("💾 Saved in Server A: " + vo.getId());

			// ✅ FIXED CONDITION
			if (commentDTO.getSourceId() == null || commentDTO.getSourceId() == 0) {
				System.out.println("🔁 A → B Triggered");
				commentSyncService.sendToServerB(vo);
			} else {
				System.out.println("⛔ Skipping A → B (Synced data)");
			}

			response.put("status", true);
			response.put("message", "Saved in Server A");
			response.put("commentVO", vo);

		} catch (Exception e) {
			e.printStackTrace();
			response.put("status", false);
			response.put("message", e.getMessage());
		}

		return response;
	}

	@Override
	public List<CommentsVO> getAllCommentsAnotherServer(Long ticketId) {
		return commentsRepo.getAllCommentsAnotherServer(ticketId);

	}

	@Override
	public List<CommentsVO> getAllCommentsMyServer(Long ticketId) {
		return commentsRepo.getAllCommentsMyServer(ticketId);

	}

	@Override
	public CommentsVO updateComments(CommentsDTO dto) {

		CommentsVO vo;

		if (dto.getId() != null) {

			vo = commentsRepo.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Not found in A by id"));

			System.out.println("✏️ Updating in A using commentsid");

			vo.setComments(dto.getComments());
			vo.setUserName(dto.getUserName());
			vo.setTicketId(dto.getTicketId());

			commentsRepo.save(vo);

			commentSyncService.updateToServerB(vo);
		}

		// ✅ 2. SYNC UPDATE FROM B
		else if (dto.getSourceId() != null) {

			vo = commentsRepo.findBySourceId(dto.getSourceId())
					.orElseThrow(() -> new RuntimeException("Not found in A by sourceId"));

			System.out.println("✏️ Updating in A using sourceId");

			vo.setComments(dto.getComments());
			vo.setUserName(dto.getUserName());
			vo.setTicketId(dto.getTicketId());

			commentsRepo.save(vo);
		}

		else {
			throw new RuntimeException("❌ id and sourceId both NULL");
		}

		return vo;
	}

	@Override
	public void deleteComments(Long id, Long sourceId) {

		// ✅ 1. LOCAL DELETE (A UI)
		if (id != null) {

			commentsRepo.deleteById(id);
			System.out.println("🗑️ Deleted in Server A (LOCAL)");

			// 🔥 Sync to B
			commentSyncService.deleteInServerB(id);
		}

		else if (sourceId != null) {

			CommentsVO vo = commentsRepo.findBySourceId(sourceId)
					.orElseThrow(() -> new RuntimeException("Not found in A by sourceId"));

			commentsRepo.delete(vo);

			System.out.println("🗑️ Deleted in Server A (SYNC)");
		}

		else {
			throw new RuntimeException("❌ id and sourceId both NULL");
		}
	}

	@Override
	public List<TicketVO> getTicketByUser(Long orgId, Long userId) {

		return ticketRepo.getByUserId(orgId, userId);
	}

	@Override
	public List<Map<String, Object>> getNotificationDetails(Long orgId, String branchCode, String client,
			String warehouse) {

		Set<Object[]> result = notificationRepo.getNotificationDetails(orgId, branchCode, client, warehouse);

		return prepareEscalationResponse(result);
	}

	private List<Map<String, Object>> prepareEscalationResponse(Set<Object[]> result) {

		List<Map<String, Object>> response = new ArrayList<>();

		List<Map<String, Object>> lowStockSummary = new ArrayList<>();
		List<Map<String, Object>> maximumStockSummary = new ArrayList<>();
		List<Map<String, Object>> holdStockSummary = new ArrayList<>();
		List<Map<String, Object>> slowMovingStockSummary = new ArrayList<>();
		List<Map<String, Object>> deadStockSummary = new ArrayList<>();
		List<Map<String, Object>> nearExpirySummary = new ArrayList<>();

		for (Object[] grid : result) {

			String type = grid[6] != null ? grid[6].toString().trim() : "";

			Map<String, Object> map = new LinkedHashMap<>();

			map.put("id", grid[7] != null ? Long.parseLong(grid[7].toString()) : 0L);
			map.put("type", type);

			if ("Low Stock".equalsIgnoreCase(type)) {

				String message = grid[2] + " (" + grid[1] + ") stock is " + grid[5] + " " + grid[3]
						+ ", below the critical quantity of " + grid[4] + " " + grid[3] + ".";

				map.put("message", message);
				lowStockSummary.add(map);

			} else if ("Maximum Stock".equalsIgnoreCase(type)) {

				String message = grid[2] + " (" + grid[1] + ") stock is " + grid[5] + " " + grid[3]
						+ ", exceeding the maximum quantity of " + grid[4] + " " + grid[3] + ".";

				map.put("message", message);
				maximumStockSummary.add(map);

			} else if ("Hold Stock".equalsIgnoreCase(type)) {

				String message = grid[2] + " (" + grid[1] + ") " + grid[5] + " " + grid[3]
						+ " has been moved to HOLD Bin " + grid[13] + ".";

				map.put("message", message);
				holdStockSummary.add(map);

			} else if ("Slow Move".equalsIgnoreCase(type)) {

				String message = grid[2] + " (" + grid[1] + ") has not moved for " + grid[16]
						+ " days. Current stock is " + grid[5] + ". Last Sale Date : " + grid[15] + ".";

				map.put("message", message);
				slowMovingStockSummary.add(map);

			} else if ("Dead Move".equalsIgnoreCase(type)) {

				String message = grid[2] + " (" + grid[1] + ") has not moved for " + grid[16]
						+ " days. Current stock is " + grid[5] + ". Last Sale Date : " + grid[15] + ".";

				map.put("message", message);
				deadStockSummary.add(map);

			} else if ("Near Expiry".equalsIgnoreCase(type)) {

				String message = grid[2] + " (" + grid[1] + ") has " + grid[5] + " " + grid[3] + " expiring in "
						+ grid[17] + " day(s). Expiry Date : " + grid[12] + ", Bin : " + grid[13] + ".";

				map.put("message", message);
				nearExpirySummary.add(map);
			}
		}

		Map<String, Object> finalResponse = new LinkedHashMap<>();

		finalResponse.put("LowStockSummary", lowStockSummary);
		finalResponse.put("MaximumStockSummary", maximumStockSummary);
		finalResponse.put("HoldStockSummary", holdStockSummary);
		finalResponse.put("SlowMovingStockSummary", slowMovingStockSummary);
		finalResponse.put("DeadStockSummary", deadStockSummary);
		finalResponse.put("NearExpirySummary", nearExpirySummary);

		response.add(finalResponse);

		return response;
	}

	@Override
	public NotificationVO updateNotificationDetails(Long orgId, Long notificationId) {

		NotificationVO notificationVO = notificationRepo.findByNotificationIdAndOrgId(orgId, notificationId);

		if (notificationVO == null) {
			throw new RuntimeException("Notification not found.");
		}

		notificationVO.setRead(true);

		return notificationRepo.save(notificationVO);
	}

}
