package com.onescorpion.nova.filter;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 请求过滤器  拦截请求，判断是否是静态资源文件且是否带上时间戳
 * 是资源文件且未加上时间戳，给请求加上时间戳请求，让客户端重新发起一个新的请求。（浏览器会判断url是否在本地缓存过，是就会读取本地缓存，导致服务器js更新,可能客户端未更新）
 *
 * @author 李挺 【fengkuangdejava@outlook.com】
 * @date 2018/10/8 11:28
 */
public class NoCacheFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(NoCacheFilter.class);
    private static final String STATIC_TAIL = "__oawx_t=";

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String jsChar = ".js";
        String cssChar = ".css";
        String jsonChar = ".json";
        String htmlChar = ".html";
        String indexChar = "index.html";
        String requestURL = req.getRequestURL().toString();
        String queryStr = req.getQueryString();
        if (requestURL.endsWith(jsChar) || requestURL.endsWith(cssChar) || requestURL.endsWith(jsonChar)) {
            String newURL;
            if (StringUtils.isNotBlank(queryStr) && !queryStr.trim().contains(NoCacheFilter.STATIC_TAIL)) {
                newURL = requestURL + "?" + queryStr + "&" + NoCacheFilter.STATIC_TAIL + System.currentTimeMillis();
                resp.sendRedirect(newURL);
            }
            if (StringUtils.isBlank(queryStr)) {
                newURL = requestURL + "?" + NoCacheFilter.STATIC_TAIL + System.currentTimeMillis();
                resp.sendRedirect(newURL);
            }
            try {
                chain.doFilter(request, response);
            } catch (Exception e) {
                logger.error(e.toString());
            }
        } else if (requestURL.endsWith(htmlChar) && !requestURL.contains(indexChar)) {
            String newURL;
            if (StringUtils.isNotBlank(queryStr) && !queryStr.trim().contains(NoCacheFilter.STATIC_TAIL)) {
                newURL = requestURL + "?" + queryStr + "&" + NoCacheFilter.STATIC_TAIL + System.currentTimeMillis();
                resp.sendRedirect(newURL);
                return;
            }
            if (StringUtils.isBlank(queryStr)) {
                newURL = requestURL + "?" + NoCacheFilter.STATIC_TAIL + System.currentTimeMillis();
                resp.sendRedirect(newURL);
                return;
            }

            try {
                chain.doFilter(request, response);
            } catch (Exception e) {
                logger.error(e.toString());
            }
        } else {
            try {
                chain.doFilter(request, response);
            } catch (Exception e) {
                logger.error(e.toString());
            }
        }

    }

    @Override
    public void destroy() {

    }
}
