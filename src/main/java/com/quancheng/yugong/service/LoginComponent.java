/*
 * Copyright (c) 2017, Quancheng-ec.com All right reserved. This software is the
 * confidential and proprietary information of Quancheng-ec.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Quancheng-ec.com.
 */
package com.quancheng.yugong.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author shimingliu 2017年2月15日 上午10:27:38
 * @version LoginComponent.java, v 0.0.1 2017年2月15日 上午10:27:38 shimingliu
 */
@Component("LoginFilter")
public class LoginComponent implements Filter {

    private final static Logger logger    = LogManager.getLogger("login.default");

    @Value("${ucenter.server.host}")
    private String              url       = "";
    @Value("${ucenter.client.appName}")
    private String              name      = "";
    @Value("${ucenter.client.whiteList}")
    private String              whiteList = "";

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                                                                                              ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        Cookie[] cookies = req.getCookies();
        String Uid = null;
        if (cookies != null) {
            for (Cookie ck : cookies) {
                if ("userId".equals(ck.getName())) {
                    Uid = ck.getValue();
                }
            }
        }
        String[] whiteListArr = whiteList.split(",");
        List<String> result = Arrays.asList(whiteListArr);
        int count = 0;
        for (String item : result) {
            // 创建 Pattern 对象
            Pattern r = Pattern.compile(item);
            // 现在创建 matcher 对象
            Matcher m = r.matcher(req.getRequestURI());
            logger.warn("item:" + item);
            while (m.find()) {
                count++;
            }
            logger.warn("count:" + count);
        }
        if (count == 0) {
            if (Uid != null) {
                logger.warn("登录成功");
            } else {
                logger.warn("跳转登录");
                String sourceUrl = req.getRequestURL().toString();
                if (!StringUtils.isEmpty(req.getQueryString())) {
                    sourceUrl = sourceUrl + "?" + req.getQueryString();
                }
                logger.warn("sourceUrl:" + sourceUrl);
                String urlCopy = "";
                urlCopy = url + "?target=" + URLEncoder.encode(sourceUrl, "UTF-8") + "&appName=" + name;
                logger.debug("urlCopy:" + urlCopy);
                res.sendRedirect(urlCopy);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
