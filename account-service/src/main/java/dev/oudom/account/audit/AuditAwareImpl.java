package dev.oudom.account.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditAware")
public class AuditAwareImpl implements AuditorAware<String> {

    private static final String SYSTEM_AUDITOR = "account-service";

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SYSTEM_AUDITOR);
    }
}
