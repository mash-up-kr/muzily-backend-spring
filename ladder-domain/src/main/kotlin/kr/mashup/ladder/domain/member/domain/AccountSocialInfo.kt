package kr.mashup.ladder.domain.member.domain

import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Embeddable
data class AccountSocialInfo(
    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    val socialType: SocialType,

    @Column(nullable = false, length = 300)
    val socialId: String,
) {

    companion object {
        fun of(socialType: SocialType, socialId: String): AccountSocialInfo {
            return AccountSocialInfo(
                socialType = socialType,
                socialId = socialId
            )
        }
    }

}
