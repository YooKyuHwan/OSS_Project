package OSS.Project.service;

import OSS.Project.domain.ReplyJpa;
import OSS.Project.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{
    private final ReplyRepository replyRepository;

    @Override
    @Transactional
    public void save(ReplyJpa replyJpa) {
        replyRepository.save(replyJpa);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReplyJpa> findByPostId(Long postId, int startIndex, int count) {
        return replyRepository.findByPostId(postId, startIndex, count);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countByReplyMember(Long memberId) {
        return replyRepository.countReplyByMember(memberId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReplyJpa> findByMemberId(Long memberId, int startIndex, int count) {
        return replyRepository.findByMemberId(memberId, startIndex, count);
    }

    @Override
    @Transactional
    public void removeReply(Long replyId) {
        ReplyJpa replyJpa = replyRepository.findOne(replyId);
        replyRepository.removeReply(replyJpa);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countReplyByPost(Long postId) {
        return replyRepository.countReplyByPost(postId);
    }
}
