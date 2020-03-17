package cst438hw2.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cst438hw2.domain.*;

@Service
public class CityService {
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	private WeatherService weatherService;
	
	public CityInfo getCityInfo(String cityName) {
		
		// TODO your code goes here
		List<City> getCity = cityRepository.findByName(cityName);

		
		if(!getCity.isEmpty()) {
			City city = getCity.get(0);
			Country country = countryRepository.findByCode(city.getCountryCode());
			TempAndTime weather = weatherService.getTempAndTime(cityName);
			Double fTemp = 32 + (weather.getTemp() - 273.15) * (9.0/5.0);
			Long date = weather.getTime();
			CityInfo iCity = new CityInfo(city.getId(), city.getName(), city.getCountryCode(), country.getName(), city.getDistrict(), city.getPopulation(),
							fTemp, Long.toString(date));
			return iCity;
		}
		
		return null;
		//return null; 
	}
	
}
