package OSS.Project.Controller;

import OSS.Project.domain.MemberJpa;
import OSS.Project.domain.PostJpa;
import OSS.Project.domain.ReplyJpa;
import OSS.Project.dto.MemberDto;
import OSS.Project.dto.ReplyDto;
import OSS.Project.service.MemberService;
import OSS.Project.service.PostService;
import OSS.Project.service.ReplyService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReplyController {
    private final PostService postService;
    private final MemberService memberService;
    private final ReplyService replyService;

    @GetMapping(value = "/showMyReply/page/{pageId}")
    public String getMyReply(@PathVariable int pageId, Model model, HttpSession httpSession){
        int count = 10;
        int startIndex = (pageId-1)*count;
        //각 페이지 마다 10개씩 보여주기

        MemberDto memberDto = (MemberDto) httpSession.getAttribute("member");
        List<ReplyJpa> replyJpas = replyService.findByMemberId(memberDto.id(), startIndex, count);
        if(!replyJpas.isEmpty()){
            model.addAttribute("replys", replyJpas);
        }
        model.addAttribute("pageId", pageId);

        Long numOfReply = replyService.countByReplyMember(memberDto.id());
        Long numOfPage = 1L;
        if(numOfReply > 0L) {
            numOfPage = (numOfReply - 1L) / 10L + 1L;
        }
        model.addAttribute("pages", numOfPage);

        List<Integer> numList = new ArrayList<>();
        int s = pageId - 2;
        if(s<1) s = 1;
        int e = Math.min(pageId+2, numOfPage.intValue());
        for(int i = s; i<=e; i++){
            numList.add(i);
            System.out.println("번호: "+i);
        }
        model.addAttribute("nums", numList);

        return "myReply";
    }

    @GetMapping(value = "/showReply/post{postId}/page{pageId}")
    public String getReplyByPost(@PathVariable Long postId, @PathVariable int pageId, Model model){
        int count = 10;
        int startIndex = (pageId-1)*count;
        //댓글 10개씩 보여주기

        System.out.println("postId : "+postId);

        List<ReplyJpa> replyJpas = replyService.findByPostId(postId, startIndex, count);
        model.addAttribute("postId", postId);
        model.addAttribute("pageId", pageId);
        if(!replyJpas.isEmpty()){
            model.addAttribute("replys", replyJpas);
        }

        Long numOfReply = replyService.countReplyByPost(postId);
        Long numOfPage = 1L;
        if(numOfReply > 0L) {
            numOfPage = (numOfReply - 1L) / 10L + 1L;
        }
        model.addAttribute("pages", numOfPage);

        List<Integer> numList = new ArrayList<>();
        int s = pageId - 2;
        if(s<1) s = 1;
        int e = Math.min(pageId+2, numOfPage.intValue());
        for(int i = s; i<=e; i++){
            numList.add(i);
            System.out.println("번호: "+i);
        }
        model.addAttribute("nums", numList);
        //첫 댓글보기 요청에서만 해당 게시글에 달린 댓글 개수를 조회하는 쿼리 날리고,
        //해당 게시글의 댓글보기 요청에 대해서만, model 통해서 댓글 개수 전달해주기

        System.out.println("numOfReply : " + numOfReply + "   numOfPage: " + numOfPage);
        return "replyOnPost";
    }

    @PostMapping(value = "/createReply/post{postId}")
    public String createReply(ReplyDto replyDto, @PathVariable Long postId, Model model, HttpSession httpSession){
        if(httpSession.getAttribute("member")==null){
            model.addAttribute("errorMessage", "로그인 후 댓글 작성해주세요.");
            return "sign_in";
        }

        ReplyJpa replyJpa = new ReplyJpa();
        replyJpa.setContent(replyDto.content());
        System.out.println("createReply content: " + replyDto.content());

        PostJpa postJpa = postService.findOnd(postId);
        MemberDto memberDto = (MemberDto) httpSession.getAttribute("member");
        MemberJpa memberJpa = memberService.getMemberInfo(memberDto.id());
        //연관관계 설정
        replyJpa.addReplyMember(memberJpa);
        replyJpa.addReplyPost(postJpa);
        replyService.save(replyJpa);
        return "redirect:/showReply/post"+postId+"/page1";
    }

    @PostMapping(value = "/post{postId}/reply{replyId}/remove/ADMIN")
    public String removeReplyByAdmin(@PathVariable Long postId, @PathVariable Long replyId, Model model){
        //구현해야함
        replyService.removeReply(replyId);
        return "redirect:/showReply/post"+postId+"/page1";
    }
}

