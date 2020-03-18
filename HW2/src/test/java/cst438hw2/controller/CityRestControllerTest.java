package cst438hw2.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import cst438hw2.domain.CityInfo;
import cst438hw2.service.CityService;
import cst438hw2.service.WeatherService;

@WebMvcTest(CityRestController.class)
public class CityRestControllerTest {

	@MockBean
	private CityService cityService;
	
    @MockBean
	private WeatherService weatherService;

	@Autowired
	private MockMvc mvc;

	// This object will be magically initialized by the initFields method below.
	private JacksonTester<CityInfo> cityJson;

	@BeforeEach
	public void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
	}
	
	@Test
	public void contextLoads() {
	}

	@Test
	public void getCityInfo() throws Exception {
		CityInfo attempt = new CityInfo();
    	attempt.setId(0);
    	attempt.setName("test");
    	attempt.setCountryCode("TSA");
    	attempt.setCountryName("testCountry");
    	attempt.setDistrict("Florida");
    	attempt.setPopulation(300000);
    	attempt.setTemp(50);
    	attempt.setTime("time");
		
	        
	    CityInfo expected = new CityInfo(0,"test" , "TSA", "testCountry", "Florida", 300000, 50, "time");

	    given(cityService.getCityInfo(attempt.getName())).willReturn(expected);

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/api/cities/" + attempt.getName()).contentType(MediaType.APPLICATION_JSON)
                        .content(cityJson.write(expected).getJson()))
                .andReturn().getResponse();
        
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                cityJson.write(expected).getJson());

	}

}
