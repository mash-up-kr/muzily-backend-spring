package kr.mashup.ladder.auth.service

import kr.mashup.ladder.domain.account.domain.SocialType
import kr.mashup.ladder.domain.common.error.model.UnknownErrorException
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct

private val authServiceMap: MutableMap<SocialType, AuthService> = EnumMap(SocialType::class.java)

@Component
class AuthServiceFinder(
    private val kaKaoAuthService: KaKaoAuthService,
) {

    @PostConstruct
    fun initAuthService() {
        authServiceMap[SocialType.KAKAO] = kaKaoAuthService
        validateInitializeAuthServices()
    }

    private fun validateInitializeAuthServices() {
        for (socialType in SocialType.values()) {
            if (authServiceMap[socialType] == null) {
                throw UnknownErrorException("소셜 타입($socialType)에 대한 AuthService를 등록해주세요.")
            }
        }
    }

    fun getService(socialType: SocialType): AuthService {
        return authServiceMap[socialType] ?: throw UnknownErrorException("지원하지 않는 $socialType 입니다")
    }

}
