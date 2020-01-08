package com.pgb.identity.dto;

import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Address Storage class used in Contact Class
 */
@Value
public class Address {
    @Pattern(regexp = "HOME|WORK|OTHER", flags = Pattern.Flag.CASE_INSENSITIVE, message = "address type can either be home, work or others (case-insensitive)")
    private String type;
    @NotNull(message = "address line 1 cannot be empty")
    private String line1;
    private String line2;
    private String line3;
    private String landmark;
    private String zipCode;

}
