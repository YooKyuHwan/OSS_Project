package OSS.Project.service;



import OSS.Project.domain.PostJpa;

import java.util.List;

public interface PostService {

    public void createPost(PostJpa postJpa);
    public void revisePost(Long id, String title, String content);

    public PostJpa findOnd(Long id);

    public List<PostJpa> findByBoardId(Long boardId, int startIndex, int count);

    public List<PostJpa> findByMemberId(Long memberId, int startIndex, int count);

    public void removePost(Long postId);

    public Long countPostByBoard(Long boardId);

    public Long countPostByMember(Long memberId);
}
