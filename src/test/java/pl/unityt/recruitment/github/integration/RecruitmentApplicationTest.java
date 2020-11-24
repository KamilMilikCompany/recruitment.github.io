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
import pl.unityt.recruitment.github.models.RepositoriesInfoDto;
import pl.unityt.recruitment.github.models.exceptions.ErrorResponse;
import pl.unityt.recruitment.github.models.exceptions.RestTemplateException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecruitmentApplicationTest {

    private static final String URI = "/repositories";
    private static final String OWNER = "hypertrace";
    private static final String REPOSITORY = "hypertrace-ingester";
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getRepositories_ShouldReturnsRepositoriesOK_WhenRepositoryExist() {
        String url = "http://localhost:" + port + URI + "/" + OWNER + "/" + REPOSITORY;
        RepositoriesInfoDto repositoriesInfoDtoExpected = new RepositoriesInfoDto("hypertrace/hypertrace-ingester", "Streaming jobs for Hypertrace", "https://github.com/hypertrace/hypertrace-ingester.git", 4, "2020-08-24T08:48:05Z");

        ResponseEntity<RepositoriesInfoDto> responseEntity = this.restTemplate.exchange(
                url, HttpMethod.GET, null, new ParameterizedTypeReference<RepositoriesInfoDto>() {
                });
        RepositoriesInfoDto repositoriesInfoDtoActual = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(repositoriesInfoDtoActual).isEqualToComparingFieldByField(repositoriesInfoDtoExpected);
    }

    @Test
    public void getRepositories_ShouldReturnsClientException_WhenRepositoryNotExist() throws JsonProcessingException {
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
