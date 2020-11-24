package pl.unityt.recruitment.github.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.unityt.recruitment.github.models.RepositoriesInfoDto;
import pl.unityt.recruitment.github.models.RepositoryParam;
import pl.unityt.recruitment.github.services.RepositoriesService;

@RestController
public class RepositoriesController {

    private final RepositoriesService repositoriesService;

    @Autowired
    public RepositoriesController(RepositoriesService repositoriesService) {
        this.repositoriesService = repositoriesService;
    }

    @GetMapping("/repositories/{owner}/{repositoryName}")
    public ResponseEntity<RepositoriesInfoDto> repositories(RepositoryParam repositoryParam) {
        return new ResponseEntity<>(repositoriesService.getRepositoriesInfo(repositoryParam), HttpStatus.OK);
    }
}
