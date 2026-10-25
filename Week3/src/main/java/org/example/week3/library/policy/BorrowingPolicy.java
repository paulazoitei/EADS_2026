package org.example.week3.library.policy;

import org.example.week3.library.model.Member;

public interface BorrowingPolicy {

    boolean canBorrow(Member member);

    int getMaximumBooks(Member member);

    String getPolicyName();
}