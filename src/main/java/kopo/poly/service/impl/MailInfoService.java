package kopo.poly.service.impl;
import kopo.poly.dto.MailInfoDTO;
import kopo.poly.mapper.IMailInfoMapper;
import kopo.poly.service.IMailInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailInfoService implements IMailInfoService {
    //shift + F6키 누르면 한번에 바꿀수 있음 ->적극적으로 사용필요
    //인터페이스 서비스에서 가져온다
    //오버라이드 한번에 하는법 ?? -> 알트 + 엔터


    private final IMailInfoMapper mailInfoMapper; // ✅ 정상 인식되도록

    //매퍼를 가져온다

    @Override
    public List<MailInfoDTO> getMailList() throws Exception {
        log.info("{}.getMailList start!", this.getClass().getSimpleName());
        List<MailInfoDTO> rDTO = mailInfoMapper.getMailList();

        log.info("{}", rDTO.get(0));
        return rDTO;
    }

    @Override
    public MailInfoDTO getMailInfo(int mailId, boolean updateReadCnt) throws Exception {
        return mailInfoMapper.getMailInfo(mailId);
    }


    @Override
    public void insertMailInfo(MailInfoDTO pDTO) throws Exception {
        log.info("{}.insertMailInfo start!", this.getClass().getSimpleName()); //DTO
        mailInfoMapper.insertMailInfo(pDTO);
    }
}
