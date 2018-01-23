package db;

import lab.dao.jdbc.CountryJdbcDao;
import lab.model.Country;
import lab.model.simple.SimpleCountry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:jdbc.xml")
class JdbcTest {

    @Autowired
    private CountryJdbcDao countryJdbcDao;

    private List<Country> expectedCountryList = new ArrayList<>();
    private List<Country> expectedCountryListStartsWithA = new ArrayList<>();
    private Country countryWithChangedName = new SimpleCountry(
            8L,
            "Russia",
            "RU");

    @BeforeEach
    void setUp() {
        countryJdbcDao.prepareDb();
        initExpectedCountryLists();
        countryJdbcDao.loadCountries();
    }

    @AfterEach
    void tearDown() {
        countryJdbcDao.clearDb();
    }

    @Disabled
    @Test
    @DirtiesContext
    void testCountryList() {
        List<Country> countryList = countryJdbcDao.getCountryList();
        assertNotNull(countryList);
        assertEquals(expectedCountryList.size(), countryList.size());
        for (int i = 0; i < expectedCountryList.size(); i++)
            assertEquals(countryList.get(i), expectedCountryList.get(i));
    }

    @Disabled
    @Test
    @DirtiesContext
    void testCountryListStartsWithA() {
        List<Country> countryList = countryJdbcDao.getCountryListStartWith("A");
        assertNotNull(countryList);
        assertEquals(expectedCountryListStartsWithA.size(), countryList.size());
        for (int i = 0; i < expectedCountryListStartsWithA.size(); i++)
            assertEquals(expectedCountryListStartsWithA.get(i), countryList.get(i));
    }

    @Disabled
    @Test
    @DirtiesContext
    void testCountryChange() {
        countryJdbcDao.updateCountryName("RU", "Russia");
        assertEquals(countryWithChangedName, countryJdbcDao.getCountryByCodeName("RU"));
    }

    private void initExpectedCountryLists() {
        for (int i = 0; i < CountryJdbcDao.COUNTRY_INIT_DATA.length;) {
            String[] countryInitData = CountryJdbcDao.COUNTRY_INIT_DATA[i++];
            String name = countryInitData[0];
            String codeName = countryInitData[1];
            Country country = new SimpleCountry((long) i, name, codeName);
            expectedCountryList.add(country);
            if (name.startsWith("A"))
                expectedCountryListStartsWithA.add(country);
        }
    }
}