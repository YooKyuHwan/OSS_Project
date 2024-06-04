package OSS.Project.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Table(name = "reply")
@Entity
@Getter @Setter
public class ReplyJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "date")
    private String date;

    //연관관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)//익명 댓글
    @JoinColumn(name = "member_id", nullable = false)
    private MemberJpa memberJpa;

    @ManyToOne(fetch = FetchType.LAZY)//댓글에서 게시글을 조회하지는 않으므로
    @JoinColumn(name = "post_id", nullable = false)
    private PostJpa postJpa;

    public void addReplyPost(PostJpa postJpa){
        if(this.postJpa==null){
            this.postJpa = postJpa;
        }

        if(!postJpa.getReplies().contains(this)){
            postJpa.getReplies().add(this);
        }
    }

    public void addReplyMember(MemberJpa memberJpa){
        if(this.memberJpa == null){
            this.memberJpa = memberJpa;
        }

        if(!memberJpa.getReplies().contains(this)){
            memberJpa.getReplies().add(this);
        }
    }
}
