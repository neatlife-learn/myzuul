package com.neatlife.myzuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RequestLogFilter extends ZuulFilter {

    @Override
    public Object run() {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        Map<String, Object> parameters = getParametersFromRequest(request);
        log.info("网关有新的访问, url: {}, method: {}, parameters: {}", request.getRequestURL(), request.getMethod(), parameters);
        return null;
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

    private Map<String, Object> getParametersFromRequest(HttpServletRequest request) {
        Enumeration<?> parameterNames = request.getParameterNames();
        Map<String, Object> parameters = new HashMap<>(2);
        while (parameterNames.hasMoreElements()) {
            String pName = (String) parameterNames.nextElement();
            Object pValue = request.getParameter(pName);
            parameters.put(pName, pValue);
        }
        return parameters;
    }

}

