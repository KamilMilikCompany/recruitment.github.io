package pl.unityt.recruitment.github.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.unityt.recruitment.github.models.RepositoriesInfoDto;
import pl.unityt.recruitment.github.models.RepositoriesInfoRest;
import pl.unityt.recruitment.github.models.RepositoryParam;
import pl.unityt.recruitment.github.models.mappers.RepositoriesInfoMapper;

@Service
public class RepositoriesService {

    private final String URI = "https://api.github.com/repos/";
    private final RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(RepositoriesService.class);
    private final RepositoriesInfoMapper repositoriesInfoMapper;

    @Autowired
    public RepositoriesService(RestTemplate restTemplate, RepositoriesInfoMapper repositoriesInfoMapper) {
        this.restTemplate = restTemplate;
        this.repositoriesInfoMapper = repositoriesInfoMapper;
    }

    public RepositoriesInfoDto getRepositoriesInfo(RepositoryParam repositoryParam) {
        final String url = URI + repositoryParam.getOwner() + "/" + repositoryParam.getRepositoryName();
        logger.debug("Url params: " + repositoryParam.toString());
        RepositoriesInfoRest repositoriesInfoRest = restTemplate.getForObject(url, RepositoriesInfoRest.class);
        if (repositoriesInfoRest == null) {
            logger.warn("Response is: " + null);
        } else {
            logger.debug("Response is: " + repositoriesInfoRest.toString());
        }
        return repositoriesInfoMapper.convertToDto(repositoriesInfoRest);
    }
}
