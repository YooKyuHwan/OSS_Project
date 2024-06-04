package OSS.Project.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Table(name = "board")
@Entity
@Getter
public class BoardJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(name = "board_name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private BoardCategory category;

    //연관관계 매핑
    @OneToMany(mappedBy = "boardJpa", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PostJpa> postJpas = new ArrayList<>();
}
