package com.example.phonenosdb.services;

import com.example.phonenosdb.controllers.PhoneNoSearchController;
import com.example.phonenosdb.entities.PhoneNoData;
import com.example.phonenosdb.repositories.PhoneNoDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PhoneNoDataService {
    private Logger logger = LoggerFactory.getLogger(PhoneNoDataService.class);

    @Autowired
    PhoneNoDataRepository phoneNoDataRepository;


    public List<PhoneNoData> findAllPhoneNos(Integer pageNo, Integer noOfRecords) {
        Pageable pageable = PageRequest.of(pageNo, noOfRecords);

        Page<PhoneNoData> phoneNoDataPage = phoneNoDataRepository.findAll(pageable);
        List<PhoneNoData> phoneNoEntityList = phoneNoDataPage.getContent();

        return phoneNoEntityList;
    }


    public List<PhoneNoData> findAllCustomerPhoneNos(String customerId, Integer pageNo, Integer noOfRecords) {
        Pageable pageable = PageRequest.of(pageNo, noOfRecords);

        Page<PhoneNoData> phoneNoDataPage = phoneNoDataRepository.findAllByCustomerIdIs(customerId, pageable);
        List<PhoneNoData> phoneNoEntityList = phoneNoDataPage.getContent();

        return phoneNoEntityList;
    }


    @Transactional
    public boolean updatePhoneNoActiveStatus(String phoneNo, boolean isActive) {
        int status = phoneNoDataRepository.updateIsActive(phoneNo, isActive);
        return status == 0 ? false : true;
    }

}
