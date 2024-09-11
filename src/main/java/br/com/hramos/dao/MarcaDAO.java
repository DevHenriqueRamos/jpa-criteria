package main.java.br.com.hramos.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.*;
import main.java.br.com.hramos.domain.Marca;

import java.util.List;

public class MarcaDAO implements IMarcaDAO {

    private void closeConnection(EntityManager entityManager, EntityManagerFactory entityManagerFactory) {
        if (entityManager != null) {
            entityManager.close();
        }
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
    @Override
    public Marca cadastrar(Marca marca) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UseCriteria");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(marca);
            entityManager.getTransaction().commit();

            return marca;
        } finally {
            closeConnection(entityManager, entityManagerFactory);
        }
    }

    @Override
    public void atualizar(Marca marca) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UseCriteria");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaUpdate<Marca> update = builder.createCriteriaUpdate(Marca.class);
            Root<Marca> root = update.from(Marca.class);
            update.set(root.get("nome"), marca.getNome());
            update.where(builder.equal(root, marca));

            entityManager.createQuery(update).executeUpdate();
            entityManager.getTransaction().commit();
        } finally {
            closeConnection(entityManager, entityManagerFactory);
        }
    }

    @Override
    public void remover(Marca marca) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UseCriteria");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try{
            entityManager.getTransaction().begin();
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaDelete<Marca> delete = builder.createCriteriaDelete(Marca.class);
            Root<Marca> root = delete.from(Marca.class);
            delete.where(builder.equal(root, marca));

            entityManager.createQuery(delete).executeUpdate();
            entityManager.getTransaction().commit();
        } finally {
            closeConnection(entityManager, entityManagerFactory);
        }
    }

    @Override
    public Marca buscarPorCodigo(String codigo) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UseCriteria");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Marca marca = null;

        try {
            entityManager.getTransaction().begin();
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Marca> query = builder.createQuery(Marca.class);
            Root<Marca> root = query.from(Marca.class);
            root.join("carros", JoinType.INNER);
            query.select(root).where(builder.equal(root.get("codigo"), codigo));

            marca = entityManager.createQuery(query).getSingleResult();
        } finally {
            closeConnection(entityManager, entityManagerFactory);
        }

        return marca;
    }

    @Override
    public List<Marca> buscarTodos() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UseCriteria");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        List<Marca> marcas = null;

        try {
            entityManager.getTransaction().begin();

            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Marca> query = builder.createQuery(Marca.class);
            Root<Marca> root = query.from(Marca.class);
            root.fetch("carros", JoinType.INNER);
            query.select(root);

            marcas = entityManager.createQuery(query).getResultList();
        } finally {
            closeConnection(entityManager, entityManagerFactory);
        }

        return marcas;
    }
}
