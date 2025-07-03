package kopo.poly.controller;

import jakarta.servlet.http.HttpServletRequest;
import kopo.poly.dto.MailDTO;
import kopo.poly.dto.MsgDTO;
import kopo.poly.service.IMailService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequestMapping(value = "/mail")
@RequiredArgsConstructor
@Controller
public class MailController {
    private final IMailService mailService;

    @GetMapping(value = "mailForm")
    public String mailFrom() {
        log.info("{}.mailFrom start!", this.getClass().getName());
        return "mail/mailForm";
    }
    @ResponseBody
    @PostMapping(value = "sendMail")
    public MsgDTO sendMail(HttpServletRequest requerst){
        log.info("{}.sendMail start!", this.getClass().getName());
        String msg;
        String toMail = CmmUtil.nvl(requerst.getParameter("toMail"));
        String title = CmmUtil.nvl(requerst.getParameter("title"));
        String contents = CmmUtil.nvl(requerst.getParameter("content"));

        log.info("toMail : {} / title : {} / contents : {}", toMail, title, contents);
        MailDTO pDTO = new MailDTO();

        pDTO.setToMail(toMail);
        pDTO.setTitle(title);
        pDTO.setContents(contents);
        int res = mailService.doSendMail(pDTO);
        if(res ==1 ){
            msg = "메일 발송하였습니다";
        }else {
            msg = "메일 발송 실패하였습니다";
        }
        log.info(msg);
        MsgDTO dto = new MsgDTO();
        dto.setMsg(msg);

        log.info("{}.sendMail end!", this.getClass().getName());
        return dto;

    }

}


