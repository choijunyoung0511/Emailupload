package kopo.poly.dto;

import lombok.Data;

@Data
public class MailInfoDTO {
    private int mailId;
    private String title;
    private String content;
    private String sender;
    private String receiver;
    private String sendTime;
}

