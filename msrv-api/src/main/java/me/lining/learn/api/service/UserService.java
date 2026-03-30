package me.lining.learn.api.service;

import me.lining.learn.api.dto.UserDTO;
import me.lining.learn.api.enums.UserStatusEnum;

/**
 * @author lining
 * @date 2026/03/30 22:50
 */
public interface UserService {

    /**
     * 根据ID查询用户
     */
    UserDTO getUserById(Long userId);

    /**
     * 创建用户
     */
    boolean createUser(UserDTO userDTO);

    /**
     * 修改用户状态
     */
    boolean updateUserStatus(Long userId, UserStatusEnum status);
}
