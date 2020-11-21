package pl.unityt.recruitment.github.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import pl.unityt.recruitment.github.models.exceptions.RestTemplateException;

import java.io.IOException;

public class RestTemplateErrorHandler extends DefaultResponseErrorHandler {

    private Logger logger = LoggerFactory.getLogger(RestTemplateErrorHandler.class);

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
            logger.error(response.getStatusText());
            throw new RestTemplateException(response.getStatusCode(), response.getStatusText());
        }
    }
}