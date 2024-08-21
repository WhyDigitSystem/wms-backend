package com.whydigit.wms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whydigit.wms.service.OutwardTransactionService;

@RestController
@RequestMapping("/api/outward")
public class OutwardTransactionController extends BaseController{

	public static final Logger LOGGER = LoggerFactory.getLogger(OutwardTransactionController.class);

	@Autowired
	OutwardTransactionService outwardTransactionService;
	
	
		
		
	
}
