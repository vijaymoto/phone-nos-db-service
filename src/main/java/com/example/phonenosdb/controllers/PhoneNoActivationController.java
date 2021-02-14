package com.example.phonenosdb.controllers;

import com.example.phonenosdb.scopes.CustomRequestScope;
import com.example.phonenosdb.services.PhoneNoDataService;
import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class PhoneNoActivationController {

    private Logger logger = LoggerFactory.getLogger(PhoneNoSearchController.class);

    @Autowired
    CustomRequestScope scope;

    @Autowired
    PhoneNoDataService phoneNoDataService;

    @PostMapping("/phone_nos/activate/{phoneNo}")
    public ResponseEntity activatePhoneNo(
            @RequestHeader(name = "request_id", required = false)
                    String requestId,
            @PathVariable @NotNull String phoneNo
    ) {
        scope.init(requestId);

        logger.info("{} [Request.IN] [Activate.PhoneNo] PhoneNo: {} Activate Request received",
                scope.getLogPrefix(), phoneNo);
        boolean status = phoneNoDataService.updatePhoneNoActiveStatus(phoneNo, true);
        logger.info("{} [Response.Out] [Activate.PhoneNo] PhoneNo: {} Activate Op Status: {}",
                scope.getLogPrefix(), phoneNo, status);

        String resp = String.format("Phone No: %s Activation: %s", phoneNo, getOpStatus(status));
        return status ? ResponseEntity.ok(resp) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    }


    @PostMapping("/phone_nos/deactivate/{phoneNo}")
    public ResponseEntity deactivatePhoneNo(
            @RequestHeader(name = "request_id", required = false)
                    String requestId,
            @PathVariable @NotNull String phoneNo
    ) {
        scope.init(requestId);

        logger.info("{} [Request.IN] [Deactivate.PhoneNo] PhoneNo: {} Deactivate Request received",
                scope.getLogPrefix(), phoneNo);
        boolean status = phoneNoDataService.updatePhoneNoActiveStatus(phoneNo, false);
        logger.info("{} [Response.Out] [Deactivate.PhoneNo] PhoneNo: {} Deactivate Op Status: {}",
                scope.getLogPrefix(), phoneNo, status);

        String resp = String.format("Phone No: %s Deactivation: %s", phoneNo, getOpStatus(status));
        return status ? ResponseEntity.ok(resp) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    }


    private String getOpStatus(boolean status) {
        return (status) ? "SUCCESS" : "FAILED";
    }

}
