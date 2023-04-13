package com.card.game.common.base.dto;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * userInfo
 *
 * @author cunzhiwang
 * @Date 2023/4/13 14:20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserInfoDTO {
    private Long id;
    private String username;
    private String password;
    private List<String> roles;
}
