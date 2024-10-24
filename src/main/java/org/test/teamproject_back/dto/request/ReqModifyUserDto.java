package org.test.teamproject_back.dto.request;

import lombok.Builder;
import lombok.Data;
import org.test.teamproject_back.entity.Address;
import org.test.teamproject_back.entity.User;

@Builder
@Data
public class ReqModifyUserDto {
    private String password;
    private String changePassword;
    private String checkPassword;
    private String email;
    private String address;
    private String detailAddress;

    public User toUser(Long userId) {
        return User.builder()
                .userId(userId)
                .password(changePassword)
                .email(email)
                .build();
    }

    public Address toAddress(Long userId) {
        return Address.builder()
                .userId((userId))
                .address(address)
                .detailAddress(detailAddress)
                .build();
    }
}
