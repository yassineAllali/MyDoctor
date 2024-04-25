package com.mydoctor.presentation.controller;

import com.mydoctor.application.service.CityService;
import com.mydoctor.presentation.mapper.ResponseMapper;
import com.mydoctor.presentation.request.create.CreateCityRequest;
import com.mydoctor.presentation.response.CityResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityService cityService;
    private final ResponseMapper responseMapper;

    public CityController(CityService cityService) {
        this.cityService = cityService;
        this.responseMapper = new ResponseMapper();
    }

    @PostMapping
    public ResponseEntity<CityResponse> createCity(@RequestBody CreateCityRequest createCityRequest) {
        CityResponse createdCity = responseMapper.map(cityService.create(createCityRequest));
        return new ResponseEntity<>(createdCity, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CityResponse>> getAllCities() {
        List<CityResponse> cities = cityService.getAll().stream()
                .map(responseMapper::map)
                .toList();
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityResponse> getCityById(@PathVariable("id") Long id) {
        CityResponse city = responseMapper.map(cityService.get(id));
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityResponse> updateCity(@PathVariable("id") Long id, @RequestBody CreateCityRequest city) {
        CityResponse updatedCity = responseMapper.map(cityService.update(id, city));
        return new ResponseEntity<>(updatedCity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable("id") Long id) {
        cityService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
