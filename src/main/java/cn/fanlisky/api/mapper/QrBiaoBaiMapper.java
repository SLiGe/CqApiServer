package cn.fanlisky.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.fanlisky.api.domain.QrBiaoBai;

import java.util.List;


public interface QrBiaoBaiMapper extends BaseMapper<QrBiaoBai> {
    List<QrBiaoBai> selectAll();
}