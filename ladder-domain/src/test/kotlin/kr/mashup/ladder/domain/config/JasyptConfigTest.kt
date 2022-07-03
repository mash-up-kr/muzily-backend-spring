package kr.mashup.ladder.domain.config

import org.jasypt.encryption.StringEncryptor
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor

/**
 * Run > Edit Configurations > Configuration > Environment variables > 'JASYPT_ENCRYPTOR_PASSWORD={암호화키}' 입력
 */
@Disabled("암호화, 복호화 결과 확인을 위한 테스트이므로 비활성화")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
class JasyptConfigTest(
    private val stringEncryptor: StringEncryptor,
) {
    @ParameterizedTest
    @ValueSource(strings = ["decrypted"])
    fun encrypt(decrypted: String) {
        print(stringEncryptor.encrypt(decrypted))
    }

    @ParameterizedTest
    @ValueSource(strings = ["encrypted"])
    fun decrypt(encrypted: String) {
        print(stringEncryptor.decrypt(encrypted))
    }
}
