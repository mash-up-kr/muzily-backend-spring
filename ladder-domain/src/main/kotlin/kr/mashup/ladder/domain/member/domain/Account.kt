package kr.mashup.ladder.domain.member.domain

import kr.mashup.ladder.domain.common.domain.BaseEntity
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Table(name = "account")
@Entity
class Account(
    @Embedded
    val socialInfo: AccountSocialInfo,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member,
) : BaseEntity() {

    companion object {
        fun of(member: Member, socialId: String, socialType: SocialType): Account {
            return Account(
                member = member,
                socialInfo = AccountSocialInfo.of(socialType, socialId)
            )
        }
    }

}
