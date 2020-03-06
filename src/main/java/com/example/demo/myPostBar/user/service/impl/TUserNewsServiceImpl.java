package com.example.demo.myPostBar.user.service.impl;

import com.example.demo.myPostBar.user.entity.TUserNews;
import com.example.demo.myPostBar.user.mapper.TUserNewsMapper;
import com.example.demo.myPostBar.user.service.ITUserNewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户-消息表 服务实现类
 * </p>
 *
 * @author youkehai
 * @since 2020-01-15
 */
@Service
public class TUserNewsServiceImpl extends ServiceImpl<TUserNewsMapper, TUserNews> implements ITUserNewsService {

}
