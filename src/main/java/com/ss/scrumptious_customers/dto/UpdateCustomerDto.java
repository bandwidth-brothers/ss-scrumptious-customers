package com.ss.scrumptious_customers.dto;

import java.sql.Date;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCustomerDto {

    private String firstName;

    private String lastName;

    @Email(message = "Email is invalid")
    private String email;

    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{4}$",
            message = "Phone number must be in the form ###-###-####.")
    private String phone;

    private Date dob;
    private String picture;
    private Boolean veteranaryStatus;

    @Valid
    private UpdateAddressDto address;
    
}