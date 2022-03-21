package com.jemenesdev.usermicroservice.feignclients;

import com.jemenesdev.usermicroservice.model.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "car-microservice")
public interface CarFeignClient {

    @PostMapping()
    Car save(@RequestBody Car car);

    @GetMapping("/byUser/{userId}")
    List<Car> getCars(@PathVariable("userId") int userId);
}
