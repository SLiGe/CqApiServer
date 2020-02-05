package cn.fanlisky.api.controller;

import cn.fanlisky.api.service.IQrShortUrlService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gary
 * @since 2019-11-17 15:48
 * <p>Code is my soul.<p/>
 */
@Controller
@RequestMapping(value = "/api/qr_short_url")
public class QrShortUrlController {

    @Resource
    private IQrShortUrlService shortUrlService;

    @RequestMapping(value = "redirect/{code}", method = RequestMethod.GET)
    public void redirectUrl(@PathVariable(value = "code") String code, HttpServletResponse response) {
        shortUrlService.redirectUrl(code, response);
    }
}
