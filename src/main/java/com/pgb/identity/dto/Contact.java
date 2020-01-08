package com.pgb.identity.dto;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pgb.identity.annotations.UniqueCredentials;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Contact Class , works in conjunction with Profile
 */
@Data
public class Contact {
    @Id
    private String id;

    @NotNull(message = "email Id cannot be null")
    @Field
    private String emailId;

    @NotNull(message = "password cannot be null")
    @Size(min = 6)
    @Field
    @JsonIgnoreProperties("hidden")
    private String password;

    @Min(1000000000L)
    @Max(9999999999L)
    @Field
    private Long mobileNo;

    @NotNull(message = "country code for mobile number should be provided")
    @Field
    private Integer countryCode;

    @Size(min = 1, message = "at-least one address should be provided")
    @Field
    private List<Address> addresses;
}
