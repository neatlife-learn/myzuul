package com.neatlife.myzuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;

import javax.servlet.http.HttpServletRequest;

/**
 * 认证
 */
@Slf4j
public class CertificationFilter extends ZuulFilter {

    @Override
    public Object run() {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        // 到redis中检查token是否存在
        if ("21232f297a57a5a743894a0e4a801fc3".equals(request.getParameter("token"))) {
            return null;
        }
        throw new ZuulRuntimeException(new ZuulException("未授权用户禁止访问", 200, "token校验失败"));
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

}

