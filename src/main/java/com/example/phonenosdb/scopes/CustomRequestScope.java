package com.example.phonenosdb.scopes;

import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Getter
public class CustomRequestScope {

    private String requestId;

    private String logPrefix;

    public void init(String requestId) {
        if (!StringUtils.hasText(requestId))
            requestId = UUID.randomUUID().toString();
        this.requestId = requestId;
        this.logPrefix = String.format("[RequestId: %s]", requestId);
    }

}
