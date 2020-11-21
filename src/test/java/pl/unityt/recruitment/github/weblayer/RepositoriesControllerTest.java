package pl.unityt.recruitment.github.weblayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.unityt.recruitment.github.controllers.RepositoriesController;
import pl.unityt.recruitment.github.models.RepositoriesInfo;
import pl.unityt.recruitment.github.models.RepositoryParam;
import pl.unityt.recruitment.github.models.exceptions.ErrorResponse;
import pl.unityt.recruitment.github.models.exceptions.RestTemplateException;
import pl.unityt.recruitment.github.services.RepositoriesService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RepositoriesController.class)
public class RepositoriesControllerTest {

    private static final String owner = "tensor";
    private static final String repositoryName = "flow";
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    RepositoriesService repositoriesService;

    @Test
    void getRepositoriesReturnsRepositoriesInfoOK() throws Exception {
        RepositoriesInfo repositoriesInfoExpected = new RepositoriesInfo(repositoryName, "TensorFlow", "https://github.com/apple/tensorflow_macos.git", 39, "2020-11-05T18:47:47Z");
        Mockito.when(repositoriesService.getRepositoriesInfo(any(RepositoryParam.class))).thenReturn(repositoriesInfoExpected);

        MvcResult mvcResult = this.mockMvc.perform(get("/repositories/{owner}/{repositoryName}", owner, repositoryName)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(repositoriesInfoExpected));
    }

    @Test
    void getRepositoriesInfoReturnsClientException() throws Exception {
        String path = "/repositories/" + owner + "/" + repositoryName;
        RestTemplateException expectedRestTemplateException = new RestTemplateException(HttpStatus.NOT_FOUND, "NOT FOUND");
        ErrorResponse expectedErrorResponse = new ErrorResponse(expectedRestTemplateException, path);
        Mockito.when(repositoriesService.getRepositoriesInfo(any(RepositoryParam.class))).thenThrow(expectedRestTemplateException);

        MvcResult mvcResult = this.mockMvc.perform(get(path)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(expectedErrorResponse);

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void getRepositoriesInfoReturnsServerException() throws Exception {
        String path = "/repositories/" + owner + "/" + repositoryName;
        RestTemplateException expectedRestTemplateException = new RestTemplateException(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR");
        ErrorResponse expectedErrorResponse = new ErrorResponse(expectedRestTemplateException, path);
        Mockito.when(repositoriesService.getRepositoriesInfo(any(RepositoryParam.class))).thenThrow(expectedRestTemplateException);

        MvcResult mvcResult = this.mockMvc.perform(get(path)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andReturn();
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(expectedErrorResponse);

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);
    }

}
