package main.java.br.com.hramos.dao.generic;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.*;
import main.java.br.com.hramos.domain.Marca;
import main.java.br.com.hramos.domain.Persistent;

import java.util.Collection;
import java.util.List;

public abstract class GenericDAO<T extends Persistent> implements IGenericDAO<T> {

    private EntityManagerFactory entityManagerFactory;

    protected void closeConnection(EntityManager entityManager) {
        if (entityManager != null) {
            entityManager.close();
        }
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    public abstract Class<T> getClassType();
    protected abstract void setUpdateFields(Root<T> root, CriteriaUpdate<T> update, T entity);

    private EntityManager getEntityManager() {
        entityManagerFactory = Persistence.createEntityManagerFactory("UseCriteria");
        return entityManagerFactory.createEntityManager();
    }

    @Override
    public T cadastrar(T entity) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();

            return entity;
        } finally {
            closeConnection(entityManager);
        }
    }

    @Override
    public void atualizar(T entity) {
        EntityManager entityManager = getEntityManager();

        try {
            entityManager.getTransaction().begin();
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaUpdate<T> update = builder.createCriteriaUpdate(getClassType());
            Root<T> root = update.from(getClassType());

            // metodo abstrado para atualizar cada classe
            setUpdateFields(root, update, entity);

            update.where(builder.equal(root, entity));

            entityManager.createQuery(update).executeUpdate();
            entityManager.getTransaction().commit();
        } finally {
            closeConnection(entityManager);
        }
    }

    @Override
    public void remover(T entity) {
        EntityManager entityManager = getEntityManager();

        try{
            entityManager.getTransaction().begin();
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaDelete<T> delete = builder.createCriteriaDelete(getClassType());
            Root<T> root = delete.from(getClassType());
            delete.where(builder.equal(root, entity));

            entityManager.createQuery(delete).executeUpdate();
            entityManager.getTransaction().commit();
        } finally {
            closeConnection(entityManager);
        }
    }

    @Override
    public T buscar(String codigo) {
        EntityManager entityManager = getEntityManager();
        T entity = null;

        try {
            entityManager.getTransaction().begin();
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(getClassType());
            Root<T> root = query.from(getClassType());

            // se a classe for do tipo carro, faz um join para pegar carro
            if (getClassType() == Marca.class) {
                root.join("carros", JoinType.INNER);
            }

            // Se a classe for do tipo Marca busca por codigo se nao buscar por uma placa
            String classKey = getClassType() == Marca.class ? "codigo" : "placa";

            query.select(root).where(builder.equal(root.get(classKey), codigo));

            entity = entityManager.createQuery(query).getSingleResult();
        } finally {
            closeConnection(entityManager);
        }

        return entity;
    }

    @Override
    public Collection<T> buscarTodos() {
        EntityManager entityManager = getEntityManager();

        List<T> entities = null;

        try {
            entityManager.getTransaction().begin();

            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(getClassType());
            Root<T> root = query.from(getClassType());

            // se a classe for do tipo carro, faz um join para pegar carro
            if (getClassType() == Marca.class) {
                root.join("carros", JoinType.INNER);
            }

            query.select(root);

            entities = entityManager.createQuery(query).getResultList();
        } finally {
            closeConnection(entityManager);
        }

        return entities;
    }
}
