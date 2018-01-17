package lab.dao.jpa;

import lab.dao.CountryDao;
import lab.model.Country;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

@Repository
public class CountryJpaDaoImpl extends AbstractJpaDao implements CountryDao {

	@Override
	public void save(Country country) {
		EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.persist(country);

        transaction.commit();
        if (em != null) {
			em.close();
		}
	}

	@Override
	public List<Country> getAllCountries() {
		List<Country> countryList = null;
		EntityManager em = emf.createEntityManager();
		if (em != null) {
			countryList = em.createQuery("from Country", Country.class).getResultList();
			em.close();
		}
		return countryList;
	}

	@Override
	public Country getCountryByName(String name) {
		EntityManager em = emf.createEntityManager();
		Country country = null;
		if (em != null) {
			country = em.createQuery("SELECT c FROM Country c WHERE c.name LIKE :name",
					Country.class).setParameter("name", name).getSingleResult();
			em.close();
		}
		return country;
	}

}
