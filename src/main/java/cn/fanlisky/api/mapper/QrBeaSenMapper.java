package cn.fanlisky.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.fanlisky.api.domain.QrBeaSen;

import java.util.Map;

public interface QrBeaSenMapper extends BaseMapper<QrBeaSen> {

    int count(Map<String,Object> map);
}