package org.example.week3.library.policy;

import org.example.week3.library.model.Member;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        name = "library.borrowing-policy",
        havingValue = "fixed",
        matchIfMissing = true
)
public class FixedLimitBorrowingPolicy implements BorrowingPolicy {

    private static final int MAX_BOOKS = 3;

    @Override
    public boolean canBorrow(Member member) {
        return member.getBorrowedBooksCount() < MAX_BOOKS;
    }

    @Override
    public int getMaximumBooks(Member member) {
        return MAX_BOOKS;
    }

    @Override
    public String getPolicyName() {
        return "Fixed limit policy";
    }
}