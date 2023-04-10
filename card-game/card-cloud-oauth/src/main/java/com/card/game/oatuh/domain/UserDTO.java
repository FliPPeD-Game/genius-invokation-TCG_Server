package com.card.game.oatuh.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by macro on 2020/6/19.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class UserDTO{
    private Long id;
    private String username;
    private String password;
    private Integer status;
    private List<String> roles;

}
