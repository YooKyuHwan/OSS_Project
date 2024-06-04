package OSS.Project.service;


import OSS.Project.domain.MemberJpa;
import OSS.Project.domain.MemberRole;
import OSS.Project.domain.PostJpa;
import OSS.Project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Qualifier
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;


    @Override
    @Transactional
    public void save(MemberJpa memberJpa){//sign up
        checkDuplicateMember(memberJpa);
        memberRepository.save(memberJpa);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberJpa signIn(String memberId, String password) throws Exception {
        List<MemberJpa> memberJpas = memberRepository.findById(memberId);
        if (memberJpas.isEmpty()){
            //System.out.println("존재하지 않는 아이디입니다. ");
            log.info("존재하지 않는 아이디입니다. ");
            throw new Exception("존재하지 않는 아이디 입니다.");
        }else{
            MemberJpa m = memberJpas.get(0);
            if(m.getRole() == MemberRole.ADMIN){
                   if(!m.getPassword().equals(password)){
                       //System.out.println("관리자 계정 비밀번호 미일치");
                        log.info("관리자 계정 비밀번호 미일치");
                       throw new Exception("비밀번호가 일치하지 않습니다.");
                   }
            }

            else if(!password.equals(m.getPassword())){
                log.info("비밀번호가 일치하지 않습니다. ");
                throw new Exception("비밀번호가 일치하지 않습니다. ");
            }
            return m;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostJpa> getPostByMember(String memberId) {
        MemberJpa memberJpaFind = memberRepository.findById(memberId).get(0);
        return memberRepository.findPost(memberJpaFind);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberJpa getMemberInfo(Long id) {//회원정보 조회
        return memberRepository.findOne(id);
    }

    @Override
    @Transactional
    public void reviseName(Long id, String name) {
        MemberJpa memberJpa = memberRepository.findOne(id);
        memberJpa.setMemberName(name);
    }

    @Override
    @Transactional
    public void reviseWorkingName(Long id, String workingName) {
        MemberJpa memberJpa = memberRepository.findOne(id);
        memberJpa.setWorkingName(workingName);
    }

    private void checkDuplicateMember(MemberJpa memberJpa){
        List<MemberJpa> memberJpas = memberRepository.findById(memberJpa.getMemberId());
        if(!memberJpas.isEmpty()){
            throw new IllegalStateException("이미 존재하는 아이디 입니다.");
        }
    }

}
