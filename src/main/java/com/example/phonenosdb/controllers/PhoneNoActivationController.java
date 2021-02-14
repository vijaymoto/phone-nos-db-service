package com.example.phonenosdb.controllers;

import com.example.phonenosdb.scopes.CustomRequestScope;
import com.example.phonenosdb.services.PhoneNoDataService;
import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

        logger.info("{} [Request.IN] [Activate.PhoneNo] Activate Request for PhoneNo: {}",
                scope.getLogPrefix(), phoneNo);
        boolean status = phoneNoDataService.updatePhoneNoActiveStatus(phoneNo, false);
        logger.info("{} [Response.Out] [Activate.PhoneNo] PhoneNo: {} Activate Op Status: {}",
                scope.getLogPrefix(), phoneNo, status);

        String resp = String.format("Phone No: %s Activation: %s", phoneNo, getOpStatus(status));
        return ResponseEntity.ok(resp);
    }


    @PostMapping("/phone_nos/deactivate/{phoneNo}")
    public ResponseEntity deactivatePhoneNo(
            @RequestHeader(name = "request_id", required = false)
                    String requestId,
            @PathVariable @NotNull String phoneNo
    ) {
        scope.init(requestId);

        logger.info("{} [Request.IN] [Deactivate.PhoneNo] Deactivate Request for PhoneNo: {}",
                scope.getLogPrefix(), phoneNo);
        boolean status = phoneNoDataService.updatePhoneNoActiveStatus(phoneNo, false);
        logger.info("{} [Response.Out] [Deactivate.PhoneNo] PhoneNo: {} Deactivate Op Status: {}",
                scope.getLogPrefix(), phoneNo, status);

        String resp = String.format("Phone No: %s Deactivation: %s", phoneNo, getOpStatus(status));
        return ResponseEntity.ok(resp);
    }


    private String getOpStatus(boolean status) {
        return (status) ? "SUCCESS" : "FAILED";
    }

}
