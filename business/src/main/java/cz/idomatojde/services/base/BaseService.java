package cz.idomatojde.services.base;

import java.util.List;

/**
 * Base service interface containing CRUD functionality
 * @param <TEntity> the backing Entity class this instance services
 */
public interface BaseService<TEntity> {

    /**
     * <b>Create</b> operation for storing a new entity
     * @param entity the entity to create at the repository layer
     */
    void create(TEntity entity);

    /**
     * <b>Retrieve</b> operation for obtaining a list of all entities of class TEntity
     */
    List<TEntity> findAll();

    /**
     * <b>Retrieve</b> operation for obtaining a single entity by its ID
     */
    TEntity getById(Long id);

    /**
     * <b>Delete</b> operation for removing the entity from the repository
     */
    void delete(TEntity entity);
}