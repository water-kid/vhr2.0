package com.cj.framework.service;

import com.cj.framework.entity.Hr;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author waterkid
 * @since 2024-02-12
 */
public interface IHrService extends IService<Hr>, UserDetailsService {

}
