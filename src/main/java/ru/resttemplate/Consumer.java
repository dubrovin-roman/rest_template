package ru.resttemplate;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.resttemplate.dto.SensorDTO;
import ru.resttemplate.dto.WeatherDataDTO;
import ru.resttemplate.util.Util;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer {
    public static void main(String[] args) {
        String sensorName = "sixth_sensor";
        String urlForRegistration = "http://localhost:8080/sensors/registration";
        String urlForMeasurements = "http://localhost:8080/measurements/add";
        String urlGetData = "http://localhost:8080/measurements?sensorName=" + sensorName;
        SensorDTO sensorDTO = new SensorDTO(sensorName);
        HttpEntity<SensorDTO> request = new HttpEntity<>(sensorDTO);

        RestTemplate restTemplate = new RestTemplate();

        String response = restTemplate.postForObject(urlForRegistration, request, String.class);

        System.out.println("Регистрация: " + response);

        AtomicInteger first_count = new AtomicInteger();

        Util.getRandomDataForSensor(sensorName).forEach(weatherDataDTO -> {
            HttpEntity<WeatherDataDTO> requestForWeatherData = new HttpEntity<>(weatherDataDTO);
            String responseForWeatherData = restTemplate.postForObject(urlForMeasurements, requestForWeatherData, String.class);
            first_count.getAndIncrement();
            System.out.println("Data " + first_count + ": " + responseForWeatherData);
        });

        ResponseEntity<List<WeatherDataDTO>> listResponseEntity = restTemplate.exchange(urlGetData, HttpMethod.GET, null, new ParameterizedTypeReference<List<WeatherDataDTO>>() {
        });

        List<WeatherDataDTO> weatherDataDTOList = listResponseEntity.getBody();

        AtomicInteger second_count = new AtomicInteger();
        assert weatherDataDTOList != null;
        weatherDataDTOList.forEach(weatherDataDTO -> {
            System.out.println(weatherDataDTO.toString());
            second_count.getAndIncrement();
            System.out.println("Data " + second_count + ": Ok");
        });
    }
}
