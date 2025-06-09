package tqs.WashNow.controllers;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tqs.WashNow.services.CarwashStationService;
import tqs.WashNow.entities.CarwashStation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.List;

/**
 * REST Controller class for managing CarwashStation resources.
 * Provides endpoints for creating, retrieving, updating, and deleting CarwashStations.
 * 
 * Endpoints:
 * - POST /api/CarwashStations: Create a new CarwashStation.
 * - GET /api/CarwashStations: Retrieve all CarwashStations.
 * - GET /api/CarwashStations/{id}: Retrieve a CarwashStation by its ID.
 * - PUT /api/CarwashStations/{id}: Update a CarwashStation by its ID.
 * - DELETE /api/CarwashStations/{id}: Delete a CarwashStation by its ID.
 * 
 * Dependencies:
 * - CarwashStationService: Service layer for handling business logic related to CarwashStations.
 * 
 * HTTP Status Codes:
 * - 201: CarWashStation created successfully.
 * - 200: Request processed successfully (ex: retrieving CarWashStations).
 * - 204: CarWashStation deleted successfully.
 * - 400: Invalid request data.
 * - 404: CarWashStation not found.
 * - 500: Internal server error.
 * 
 * @see CarwashStationService
 * @see CarwashStation
 */
@RestController
@RequestMapping("/api/CarwashStations")
@Tag(name = "CarwashStation", description = "CarwashStation API")
public class CarwashStationController {

    private final CarwashStationService CarwashStationService;

    public CarwashStationController(CarwashStationService CarwashStationService) {
        this.CarwashStationService = CarwashStationService;
    }

    @PostMapping
    @Operation(summary = "Create a new CarwashStation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "CarwashStation created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<CarwashStation> createCarwashStation(@RequestBody CarwashStation CarwashStation) {
        CarwashStation createdCarwashStation = CarwashStationService.createCarwashStation(CarwashStation);
        return new ResponseEntity<>(createdCarwashStation, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all CarwashStations")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "CarwashStations retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<CarwashStation>> getAllCarwashStations() {
        List<CarwashStation> CarwashStations = CarwashStationService.getAllCarwashStations();
        return new ResponseEntity<>(CarwashStations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a CarwashStation by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "CarwashStation retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "CarwashStation not found")
    })
    public ResponseEntity<CarwashStation> getCarwashStationById(@PathVariable Long id) {
        CarwashStation CarwashStation = CarwashStationService.getCarwashStationById(id);
        if (CarwashStation != null) {
            return new ResponseEntity<>(CarwashStation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a CarwashStation by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "CarwashStation updated successfully"),
        @ApiResponse(responseCode = "404", description = "CarwashStation not found")
    })
    public ResponseEntity<CarwashStation> updateCarwashStation(@PathVariable Long id, @RequestBody CarwashStation CarwashStation) {
        CarwashStation updatedCarwashStation = CarwashStationService.updateCarwashStationById(id, CarwashStation);
        if (updatedCarwashStation != null) {
            return new ResponseEntity<>(updatedCarwashStation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a CarwashStation by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "CarwashStation deleted successfully"),
        @ApiResponse(responseCode = "404", description = "CarwashStation not found")
    })
    public ResponseEntity<Void> deleteCarwashStation(@PathVariable Long id) {
        if (CarwashStationService.getCarwashStationById(id) != null) {
            CarwashStationService.deleteCarwashStationById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    

    
}