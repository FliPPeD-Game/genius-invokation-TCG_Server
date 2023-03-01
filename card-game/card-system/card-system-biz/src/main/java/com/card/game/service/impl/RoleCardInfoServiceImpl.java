package com.card.game.service.impl;

import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.card.game.common.web.utils.BeanMapperUtils;
import com.card.game.mapper.RoleCardInfoMapper;
import com.card.game.pojo.entity.RoleCardInfoEntity;
import com.card.game.pojo.entity.RoleSkillInfoEntity;
import com.card.game.pojo.entity.SkillCostEntity;
import com.card.game.pojo.vo.RoleCardInfoVO;
import com.card.game.pojo.vo.RoleSkillInfoVO;
import com.card.game.pojo.vo.SkillCostVO;
import com.card.game.service.RoleCardInfoService;
import com.card.game.service.RoleSkillInfoService;
import com.card.game.service.SkillCostService;
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

import static com.card.game.utils.HttpUtil.getHttpResponse;
import static com.card.game.utils.HttpUtil.getParams;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleCardInfoServiceImpl extends ServiceImpl<RoleCardInfoMapper, RoleCardInfoEntity> implements RoleCardInfoService {

    private final RoleSkillInfoService roleSkillInfoService;

    private final SkillCostService skillCostService;


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
                skillInfo.setRoleId(cardInfo.getId());
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
        boolean isSave = this.saveOrUpdateBatch(cardInfos);
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
    public List<RoleCardInfoVO> getAllRoleCardInfo() {
        List<RoleCardInfoEntity> cardInfos = this.list();
        List<RoleCardInfoVO> cardInfoVos = BeanMapperUtils.mapList(cardInfos, RoleCardInfoVO.class);
        cardInfoVos.forEach(cardInfo -> {
            QueryWrapper<RoleSkillInfoEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_id", cardInfo.getId());
            List<RoleSkillInfoEntity> roleSkillInfos = roleSkillInfoService.list(queryWrapper);
            List<RoleSkillInfoVO> roleSkillInfoVos = BeanMapperUtils.mapList(roleSkillInfos, RoleSkillInfoVO.class);
            cardInfo.setSkillInfoVos(roleSkillInfoVos);
            roleSkillInfoVos.forEach(roleSkill -> {
                QueryWrapper<SkillCostEntity> wrapper = new QueryWrapper<>();
                wrapper.eq("skill_id", roleSkill.getId());
                List<SkillCostEntity> skillCosts = skillCostService.list(wrapper);
                val skillCostVos = BeanMapperUtils.mapList(skillCosts, SkillCostVO.class);
                roleSkill.setCosts(skillCostVos);
            });

        });
        return cardInfoVos;
    }


}
