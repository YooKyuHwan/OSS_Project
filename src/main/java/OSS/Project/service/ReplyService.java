package OSS.Project.service;


import OSS.Project.domain.ReplyJpa;

import java.util.List;

public interface ReplyService {
    public List<ReplyJpa> findByPostId(Long postId, int startIndex, int count);
    public List<ReplyJpa> findByMemberId(Long memberId, int startIndex, int count);
    public void removeReply(Long replyId);
    public Long countReplyByPost(Long postId);
    public void save(ReplyJpa replyJpa);

    public Long countByReplyMember(Long memberId);
}
