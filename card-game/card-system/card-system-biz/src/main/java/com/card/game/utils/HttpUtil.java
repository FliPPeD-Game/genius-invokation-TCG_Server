package com.card.game.utils;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class HttpUtil {

    @NotNull
    public static Map<String, Object> getParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", 1);
        params.put("page_size", 10);
        params.put("card_type", 0);
        Map<String, Object> roleSearch = new HashMap<>();
        roleSearch.put("element_type", "");
        roleSearch.put("weapon", "");
        roleSearch.put("belong", "");
        Map<String, Object> actionSearch = new HashMap<>();
        actionSearch.put("action_card_type", "");
        actionSearch.put("cost_num", "");
        actionSearch.put("is_other_cost", false);
        params.put("role_search", roleSearch);
        params.put("action_search", actionSearch);
        return params;
    }

    public static HttpResponse getHttpResponse(Map<String, Object> params, String url, HttpMethod method) {
        String body = JSONUtil.toJsonStr(params);
        HttpResponse execute = null;
        if (method == HttpMethod.GET) {
            execute = HttpRequest.get(url).execute();
        } else if (HttpMethod.POST == method) {
            execute = HttpRequest.post(url)
                    .header(Header.CONTENT_TYPE, "application/json")
                    .body(body)
                    .execute();
        }
        return execute;
    }
}
