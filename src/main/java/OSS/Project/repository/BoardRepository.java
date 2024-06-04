package OSS.Project.repository;


import OSS.Project.domain.BoardJpa;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private final EntityManager em;
    public List<BoardJpa> findByCategory(String category){
        return em.createQuery("select b from BoardJpa b where b.category = :category", BoardJpa.class)
                .setParameter("category", category)
                .getResultList();
    }

    public List<BoardJpa> findAll(){
        return em.createQuery("select b from BoardJpa b", BoardJpa.class).getResultList();
    }

    public BoardJpa findOne(Long id){
        return em.find(BoardJpa.class, id);
    }
}
