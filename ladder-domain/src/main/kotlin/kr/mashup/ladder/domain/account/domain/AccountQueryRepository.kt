package kr.mashup.ladder.domain.account.domain

interface AccountQueryRepository {

    fun findSocialIdAndSocialType(socialId: String, socialType: SocialType): Account?

}
