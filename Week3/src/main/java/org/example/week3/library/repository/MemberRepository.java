package org.example.week3.library.repository;



import org.example.week3.library.model.Member;
import org.springframework.stereotype.Repository;
import org.example.week3.library.model.MemberRole;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {

    private final List<Member> members = new ArrayList<>();

    public MemberRepository() {
        members.add(new Member(1L, "Ion Popescu",
                MemberRole.STUDENT));

        members.add(new Member(2L, "Maria Ionescu",
                MemberRole.PREMIUM));
    }

    public Optional<Member> findById(Long id) {
        return members.stream()
                .filter(member -> member.getId().equals(id))
                .findFirst();
    }

    public List<Member> findAll() {
        return members;
    }
}