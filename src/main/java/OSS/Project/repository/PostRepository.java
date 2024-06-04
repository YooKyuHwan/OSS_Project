package OSS.Project.repository;


import OSS.Project.domain.PostJpa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {
    private final EntityManager em;

    public void save(PostJpa postJpa){
        em.persist(postJpa);
    }

    public PostJpa getRef(Long id){
        return em.getReference(PostJpa.class, id);
    }

    public void delete(PostJpa postJpa){
        em.remove(postJpa);
    }

    public PostJpa findOne(Long id){
        return em.find(PostJpa.class, id);
        //return em.getReference(PostJpa.class, id);
    }

    public Long countPostByBoard(Long boardId) {
        TypedQuery<Long> query =
                em.createQuery("select count(p) from PostJpa p where p.boardJpa.id = :boardId", Long.class);
        query.setParameter("boardId", boardId);
        return query.getSingleResult();
    }

    public Long countPostByMember(Long memberId) {
        TypedQuery<Long> query =
                em.createQuery("select count(p) from PostJpa p where p.memberJpa.id = :memberId", Long.class);
        query.setParameter("memberId", memberId);
        return query.getSingleResult();
    }

    public List<PostJpa> findByBoardId(Long boardId, int startIndex, int count){
        TypedQuery<PostJpa> query =
                em.createQuery("select p " +
                        "from PostJpa p " +
                        "where p.boardJpa.id = :boardId " +
                        "order by p.id desc ", PostJpa.class);
        query.setParameter("boardId", boardId);
        query.setFirstResult(startIndex);
        query.setMaxResults(count);
        return query.getResultList();
    }

    public List<PostJpa> findByMemberId(Long memberId, int startIndex, int count){
        TypedQuery<PostJpa> query =
                em.createQuery("select p " +
                        "from PostJpa p " +
                        "where p.memberJpa.id = :memberId " +
                        "order by p.id desc ", PostJpa.class);
        query.setParameter("memberId", memberId);
        query.setFirstResult(startIndex);
        query.setMaxResults(count);
        return query.getResultList();
    }

    public void removePost(PostJpa postJpa){
        em.remove(postJpa);
    }
}
