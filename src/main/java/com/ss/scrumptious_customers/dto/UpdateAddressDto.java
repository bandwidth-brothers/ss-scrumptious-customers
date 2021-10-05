package com.ss.scrumptious_customers.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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

    @Size(min = 2, max = 2, message = "State must consist of only 2 characters.")
    private String state;
    
    @Pattern(regexp = "^\\d{5}(?:[-\\s]\\d{4})?$")
    private String zip;

}
