package com.card.game.groovy

import com.card.game.controller.SysCommonController
import com.card.game.mapper.SysImageInfoMapper
import com.card.game.pojo.vo.ImageInfoVO
import com.card.game.service.SysImageInfoService
import org.junit.Assert
import org.junit.function.ThrowingRunnable
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.HttpStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.http.MediaType

import spock.lang.Specification

import static org.mockito.Mockito.when;

import javax.annotation.Resource


/**
 * 系统通用接口单元测试
 *
 * @author cunzhiwang
 * @Date 2023/1/18 10:50
 */
@WebMvcTest(SysCommonController.class)
@ContextConfiguration(classes = SysImageInfoMapper.class)
class SysCommonControllerTest extends Specification {
    @Resource
    MockMvc mockMvc;

    @MockBean
    SysImageInfoService sysImageInfoService;

    def "getProfilePhotos"() {
        given:
        if (excption != null) {
            when(sysImageInfoService.getProfilePhotos()).thenThrow(excption)

        } else {
            def vo = new ImageInfoVO(country: "test", imageInfo: null);
            List<ImageInfoVO> mockList = [vo];
            when(sysImageInfoService.getProfilePhotos()).thenReturn(list);

        }
        when:
        def result = null;
        def catExp
        try {
            result = mockMvc.perform(get("/sys/getProfilePhotos").contentType(MediaType.APPLICATION_JSON))
        } catch (Exception e) {
            catExp=e;
        }




        then:
        if(result==null){
            Assert.assertThrows(excption, { ->
                throw catExp;
            })
        }else {
           result.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()))


        }


        where:
        list                         | excption || expectedResult
        new ArrayList<ImageInfoVO>() |IllegalArgumentException.class     || "没有数据"

    }


}