package me.lining.learn.interfaces.facade.dubbo;

import me.lining.learn.api.dto.UserDTO;
import me.lining.learn.api.enums.UserStatusEnum;
import me.lining.learn.api.service.UserService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author lining
 * @date 2026/03/30 23:04
 */
@DubboService
public class UserServiceImpl implements UserService {
    @Override
    public UserDTO getUserById(Long aLong) {
        return UserDTO.builder().age(1).username("lining").email("lining@gmail.com").id(1L).build();
    }

    @Override
    public boolean createUser(UserDTO userDTO) {
        return false;
    }

    @Override
    public boolean updateUserStatus(Long aLong, UserStatusEnum userStatusEnum) {
        return false;
    }
}
