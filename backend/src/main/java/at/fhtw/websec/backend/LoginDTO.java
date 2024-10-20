package at.fhtw.websec.backend;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    private String username;
    private String password;

    public LoginDTO(){

    }
}
