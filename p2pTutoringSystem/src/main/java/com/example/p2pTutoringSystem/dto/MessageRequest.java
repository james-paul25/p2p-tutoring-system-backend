package com.example.p2pTutoringSystem.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
public class MessageRequest {
    private String message;
    private MultipartFile file;
    private String senderRole;
}
