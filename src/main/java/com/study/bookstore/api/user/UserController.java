package com.study.bookstore.api.user;

import com.study.bookstore.api.user.dto.request.UserRequest;
import com.study.bookstore.service.user.facade.UserFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserFacade userFacade;

    @PostMapping("/register")
    public ResponseEntity<Long> createUser(
            @Valid @RequestBody UserRequest request
    ) {
        return ResponseEntity.status(201).body(userFacade.createUser(request));
    }

    /**
     *  api 주소는 "/me"
     *  GetMapping
     *  return UserResponse
     *  method getCurrentUser
     *  parameter -> 직접적으로 userid 를 받아서는 안됩니다. 시큐리티세팅했습니다.
     *  userid 기준으로  해당 유저를 찾아내는 겁니다.
     */

    /**
     *  api 주소는 "/me"
     *  PutMapping
     *  return UserResponse
     *  method updateCurrentUser
     *  parameter -> 직접적으로 userid 를 받아서는 안됩니다. 시큐리티세팅했습니다.
     *  body -> UserUpdateRequest
     *  userid 기준으로  해당 유저를 찾아내는 겁니다.
     */

    /**
     *  api 주소는 "/me"
     *  DeleteMapping
     *  return void
     *  method deleteCurrentUser
     *  parameter -> 직접적으로 userid 를 받아서는 안됩니다. 시큐리티세팅했습니다.
     *  userid 기준으로  해당 유저를 찾아내는 겁니다.
     */

    /**
     *  api 주소는 "/{userId}"
     *  GetMapping
     *  return UserResponse
     *  method getUser
     *  parameter -> 직접적으로 userid 를 받는 녀석
     *  userid 기준으로  해당 유저를 찾아내는 겁니다.
     */
}
