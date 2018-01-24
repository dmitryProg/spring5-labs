package lab.service;

import java.util.List;

import lab.dao.CountryDao;
import lab.dao.jdbc.CountryJdbcDao;
import lab.model.Country;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.*;

//@Repository is more convenient declaration for such a class than general @Service
@Repository
@Transactional
@Data
public class CountryServiceImpl implements CountryService {

	@Autowired
	private CountryJdbcDao countryDao;

	public List<Country> getAllCountriesInsideTransaction(
			Propagation propagation) {
		if (REQUIRED.equals(propagation)) {
			return getAllCountriesRequired();
		} else if (Propagation.REQUIRES_NEW.equals(propagation)) {
			return getAllCountriesRequiresNew();
		} else if (Propagation.SUPPORTS.equals(propagation)) {
			return getAllCountriesSupports();
		} else if (Propagation.NEVER.equals(propagation)) {
			return getAllCountriesNever();
		} else if (Propagation.MANDATORY.equals(propagation)) {
			return getAllCountriesMandatory();
		} else if (Propagation.NOT_SUPPORTED.equals(propagation)) {
			return getAllCountriesNotSupported();
		} else {
			return getAllCountries();
		}
	}

	@Override
    @Transactional(readOnly = true)
	public List<Country> getAllCountriesRequired() {
		return countryDao.getCountryList();
	}

    @Override
    @Transactional(readOnly = true, propagation = REQUIRES_NEW)
	public List<Country> getAllCountriesRequiresNew() {
		return countryDao.getCountryList();
	}

    @Override
    @Transactional(readOnly = true, propagation = SUPPORTS)
	public List<Country> getAllCountriesSupports() {
		return countryDao.getCountryList();
	}

    @Override
    @Transactional(readOnly = true, propagation = NEVER)
	public List<Country> getAllCountriesNever() {
		return countryDao.getCountryList();
	}

    @Override
    @Transactional(readOnly = true, propagation = MANDATORY)
	public List<Country> getAllCountriesMandatory() {
		return countryDao.getCountryList();
	}

    @Override
    @Transactional(readOnly = true, propagation = NOT_SUPPORTED)
	public List<Country> getAllCountriesNotSupported() {
		return countryDao.getCountryList();
	}

    @Override
    @Transactional(readOnly = true)
	public List<Country> getAllCountries() {
		return countryDao.getCountryList();
	}

}