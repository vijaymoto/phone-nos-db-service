package com.example.phonenosdb.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "phone_nos",
        indexes = {
                @Index(name = "idx_phone_nos_customer_id", columnList = "customer_id")
        }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNoData {

    @Id
    @Column(name = "phone_no")
    String phoneNo;

    @Column(name = "customer_id")
    String customerId;

    @Column(name = "is_active")
    Boolean isActive;

}
