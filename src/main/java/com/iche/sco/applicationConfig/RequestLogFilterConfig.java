package com.iche.sco.applicationConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RequestLogFilterConfig {
    public CommonsRequestLoggingFilter commonsRequestLoggingFilter(){
        var filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setIncludeHeaders(true);

        return filter;
    }
}
