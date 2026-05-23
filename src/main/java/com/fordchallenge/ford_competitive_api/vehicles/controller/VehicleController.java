package com.fordchallenge.ford_competitive_api.vehicles.controller;

import com.fordchallenge.ford_competitive_api.vehicles.dto.VehicleResponse;
import com.fordchallenge.ford_competitive_api.vehicles.dto.VehicleSearchRequest;
import com.fordchallenge.ford_competitive_api.vehicles.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/search")
    public VehicleResponse searchVehicle(@RequestBody @Valid VehicleSearchRequest request) {
        return vehicleService.searchVehicle(request);
    }
}