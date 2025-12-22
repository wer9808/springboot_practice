package com.example.article_crud.domain.user;

import com.example.article_crud.common.exception.ApiErrorCode;
import com.example.article_crud.common.exception.service.BusinessException;
import com.example.article_crud.domain.user.dto.CurrentUserDto;
import com.example.article_crud.domain.user.dto.UserResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private void checkUserReadPermission(UUID userId, CurrentUserDto currentUserDto) throws BusinessException {
        if (!userId.equals(currentUserDto.id())) {
            throw new BusinessException(ApiErrorCode.PERMISSION_ACCESS_DENIED);
        }
    }

    public UserResponse findUser(UUID userId, CurrentUserDto currentUserDto) throws BusinessException {
        checkUserReadPermission(userId, currentUserDto);

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ApiErrorCode.USER_NOT_FOUND));

        return UserResponse.from(user);
    }

    public UserResponse findCurrentUser(CurrentUserDto currentUserDto) throws BusinessException {
        User user = this.userRepository.findById(currentUserDto.id())
                .orElseThrow(() -> new BusinessException(ApiErrorCode.USER_NOT_FOUND));

        return UserResponse.from(user);
    }

}
