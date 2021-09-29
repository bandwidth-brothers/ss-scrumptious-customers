package com.ss.scrumptious_customers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAddressDto {

    // everything is optional

    private String line1;
    private String line2;
    private String city;
    private String state;
    private String zip;

}
