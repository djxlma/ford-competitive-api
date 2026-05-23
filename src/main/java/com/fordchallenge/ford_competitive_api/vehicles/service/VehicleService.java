package com.fordchallenge.ford_competitive_api.vehicles.service;

import com.fordchallenge.ford_competitive_api.vehicles.dto.VehicleResponse;
import com.fordchallenge.ford_competitive_api.vehicles.dto.VehicleSearchRequest;
import com.fordchallenge.ford_competitive_api.vehicles.entity.Vehicle;
import com.fordchallenge.ford_competitive_api.vehicles.entity.VehicleSpec;
import com.fordchallenge.ford_competitive_api.vehicles.repository.VehicleRepository;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public VehicleResponse searchVehicle(VehicleSearchRequest request) {

        Vehicle vehicle = vehicleRepository
                .findByMarcaIgnoreCaseAndModeloIgnoreCaseAndAnoAndVersaoIgnoreCase(
                        request.marca(),
                        request.modelo(),
                        request.ano(),
                        request.versao()
                )
                .orElseGet(() -> createMockVehicle(request));

        return mapToResponse(vehicle);
    }

    private Vehicle createMockVehicle(VehicleSearchRequest request) {

        Vehicle vehicle = Vehicle.builder()
                .marca(request.marca())
                .modelo(request.modelo())
                .ano(request.ano())
                .versao(request.versao())
                .build();

        VehicleSpec spec = VehicleSpec.builder()
                .potencia("180cv")
                .torque("27kgfm")
                .combustivel("Flex")
                .cambio("Automático")
                .consumo("11km/l")
                .fonteUrl("https://mock-api.ford-challenge.com")
                .vehicle(vehicle)
                .build();

        vehicle.setSpecs(spec);

        return vehicleRepository.save(vehicle);
    }

    private VehicleResponse mapToResponse(Vehicle vehicle) {

        return new VehicleResponse(
                vehicle.getId(),
                vehicle.getMarca(),
                vehicle.getModelo(),
                vehicle.getAno(),
                vehicle.getVersao(),
                vehicle.getSpecs().getPotencia(),
                vehicle.getSpecs().getTorque(),
                vehicle.getSpecs().getCombustivel(),
                vehicle.getSpecs().getCambio(),
                vehicle.getSpecs().getConsumo()
        );
    }
}