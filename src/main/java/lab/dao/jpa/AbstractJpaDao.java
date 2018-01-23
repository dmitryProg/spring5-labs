package lab.dao.jpa;

import lombok.Setter;
import lombok.val;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.util.function.Consumer;
import java.util.function.Function;

public class AbstractJpaDao {
    @Setter(onMethod = @__(@PersistenceUnit))
	protected EntityManagerFactory emf;

    public <T> T mapEntityManagerFactory(Function<EntityManagerFactory, T> emfMapper) {
        return emfMapper.apply(emf);
    }

    @SuppressWarnings("WeakerAccess")
    protected void withEntityManagerFactory(Consumer<EntityManagerFactory> entityManagerFactoryConsumer) {
        entityManagerFactoryConsumer.accept(emf);
    }

    protected <T> T mapEntityManager(Function<EntityManager, T> emMapper) {
        return mapEntityManagerFactory(emf -> {
            val em = emf.createEntityManager();
            T t = null;
            if (em != null) {
                t = emMapper.apply(em);
                em.close();
            }
            return t;
        });
    }

    protected void withEntityManager(Consumer<EntityManager> entityManagerConsumer) {
        withEntityManagerFactory(emf -> {
            val em = emf.createEntityManager();
            if (em != null) {
                entityManagerConsumer.accept(em);
                em.close();
            }
        });
    }

    protected <T> T mapEntityManagerUnderTransaction(Function<EntityManager, T> emMapper) {
        return mapEntityManager(em -> {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            T t = emMapper.apply(em);
            transaction.commit();
            return t;
        });
    }

    protected void withEntityManagerUnderTransaction(Consumer<EntityManager> entityManagerConsumer) {
        withEntityManager(em -> {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            entityManagerConsumer.accept(em);
            transaction.commit();
        });
    }
}