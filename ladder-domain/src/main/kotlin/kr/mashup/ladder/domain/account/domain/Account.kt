package kr.mashup.ladder.domain.account.domain

import kr.mashup.ladder.domain.common.domain.BaseEntity
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.Table

@Table(name = "account")
@Entity
class Account(
    @Embedded
    val socialInfo: AccountSocialInfo,

    @Column(nullable = false)
    var nickname: String,

    var profileUrl: String? = null,
) : BaseEntity() {

    fun update(nickname: String, profileUrl: String?) {
        this.nickname = nickname
        this.profileUrl = profileUrl
    }

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
