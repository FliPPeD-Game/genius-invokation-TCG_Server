package com.card.game.controller;

import com.card.game.common.result.Result;
import com.card.game.pojo.dto.ImageInfoDTO;
import com.card.game.pojo.entity.SysImageInfoEntity;
import com.card.game.pojo.vo.ImageInfoVO;
import com.card.game.service.CommonService;
import com.card.game.service.SysImageInfoService;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 系统APi
 *
 * @author cunzhiwang
 * @Date 2023/1/18 10:06
 */
@RestController
@RequestMapping("/sys")
@RequiredArgsConstructor
public class SysCommonController {

    private final SysImageInfoService sysImageInfoService;
    private final CommonService commonService;

    /**
     * 保存或更新头像信息
     *
     * @param imageInfoList 头像信息
     * @return 失败信息
     */
    @PostMapping("/saveProfilePhotos")
    public Result<List<SysImageInfoEntity>> saveImages(@RequestBody List<ImageInfoDTO> imageInfoList) {
        if (imageInfoList == null || CollectionUtils.isEmpty(imageInfoList)) {
            return Result.error("参数不能为空");
        }
        List<SysImageInfoEntity> sysImageInfoEntities = sysImageInfoService.saveImages(imageInfoList);
        return Result.success(sysImageInfoEntities);
    }

    /**
     * 获取头像相关信息
     *
     * @return 头像信息
     */
    @GetMapping("/getProfilePhotos")
    public Result<List<ImageInfoVO>> getProfilePhotos() {
        List<ImageInfoVO> profilePhotos = sysImageInfoService.getProfilePhotos();
        if (CollectionUtils.isEmpty(profilePhotos)) {
            return Result.error("没有数据");
        } else {
            return Result.success(profilePhotos);
        }
    }

    @GetMapping("/addRoleCardInfo")
    public Result<Boolean> addRoleCardInfo(@RequestParam("url") String url) {
        return Result.success(commonService.addRoleCardInfo(url));
    }

}
