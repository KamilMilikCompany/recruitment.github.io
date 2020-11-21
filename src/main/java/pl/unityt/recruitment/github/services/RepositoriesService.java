package pl.unityt.recruitment.github.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.unityt.recruitment.github.models.RepositoriesInfo;
import pl.unityt.recruitment.github.models.RepositoryParam;

@Service
public class RepositoriesService {

    private final RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(RepositoriesService.class);
    
    @Autowired
    public RepositoriesService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RepositoriesInfo getRepositoriesInfo(RepositoryParam repositoryParam) {
        final String url = "https://api.github.com/repos/" + repositoryParam.getOwner() + "/" + repositoryParam.getRepositoryName();
        RepositoriesInfo repositoriesInfo = restTemplate.getForObject(url, RepositoriesInfo.class);
        logger.info(repositoryParam.toString());
        logger.info(repositoriesInfo != null ? repositoriesInfo.toString() : null);
        return repositoriesInfo;
    }
}
