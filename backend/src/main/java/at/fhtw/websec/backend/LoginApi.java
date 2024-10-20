package at.fhtw.websec.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginApi {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> postLogin(@RequestBody LoginDTO loginData) {
        return new ResponseEntity<>(userService.loadUserByUsername(loginData.getUsername()), HttpStatus.OK);
    }
}
