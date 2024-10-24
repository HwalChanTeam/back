package org.test.teamproject_back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.teamproject_back.dto.request.ReqModifyUserDto;
import org.test.teamproject_back.dto.response.RespUserInfoDto;
import org.test.teamproject_back.entity.Address;
import org.test.teamproject_back.entity.User;
import org.test.teamproject_back.exception.SignupException;
import org.test.teamproject_back.repository.AddressMapper;
import org.test.teamproject_back.repository.UserMapper;
import org.test.teamproject_back.security.principal.PrincipalUser;

import java.sql.SQLException;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AuthService authService;
    @Autowired
    private AddressMapper addressMapper;

    public RespUserInfoDto getUserInfo() {
        PrincipalUser principalUser = (PrincipalUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User user = userMapper.findUserByUserId(principalUser.getId());
        Address address = addressMapper.findAddressByUserId(principalUser.getId());

        return RespUserInfoDto.builder()
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .password(user.getPassword())
                .img(user.getImg())
                .address(address)
                .build();
    }

    @Transactional(rollbackFor = SQLException.class)
    public void modifyUserInfo(ReqModifyUserDto dto) throws SignupException {
        PrincipalUser principalUser = (PrincipalUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userMapper.findUserByUserId(principalUser.getId());

        if (!bCryptPasswordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("사용자 정보를 확인하세요.");
        }

        if (!authService.checkPassword(dto.getPassword(), dto.getCheckPassword())) {
            throw new SignupException("비밀번호가 일치하지 않습니다.");
        }
        userMapper.updateUserInfo(dto.toUser(user.getUserId()));
        addressMapper.updateAddress(dto.toAddress(user.getUserId()));
    }
}
