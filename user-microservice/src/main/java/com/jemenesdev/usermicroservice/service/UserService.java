package com.jemenesdev.usermicroservice.service;

import com.jemenesdev.usermicroservice.entity.User;
import com.jemenesdev.usermicroservice.feignclients.BikeFeignClient;
import com.jemenesdev.usermicroservice.feignclients.CarFeignClient;
import com.jemenesdev.usermicroservice.model.Bike;
import com.jemenesdev.usermicroservice.model.Car;
import com.jemenesdev.usermicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CarFeignClient carFeignClient;

    @Autowired
    BikeFeignClient bikeFeignClient;

    public List<User> getAll()  {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    //todo Comprobar si el usuario existe o no y como proceder.
    public User save(User user) {
        return userRepository.save(user);
    }

    public List<Car> getCars(int userId) {
        return restTemplate.getForObject("http://car-microservice/byUser/" + userId, List.class);
    }

    public List<Bike> getBikes(int userId) {
        return restTemplate.getForObject("http://bike-microservice/byUser/" + userId, List.class);
    }

    public Car saveCar(int userId, Car car) {
        car.setUserId(userId);
        Car carNew = carFeignClient.save(car);
        return carNew;
    }

    public Bike saveBike(int userId, Bike bike) {
        bike.setUserId(userId);
        Bike bikeNew = bikeFeignClient.save(bike);
        return bikeNew;
    }

    public Map<String, Object> getUserAndVehicles(int userId) {
        Map<String, Object> result = new HashMap<>();
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            result.put("Message", "User doesn't exist.");
            return result;
        }
        result.put("User", user);
        List<Car> cars = carFeignClient.getCars(userId);
        if(cars.isEmpty())
            result.put("Cars", "This user don't have any cars.");
        else
            result.put("Cars", cars);
        List<Bike> bikes = bikeFeignClient.getBikes(userId);
        if(bikes.isEmpty())
            result.put("Bikes", "This user don't have any bikes");
        else
            result.put("Bikes", bikes);
        return result;
    }
}
