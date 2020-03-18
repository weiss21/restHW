package cst438hw2.service;
 
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.BDDMockito.given;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.ArgumentMatchers.anyString;

import cst438hw2.domain.*;
 
@SpringBootTest
public class CityServiceTest {

	@MockBean
	private WeatherService weatherService;
	
	@Autowired
	private CityService cityService;
	
	@MockBean
	private CityRepository cityRepository;
	
	@MockBean
	private CountryRepository countryRepository;

	
	@Test
	public void contextLoads() {
	}

	@BeforeEach
	public void setupEach() {
	    MockitoAnnotations.initMocks(this);
	    cityService = new CityService(cityRepository, countryRepository, weatherService);
	}
	
	@Test
	public void testCityFound() throws Exception {
		// TODO 
		City forTest = new City(0,"test", "TSA", "Florida", 300000);
		List<City> cityList = new ArrayList<City>();
		cityList.add(forTest);
		
		given(cityRepository.findByName(forTest.getName())).willReturn(cityList);
		assertThat(cityService).isNotNull();
		
		//parameters are temp, time and timezone
        TempAndTime location = new TempAndTime(21.0, 0, 0);
        given(weatherService.getTempAndTime(forTest.getName())).willReturn(location);
        
        Country country = new Country(forTest.getCountryCode(), "testCountry");
        given(countryRepository.findByCode(forTest.getCountryCode())).willReturn(country);

        
        CityInfo cityInfo = cityService.getCityInfo(forTest.getName());
        cityInfo.setTemp(21.0);
        assertThat(cityInfo).isNotNull();
        assertThat(cityInfo.getName()).isEqualTo(forTest.getName());
        assertThat(cityInfo.getCountryCode()).isEqualTo(forTest.getCountryCode());
        assertThat(cityInfo.getCountryName()).isEqualTo(country.getName());
        assertThat(cityInfo.getPopulation()).isEqualTo(forTest.getPopulation());
        assertThat(cityInfo.getDistrict()).isEqualTo(forTest.getDistrict());
        assertThat(cityInfo.getTemp()).isEqualTo(location.getTemp());
	}
	
	@Test 
	public void  testCityNotFound() {
		// TODO 
		List<City> noCity = Arrays.asList();
		String cityName = "testName";
		
		assertNull(cityService.getCityInfo(cityName));
	}
	
	@Test 
	public void  testCityMultiple() {
		// TODO 
		
		List<City> cityList = new ArrayList<City>();
        cityList.add(new City(0,"test", "TSA", "Florida", 300000));
        cityList.add(new City(2000, "test2", "TSA", "Montana", 1500));
        given(cityRepository.findByName("test")).willReturn(cityList);
        
        assertThat(cityService).isNotNull();
        
        TempAndTime location = new TempAndTime(21.0, 0, 0);
        given(weatherService.getTempAndTime("test")).willReturn(location);
        
        Country country = new Country("TSA", "testCountry");
        given(countryRepository.findByCode("TSA")).willReturn(country);
        
        CityInfo expected = new CityInfo(0,"test", "TSA", "testCountry" ,"Florida", 300000, 21.0, "1970/01/01 00:00:00");
        CityInfo testActual = cityService.getCityInfo("test");
        testActual.setTemp(21.0);
        assertEquals(expected, testActual);
		
	}

}
