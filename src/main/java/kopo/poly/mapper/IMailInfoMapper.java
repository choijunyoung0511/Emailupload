package kopo.poly.mapper;

import kopo.poly.dto.MailInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface IMailInfoMapper {

    // 메일 저장
    int insertMailInfo(MailInfoDTO pDTO) throws Exception;

    // 메일 단건 조회
    MailInfoDTO getMailInfo(int mailId) throws Exception;

    // 메일 목록 조회
    List<MailInfoDTO> getMailList() throws Exception;
}
