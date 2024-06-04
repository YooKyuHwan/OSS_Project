package OSS.Project.repository;

import OSS.Project.domain.ReplyJpa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReplyRepository {
    private final EntityManager em;

    public void save(ReplyJpa replyJpa){
        em.persist(replyJpa);
        //return replyJpa.getId();
    }

    public List<ReplyJpa> findByPostId(Long postId, int startIndex, int count){
        TypedQuery<ReplyJpa> query =
                em.createQuery("select r " +
                        "from ReplyJpa r " +
                        "where r.postJpa.id = :postId " +
                        "order by r.id desc ", ReplyJpa.class);
        query.setParameter("postId", postId);
        query.setFirstResult(startIndex);
        query.setMaxResults(count);
        return query.getResultList();
    }

    public Long countReplyByPost(Long postId){
        TypedQuery<Long> query =
                em.createQuery("select count(r) from ReplyJpa r where r.postJpa.id = :postId", Long.class);
        query.setParameter("postId", postId);
        return query.getSingleResult();
    }

    public Long countReplyByMember(Long memberId){
        TypedQuery<Long> query =
                em.createQuery("select count(r) from ReplyJpa r where r.memberJpa.id = :memberId", Long.class);
        query.setParameter("memberId", memberId);
        return query.getSingleResult();
    }

    public List<ReplyJpa> findByMemberId(Long memberId, int startIndex, int count){
        TypedQuery<ReplyJpa> query =
                em.createQuery("select r " +
                        "from ReplyJpa r " +
                        "where r.memberJpa.id = :memberId o" +
                        "rder by r.id desc ", ReplyJpa.class);
        query.setParameter("memberId", memberId);
        query.setFirstResult(startIndex);
        query.setMaxResults(count);
        return query.getResultList();
    }

    public void removeReply(ReplyJpa replyJpa){
        em.remove(replyJpa);
    }

    public ReplyJpa findOne(Long id){
        return em.find(ReplyJpa.class, id);
    }

}
