package ru.rodionovsasha.shoppinglist.api.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.rodionovsasha.shoppinglist.context.SharedContext;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Slf4j
@Component
public class HttpClientProvider {
    private static final int DEFAULT_TIMEOUT = 2 * 60 * 1000;
    private static final HttpClientContext CLIENT_CONTEXT = HttpClientContext.create();

    @Autowired
    private SharedContext sharedContext;

    private CloseableHttpClient httpClient;

    @PostConstruct
    public void buildHttpClient() {
        this.httpClient = HttpClients.custom()
                .setDefaultRequestConfig(getRequestConfig())
                .setRedirectStrategy(new LaxRedirectStrategy())
                .setMaxConnTotal(20)
                .setMaxConnPerRoute(10)
                .build();
    }

    @SneakyThrows
    String executeRequest(HttpUriRequest request) {
        CloseableHttpResponse response = null;
        try {
            response = executeHttpRequest(request);
            sharedContext.responseCode = response.getStatusLine().getStatusCode();
            return EntityUtils.toString(response.getEntity());
        } finally {
            log.debug("Closing http client...");
            HttpClientUtils.closeQuietly(response);
        }
    }

    @SneakyThrows
    Integer executeRequestAndGetStatusCode(HttpUriRequest request) {
        CloseableHttpResponse response = null;
        try {
            response = executeHttpRequest(request);
            return response.getStatusLine().getStatusCode();
        } finally {
            log.debug("Closing http client...");
            HttpClientUtils.closeQuietly(response);
        }
    }

    private CloseableHttpResponse executeHttpRequest(HttpUriRequest request) throws IOException {
        return httpClient.execute(request, CLIENT_CONTEXT);
    }

    private RequestConfig getRequestConfig() {
        return RequestConfig.custom()
                .setRedirectsEnabled(true)
                .setCircularRedirectsAllowed(true)
                .setMaxRedirects(10)
                .setConnectTimeout(DEFAULT_TIMEOUT)
                .setConnectionRequestTimeout(DEFAULT_TIMEOUT)
                .setSocketTimeout(DEFAULT_TIMEOUT)
                .setCookieSpec(CookieSpecs.DEFAULT)
                .build();
    }
}