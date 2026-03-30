package me.lining.learn.api.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lining
 * @date 2026/03/30 22:51
 */

@Data
@Builder
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private Integer age;
    private String email;
}
