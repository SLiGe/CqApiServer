package cn.fanlisky.api.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Gary
 * @since 2019-10-19 16:21
 * <p>Code is my soul.<p/>
 */
@Mapper
public interface QrDataMapper {

    /**
     * 查询数据总数
     *
     * @param tableName 表名
     * @return 总数
     */
    @Select("select count(1) from ${tableName}")
    Integer getDataCount(@Param("tableName") String tableName);
}
