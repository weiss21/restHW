package cst438hw2.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
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
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime date = LocalDateTime.ofEpochSecond((weather.getTime() + weather.getTimeZone()), 0, ZoneOffset.UTC); 
			CityInfo iCity = new CityInfo(city.getId(), city.getName(), city.getCountryCode(), country.getName(), city.getDistrict(), city.getPopulation(),
							fTemp, dtf.format(date));
			return iCity;
		}
		
		return null;
		//return null; 
	}
	
	public CityService(CityRepository cityRepository, CountryRepository countryRepository, WeatherService weatherService) {
	    this.cityRepository = cityRepository;
	    this.countryRepository = countryRepository;
	    this.weatherService = weatherService;
	}
	
}
