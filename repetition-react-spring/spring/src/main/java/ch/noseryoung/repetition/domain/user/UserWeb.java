package ch.noseryoung.repetition.domain.user;

import ch.noseryoung.repetition.domain.role.Role;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserWeb {
    private final UserService userService;

    public UserWeb(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Returns the user by id")
    @GetMapping("/{userId}")
    public ResponseEntity<User> find(@PathVariable("userId") Integer userId) throws InstanceNotFoundException {
        User user = userService.findById(userId);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Gives back all entries in users table")
    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok().body(users);
    }

    @Operation(summary = "Creates new user entry")
    @PostMapping("/")
    public ResponseEntity<User> newAuthor(@Valid @RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/").toUriString());
        return ResponseEntity.created(uri).body(userService.create(user));
    }

    @Operation(summary = "Creates new role entry")
    @PostMapping("/role")
    public ResponseEntity<Role> newRole(@Valid @RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/role").toUriString());
        return ResponseEntity.created(uri).body(userService.createRole(role));
    }

    @Operation(summary = "Add role to user")
    @PostMapping("/role/addtouser")
    public ResponseEntity<?> addRoleToUser(@Valid @RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Updates or creates user entry based on existence")
    @PutMapping("/{userId}")
    public ResponseEntity<User> update(@Valid @PathVariable Integer userId, @RequestBody User updateduser) throws InstanceNotFoundException {
        User updated = userService.update(userId, updateduser);
        return ResponseEntity.ok().body(updated);
    }

    @Operation(summary = "Delete a specific User")
    @DeleteMapping("/{userId}")
    public ResponseEntity<User> delete(@PathVariable("userId") Integer userId) throws InstanceNotFoundException {
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Add role to user")
    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try{
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("baguette".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() +10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }
            catch (Exception e) {
                log.error("Error login in: {}", e.getMessage());
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refreshtoken is missing");
        }
    }


    @ExceptionHandler
    public ResponseEntity<String> exeptionHandler(Exception request) {
        return ResponseEntity.status(400).body(request.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> exeptionHandler(InstanceNotFoundException request) {
        return ResponseEntity.status(404).body(request.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> exeptionHandler(InstanceAlreadyExistsException request) {
        return ResponseEntity.status(418).body(request.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> exeptionHandler(MethodArgumentNotValidException request) {
        return ResponseEntity.status(406).body("Arguments not valid");
    }

}
@Data
class RoleToUserForm {
    private String username;
    private String roleName;
}