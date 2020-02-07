package com.dyz.infrastructure.getwayzuul.filter;


import com.dyz.infrastructure.getwayzuul.util.FilterUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;

@Slf4j
@Component
public class ResponseFilter extends ZuulFilter {

    private static final int FILTER_ORDER = 1;

    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String correlationId = FilterUtils.getCorrelationId();
        log.info("adding the correlation id to the outbound headers. {}", correlationId);
        ctx.getResponse().addHeader(FilterUtils.CORRELATION_ID, correlationId);
        log.info("completing outgoing request for {}.", ctx.getRequest().getRequestURI());
        return null;
    }
}
