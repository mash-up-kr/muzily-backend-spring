package kr.mashup.ladder.domain.account

import kr.mashup.ladder.domain.common.domain.BaseTimeEntity
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Account(
    @Embedded
    val socialInfo: AccountSocialInfo,

    @Column(nullable = false)
    val nickname: String,

    val profileUrl: String,
) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    companion object {
        fun of(
            socialType: SocialType,
            socialId: String,
            nickname: String,
            profileUrl: String,
        ): Account {
            return Account(
                socialInfo = AccountSocialInfo.of(
                    socialType = socialType,
                    socialId = socialId
                ),
                nickname = nickname,
                profileUrl = profileUrl
            )
        }
    }

}
