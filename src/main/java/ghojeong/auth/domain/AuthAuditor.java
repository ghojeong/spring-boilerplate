package ghojeong.auth.domain;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthAuditor implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return Optional.empty();
        }
        if (auth.getDetails() instanceof MemberUserDetails details) {
            String auditor = String.format(
                    "MEMBER,%d,%s",
                    details.getUserSeq(),
                    details.getNickname()
            );

            return Optional.of(auditor);
        }
        if (auth.getDetails() instanceof AdminUserDetails details) {
            String auditor = String.format(
                    "ADMIN,%d,%s",
                    details.getAdminUserSeq(),
                    details.getNickname()
            );
            return Optional.of(auditor);
        }
        return Optional.empty();
    }
}
