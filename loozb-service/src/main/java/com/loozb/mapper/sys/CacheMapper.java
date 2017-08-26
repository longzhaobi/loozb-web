package com.loozb.mapper.sys;


import com.loozb.model.sys.Cache;
import com.loozb.model.RedisKeyValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author 龙召碧
 * @since 2017-07-10
 */
public interface CacheMapper {

    List<Cache> selectData(@Param("cm") Map<String, Object> params);

    List<RedisKeyValue> selectTableName();
}