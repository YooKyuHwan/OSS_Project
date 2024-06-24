package OSS.Project.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Table(name = "post")
@Entity
@Getter @Setter
public class PostJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //GenerationType.IDENTITY는 기본키(PK)생성을 DB에 위임하는 것,
    //Mysql에서는 AUTO_INCREMENT
    @Column(name = "post_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "date")
    private String date;

    //연관관계 매핑
    @ManyToOne(fetch = FetchType.EAGER)//보통 게시글은 누가 올린지 표시 되므로
    @JoinColumn(name = "member_id", nullable = false)
    private MemberJpa memberJpa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private BoardJpa boardJpa;

    @OneToMany(mappedBy = "postJpa", fetch = FetchType.LAZY)// cascade = CascadeType.REMOVE, orphanRemoval = true)
    //댓글보기를 눌렀을 때만 가져오게끔 구현

    private List<ReplyJpa> replies;

    public void addPostBoard(BoardJpa boardJpa){
        if(this.boardJpa == null){
            this.boardJpa = boardJpa;
        }

        if(!boardJpa.getPostJpas().contains(this)){
            boardJpa.getPostJpas().add(this);
        }
    }

    public void addPostMember(MemberJpa memberJpa){
        if(this.memberJpa == null){
            this.memberJpa = memberJpa;
        }

        if(!memberJpa.getPostJpas().contains(this)){
            memberJpa.getPostJpas().add(this);
        }
    }
}
