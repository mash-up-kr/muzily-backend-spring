package kr.mashup.ladder.auth.controller

import kr.mashup.ladder.auth.dto.request.AuthRequest
import kr.mashup.ladder.auth.dto.response.LoginResponse
import kr.mashup.ladder.config.resolver.MEMBER_ID
import kr.mashup.ladder.domain.member.domain.Member
import kr.mashup.ladder.domain.member.infra.jpa.MemberRepository
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpSession
import javax.validation.Valid

@RestController
class TestAuthApiController(
    private val memberRepository: MemberRepository,
    private val httpSession: HttpSession,
) {
    @PostMapping("/test-api/v1/auth")
    @Transactional
    fun handleAuthentication(@Valid @RequestBody request: AuthRequest): LoginResponse {
        val member = memberRepository.save(Member.newKaKao("y2o2u2n", "star"))
        httpSession.setAttribute(MEMBER_ID, member.id)
        return LoginResponse(token = httpSession.id)
    }

    @PostMapping("/test-api/v1/auth/anonymous")
    @Transactional
    fun createAnonymousMember(): LoginResponse {
        val member = memberRepository.save(Member.newAnonymous())
        httpSession.setAttribute(MEMBER_ID, member.id)
        return LoginResponse(token = httpSession.id)
    }
}
