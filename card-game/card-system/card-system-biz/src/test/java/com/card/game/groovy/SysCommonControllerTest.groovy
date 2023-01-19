package com.card.game.groovy

import com.card.game.common.result.Result
import com.card.game.controller.SysCommonController
import com.card.game.mapper.SysImageInfoMapper
import com.card.game.pojo.vo.ImageInfoVO
import com.card.game.service.SysImageInfoService
import org.junit.Assert
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.FluxExchangeResult
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.test.web.servlet.MockMvc;
import org.spockframework.spring.SpringExtension
import com.sun.org.apache.xerces.internal.parsers.SecurityConfiguration

import spock.lang.Specification
import spock.lang.Unroll

import static org.mockito.Mockito.when;

import javax.annotation.Resource


/**
 * 系统通用接口单元测试
 *
 * @author cunzhiwang
 * @Date 2023/1/18 10:50
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class SysCommonControllerTest extends Specification {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    SysImageInfoService sysImageInfoService;

    @Unroll
    def "getProfilePhotos #CASE"() {
        given:
        def vo = new ImageInfoVO(country: "test", imageInfo: null);
        List<ImageInfoVO> mockList = [vo];
        if (conditon == 1) {
            when(sysImageInfoService.getProfilePhotos()).thenReturn(mockList);
        } else if (conditon == 2) {
            when(sysImageInfoService.getProfilePhotos()).thenReturn(null);
        }
        expect:
        WebTestClient.ResponseSpec result = webTestClient.get().uri("/sys/getProfilePhotos").exchange();
        FluxExchangeResult returnResult = result.returnResult(Result.class)
       if(conditon==2){
           returnResult.getStatus().INTERNAL_SERVER_ERROR.value() == status
       }else if(conditon==1){
           returnResult.getStatus().OK.value() == status
       }
        where:
        CASE | conditon | status
        0    | 1        | 200
        1    | 2        | 500


    }


}