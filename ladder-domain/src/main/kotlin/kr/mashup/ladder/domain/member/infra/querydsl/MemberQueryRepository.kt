package kr.mashup.ladder.domain.member.infra.querydsl

import kr.mashup.ladder.domain.member.domain.Member
import kr.mashup.ladder.domain.member.domain.SocialType

interface MemberQueryRepository {

    fun existsMemberById(accountId: Long): Boolean

    fun existsMemberBySocialIdAndSocialType(socialId: String, socialType: SocialType): Boolean

    fun findMemberBySocialIdAndSocialType(socialId: String, socialType: SocialType): Member?

}
