package at.fhtw.websec.backend;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.security.MessageDigest;

public class LowSodiumPasswordEncoder implements PasswordEncoder
{
    @Override
    public String encode(CharSequence rawPassword) {
        return hashString(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }

    private String hashString(String password)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);

                if(hex.length() == 1)
                    hexString.append('0');

                hexString.append(hex);
            }

            return hexString.toString();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}