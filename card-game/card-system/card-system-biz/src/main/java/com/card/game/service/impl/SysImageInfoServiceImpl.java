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
            return new ArrayList<>();
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


}
