package main.java.br.com.hramos.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.*;
import main.java.br.com.hramos.domain.Carro;

import java.util.List;

public class CarroDAO implements ICarroDAO{

    private void closeConnection(EntityManager entityManager, EntityManagerFactory entityManagerFactory) {
        if (entityManager != null) {
            entityManager.close();
        }
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    @Override
    public Carro cadastrar(Carro carro) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UseCriteria");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(carro);
            entityManager.getTransaction().commit();

            return carro;
        } finally {
            closeConnection(entityManager, entityManagerFactory);
        }
    }

    @Override
    public void remover(Carro carro) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UseCriteria");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try{
            entityManager.getTransaction().begin();
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaDelete<Carro> delete = builder.createCriteriaDelete(Carro.class);
            Root<Carro> root = delete.from(Carro.class);
            delete.where(builder.equal(root, carro));

            entityManager.createQuery(delete).executeUpdate();
            entityManager.getTransaction().commit();
        } finally {
            closeConnection(entityManager, entityManagerFactory);
        }
    }

    public List<Carro> buscarTodos() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UseCriteria");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        List<Carro> carros = null;

        try {
            entityManager.getTransaction().begin();

            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Carro> query = builder.createQuery(Carro.class);
            Root<Carro> root = query.from(Carro.class);
            query.select(root);

            carros = entityManager.createQuery(query).getResultList();
        } finally {
            closeConnection(entityManager, entityManagerFactory);
        }

        return carros;
    }
}
