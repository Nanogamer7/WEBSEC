package at.fhtw.websec.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class EncoderConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        String idForEncode = "lowsodium";

        Map<String, PasswordEncoder> encoders = new HashMap<>();

        encoders.put("sha256", new StandardPasswordEncoder());
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("lowsodium", new LowSodiumPasswordEncoder());

        return new DelegatingPasswordEncoder(idForEncode, encoders);
    }
}
