package com.mashibing.servicedriveruser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mashibing.internalcommon.dto.DriverUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author xcy
 * @date 2023/2/16 - 10:29
 */
@Repository
public interface DriverUserMapper extends BaseMapper<DriverUser> {

	public Integer selectDriverUserCountByCityCode(@Param("cityCode") String cityCode);
}
