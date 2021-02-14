package com.example.phonenosdb.repositories;

import com.example.phonenosdb.entities.PhoneNoData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PhoneNoDataRepository extends JpaRepository<PhoneNoData, String> {

    Page<PhoneNoData> findAllByCustomerIdIs(@Param("customerId") String customerId, Pageable pageable);

    @Modifying
    @Query("UPDATE PhoneNoData p set p.isActive = :isActive WHERE p.phoneNo = :phoneNo")
    int updateIsActive(@Param("phoneNo") String phoneNo,
                             @Param("isActive") Boolean isActive);

}
