package org.example.addressbook.application.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class UserInfo extends CommonInfo{
    private String fIO;

    private Integer cityId;

    private String email;

    private String phone;
}
