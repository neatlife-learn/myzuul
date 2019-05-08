package com.neatlife.myzuul;

import com.neatlife.myzuul.filter.CertificationFilter;
import com.neatlife.myzuul.filter.CustomErrorFilter;
import com.neatlife.myzuul.filter.RequestLogFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy
public class MyzuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyzuulApplication.class, args);
    }

    /**
     * 请求日志记录
     */
    @Bean
    public RequestLogFilter requestLogFilter() {
        return new RequestLogFilter();
    }

    /**
     * 统一处理异常
     */
    @Bean
    public CustomErrorFilter customErrorFilter() {
        return new CustomErrorFilter();
    }


    /**
     * 认证
     */
    @Bean
    public CertificationFilter certificationFilter() {
        return new CertificationFilter();
    }

}
