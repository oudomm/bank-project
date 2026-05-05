package dev.oudom.bank.loan.mapper;

import dev.oudom.bank.loan.domain.Loan;
import dev.oudom.bank.loan.dto.CreateLoanRequest;
import dev.oudom.bank.loan.dto.LoanResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoanMapper {

    @Mapping(target = "loanNumber", source = "loanNumber")
    @Mapping(target = "outstandingAmount", source = "request.principalAmount")
    @Mapping(target = "status", constant = "PENDING")
    Loan toEntity(CreateLoanRequest request, String loanNumber);

    LoanResponse toResponse(Loan loan);
}
