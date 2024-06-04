package OSS.Project.service;


import OSS.Project.domain.PostJpa;
import OSS.Project.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;

    @Override
    @Transactional
    public void createPost(PostJpa postJpa) {
        postRepository.save(postJpa);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countPostByBoard(Long boardId) {
        return postRepository.countPostByBoard(boardId);
    }

    @Override
    @Transactional
    public void revisePost(Long id, String title, String content) {
        PostJpa postJpa = postRepository.findOne(id);
        postJpa.setTitle(title);
        postJpa.setContent(content);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostJpa> findByMemberId(Long memberId, int startIndex, int count) {
        return postRepository.findByMemberId(memberId, startIndex, count);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostJpa> findByBoardId(Long boardId, int startIndex, int count) {
        return postRepository.findByBoardId(boardId, startIndex, count);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countPostByMember(Long memberId) {
        return postRepository.countPostByMember(memberId);
    }

    @Override
    @Transactional
    public void removePost(Long postId) {
        //PostJpa postJpa = postRepository.findOne(postId);
        PostJpa postJpa = postRepository.getRef(postId);
        postRepository.removePost(postJpa);
    }

    @Override
    @Transactional(readOnly = true)
    public PostJpa findOnd(Long id) {
        return postRepository.findOne(id);
    }
}
