package OSS.Project.Controller;

import OSS.Project.domain.PostJpa;
import OSS.Project.service.BoardService;
import OSS.Project.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final PostService postService;


    @GetMapping(value = "/board{id}/page{pageId}")
    public String viewBoard(@PathVariable Long id, @PathVariable int pageId, Model model){
        //System.out.println("get request board"+id);
        model.addAttribute("boardId", id);

        int count = 10;
        int startIndex = (pageId-1)*10;
        //게시글 10개씩 보여주기

        List<PostJpa> postJpas = postService.findByBoardId(id, startIndex, count);
        model.addAttribute("pageId", pageId);
        model.addAttribute("boardId", id);

        if(!postJpas.isEmpty()){
            model.addAttribute("posts", postJpas);
        }else{
            model.addAttribute("message", "작성된 게시글이 없습니다.");
        }

        Long numOfPost = postService.countPostByBoard(id);
        System.out.println("numOfPost: " + numOfPost);
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
        return "board"+id;
    }
}

