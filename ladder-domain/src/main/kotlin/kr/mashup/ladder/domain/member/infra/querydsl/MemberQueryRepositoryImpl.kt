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

    override fun existsMemberById(accountId: Long): Boolean {
        return queryFactory.selectOne()
            .from(member)
            .where(
                member.id.eq(accountId),
            ).fetchFirst() != null
    }

    override fun existsMemberBySocialIdAndSocialType(socialId: String, socialType: SocialType): Boolean {
        return queryFactory.selectOne()
            .from(member)
            .innerJoin(account).on(account.member.id.eq(member.id))
            .where(
                account.socialInfo.socialId.eq(socialId),
                account.socialInfo.socialType.eq(socialType)
            ).fetchFirst() != null
    }

    override fun findMemberBySocialIdAndSocialType(socialId: String, socialType: SocialType): Member? {
        return queryFactory.selectFrom(member)
            .innerJoin(member.accounts, account)
            .where(
                account.socialInfo.socialId.eq(socialId),
                account.socialInfo.socialType.eq(socialType)
            ).fetchOne()
    }

}
