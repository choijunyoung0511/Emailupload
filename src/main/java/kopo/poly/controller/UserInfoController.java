package kopo.poly.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kopo.poly.dto.MsgDTO;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.service.impl.UserInfoService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Slf4j
@RequestMapping(value = "/user")
@RequiredArgsConstructor
@Controller
public class UserInfoController {

    private final UserInfoService userInfoService;

    // 회원가입 폼
    @GetMapping(value = "userRegForm")
    public String userRegForm() {
        log.info("{}.userRegForm End", this.getClass().getName());
        return "/user/userRegForm";
    }

    // 아이디 존재 여부 체크
    @ResponseBody
    @PostMapping(value = "getUserIdExists")
    public UserInfoDTO getUserExists(HttpServletRequest request) throws Exception {
        log.info("{}.getUserIdExists Start", this.getClass().getName());

        String userId = CmmUtil.nvl(request.getParameter("userId"));
        log.info("userId : {}", userId);

        UserInfoDTO pDTO = new UserInfoDTO();
        pDTO.setUserId(userId);

        UserInfoDTO rDTO = Optional.ofNullable(userInfoService.getUserIdExists(pDTO))
                .orElseGet(UserInfoDTO::new);

        log.info("{}.getUserIdExists End", this.getClass().getName());
        return rDTO;
    }

    // 이메일 존재 여부 체크
    @ResponseBody
    @PostMapping(value = "getEmailExists")
    public UserInfoDTO getEmailExists(HttpServletRequest request) throws Exception {
        log.info("{}.getEmailExists Start", this.getClass().getName());

        String email = CmmUtil.nvl(request.getParameter("email"));
        log.info("email : {}", email);

        UserInfoDTO pDTO = new UserInfoDTO();
        pDTO.setEmail(EncryptUtil.enAES128CBC(email));

        UserInfoDTO rDTO = Optional.ofNullable(userInfoService.getEmailExists(pDTO))
                .orElseGet(UserInfoDTO::new);

        log.info("{}.getEmailExists End", this.getClass().getName());
        return rDTO;
    }

    // 회원가입
    @ResponseBody
    @PostMapping(value = "insertUserInfo")
    public MsgDTO insertUserInfo(HttpServletRequest request) {
        log.info("{}.insertUserInfo Start", this.getClass().getName());

        int res = 0;
        String msg;
        MsgDTO dto;

        try {
            String userId = CmmUtil.nvl(request.getParameter("userId"));
            String userName = CmmUtil.nvl(request.getParameter("userName"));
            String password = CmmUtil.nvl(request.getParameter("password"));
            String email = CmmUtil.nvl(request.getParameter("email"));
            String addr1 = CmmUtil.nvl(request.getParameter("addr1"));
            String addr2 = CmmUtil.nvl(request.getParameter("addr2"));

            log.info("userId : {}", userId);
            log.info("userName : {}", userName);
            log.info("password : {}", password);
            log.info("email : {}", email);
            log.info("addr1 : {}", addr1);
            log.info("addr2 : {}", addr2);

            UserInfoDTO pDTO = new UserInfoDTO();
            pDTO.setUserId(userId);
            pDTO.setUserName(userName);
            pDTO.setPassword(EncryptUtil.encHashSHA256(password));
            pDTO.setEmail(EncryptUtil.enAES128CBC(email));
            pDTO.setAddr1(addr1);
            pDTO.setAddr2(addr2);

            res = userInfoService.insertUserInfo(pDTO);
            log.info("회원가입 결과(res) : {}", res);

            if (res == 1) {
                msg = "회원가입되었습니다.";
            } else if (res == 2) {
                msg = "이미 가입된 아이디입니다.";
            } else {
                msg = "오류로 인해 회원가입이 실패하였습니다.";
            }

        } catch (Exception e) {
            msg = "실패하였습니다 :" + e;
            res = 0;
            log.error("회원가입 에러", e);
        }

        dto = new MsgDTO();
        dto.setResult(res);
        dto.setMsg(msg);

        log.info("{}.insertUserInfo End", this.getClass().getName());
        return dto;
    }

    // 로그인 페이지
    @GetMapping(value = "login")
    public String login() {
        log.info("{}.login Start", this.getClass().getName());
        log.info("{}.login End", this.getClass().getName());
        return "/user/login";
    }

