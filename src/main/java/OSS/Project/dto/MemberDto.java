package OSS.Project.dto;


import OSS.Project.domain.MemberRole;

public record MemberDto(Long id,
                        String memberId,
                        String name,
                        String workingName,
                        String email,
                        MemberRole role) {
}
