package at.fhtw.websec.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserApi {
    final UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("@userService.verifyAccess(authentication.name, #id) || hasRole('ADMIN')")
    public ResponseEntity<UserDetails> getUser(@PathVariable int id) {
        return ResponseEntity.ok(userService.loadUserById(id));
    }
}
