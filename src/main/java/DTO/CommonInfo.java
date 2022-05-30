package DTO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CommonInfo {
    private Integer id;

    private Timestamp updated;
}
