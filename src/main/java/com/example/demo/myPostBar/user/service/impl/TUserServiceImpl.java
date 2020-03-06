package com.example.demo.myPostBar.user.service.impl;

import com.example.demo.myPostBar.user.entity.TUser;
import com.example.demo.myPostBar.user.mapper.TUserMapper;
import com.example.demo.myPostBar.user.service.ITUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author youkehai
 * @since 2020-01-15
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements ITUserService {

}
