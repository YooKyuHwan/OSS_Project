package OSS.Project.repository;

import OSS.Project.domain.MemberJpa;
import OSS.Project.domain.PostJpa;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    //@PersistenceContext
    private final EntityManager em;//spring data jpa에 의해서 자동주입

    public void save(MemberJpa memberJpa){
        em.persist(memberJpa);
    }

    public MemberJpa findOne(Long id){
        return em.find(MemberJpa.class, id);
    }

    public List<MemberJpa> findById(String memberId){
        return em.createQuery("select m from MemberJpa m where m.memberId = :ID", MemberJpa.class)
                .setParameter("ID", memberId)
                .getResultList();
    }

    public List<MemberJpa> findAll(){
        return em.createQuery("select m from MemberJpa m", MemberJpa.class).getResultList();
    }

    public List<PostJpa> findPost(MemberJpa memberJpa){
        return memberJpa.getPostJpas();
    }

}
