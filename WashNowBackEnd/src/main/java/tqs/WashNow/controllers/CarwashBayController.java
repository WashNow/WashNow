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
import tqs.WashNow.services.CarwashBayService;
import tqs.WashNow.entities.CarwashBay;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.List;

/**
 * REST controller which provides endpoints responsible for managing car wash bay operations.
 * This controller allows clients to create, retrieve, update, and delete car wash bay information.
 *
 * Endpoints:
 * - POST /api/CarwashBays: Create a new CarwashBay.
 * - GET /api/CarwashBays: Retrieve all CarwashBays.
 * - GET /api/CarwashBays/{id}: Retrieve a specific CarwashBay by its ID.
 * - PUT /api/CarwashBays/{id}: Update a specific CarwashBay by its ID.
 * - DELETE /api/CarwashBays/{id}: Delete a specific CarwashBay by its ID.
 * 
 * Response Codes:
 * - 201: CarwashBay created successfully.
 * - 200: Request processed successfully (for example retrieving or updating CarwashBays).
 * - 204: CarwashBay deleted successfully.
 * - 400: Invalid request data.
 * - 404: CarwashBay not found.
 * - 500: Internal server error.
 * 
 * Dependencies:
 * - CarwashBayService: Service layer for handling CarwashBay business logic.
 * 
 * @see CarwashBayService
 * @see CarwashBay
 */
@RestController
@RequestMapping("/api/CarwashBays")
@Tag(name = "CarwashBay", description = "CarwashBay API")
public class CarwashBayController {

    private final CarwashBayService CarwashBayService;

    public CarwashBayController(CarwashBayService CarwashBayService) {
        this.CarwashBayService = CarwashBayService;
    }

    @PostMapping
    @Operation(summary = "Create a new CarwashBay")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "CarwashBay created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<CarwashBay> createCarwashBay(@RequestBody CarwashBay CarwashBay) {
        CarwashBay createdCarwashBay = CarwashBayService.createCarwashBay(CarwashBay);
        return new ResponseEntity<>(createdCarwashBay, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all CarwashBays")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "CarwashBays retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<CarwashBay>> getAllCarwashBays() {
        List<CarwashBay> CarwashBays = CarwashBayService.getAllCarwashBays();
        return new ResponseEntity<>(CarwashBays, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a CarwashBay by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "CarwashBay retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "CarwashBay not found")
    })
    public ResponseEntity<CarwashBay> getCarwashBayById(@PathVariable Long id) {
        CarwashBay CarwashBay = CarwashBayService.getCarwashBayById(id);
        if (CarwashBay != null) {
            return new ResponseEntity<>(CarwashBay, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a CarwashBay by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "CarwashBay updated successfully"),
        @ApiResponse(responseCode = "404", description = "CarwashBay not found")
    })
    public ResponseEntity<CarwashBay> updateCarwashBay(@PathVariable Long id, @RequestBody CarwashBay CarwashBay) {
        CarwashBay updatedCarwashBay = CarwashBayService.updateCarwashBayById(id, CarwashBay);
        if (updatedCarwashBay != null) {
            return new ResponseEntity<>(updatedCarwashBay, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a CarwashBay by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "CarwashBay deleted successfully"),
        @ApiResponse(responseCode = "404", description = "CarwashBay not found")
    })
    public ResponseEntity<Void> deleteCarwashBay(@PathVariable Long id) {
        if (CarwashBayService.getCarwashBayById(id) != null) {
            CarwashBayService.deleteCarwashBayById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    

    
}