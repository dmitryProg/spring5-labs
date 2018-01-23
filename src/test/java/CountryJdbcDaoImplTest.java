import lab.dao.CountryDao;
import lab.model.Country;
import lab.model.simple.SimpleCountry;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Illustrates basic use of Hibernate as a JPA provider.
 */
@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:orm.xml")
@NoArgsConstructor
class CountryJdbcDaoImplTest {

	private Country exampleCountry =
			new SimpleCountry(1L, "Australia", "AU");

	@Autowired
	private CountryDao countryJpaDao;

	@BeforeEach
	void setUp() {
		countryJpaDao.save(exampleCountry);
	}

	@Test
	void testSaveCountry() {

		List<Country> countryList = countryJpaDao.getAllCountries();
		assertEquals(1, countryList.size());
		assertEquals(exampleCountry, countryList.get(0));
	}

	@Test
	void testGetAllCountries() {

		countryJpaDao.save(new SimpleCountry(1L, "Canada", "CA"));

		List<Country> countryList = countryJpaDao.getAllCountries();
		assertEquals(1, countryList.size());
	}

	@Test
	@SneakyThrows
	void testGetCountryByName() {
		assertThat(countryJpaDao.getCountryByName("Australia"), is(exampleCountry));
	}

}
