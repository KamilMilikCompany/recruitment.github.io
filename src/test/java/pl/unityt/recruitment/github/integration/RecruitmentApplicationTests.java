package pl.unityt.recruitment.github.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import pl.unityt.recruitment.github.models.RepositoriesInfo;
import pl.unityt.recruitment.github.models.exceptions.ErrorResponse;
import pl.unityt.recruitment.github.models.exceptions.RestTemplateException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecruitmentApplicationTests {

    private static final String URI = "/repositories";
    private static final String OWNER = "hypertrace";
    private static final String REPOSITORY = "hypertrace-ingester";
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getRepositoriesReturnsRepositoriesInfoOK() {
        String url = "http://localhost:" + port + URI + "/" + OWNER + "/" + REPOSITORY;
        RepositoriesInfo repositoriesInfoExpected = new RepositoriesInfo("hypertrace/hypertrace-ingester", "Streaming jobs for Hypertrace", "https://github.com/hypertrace/hypertrace-ingester.git", 4, "2020-08-24T08:48:05Z");

        ResponseEntity<RepositoriesInfo> responseEntity = this.restTemplate.exchange(
                url, HttpMethod.GET, null, new ParameterizedTypeReference<RepositoriesInfo>() {
                });
        RepositoriesInfo repositoriesInfoActual = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(repositoriesInfoActual).isEqualToComparingFieldByField(repositoriesInfoExpected);
    }

    @Test
    public void getRepositoriesReturnsNotFound() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String wrongPath = "/wrong/path";
        String url = "http://localhost:" + port + URI + wrongPath;
        RestTemplateException expectedRestTemplateException = new RestTemplateException(HttpStatus.NOT_FOUND, "Not Found");
        ErrorResponse expectedErrorResponse = new ErrorResponse(expectedRestTemplateException, URI + wrongPath);

        ResponseEntity<String> responseEntity = this.restTemplate.exchange(
                url, HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
                });

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).isEqualToIgnoringWhitespace(mapper.writeValueAsString(expectedErrorResponse));
    }
}
