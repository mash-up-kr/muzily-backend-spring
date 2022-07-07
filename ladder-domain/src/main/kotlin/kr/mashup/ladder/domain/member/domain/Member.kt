package kr.mashup.ladder.domain.member.domain

import kr.mashup.ladder.domain.common.domain.BaseEntity
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table

private const val ANONYMOUS_DEFAULT_NICKNAME = "Anonymous"

@Table(name = "member")
@Entity
class Member(
    @Column(nullable = false)
    var nickname: String,

    var profileUrl: String? = null,
) : BaseEntity() {

    @OneToMany(mappedBy = "member", cascade = [CascadeType.ALL], orphanRemoval = true)
    private val accounts: MutableList<Account> = mutableListOf()

    fun update(nickname: String, profileUrl: String?) {
        this.nickname = nickname
        this.profileUrl = profileUrl
    }

    fun addAccount(socialId: String, socialType: SocialType) {
        this.accounts.add(Account.of(member = this, socialId = socialId, socialType = socialType))
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

        fun newAnonymous(): Member {
            return Member(
                nickname = ANONYMOUS_DEFAULT_NICKNAME
            )
        }
    }

}
