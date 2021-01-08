package ru.restaurant.web.user;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.restaurant.AuthorizedUser;
import ru.restaurant.model.User;
import ru.restaurant.service.UserService;
import ru.restaurant.to.UserTo;
import ru.restaurant.util.UserUtil;

import javax.validation.Valid;
import java.net.URI;

import static ru.restaurant.web.user.UserController.REST_URL;


@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    public final static String REST_URL = "/rest";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public User getCurrent(@AuthenticationPrincipal AuthorizedUser user) {
        return userService.get(user.getId());
    }

    @PostMapping("/users/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserTo userTo) {
        User createdUser = userService.create(UserUtil.createNewFromTo(userTo));
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .build()
                .toUri();
        return ResponseEntity.created(uri)
                .body(createdUser);
    }
}
