package com.ss.scrumptious_customers.dto;

import javax.validation.constraints.NotBlank;
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
public class CreateAddressDto {

    @NotBlank(message = "Address line1 is mandatory")
    private String line1;

    private String line2;

    @NotBlank(message = "City is mandatory")
    private String city;

    @NotBlank(message = "State is mandatory")
    @Size(min = 2, max = 2, message = "State must consist of only 2 characters.")
    private String state;

    @NotBlank(message = "Zipcode is mandatory")
    @Pattern(regexp = "^\\d{5}(?:[-\\s]\\d{4})?$",
        message = "Zipcode does not meet expected format: '#####-####' or '#####'")
    private String zip;

}
