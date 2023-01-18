package com.card.game.pojo.dto;

import java.util.List;
import lombok.Data;

/**
 * 图片DTO
 *
 * @author cunzhiwang
 * @Date 2023/1/17 10:36
 */
@Data
public class ImageInfoDTO {

    private String country;
    private List<BaseImage> imageInfo;


    @Data
    public static class BaseImage{
        private String name;
        private String src;
    }

}
