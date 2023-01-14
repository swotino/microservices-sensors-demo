package it.developer.temperatureservice.controller;

import it.developer.temperatureservice.dto.DataRequest;
import it.developer.temperatureservice.dto.DataResponse;
import it.developer.temperatureservice.service.TemperatureService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/temperature")
@AllArgsConstructor
public class APIController {

    private final TemperatureService temperatureService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void postData(@RequestBody DataRequest data) {

        temperatureService.postData(data);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<DataResponse> getData(@PathVariable("id") String sensorId, @RequestParam("from") Long from, @RequestParam("to") Long to) {

        return temperatureService.getData(sensorId, from, to);
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<DataResponse> getAllData() {

        return temperatureService.getAllData();
    }
}
