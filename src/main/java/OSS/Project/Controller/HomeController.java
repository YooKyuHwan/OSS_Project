package OSS.Project.Controller;

import OSS.Project.domain.BoardJpa;
import OSS.Project.domain.PostJpa;
import OSS.Project.service.BoardService;
import OSS.Project.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final BoardService boardService;
    private final PostService postService;

    @GetMapping(value = "/")
    public String hello(Model model){
        List<BoardJpa> boardJpas = boardService.findAll();
        for(int i=1; i<=boardJpas.size(); i++){
            List<PostJpa> postJpas = postService.findByBoardId(boardJpas.get(i-1).getId(), 0, 3);
            model.addAttribute("posts"+i, postJpas);
        }
        return "home";
    }
}
