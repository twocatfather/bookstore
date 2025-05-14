package com.study.bookstore.service.user.facade;

import com.study.bookstore.api.user.dto.request.UserRequest;
import com.study.bookstore.api.user.dto.request.UserUpdateRequest;
import com.study.bookstore.api.user.dto.response.UserResponse;
import com.study.bookstore.domain.user.User;
import com.study.bookstore.domain.user.UserMapper;
import com.study.bookstore.global.error.DuplicatedUserException;
import com.study.bookstore.global.facade.BCryptPasswordEncoderFacade;
import com.study.bookstore.infrastructure.user.entity.UserJpaEntity;
import com.study.bookstore.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserFacade {
    private final UserService userService;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoderFacade passwordEncoderFacade;

    @Transactional
    public Long createUser(UserRequest controllerDto) {
        User domain = userMapper.toDomain(controllerDto);
        String encode = passwordEncoderFacade.encode(controllerDto.password());
        domain.encodedPassword(encode);

        validateDuplicate(domain.getEmail());
        return userService.createUser(domain);
    }

    public void validateDuplicate(String email) {
        userService.findByEmail(email).ifPresent(user -> {
            throw new DuplicatedUserException("중복 이메일");
        });
    }

    // updateUser
    public UserResponse updateUser(Long userId, @Valid UserUpdateRequest request) {
        return userService.updateUser(userId, request);
    }

    // deleteUser
    public void deleteUser(Long userId) {
        userService.deleteUser(userId);
    }

    // getUserById
    public UserResponse getUserById(Long userId) {
        UserJpaEntity user = userService.findUserById(userId);
        return UserResponse.fromEntity(user);
    }

}
