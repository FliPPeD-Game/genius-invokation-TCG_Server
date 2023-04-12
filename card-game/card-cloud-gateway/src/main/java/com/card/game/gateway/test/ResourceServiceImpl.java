package com.card.game.gateway.test;

import cn.hutool.core.collection.CollUtil;
import com.card.game.gateway.constant.RedisConstant;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author cunzhiwang
 * @Date 2023/4/12 17:31
 */
@RequiredArgsConstructor
@Service
public class ResourceServiceImpl {
    private final RedisTemplate<String,Object> redisTemplate;

    @PostConstruct
    public void initData() {
        Map<String, List<String>> resourceRolesMap = new TreeMap<>();
        resourceRolesMap.put("/card/info/getAllRoleCardInfo", CollUtil.toList("ADMIN"));
        resourceRolesMap.put("/api/user/currentUser", CollUtil.toList("ADMIN", "TEST"));
        redisTemplate.opsForHash().putAll(RedisConstant.RESOURCE_ROLES_MAP, resourceRolesMap);
    }
}
