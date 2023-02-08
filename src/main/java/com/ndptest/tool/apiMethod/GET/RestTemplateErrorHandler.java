package com.ndptest.tool.apiMethod.GET;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class RestTemplateErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return false;
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        // 여기서 4xx..(클라) 5xx 에러(서버) 처리(
        if (httpResponse.getStatusCode()
                .series() == HttpStatus.Series.SERVER_ERROR) {
            // handle SERVER_ERROR
            System.out.println(httpResponse.getStatusText() + "로 예외처리 됨");

        } else if (httpResponse.getStatusCode()
                .series() == HttpStatus.Series.CLIENT_ERROR) {
            // handle CLIENT_ERROR
            System.out.println(httpResponse.getStatusText() + "로 예외처리 됨");
        }
    }
}

