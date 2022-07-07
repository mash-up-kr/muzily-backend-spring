package kr.mashup.ladder.domain.member.infra.jpa

import kr.mashup.ladder.domain.member.domain.Member
import kr.mashup.ladder.domain.member.infra.querydsl.MemberQueryRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Long>, MemberQueryRepository
