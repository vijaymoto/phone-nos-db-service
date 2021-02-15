package com.example.phonenosdb.controllers;

import com.example.phonenosdb.entities.PhoneNoData;
import com.example.phonenosdb.scopes.CustomRequestScope;
import com.example.phonenosdb.services.PhoneNoDataService;
import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PhoneNoFindController {
    private Logger logger = LoggerFactory.getLogger(PhoneNoFindController.class);

    @Autowired
    CustomRequestScope scope;

    @Autowired
    PhoneNoDataService phoneNoDataService;

    @GetMapping("/phone_nos")
    public ResponseEntity findAllPhoneNos(
            @RequestHeader(name = "request_id", required = false)
                    String requestId,
            @RequestParam(name = "page_no", required = false, defaultValue = "0")
                    Integer pageNo,
            @RequestParam(name = "no_of_records", required = false, defaultValue = "10")
                    Integer noOfRecords) {
        scope.init(requestId);
        logger.info("{} [Request.IN] [FindAll.PhoneNos] Request params pageNo: {}, noOfRecords: {}",
                scope.getLogPrefix(), pageNo, noOfRecords);

        List<PhoneNoData> phoneNoDataList = phoneNoDataService.findAllPhoneNos(pageNo, noOfRecords);
        logger.info("{} [Response.OUT] [FindAll.PhoneNos] PhoneNo Data List: {}",
                scope.getLogPrefix(), phoneNoDataList);

        return ResponseEntity.ok(phoneNoDataList);
    }


    @GetMapping("/customer/{customerId}/phone_nos")
    public ResponseEntity findAllCustomerPhoneNos(
            @RequestHeader(name = "request_id", required = false)
                    String requestId,
            @PathVariable @NotNull String customerId,
            @RequestParam(name = "page_no", required = false, defaultValue = "0")
                    Integer pageNo,
            @RequestParam(name = "no_of_records", required = false, defaultValue = "10")
                    Integer noOfRecords) {
        scope.init(requestId);
        logger.info("{} [Request.IN] [FindAll.Customer.PhoneNos] Request params customerId: {}, pageNo: {}, noOfRecords: {}",
                scope.getLogPrefix(), customerId, pageNo, noOfRecords);

        List<PhoneNoData> phoneNoDataList = phoneNoDataService.findAllCustomerPhoneNos(customerId, pageNo, noOfRecords);
        logger.info("{} [Response.OUT] [FindAll.Customer.PhoneNos] PhoneNo Data List: {}",
                scope.getLogPrefix(), phoneNoDataList);

        return ResponseEntity.ok(phoneNoDataList);
    }

}
