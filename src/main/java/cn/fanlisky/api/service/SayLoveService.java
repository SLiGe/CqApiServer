package cn.fanlisky.api.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.fanlisky.api.enums.StatusCode;
import cn.fanlisky.api.mapper.QrBiaoBaiMapper;
import cn.fanlisky.api.domain.QrBiaoBai;
import cn.fanlisky.api.model.response.RestfulResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @description: 表白墙业务类
 * @author Gary
 * @create 2018-12-28 16:49
 */
@Service
public class SayLoveService extends ServiceImpl<QrBiaoBaiMapper, QrBiaoBai> {

    /**
     * 获取表白列表
     */
    public RestfulResponse getLoveList() {
        List<QrBiaoBai> qrBiaoBai = list();
        return RestfulResponse.getRestfulResponse(StatusCode.QUERY_SUCCESS, qrBiaoBai);
    }

    /**
     * 新增表白
     */
    @Transactional(rollbackFor = Exception.class)
    public RestfulResponse addLove(QrBiaoBai qrBiaoBai) {
        RestfulResponse response = new RestfulResponse();
        boolean result = save(qrBiaoBai);
        if (result) {
            response.setMsg("新增成功");
            response.setStatus("200");
        } else {
            response.setMsg("新增失败");
            response.setStatus("201");
        }
        return response;
    }

    /**
     * 根据id获取数据
     */
    public RestfulResponse searchLove(String id) {
        RestfulResponse response = new RestfulResponse();
        QrBiaoBai qrBiaoBai = getById(Integer.parseInt(id));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        Res res = new Res();
        res.setId(qrBiaoBai.getId());
        String addTime = sdf.format(qrBiaoBai.getAddtime());
        res.setAddtime(addTime);
        res.setUptime(addTime);
        res.setContent(qrBiaoBai.getContent());
        res.setMe(qrBiaoBai.getMe());
        res.setQq(qrBiaoBai.getQq());
        res.setYou(qrBiaoBai.getYou());
        response.setStatus("200");
        response.setMsg("查询成功");
        response.setData(res);
        return response;
    }

    /**
     * 写内部类的原因是自己懒
     */
    class Res {

        private Integer id;

        private String content;

        private String qq;

        private String me;

        private String you;

        private String addtime;

        private String uptime;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getMe() {
            return me;
        }

        public void setMe(String me) {
            this.me = me;
        }

        public String getYou() {
            return you;
        }

        public void setYou(String you) {
            this.you = you;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getUptime() {
            return uptime;
        }

        public void setUptime(String uptime) {
            this.uptime = uptime;
        }
    }

}
