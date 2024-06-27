package ghojeong.user.controller;

import ghojeong.auth.domain.MemberUserDetails;
import ghojeong.user.domain.entity.User;
import ghojeong.user.dto.request.UpdateUserRequest;
import ghojeong.user.dto.response.DeleteUserResponse;
import ghojeong.user.dto.response.FetchUserResponse;
import ghojeong.user.dto.response.UpdateUserResponse;
import ghojeong.user.service.UserCommandService;
import ghojeong.user.service.UserQueryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserController {
    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

    @Secured("ROLE_MEMBER")
    @GetMapping("/users/me")
    public FetchUserResponse fetchUserOfMine(
            @AuthenticationPrincipal MemberUserDetails userDetails
    ) {
        return FetchUserResponse.of(
                userQueryService.findUserWithTags(userDetails.getUser())
        );
    }

    @Secured("ROLE_MEMBER")
    @PutMapping("/users/me")
    public UpdateUserResponse updateUserOfMine(
            @AuthenticationPrincipal MemberUserDetails userDetails,
            @Valid @RequestBody UpdateUserRequest request
    ) {
        final User user = userDetails.getUser();
        userCommandService.updateUser(user, request);
        return UpdateUserResponse.of(
                userQueryService.findUserWithTags(user)
        );
    }

    @Secured("ROLE_MEMBER")
    @DeleteMapping("/users/me")
    public DeleteUserResponse deleteUserOfMine(
            @AuthenticationPrincipal MemberUserDetails userDetails
    ) {
        return userCommandService.deleteUser(userDetails.getUser());
    }
}
