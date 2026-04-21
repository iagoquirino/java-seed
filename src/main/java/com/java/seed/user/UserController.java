package com.java.seed.user;

import com.java.seed.shared.Result;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import static com.java.seed.shared.Result.SUCCESS;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    UserResponse publish(@RequestBody UserRequest request) {
        userService.publish(request);
        return new UserResponse(SUCCESS);
    }

    record UserRequest(@NotBlank String name){}

    record UserResponse(@NotBlank Result result){}
}
