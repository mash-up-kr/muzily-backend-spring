package kr.mashup.ladder.domain.member.infra.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import kr.mashup.ladder.domain.member.domain.Member
import kr.mashup.ladder.domain.member.domain.QAccount.account
import kr.mashup.ladder.domain.member.domain.QMember.member
import kr.mashup.ladder.domain.member.domain.SocialType
import org.springframework.stereotype.Repository

@Repository
class MemberQueryRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : MemberQueryRepository {

    override fun findMemberBySocialIdAndSocialType(socialId: String, socialType: SocialType): Member? {
        return queryFactory.selectFrom(member)
            .innerJoin(member.accounts, account)
            .where(
                account.socialInfo.socialId.eq(socialId),
                account.socialInfo.socialType.eq(socialType)
            ).fetchOne()
    }

    override fun existsMemberById(memberId: Long): Boolean {
        return queryFactory.selectOne()
            .from(member)
            .where(
                member.id.eq(memberId)
            ).fetchFirst() != null
    }

}
