package com.sys.ims.dto;

import com.sys.ims.enums.ComplaintPriority;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class ComplaintRequest {

    private UUID customerId;

    private Integer productId;


    private String title;


    private String description;


    private ComplaintPriority priority;
}
