package com.card.game.oatuh.config;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 白名单
 *
 * @author tomyou
 * @version 1.0 created on 2022/10/13 16:56
 */
@ConfigurationProperties(prefix = "security.enable")
@Getter
@Setter
public class SecurityUrlProperties {

    private List<String> urls;
}
