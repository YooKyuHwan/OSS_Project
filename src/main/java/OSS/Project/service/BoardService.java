package OSS.Project.service;



import OSS.Project.domain.BoardJpa;

import java.util.List;

public interface BoardService {
    public List<BoardJpa> findAll();
    public  BoardJpa findOne(Long id);
}
