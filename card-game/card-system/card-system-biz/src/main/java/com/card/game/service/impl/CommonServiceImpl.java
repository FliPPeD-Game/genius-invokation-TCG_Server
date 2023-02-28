package com.card.game.service.impl;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.card.game.common.web.utils.BeanMapperUtils;
import com.card.game.pojo.entity.ActionCardInfoEntity;
import com.card.game.pojo.entity.RoleCardInfoEntity;
import com.card.game.pojo.entity.RoleSkillInfoEntity;
import com.card.game.pojo.entity.SkillCostEntity;
import com.card.game.pojo.vo.CardInfoVo;
import com.card.game.pojo.vo.RoleSkillInfoVo;
import com.card.game.pojo.vo.SkillCostVo;
import com.card.game.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommonServiceImpl implements CommonService {
    private final RoleCardInfoService roleCardInfoService;

    private final RoleSkillInfoService roleSkillInfoService;

    private final SkillCostService skillCostService;

    private final ActionCardInfoService actionCardInfoService;

    /**
     * {
     * "page": 1,
     * "page_size": 10,
     * "card_type": 0,
     * "role_search": {
     * "element_type": "",
     * "weapon": "",
     * "belong": ""
     * },
     * "action_search": {
     * "action_card_type": "",
     * "cost_num": "",
     * "is_other_cost": false
     * }
     * }
     *
     * @param url url
     * @return 是否保存成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addRoleCardInfo(String url) {
        Map<String, Object> params = getParams();
        params.put("page_size", 28);
        HttpResponse execute = getHttpResponse(params, url, HttpMethod.POST);
        JSONObject responseJson = JSONUtil.parseObj(execute.body());
        Map<String, Object> result = responseJson.get("data", Map.class);
        log.info(JSONUtil.toJsonStr(result));
        JSONArray jsonArray = (JSONArray) result.get("role_card_infos");
        List<RoleCardInfoEntity> cardInfos = new ArrayList<>();
        List<RoleSkillInfoEntity> skillInfoList = new ArrayList<>();
        List<SkillCostEntity> costList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object = (JSONObject) jsonArray.get(i);
            RoleCardInfoEntity cardInfo = new RoleCardInfoEntity();
            cardInfo.setId(object.get("id", Long.class));
            cardInfo.setName(object.get("name", String.class));
            cardInfo.setHp(Integer.valueOf(object.get("hp", String.class)));
            cardInfo.setElementType(object.get("element_type", Integer.class));
            cardInfo.setResource(object.get("resource", String.class));
            cardInfo.setWeapon(object.get("weapon", String.class));
            cardInfo.setBelongs(object.get("belongs", JSONArray.class).toString());
            cardInfos.add(cardInfo);
            JSONArray skillInfos = object.get("role_skill_infos", JSONArray.class);
            for (int j = 0; j < skillInfos.size(); j++) {
                JSONObject skill = skillInfos.get(j, JSONObject.class);
                RoleSkillInfoEntity skillInfo = new RoleSkillInfoEntity();
                Long skillId = skill.get("id", Long.class);
                skillInfo.setId(skillId);
                skillInfo.setName(skill.get("name", String.class));
                skillInfo.setResource(skill.get("resource", String.class));
                skillInfo.setSkillText(skill.get("skill_text", String.class));
                skillInfo.setType(skill.get("type", JSONArray.class).toString());
                JSONArray costs = skill.get("skill_costs", JSONArray.class);
                for (int k = 0; k < costs.size(); k++) {
                    JSONObject costJson = costs.get(i, JSONObject.class);
                    if (costJson != null) {
                        SkillCostEntity cost = new SkillCostEntity();
                        cost.setCostNum(Integer.valueOf(costJson.get("cost_num", String.class)));
                        cost.setCostType(Integer.valueOf(costJson.get("cost_type", String.class)));
                        cost.setIcon(costJson.get("icon", String.class));
                        cost.setSkillId(skillId);
                        costList.add(cost);
                    }
                }
                skillInfoList.add(skillInfo);
            }
        }
        boolean isSave = roleCardInfoService.saveOrUpdateBatch(cardInfos);
        if (isSave) {
            isSave = roleSkillInfoService.saveOrUpdateBatch(skillInfoList);
            if (isSave) {
                isSave = skillCostService.saveOrUpdateBatch(costList);
            } else {
                isSave = false;
            }
        }
        return isSave;
    }


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
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject action = (JSONObject) jsonArray.get(i);
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
        return actionCardInfoService.saveOrUpdateBatch(cardInfoList);
    }


    @Override
    public List<CardInfoVo> getAllRoleCardInfo() {
        List<RoleCardInfoEntity> cardInfos = roleCardInfoService.list();
        List<CardInfoVo> cardInfoVos=BeanMapperUtils.mapList(cardInfos,CardInfoVo.class);
        cardInfoVos.forEach(cardInfo -> {
            QueryWrapper queryWrapper = new QueryWrapper<>(RoleSkillInfoEntity.class);
            queryWrapper.eq("id", cardInfo.getId());
            List<RoleSkillInfoEntity> roleSkillInfos = roleSkillInfoService.list(queryWrapper);
            List<RoleSkillInfoVo> roleSkillInfoVos = BeanMapperUtils.mapList(roleSkillInfos, RoleSkillInfoVo.class);
            cardInfo.setSkillInfoVos(roleSkillInfoVos);
            if (roleSkillInfoVos != null) {
                roleSkillInfoVos.forEach(roleSkill -> {
                    QueryWrapper wrapper = new QueryWrapper<>(SkillCostEntity.class);
                    wrapper.eq("skill_id", roleSkill.getId());
                    List<SkillCostEntity> skillCosts = skillCostService.list(wrapper);
                    val skillCostVos = BeanMapperUtils.mapList(skillCosts, SkillCostVo.class);
                    roleSkill.setCosts(skillCostVos);
                });
            }

        });
        return null;

    }

    @NotNull
    private Map<String, Object> getParams() {
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

    private HttpResponse getHttpResponse(Map<String, Object> params, String url, HttpMethod method) {
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
