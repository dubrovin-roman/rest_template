package ru.resttemplate.util;

import ru.resttemplate.dto.SensorDTO;
import ru.resttemplate.dto.WeatherDataDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Util {

    public static List<WeatherDataDTO> getRandomDataForSensor(String sensorName) {
        List<WeatherDataDTO> list = new ArrayList<>();
        SensorDTO sensorDTO = new SensorDTO(sensorName);
        Random random = new Random();

        for (int i = 0; i < 1000; i++) {
            WeatherDataDTO weatherDataDTO = new WeatherDataDTO();
            weatherDataDTO.setValue(round(random.nextDouble(-100, 100), 1));
            weatherDataDTO.setRaining(i % 2 == 0);
            weatherDataDTO.setSensor(sensorDTO);
            list.add(weatherDataDTO);
        }

        return list;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

}
