package com.terminal.manage.feign;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Jyt
 * @date 2021/12/22
 */
@FeignClient(name = "terminal-system-user")
public interface UserFeignService {

}
