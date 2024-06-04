package OSS.Project.service;


import OSS.Project.domain.BoardJpa;
import OSS.Project.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;

    @Override
    @Transactional(readOnly = true)
    public List<BoardJpa> findAll() {
        return boardRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public BoardJpa findOne(Long id) {
        return boardRepository.findOne(id);
    }
}
