package com.card.game.service.impl;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.card.game.common.redis.RedisCache;
import com.card.game.common.redis.constants.RedisPrefixConstant;
import com.card.game.common.web.utils.BeanMapperUtils;
import com.card.game.mapper.SysImageInfoMapper;
import com.card.game.pojo.dto.ImageInfoDTO;
import com.card.game.pojo.dto.ImageInfoDTO.BaseImage;
import com.card.game.pojo.entity.RoleCardInfoEntity;
import com.card.game.pojo.entity.RoleSkillInfoEntity;
import com.card.game.pojo.entity.SkillCostEntity;
import com.card.game.pojo.entity.SysImageInfoEntity;
import com.card.game.pojo.vo.ImageInfoVO;
import com.card.game.service.RoleCardInfoService;
import com.card.game.service.RoleSkillInfoService;
import com.card.game.service.SkillCostService;
import com.card.game.service.SysImageInfoService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 图片操作服务类
 *
 * @author cunzhiwang
 * @Date 2023/1/17 11:03
 */
@Service
@RequiredArgsConstructor
public class SysImageInfoServiceImpl extends ServiceImpl<SysImageInfoMapper, SysImageInfoEntity> implements
        SysImageInfoService {

    private final RedisCache redisCache;

    private final SysImageInfoMapper sysImageInfoMapper;

    private final RoleCardInfoService roleCardInfoService;

    private final RoleSkillInfoService roleSkillInfoService;

    private final SkillCostService skillCostService;

    @Override
    public List<SysImageInfoEntity> saveImages(List<ImageInfoDTO> imageInfoList) {
        List<SysImageInfoEntity> imageInfoEntities = new ArrayList<>();
        imageInfoList.forEach(imageInfo -> {
            for (BaseImage image : imageInfo.getImageInfo()) {
                SysImageInfoEntity imageInfoEntity = new SysImageInfoEntity();
                imageInfoEntity.setCountry(imageInfo.getCountry());
                imageInfoEntity.setName(image.getName());
                imageInfoEntity.setSrc(image.getSrc());
                imageInfoEntities.add(imageInfoEntity);
            }
        });
        AtomicBoolean flag = new AtomicBoolean(true);
        List<SysImageInfoEntity> failImageInfos = new ArrayList<>();
        List<SysImageInfoEntity> successImageInfos = new ArrayList<>();
        imageInfoEntities.forEach(image -> {
            // 保存或更新
            boolean isSuccess = this.saveOrUpdate(image,
                    Wrappers.lambdaQuery(SysImageInfoEntity.class).eq(SysImageInfoEntity::getName, image.getName()));
            if (isSuccess) {
                successImageInfos.add(image);
            } else {
                flag.set(false);
                failImageInfos.add(image);
            }
        });
        // 保存或更新缓存
        if (CollectionUtils.isNotEmpty(successImageInfos)) {
            redisCache.setCacheList(RedisPrefixConstant.IMAGE_INFO_PREFIX + "ProfilePhotos", successImageInfos);
        }
        // 若存在更新失败的数据，则返回更新或保存失败数据
        if (!flag.get() && CollectionUtils.isNotEmpty(failImageInfos)) {
            return failImageInfos;
        } else {
            return new ArrayList<SysImageInfoEntity>();
        }

    }

    @Override
    public List<ImageInfoVO> getProfilePhotos() {
        List<Object> cacheList = redisCache.getCacheList(RedisPrefixConstant.IMAGE_INFO_PREFIX + "ProfilePhotos");
        List<SysImageInfoEntity> imageInfoEntities;
        if (CollectionUtils.isNotEmpty(cacheList)) {
            imageInfoEntities = BeanMapperUtils.mapList(cacheList, SysImageInfoEntity.class);
        } else {
            imageInfoEntities = this.list();
            redisCache.setCacheList(RedisPrefixConstant.IMAGE_INFO_PREFIX + "ProfilePhotos", imageInfoEntities);
        }
        Map<String, List<SysImageInfoEntity>> imageMap = imageInfoEntities.stream()
                .collect(Collectors.groupingBy(SysImageInfoEntity::getCountry));
        List<ImageInfoVO> imageInfoVOS = new ArrayList<>();
        imageMap.forEach((key, value) -> {
            ImageInfoVO imageInfoVO = new ImageInfoVO();
            List<BaseImage> baseImages = BeanMapperUtils.mapList(value, BaseImage.class);
            imageInfoVO.setCountry(key);
            imageInfoVO.setImageInfo(baseImages);
            imageInfoVOS.add(imageInfoVO);
        });
        return imageInfoVOS;
    }

    @Override
    public SysImageInfoEntity getRandomAvatar() {
        return sysImageInfoMapper.getRandomAvatar();
    }

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
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addRoleCardInfo(String url) {
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
        String body = JSONUtil.toJsonStr(params);
        HttpResponse execute = HttpRequest.post(url)
                .header(Header.CONTENT_TYPE, "application/json")
                .body(body)
                .execute();
        JSONObject responseJson = JSONUtil.parseObj(execute.body());
        Map result = responseJson.get("data", Map.class);
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
                    if(costJson!=null){
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

}
