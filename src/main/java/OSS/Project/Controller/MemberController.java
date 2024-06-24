package OSS.Project.Controller;

import OSS.Project.domain.MemberJpa;
import OSS.Project.domain.MemberRole;
import OSS.Project.dto.LoginInfo;
import OSS.Project.dto.MemberDto;
import OSS.Project.dto.SignUpInfo;
import OSS.Project.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {
    //private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;

    @GetMapping(value = "/sign_in.html")
    public String singIn(){
        return "sign_in";
    }

    @PostMapping(value = "/signOut")
    public String signOut(HttpSession httpSession){
        httpSession.invalidate();
        return "redirect:/";
    }

    @GetMapping(value = "sign_up.html")
    public String signUp(){
        return "/sign_up";
    }

    @PostMapping(value = "/sign_up_check")
    public String signUpCheck(SignUpInfo signUpInfo) throws IOException {
        String memberId = signUpInfo.id();
        String name = signUpInfo.name();
        String password = signUpInfo.password();
        String workingName = signUpInfo.workingname();
        String email = signUpInfo.email();

        MemberJpa memberJpa = new MemberJpa();
        memberJpa.setMemberId(memberId);
        memberJpa.setMemberName(name);
        //memberJpa.setPassword(passwordEncoder.encode(password));
        //data.sql로 값 추가를 위해, 비번 암호화 생략
        memberJpa.setPassword(password);
        memberJpa.setWorkingName(workingName);
        memberJpa.setEmail(email);
        memberJpa.setRole(MemberRole.NORMAL);

        try{
            memberService.save(memberJpa);
            //System.out.println("가입 성공");
            log.info("가입 성공");
            return "redirect:/sign_in.html";
        } catch (Exception e){
            log.error("가입 실패", e);
            //System.out.println(e.toString());
            //System.out.println("가입 실패");
            return "sign_up_duplicate";
        }
    }


    @PostMapping(value = "check_login")
    public String signInCheck(LoginInfo loginInfo, HttpSession httpSession){
        String memberId = loginInfo.id();
        String password = loginInfo.password();

        //System.out.println("ID: "+memberId + "  Pwd: "+password);
        //System.out.println("check");
        log.info("ID: " + memberId + "Pwd: " + password);
        log.info("check");
        try{
            MemberJpa memberJpa = memberService.signIn(memberId, password);
            //System.out.println("로그인 성공");
            log.info("로그인 성공");
            MemberDto memberDto = new MemberDto(
                    memberJpa.getId(),
                    memberJpa.getMemberId(),
                    memberJpa.getMemberName(),
                    memberJpa.getWorkingName(),
                    memberJpa.getEmail(),
                    memberJpa.getRole());
            httpSession.setAttribute("member", memberDto);
            return "redirect:/";

        } catch (Exception e){
            log.error("로그인 실패", e);
            return "sign_in_error";
        }
    }

    @GetMapping(value = "/memberInfo")
    public String memberInfoPage(HttpSession httpSession, Model model){
        MemberDto memberDto = (MemberDto) httpSession.getAttribute("member");
        model.addAttribute("member", memberDto);
        return "memberInfo";
    }

    @GetMapping(value = "/reviseMemberName")
    public String reviseName(@RequestParam String name, HttpSession httpSession, Model model){
        MemberDto memberDto = (MemberDto) httpSession.getAttribute("member");
        memberService.reviseName(memberDto.id(), name);
        MemberDto newMember = new MemberDto(
                memberDto.id(),
                memberDto.memberId(),
                name,
                memberDto.workingName(),
                memberDto.email(),
                memberDto.role());
        httpSession.setAttribute("member", newMember);
        model.addAttribute("member", newMember);
        return "memberInfo";
    }

    @GetMapping(value = "/reviseWorkingName")
    public String reviseWorkingName(@RequestParam String workingName, HttpSession httpSession, Model model){
        MemberDto memberDto = (MemberDto) httpSession.getAttribute("member");
        memberService.reviseWorkingName(memberDto.id(), workingName);
        MemberDto newMember = new MemberDto(
                memberDto.id(),
                memberDto.memberId(),
                memberDto.name(),
                workingName,
                memberDto.email(),
                memberDto.role());
        model.addAttribute("member", newMember);
        httpSession.setAttribute("member", newMember);
        //System.out.println("별명 수정 " + workingName);
        log.info("별명 수정 " + workingName);
        return "memberInfo";
    }
}
