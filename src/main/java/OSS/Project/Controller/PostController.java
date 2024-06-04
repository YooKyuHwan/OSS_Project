package OSS.Project.Controller;

import OSS.Project.domain.BoardJpa;
import OSS.Project.domain.MemberJpa;
import OSS.Project.domain.PostJpa;
import OSS.Project.dto.MemberDto;
import OSS.Project.dto.PostDto;
import OSS.Project.service.BoardService;
import OSS.Project.service.MemberService;
import OSS.Project.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final BoardService boardService;
    private final MemberService memberService;

    @GetMapping(value = "/board/{boardId}/createPostPage")
    public String createPostPage(@PathVariable long boardId, Model model, HttpSession httpSession){
        //System.out.println("게시글 생성 요청");
        log.info("게시글 생성 요청");
        if(httpSession.getAttribute("member")==null){
            //System.out.println("게시글 작성 권한 없습니다.");
            log.info("게시글 작성 권한이 없습니다. ");
            model.addAttribute("errorMessage", "로그인 후 게시글을 작성해주세요.");
            return "/sign_in";
        }
        //System.out.println("게시글 작성 가능합니다. ");
        log.info("게시글 작성이 가능합니다. ");
        model.addAttribute("boardId", boardId);
        return "/createPost";
    }

    @PostMapping(value = "/board/{boardId}/writePost")
    public String createPost(PostDto postDto, @PathVariable long boardId, Model model, HttpSession httpSession){
        PostJpa postJpa = new PostJpa();
        postJpa.setTitle(postDto.title());
        postJpa.setContent(postDto.content());

        BoardJpa boardJpa = boardService.findOne(boardId);
        MemberDto memberDto = (MemberDto) httpSession.getAttribute("member");
        MemberJpa memberJpa = memberService.getMemberInfo(memberDto.id());
        postJpa.addPostBoard(boardJpa);
        postJpa.addPostMember(memberJpa);
        postService.createPost(postJpa);

        //
        log.info("title: " + postDto.title());
        log.info("content: " + postDto.content());
        return "redirect:/board"+boardId+"/page1";
    }

    @GetMapping(value = "/board/{boardId}/cancel")
    public String  cancelPost(@PathVariable Long boardId, Model model){
        model.addAttribute("boardId", boardId);
        return "redirect:/board"+boardId+"/page1";
    }

    @GetMapping(value = "/board/{boardId}/post{postId}")
    public String getPost(@PathVariable Long boardId, @PathVariable Long postId, Model model){
        PostJpa postJpa = postService.findOnd(postId);
        PostDto postDto = new PostDto(
                postJpa.getId(),
                postJpa.getTitle(),
                postJpa.getContent(),
                postJpa.getDate());
        model.addAttribute("post", postDto);
        return "postPage";
    }

    @GetMapping(value = "/showMyPost/page/{pageId}")
    public String getMyPost(@PathVariable int pageId, Model model, HttpSession httpSession){
        int count = 10;
        int startIndex = (pageId-1)*count;
        //각 페이지 마다 10개씩 보여주기

        MemberDto memberDto = (MemberDto) httpSession.getAttribute("member");
        List<PostJpa> postJpas = postService.findByMemberId(memberDto.id(), startIndex, count);
        model.addAttribute("pageId", pageId);
        if(!postJpas.isEmpty()){
            model.addAttribute("posts", postJpas);
        }

        Long numOfPost = postService.countPostByMember(memberDto.id());
        Long numOfPage = 1L;
        if(numOfPost > 0L){
            numOfPage = (numOfPost - 1L) / 10L + 1L;
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

        return "/myPost";
    }

    @PostMapping(value = "/board/{boardId}/post{postId}/remove")
    public String removeMyPost(@PathVariable Long boardId, @PathVariable Long postId, Model model, HttpSession httpSession){
        System.out.println("게시글 삭제 요청");

        postService.removePost(postId);
        return "redirect:/showMyPost/page/1";
    }

    @PostMapping(value = "/board/{boardId}/post{postId}/remove/ADMIN")
    public String removePostByAdmin(@PathVariable Long boardId, @PathVariable Long postId, Model model, HttpSession httpSession){
        System.out.println("관리자에 의한 게시글 삭제 요청");

        postService.removePost(postId);
        return "redirect:/board" + boardId +"/page1";
    }
}
