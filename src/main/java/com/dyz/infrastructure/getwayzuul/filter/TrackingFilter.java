package com.dyz.infrastructure.getwayzuul.filter;

import com.dyz.infrastructure.getwayzuul.util.FilterUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;


@Slf4j
@Component
public class TrackingFilter extends ZuulFilter {

    private static final int FILTER_ORDER = 1;

    @Override
    public String filterType() {
        return PRE_TYPE;
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
        if (isCorrelationIdPresent()) {
            log.info("ms-correlation-id found in http request header: {}", FilterUtils.getCorrelationId());
        } else {
            FilterUtils.setCorrelationId(generateCorrelationId());
            log.info("ms-correlation-id not found in http request header, generate a new correlation id: {}",
                    FilterUtils.getCorrelationId());
        }
        RequestContext ctx = RequestContext.getCurrentContext();
        log.info("processing incoming request for {}.", ctx.getRequest().getRequestURI());
        return null;
    }

    private boolean isCorrelationIdPresent() {
        return Objects.nonNull(FilterUtils.getCorrelationId());
    }

    private String generateCorrelationId() {
        return UUID.randomUUID().toString();
    }
}
