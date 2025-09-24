package kopo.poly.service;

import kopo.poly.dto.MailInfoDTO;

import java.util.List;

public interface IMailInfoService {
        List<MailInfoDTO> getMailList() throws Exception;
        MailInfoDTO getMailInfo(int mailId, boolean updateReadCnt) throws Exception;
        void insertMailInfo(MailInfoDTO pDTO) throws Exception;
    }

