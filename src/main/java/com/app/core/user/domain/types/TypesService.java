package com.app.core.user.domain.types;

import com.app.core.user.domain.exceptions.NotFoundErrorException;
import com.app.core.user.domain.types.mapper.TypesMapper;
import com.app.core.user.domain.types.model.Types;
import com.app.core.user.infraestructure.repository.TypesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Log4j2
@Service
@AllArgsConstructor
public class TypesService {

    private TypesRepository typesRepository;
    private TypesMapper typesMapper;

    public Mono<List<Types>> find(Types types) {
        return this.typesRepository.findAll(types.getType())
                .collectList()
                .map(this.typesMapper::convertListRepositoryInDomain)
                .doOnError(log::error);
    }

    public Mono<List<Types>>findOne(Types types) {
        return this.typesRepository.findOne(types.getType(), types.getName())
                .collectList()
                .map(this.typesMapper::convertListRepositoryInDomain)
                .doOnError(log::error);
    }

    public Mono<Boolean> insert(Types types) {
        return validateIfDataExist(types)
                .map(exist -> this.typesMapper.convertDomainInRepository(types))
                .flatMap(typeEntity -> this.typesRepository.insert(types.getType(), typeEntity))
                .doOnError(log::error);
    }

    public Mono<Boolean> update(Types types) {
        return validateIfDataNotExist(types)
                .map(exist -> this.typesMapper.convertDomainInRepository(types))
                .flatMap(typeEntity -> this.typesRepository.update(types.getType(), typeEntity, types.getPrevName()))
                .doOnError(log::error);
    }

    public Mono<Boolean> delete(Types types) {
        return validateIfDataNotExist(types)
                .map(exist -> this.typesMapper.convertDomainInRepository(types))
                .flatMap(typesEntity -> this.typesRepository.delete(types.getType(), typesEntity.getName()))
                .doOnError(log::error);
    }

    private Mono<Boolean> validateIfDataExist(Types types) {
        return findOne(types)
                .map(typesList -> {
                    if (!typesList.isEmpty()) {
                        throw new NotFoundErrorException("Data in %s exist.".formatted(types.getType()));
                    }
                    return false;
                });
    }

    private Mono<Boolean> validateIfDataNotExist(Types types) {
        Types updateType = Types.builder()
                .type(types.getType())
                .name(types.getPrevName() == null
                        ? types.getName() : types.getPrevName())
                .build();
        return findOne(updateType)
                .map(typesList -> {
                    if (typesList.isEmpty()) {
                        throw new NotFoundErrorException("Data in %s not exist.".formatted(updateType.getType()));
                    }
                    return true;
                });
    }
}
