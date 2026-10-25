package org.example.week3.library.policy;

import org.example.week3.library.model.Member;
import org.example.week3.library.model.MemberRole;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        name = "library.borrowing-policy",
        havingValue = "role"
)
public class RoleBasedBorrowingPolicy implements BorrowingPolicy {

    @Override
    public boolean canBorrow(Member member) {
        return member.getBorrowedBooksCount()
                < getMaximumBooks(member);
    }

    @Override
    public int getMaximumBooks(Member member) {
        return switch (member.getRole()) {
            case STUDENT -> 2;
            case REGULAR -> 3;
            case PREMIUM -> 5;
        };
    }

    @Override
    public String getPolicyName() {
        return "Role-based policy";
    }
}