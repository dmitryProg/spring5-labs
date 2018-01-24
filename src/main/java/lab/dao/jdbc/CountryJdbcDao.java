package lab.dao.jdbc;

import lab.dao.CountryNotFoundException;
import lab.model.Country;
import lab.model.simple.SimpleCountry;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CountryJdbcDao extends NamedParameterJdbcDaoSupport {

    public static final String[][] COUNTRY_INIT_DATA = {
            {"Australia", "AU"},
            {"Canada", "CA"},
            {"France", "FR"},
            {"Hong Kong", "HK"},
            {"Iceland", "IC"},
            {"Japan", "JP"},
            {"Nepal", "NP"},
            {"Russian Federation", "RU"},
            {"Sweden", "SE"},
            {"Switzerland", "CH"},
            {"United Kingdom", "GB"},
            {"United States", "US"}};
    private static final String LOAD_COUNTRIES_SQL = "INSERT INTO Country (name, code_name) VALUES ('%s', '%s')";
    private static final String GET_ALL_COUNTRIES_SQL = "SELECT id, name, code_name FROM Country";
    private static final String GET_COUNTRIES_BY_NAME_SQL = "SELECT id, name, code_name FROM Country WHERE name LIKE :name";
    private static final String GET_COUNTRY_BY_NAME_SQL = "SELECT id, name, code_name FROM Сountry WHERE name='%s'";
    private static final String GET_COUNTRY_BY_CODE_NAME_SQL = "SELECT id, name, code_name FROM Сountry WHERE code_name = '%s'";
    private static final String UPDATE_COUNTRY_NAME_SQL = "UPDATE Сountry SET name='%s' WHERE code_name='%s'";
    
    private static final RowMapper<Country> COUNTRY_ROW_MAPPER = (rs, i) -> new SimpleCountry(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("code_name"));

    public CountryJdbcDao(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    public List<Country> getCountryList() {
        return getJdbcTemplate().query(
                GET_ALL_COUNTRIES_SQL,
                COUNTRY_ROW_MAPPER);
    }

    public List<Country> getCountryListStartWith(String name) {
        return getNamedParameterJdbcTemplate().query(
                GET_COUNTRIES_BY_NAME_SQL,
                new MapSqlParameterSource("name", name + "%"),
                COUNTRY_ROW_MAPPER);
    }

    public void updateCountryName(String codeName, String newCountryName) {
        getJdbcTemplate().update(String.format(
                UPDATE_COUNTRY_NAME_SQL, newCountryName, codeName));
    }

    //    @PostConstruct
    public void loadCountries() {
        List<Country> countries = getJdbcTemplate().query("select * from country", COUNTRY_ROW_MAPPER);
        for (String[] countryData : COUNTRY_INIT_DATA)
            getJdbcTemplate().execute(
                    String.format(LOAD_COUNTRIES_SQL, countryData[0], countryData[1]));
    }

    public Country getCountryByCodeName(String codeName) {
        return getJdbcTemplate().query(
                String.format(GET_COUNTRY_BY_CODE_NAME_SQL, codeName),
                COUNTRY_ROW_MAPPER)
                .get(0);
    }

    public void save(Country country) {
        // TODO: 16/10/2017
    }

    public List<Country> getAllCountries() {
        return getCountryList();
    }

    public Country getCountryByName(String name) throws lab.dao.CountryNotFoundException {

        List<Country> countryList = getJdbcTemplate().query(
                String.format(GET_COUNTRY_BY_NAME_SQL, name),
                COUNTRY_ROW_MAPPER);

        if (countryList.isEmpty())
            throw new CountryNotFoundException();

        return countryList.get(0);
    }

    public void prepareDb() {
        getJdbcTemplate().update(DaoConstants.CREATE_COUNTRY_TABLE_SQL);
    }

    public void clearDb() {
        getJdbcTemplate().update(DaoConstants.DROP_COUNTRY_TABLE_SQL);
    }
}
