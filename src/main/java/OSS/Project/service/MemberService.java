package OSS.Project.service;

import OSS.Project.domain.MemberJpa;
import OSS.Project.domain.PostJpa;

import java.util.List;

public interface MemberService {
    public void save(MemberJpa memberJpa);
    public MemberJpa signIn(String memberId, String password) throws Exception;
    public List<PostJpa> getPostByMember(String memberId);
    public MemberJpa getMemberInfo(Long id);
    public void reviseName(Long id, String name);

    public void reviseWorkingName(Long id, String workingName);
}
