package com.card.game.service.impl;

import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.card.game.common.web.utils.BeanMapperUtils;
import com.card.game.mapper.ActionCardInfoMapper;
import com.card.game.pojo.entity.ActionCardInfoEntity;
import com.card.game.pojo.vo.ActionCardInfoVO;
import com.card.game.service.ActionCardInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.card.game.utils.HttpUtil.getHttpResponse;
import static com.card.game.utils.HttpUtil.getParams;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActionCardInfoServiceImpl extends ServiceImpl<ActionCardInfoMapper, ActionCardInfoEntity> implements
        ActionCardInfoService {
    private final ActionCardInfoMapper actionCardInfoMapper;

    @Override
    public boolean addActionCardInfo(String url) {
        Map<String, Object> params = getParams();
        params.put("page_size", 30);
        params.put("card_type", 1);
        Map<String, Object> actionSearch = (Map<String, Object>) params.get("action_search");
        actionSearch.put("action_card_type", 5);
        HttpResponse execute = getHttpResponse(params, url, HttpMethod.POST);
        JSONObject responseJson = JSONUtil.parseObj(execute.body());
        Map<String, Object> result = responseJson.get("data", Map.class);
        log.info(JSONUtil.toJsonStr(result));
        JSONArray jsonArray = (JSONArray) result.get("action_card_infos");
        List<ActionCardInfoEntity> cardInfoList = new ArrayList<>();
        for (Object o : jsonArray) {
            JSONObject action = (JSONObject) o;
            ActionCardInfoEntity actionCardInfo = new ActionCardInfoEntity();
            actionCardInfo.setId(action.get("id", Long.class));
            actionCardInfo.setActionCardTags(action.get("action_card_tags", JSONArray.class).toString());
            actionCardInfo.setActionType(action.get("action_type", String.class));
            actionCardInfo.setContent(action.get("content", String.class));
            actionCardInfo.setCostNum1(Integer.valueOf(action.get("cost_num1", String.class)));
            actionCardInfo.setCostNum2(Integer.valueOf(action.get("cost_num2", String.class)));
            actionCardInfo.setCostType1Icon(action.get("cost_type1_icon", String.class));
            actionCardInfo.setCostType2Icon(action.get("cost_type2_icon", String.class));
            actionCardInfo.setRankId(action.get("rank_id", Integer.class));
            actionCardInfo.setResource(action.get("resource", String.class));
            actionCardInfo.setName(action.get("name", String.class));
            cardInfoList.add(actionCardInfo);
        }
        return this.saveOrUpdateBatch(cardInfoList);
    }

    @Override
    public List<ActionCardInfoVO> getActionCardInfos(List<Long> ids) {
        return BeanMapperUtils.mapList(actionCardInfoMapper.getActionCardInfos(ids), ActionCardInfoVO.class);

    }

}
