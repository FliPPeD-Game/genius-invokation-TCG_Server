package com.card.game.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.card.game.pojo.dto.ImageInfoDTO;
import com.card.game.pojo.entity.SysImageInfoEntity;
import com.card.game.pojo.vo.ImageInfoVO;
import java.util.List;

/**
 * 图片服务类
 *
 * @author cunzhiwang
 * @Date 2023/1/17 10:55
 */
public interface SysImageInfoService extends IService<SysImageInfoEntity> {

    /**
     * 保存头像信息
     *
     * @param imageInfoList 头像信息
     * @return 失败头像信息
     */

    List<SysImageInfoEntity> saveImages(List<ImageInfoDTO> imageInfoList);

    /**
     * 获取所有头像
     *
     * @return 头像信息
     */
    List<ImageInfoVO> getProfilePhotos();

    /**
     * 随机获取头像
     *
     * @return 头像信息
     */
    String getRandomAvatar();

}
