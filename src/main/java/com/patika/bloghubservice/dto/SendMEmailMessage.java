package com.patika.bloghubservice.dto;

import com.patika.bloghubservice.client.email.dto.request.enums.EmailTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SendMEmailMessage {
    private String to;
    private EmailTemplate emailTemplate;
}
