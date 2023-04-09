package com.card.game.common.base.vo;

import com.card.game.common.base.dto.ImageInfoDTO;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * 头像信息视图
 *
 * @author cunzhiwang
 * @Date 2023/1/17 15:23
 */
@Getter
@Setter
public class ImageInfoVO {

    private String country;
    private List<ImageInfoDTO.BaseImage> imageInfo;


    @Getter
    @Setter
    public static class BaseImage{
        private String name;
        private String src;
    }

}
