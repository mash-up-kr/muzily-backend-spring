package kr.mashup.ladder.domain.account.infra.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import kr.mashup.ladder.domain.account.domain.Account
import kr.mashup.ladder.domain.account.domain.QAccount.account
import kr.mashup.ladder.domain.account.domain.SocialType
import org.springframework.stereotype.Repository

@Repository
class AccountQueryRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : AccountQueryRepository {

    override fun existsBySocialIdAndSocialType(socialId: String, socialType: SocialType): Boolean {
        return queryFactory.selectOne()
            .from(account)
            .where(
                account.socialInfo.socialId.eq(socialId),
                account.socialInfo.socialType.eq(socialType)
            ).fetchFirst() != null
    }

    override fun findBySocialIdAndSocialType(socialId: String, socialType: SocialType): Account? {
        return queryFactory.selectFrom(account)
            .where(
                account.socialInfo.socialId.eq(socialId),
                account.socialInfo.socialType.eq(socialType)
            ).fetchOne()
    }

}
