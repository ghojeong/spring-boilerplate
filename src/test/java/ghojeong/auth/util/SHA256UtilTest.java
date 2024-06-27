package ghojeong.auth.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SHA256UtilTest {

    @DisplayName("SHA256 으로 문자열을 암호화 할 수 있다.")
    @Test
    void comparePassword() {
        assertThat(SHA256Util.encrypt(
                "Dream77!!"
        )).isEqualTo("4e17daf5539ba42a4a4b7d798aa5894b6e08ef9ed51e8f66d9db7463d8bf9852");
    }
}
