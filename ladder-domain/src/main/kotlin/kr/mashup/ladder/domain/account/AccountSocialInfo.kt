package kr.mashup.ladder.domain.account

import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Embeddable
class AccountSocialInfo(
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val socialType: SocialType,

    @Column(nullable = false)
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