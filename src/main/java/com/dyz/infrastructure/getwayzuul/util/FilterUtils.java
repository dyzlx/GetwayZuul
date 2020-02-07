package com.dyz.infrastructure.getwayzuul.util;

import com.netflix.zuul.context.RequestContext;

import java.util.Objects;

public class FilterUtils {

    public static final String CORRELATION_ID = "ms-correlation-id";

    public static String getCorrelationId() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String correlationId = ctx.getRequest().getHeader(CORRELATION_ID);
        if (Objects.nonNull(correlationId)) {
            return correlationId;
        } else {
            return ctx.getZuulRequestHeaders().get(CORRELATION_ID);
        }
    }

    public static void setCorrelationId(String correlationId) {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader(CORRELATION_ID, correlationId);
    }
}