    // 로그인 처리
    @ResponseBody
    @PostMapping(value = "loginProc")
    public MsgDTO loginProc(HttpServletRequest request, HttpSession session) {
        log.info("{}.loginProc Start", this.getClass().getName());

        int res = 0;
        String msg;
        MsgDTO dto;

        try {
            String userId = CmmUtil.nvl(request.getParameter("userId"));
            String password = CmmUtil.nvl(request.getParameter("password"));
            log.info("userId : {} / password : {}", userId, password);

            UserInfoDTO pDTO = new UserInfoDTO();
            pDTO.setUserId(userId);
            pDTO.setPassword(EncryptUtil.encHashSHA256(password));

            UserInfoDTO rDTO = userInfoService.getLogin(pDTO);

            if (!CmmUtil.nvl(rDTO.getUserId()).isEmpty()) {
                res = 1;
                msg = "로그인이 성공하였습니다";
                session.setAttribute("SS_USER_ID", userId);
                session.setAttribute("SS_USER_NAME", CmmUtil.nvl(rDTO.getUserName()));
            } else {
                msg = "아이디와 비밀번호가 올바르지 않습니다";
            }

        } catch (Exception e) {
            msg = "시스템 문제로 로그인이 실패했습니다";
            res = 2;
            log.error("로그인 에러", e);
        }

        dto = new MsgDTO();
        dto.setResult(res);
        dto.setMsg(msg);

        log.info("{}.loginProc End", this.getClass().getName());
        return dto;
    }

    // 로그인 결과 페이지
    @GetMapping(value = "loginResult")
    public String loginSuccess() {
        log.info("{}.loginResult Start", this.getClass().getName());
        log.info("{}.loginResult End", this.getClass().getName());
        return "/user/loginResult";
    }

    // 아이디 찾기 페이지
    @GetMapping(value = "searchUserId")
    public String searchUserId() {
        log.info("{}.searchUserId Start", this.getClass().getName());
        log.info("{}.searchUserId End", this.getClass().getName());
        return "/user/searchUserId";
    }

    // 아이디 찾기 처리
    @PostMapping(value = "searchUserIdProc")
    public String searchUserIdProc(HttpServletRequest request, ModelMap model) throws Exception {
        log.info("{}.searchUserIdProc Start", this.getClass().getName());

        String userName = CmmUtil.nvl(request.getParameter("UserName"));
        String email = CmmUtil.nvl(request.getParameter("email"));
        log.info("userName : {} / email : {}", userName, email);

        UserInfoDTO pDTO = new UserInfoDTO();
        pDTO.setUserName(userName);
        pDTO.setEmail(EncryptUtil.enAES128CBC(email));

        UserInfoDTO rDTO = Optional.ofNullable(userInfoService.searchUserIdOrPasswordProc(pDTO))
                .orElseGet(UserInfoDTO::new);

        model.addAttribute("rDTO", rDTO);

        log.info("{}.searchUserIdProc End", this.getClass().getName());
        return "/user/searchUserIdResult";
    }

    // 비밀번호 찾기 페이지
    @GetMapping(value = "searchPassword")
    public String searchPassword(HttpSession session) {
        log.info("{}.searchPassword Start", this.getClass().getName());

        session.setAttribute("NEW_PASSWORD", "");
        session.removeAttribute("NEW_PASSWORD");

        return "/user/searchPassword";
    }

    // 비밀번호 찾기 처리
    @PostMapping(value = "searchPasswordProc")
    public String searchPasswordProc(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {
        log.info("{}.searchPasswordProc Start", this.getClass().getName());

        String userId = CmmUtil.nvl(request.getParameter("userId"));
        String userName = CmmUtil.nvl(request.getParameter("userName"));
        String email = CmmUtil.nvl(request.getParameter("email"));

        UserInfoDTO pDTO = new UserInfoDTO();
        pDTO.setUserId(userId);
        pDTO.setUserName(userName);
        pDTO.setEmail(EncryptUtil.enAES128CBC(email));

        UserInfoDTO rDTO = Optional.ofNullable(userInfoService.searchUserIdOrPasswordProc(pDTO))
                .orElseGet(UserInfoDTO::new);

        model.addAttribute("rDTO", rDTO);
        session.setAttribute("NEW_PASSWORD", userId);

        log.info("{}.searchPasswordProc End", this.getClass().getName());
        return "/user/newPassword";
    }

    // 새 비밀번호 저장
    @PostMapping(value = "newPasswordProc")
    public String newPasswordProc(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {
        log.info("{}.newPasswordProc Start", this.getClass().getName());

        String msg;
        String newPasswordUserId = CmmUtil.nvl((String) session.getAttribute("NEW_PASSWORD"));

        if (!newPasswordUserId.isEmpty()) {
            String password = CmmUtil.nvl(request.getParameter("password"));
            log.info("password : {}", password);

            UserInfoDTO pDTO = new UserInfoDTO();
            pDTO.setUserId(newPasswordUserId);
            pDTO.setPassword(EncryptUtil.encHashSHA256(password));

            userInfoService.newPasswordProc(pDTO);

            session.setAttribute("NEW_PASSWORD", "");
            session.removeAttribute("NEW_PASSWORD");

            msg = "비밀번호가 재설정 되었습니다.";
        } else {
            msg = "비정상 접근입니다.";
        }

        model.addAttribute("msg", msg);

        log.info("{}.newPasswordProc End", this.getClass().getName());
        return "/user/newPasswordResult";
    }
}
