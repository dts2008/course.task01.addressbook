package DTO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserInfo extends CommonInfo{
    private String fIO;

    private Integer cityId;

    private String email;

    private String phone;
}
