package cst438hw2.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import cst438hw2.domain.*;
import cst438hw2.service.CityService;

@Controller
public class CityController {
	
	@Autowired
	private CityService cityService;
	@Autowired
    CityRepository cityRepository;
    @Autowired
    CountryRepository countryRepository;
	
	@GetMapping("/cities/{city}")
	public String getWeather(@PathVariable("city") String cityName, Model model) {

		CityInfo city = cityService.getCityInfo(cityName);
        model.addAttribute("city", city);
		return "city_weather";
	} 
	
	@GetMapping("/cities/new")
    public String createCity( Model model) {
        City city = new City();
        model.addAttribute("city", city);
        return "city_form";
    }

    
    @PostMapping("/cities/new")
    public String processCityForm(@Valid City city, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "city_form";
        } 

        cityRepository.save(city);
        return "city_show";
    }
    

	
}