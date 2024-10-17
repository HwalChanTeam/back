package org.test.teamproject_back.repository;

import org.apache.ibatis.annotations.Mapper;
import org.test.teamproject_back.entity.User;

@Mapper
public interface AdminUserMapper {
    User findUserById(Long userId);
    int deleteUserByUserId(Long userId);
    int updateUserByUserId(User user);
}