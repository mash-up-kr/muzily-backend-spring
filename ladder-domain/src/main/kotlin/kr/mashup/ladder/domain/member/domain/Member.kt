package kr.mashup.ladder.domain.member.domain

import kr.mashup.ladder.domain.common.domain.BaseEntity
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.OneToMany
import javax.persistence.Table

private const val ANONYMOUS_DEFAULT_NICKNAME = "Anonymous"

@Table(name = "member")
@Entity
class Member(
    @Column(nullable = false)
    var nickname: String,

    var profileUrl: String? = null,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var accountConnectType: AccountConnectType = AccountConnectType.UNCONNECTED,
) : BaseEntity() {

    @OneToMany(mappedBy = "member", cascade = [CascadeType.ALL], orphanRemoval = true)
    val accounts: MutableList<Account> = mutableListOf()

    fun update(nickname: String, profileUrl: String?) {
        this.nickname = nickname
        this.profileUrl = profileUrl
    }

    fun addAccount(socialId: String, socialType: SocialType) {
        this.accounts.add(Account.of(member = this, socialId = socialId, socialType = socialType))
        this.accountConnectType = AccountConnectType.CONNECTED
    }

    companion object {
        fun newInstance(
            socialType: SocialType,
            socialId: String,
            nickname: String,
            profileUrl: String? = null,
        ): Member {
            val member = Member(
                nickname = nickname,
                profileUrl = profileUrl
            )
            member.addAccount(socialId = socialId, socialType = socialType)
            return member
        }

        fun newKaKao(
            socialId: String,
            nickname: String,
            profileUrl: String? = null,
        ): Member {
            return newInstance(SocialType.KAKAO, socialId, nickname, profileUrl)
        }

        fun newAnonymous(): Member {
            return Member(
                nickname = ANONYMOUS_DEFAULT_NICKNAME
            )
        }
    }

}
