package com.app.core.user.infraestructure.repository;

import com.app.core.user.infraestructure.entity.TypesEntity;
import com.app.core.user.infraestructure.exceptions.InternalServerErrorException;
import com.app.core.user.infraestructure.mapper.TypesRepositoryMapper;
import lombok.AllArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.app.core.user.infraestructure.constants.TypesSqlConstants.*;

@Repository
@AllArgsConstructor
public class TypesRepository {

    private final DatabaseClient databaseClient;
    private final TypesRepositoryMapper typesRepositoryMapper;

    public Flux<TypesEntity> findAll(String type) {
        return databaseClient
                .sql(typesRepositoryMapper.queryFormatSelect(type))
                .map((row, meta) -> this.typesRepositoryMapper.convertRowInRepository(row))
                .all()
                .onErrorResume(error ->
                        Mono.error(new InternalServerErrorException(
                                MESSAGE_ERROR_FIND_ALL.formatted(type, error.getMessage()))));
    }

    public Flux<TypesEntity> findOne(String type, String name) {
        return findAll(type)
                .filter(typesEntity -> typesEntity.getName().equalsIgnoreCase(name));
    }

    public Mono<Boolean> insert(String type, TypesEntity typesEntity) {
        return transaction(
                typesRepositoryMapper.queryFormatInsert(type, typesEntity),
                type,
                MESSAGE_ERROR_INSERT);
    }

    public Mono<Boolean> update(String type, TypesEntity typesEntity, String name) {
        return transaction(
                typesRepositoryMapper.queryFormatUpdate(type, typesEntity, name),
                type,
                MESSAGE_ERROR_UPDATE);
    }

    public Mono<Boolean> delete(String type, String name) {
        return transaction(
                typesRepositoryMapper.queryFormatDelete(type, name),
                type,
                MESSAGE_ERROR_DELETE);
    }

    private Mono<Boolean> transaction(String query, String type, String message) {
        return databaseClient
                .sql(query)
                .fetch()
                .rowsUpdated()
                .map(rows -> rows > 0)
                .onErrorResume(error ->
                        Mono.error(new InternalServerErrorException(
                                message.formatted(type, error.getMessage()))));
    }
}
