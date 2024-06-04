package OSS.Project.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table(name="member")
@Entity
@Getter @Setter
public class MemberJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "member_password")
    private String password;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "working_name")
    private String  workingName;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_role")
    private MemberRole role;

    //연관관계 매핑
    @OneToMany(mappedBy = "memberJpa", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    //Post엔티티의 필드 값인 'memebr'에 연관
    //사용자 개인정보에서 '내가 올린 게시글' 클릭해야 가져오게끔 구현
    private List<PostJpa> postJpas = new ArrayList<>();


    @OneToMany(mappedBy = "memberJpa", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    //사용자 개인정보에서 '내가 쓴 댓글' 클릭해야 가져오게끔 구현
    private List<ReplyJpa> replies = new ArrayList<>();
}
