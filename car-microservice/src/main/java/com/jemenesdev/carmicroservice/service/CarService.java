package com.jemenesdev.carmicroservice.service;

import com.jemenesdev.carmicroservice.entity.Car;
import com.jemenesdev.carmicroservice.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    @Autowired
    CarRepository carRepository;

    public List<Car> getAll() {
        return carRepository.findAll();
    }

    public Car getCarById(int id) {
        return carRepository.findById(id).orElse(null);
    }

    public Car save(Car car) {
        return carRepository.save(car);
    }

    public List<Car> byUserId(int userId) {
        return carRepository.findByUserId(userId);
    }
}
