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
        withEntityManagerUnderTransaction(em -> em.merge(country));
    }

    @Override
    public List<Country> getAllCountries() {
        return mapEntityManagerUnderTransaction(em ->
                em.createQuery("select c from SimpleCountry c", Country.class)
                        .getResultList());
    }

    @Override
    public Country getCountryByName(String name) {
        return mapEntityManagerUnderTransaction(em ->
                em.createQuery("select c from SimpleCountry c where c.name=:name", Country.class)
                        .setParameter("name", name)
                        .getSingleResult());
    }

}
