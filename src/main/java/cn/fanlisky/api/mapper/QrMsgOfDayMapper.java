package cn.fanlisky.api.mapper;

import cn.fanlisky.api.domain.QrMsgOfDay;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 每日一句 Mapper 接口
 * </p>
 *
 * @author Gary
 * @since 2019-05-26
 */
public interface QrMsgOfDayMapper extends BaseMapper<QrMsgOfDay> {

    @Select(value = "SELECT * FROM `qr_msg_of_day` WHERE SEND_DATE IN (SELECT SEND_DATE from qr_msg_of_day GROUP BY SEND_DATE HAVING COUNT(*) >1 )")
    List<QrMsgOfDay> queryRepeatData();

    @Select("SELECT MAX(ID) FROM qr_msg_of_day WHERE DATE_FORMAT(CREATE_DATE,'%Y-%m-%d') = '2019-09-21' ")
    Long queryMaxDataId();
}
