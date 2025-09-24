package kopo.poly.controller;

import jakarta.servlet.http.HttpServletRequest;
import kopo.poly.dto.MailDTO;
import kopo.poly.dto.MailInfoDTO;
import kopo.poly.service.IMailInfoService;
import kopo.poly.service.IMailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {

    private final IMailInfoService mailInfoService;
    private final IMailService mailService;

    // 📌 메일 목록
    @GetMapping("mailList")
    public String mailList(ModelMap model) throws Exception {
        List<MailInfoDTO> rList = Optional.ofNullable(mailInfoService.getMailList())
                .orElseGet(ArrayList::new);

        model.addAttribute("rList", rList);
        return "mail/mailList"; // /WEB-INF/views/mail/mailList.jsp
    }

    // 📌 메일 상세
    @GetMapping("mailInfo")
    public String mailInfo(@RequestParam("mailId") int mailId, ModelMap model) throws Exception {
        MailInfoDTO rDTO = Optional.ofNullable(mailInfoService.getMailInfo(mailId, true))
                .orElseGet(MailInfoDTO::new);

        model.addAttribute("rDTO", rDTO);
        return "mail/mailInfo"; // /WEB-INF/views/mail/mailInfo.jsp
    }

    // 📌 메일 등록 폼
    @GetMapping("mailForm")
    public String mailForm() {
        return "mail/mailForm"; // /WEB-INF/views/mail/mailForm.jsp
    }

    // 📌 메일 DB 저장만 (기존 유지)
    @PostMapping("insertMailInfo")
    @ResponseBody
    public String insertMailInfo(HttpServletRequest request) throws Exception {
        MailInfoDTO pDTO = new MailInfoDTO();

        pDTO.setReceiver(request.getParameter("toMail"));     // 받는사람
        pDTO.setTitle(request.getParameter("title"));         // 제목
        pDTO.setContent(request.getParameter("contents"));    // 내용
        mailInfoService.insertMailInfo(pDTO);

        return "success";
    }
    //컨트롤러->서비스로 pdto에담아서 전당
    // 📌 메일 발송 + DB 저장 (신규 추가)
    @PostMapping("sendMail")
    @ResponseBody
    public String sendMail(HttpServletRequest request) throws Exception {
        //세션값
        String toMail = request.getParameter("toMail");
        String title = request.getParameter("title");
        String contents = request.getParameter("contents");
        //DB에저장할려고 DTO에 저장
        MailDTO pDTO = new MailDTO();
        pDTO.setToMail(toMail);
        pDTO.setTitle(title);
        pDTO.setContents(contents);

        int res = mailService.doSendMail(pDTO);
        log.info("{}.sendMail result: {}", this.getClass().getSimpleName(), res);
        //성공하면  : 1 반환
        return res == 1 ? "success" : "fail";
    }
}
