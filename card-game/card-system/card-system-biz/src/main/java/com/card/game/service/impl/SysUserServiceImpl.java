package com.card.game.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.card.game.api.user.dto.AvatarDTO;
import com.card.game.api.user.dto.SysUserDTO;
import com.card.game.api.user.vo.SysUserVO;
import com.card.game.common.exception.BizException;
import com.card.game.common.redis.RedisCache;
import com.card.game.common.redis.constants.RedisPrefixConstant;
import com.card.game.common.result.ResultCode;
import com.card.game.common.web.utils.BeanMapperUtils;
import com.card.game.mapper.SysUserMapper;
import com.card.game.pojo.dto.EmailRegisterDTO;
import com.card.game.pojo.dto.user.SysUserUpdateDTO;
import com.card.game.pojo.entity.SysImageInfoEntity;
import com.card.game.pojo.entity.SysUserEntity;
import com.card.game.security.enums.SecurityLoginType;
import com.card.game.security.support.userdetails.SecurityMailUserDetails;
import com.card.game.security.utils.JwtUtil;
import com.card.game.security.utils.SecurityContextUtils;
import com.card.game.service.SysImageInfoService;
import com.card.game.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tom
 * @since 2023-01-08
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements SysUserService {

    private final SysUserMapper sysUserMapper;

    private final RedisCache redisCache;

    private final PasswordEncoder passwordEncoder;

    private final SysImageInfoService sysImageInfoService;

    @Override
    public Map<String, Object> registerUser(EmailRegisterDTO emailRegisterDTO) {
        //判断用户是否已经注册
        if (isUserRegisteredByMailAccount(emailRegisterDTO.getEmail())) {
            throw new BizException(ResultCode.USER_IS_EXIST);
        }

        //校验邮箱验证码
        String cacheCode = redisCache.getCacheObject(
                RedisPrefixConstant.MAIL_CODE_PREFIX + emailRegisterDTO.getEmail());
        //判断验证码是否存在
        if (StringUtils.isBlank(cacheCode)) {
            throw new BizException(ResultCode.MAIL_CODE_IS_EXPIRE);
        }
        //判断验证码是否相同
        if (!StringUtils.equals(cacheCode, emailRegisterDTO.getMailCode())) {
            throw new BizException(ResultCode.MAIL_CODE_CHECK_ERROR);
        }

        //校验两次密码是否一致
        if (!StringUtils.equals(emailRegisterDTO.getPassword(), emailRegisterDTO.getRePassword())) {
            throw new BizException(ResultCode.RE_PASSWORD_CHECK_ERROR);
        }
        //构造默认用户
        SysUserEntity defaultUser = SysUserEntity.buildDefaultUser();
        defaultUser.setEmail(emailRegisterDTO.getEmail());
        //密码加密
        defaultUser.setPassword(passwordEncoder.encode(emailRegisterDTO.getPassword()));

        // 获取随机头像
        SysImageInfoEntity avatar = sysImageInfoService.getRandomAvatar();

        defaultUser.buildAvatarInfo(avatar);

        //存入数据库
        sysUserMapper.insert(defaultUser);

        SecurityMailUserDetails principal = new SecurityMailUserDetails(
                BeanMapperUtils.map(defaultUser, SysUserDTO.class));

        //存入redis,1天后过期
        redisCache.setCacheObject(RedisPrefixConstant.AUTHENTICATION_PREFIX + principal.getMailAccount(),
                principal, 24, TimeUnit.HOURS);
        //生成token
        String token = JwtUtil.createJwt(emailRegisterDTO.getEmail(), SecurityLoginType.MAIL);

        return SecurityContextUtils.buildUserMailContext(principal, token);
    }

    @Override
    public Boolean isUserRegisteredByMailAccount(String mailAccount) {
        Long count = sysUserMapper
                .selectCount(Wrappers.<SysUserEntity>lambdaQuery()
                        .eq(SysUserEntity::getEmail, mailAccount));
        return count > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUserVO updateUserInfo(SysUserUpdateDTO sysUserUpdateDTO) {
        //校验两次密码是否一致
        if (!StringUtils.equals(sysUserUpdateDTO.getPassword(), sysUserUpdateDTO.getRePassword())) {
            throw new BizException(ResultCode.RE_PASSWORD_CHECK_ERROR);
        }
        //密码加密
        if (StringUtils.isNotBlank(sysUserUpdateDTO.getPassword())){
            sysUserUpdateDTO.setPassword(passwordEncoder.encode(sysUserUpdateDTO.getPassword()));
        }

        //当前登陆用户信息
        SecurityMailUserDetails currentUserInfo = SecurityContextUtils.getCurrentUserInfo();
        if (Objects.isNull(currentUserInfo)) {
            throw new BizException(ResultCode.ERROR);
        }
        String currentMailAccount = currentUserInfo.getMailAccount();
        Long id = currentUserInfo.getSysUserDTO().getId();


        //修改数据库
        SysUserEntity userEntity = sysUserMapper.selectById(id);
        BeanUtil.copyProperties(sysUserUpdateDTO, userEntity, CopyOptions.create().ignoreNullValue());
        userEntity.buildAvatarInfo(sysUserUpdateDTO.getAvatarInfo());
        sysUserMapper.updateById(userEntity);

        log.info("userEntity is {}", userEntity);
        //更新redis里用户的信息
        BeanMapperUtils.copy(sysUserUpdateDTO, currentUserInfo.sysUserDTO);
        //当前拿到的密码已经被清空了
        currentUserInfo.sysUserDTO.setPassword(userEntity.getPassword());
        BeanUtil.copyProperties(sysUserUpdateDTO, currentUserInfo.sysUserDTO, CopyOptions.create().ignoreNullValue());
        //更新缓存里的信息
        redisCache.deleteObject(RedisPrefixConstant.AUTHENTICATION_PREFIX + currentMailAccount);
        redisCache.setCacheObject(RedisPrefixConstant.AUTHENTICATION_PREFIX + currentMailAccount,
                currentUserInfo, 24, TimeUnit.HOURS);

        SysUserVO sysUserVO = BeanMapperUtils.map(userEntity, SysUserVO.class);

        sysUserVO.setAvatarInfo(
                AvatarDTO.builder()
                        .url(userEntity.getAvatar())
                        .country(userEntity.getCountry())
                        .build());
        return sysUserVO;
    }
}
