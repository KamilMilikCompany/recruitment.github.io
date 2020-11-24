package pl.unityt.recruitment.github.models.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.unityt.recruitment.github.models.RepositoriesInfoDto;
import pl.unityt.recruitment.github.models.RepositoriesInfoRest;

@Component
public class RepositoriesInfoMapper {

    private final ModelMapper modelMapper;

    public RepositoriesInfoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RepositoriesInfoDto convertToDto(RepositoriesInfoRest repositoriesInfoRest) {
        return modelMapper.map(repositoriesInfoRest, RepositoriesInfoDto.class);
    }
}
