package com.neatlife.myzuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
public class CustomErrorFilter extends ZuulFilter {

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        // Remove error code to prevent further error handling in follow up filters
        ctx.remove("error.status_code");
        // block the SendErrorFilter from running
        ctx.set("sendErrorFilter.ran");

        ctx.setSendZuulResponse(false);
        ctx.getResponse().setContentType("application/json");
        ctx.getResponse().setCharacterEncoding("utf-8");
        ctx.getResponse().setHeader("Access-Control-Allow-Origin", "*");
        ctx.getResponse().setHeader("Access-Control-Allow-Methods", "*");
        ctx.getResponse().setHeader("Access-Control-Allow-Age", "86400");
        ctx.getResponse().setHeader("Access-Control-Allow-Headers", "*");
        ctx.setResponseStatusCode(200);
        StringWriter sw = new StringWriter();
        ctx.getThrowable().printStackTrace(new PrintWriter(sw, true));
        try {
            ZuulException zuulException = (ZuulException) ctx.getThrowable().getCause().getCause();

            ctx.getResponse().getWriter().write(
            "{\"message\": \"" +
                    zuulException.getMessage() +
                    "\", \"code\" :" +
                    zuulException.nStatusCode
                    + "}"
            );
        } catch (Exception e) {
            log.error("写入异常到客户端异常, estring: {}", e.toString());
        }
        return null;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public String filterType() {
        return FilterConstants.ERROR_TYPE;
    }

}

