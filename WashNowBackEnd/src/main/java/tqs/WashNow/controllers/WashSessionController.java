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
import tqs.WashNow.services.WashSessionService;
import tqs.WashNow.entities.WashSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.List;

/**
 * Controller for managing WashSession entities.
 * Provides endpoints for creating, retrieving, updating, and deleting WashSessions.
 * 
 * Endpoints:
 * - POST /api/WashSessions: Create a new WashSession.
 * - GET /api/WashSessions: Retrieve all WashSessions.
 * - GET /api/WashSessions/{id}: Retrieve a specific WashSession by its ID.
 * - PUT /api/WashSessions/{id}: Update a specific WashSession by its ID.
 * - DELETE /api/WashSessions/{id}: Delete a specific WashSession by its ID.
 * 
 * Dependencies:
 * - WashSessionService: Service layer for handling WashSession business logic.
 * 
 * Response Codes:
 * - 201: WashSession created successfully.
 * - 200: Request processed successfully (for example retrieving or updating WashSessions).
 * - 204: WashSession deleted successfully.
 * - 400: Invalid request data.
 * - 404: WashSession not found.
 * - 500: Internal server error.
 * 
 * @see WashSessionService
 * @see WashSession
 */
@RestController
@RequestMapping("/api/WashSessions")
@Tag(name = "WashSession", description = "WashSession API")
public class WashSessionController {

    private final WashSessionService WashSessionService;

    public WashSessionController(WashSessionService WashSessionService) {
        this.WashSessionService = WashSessionService;
    }

    @PostMapping
    @Operation(summary = "Create a new WashSession")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "WashSession created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<WashSession> createWashSession(@RequestBody WashSession WashSession) {
        WashSession createdWashSession = WashSessionService.createWashSession(WashSession);
        return new ResponseEntity<>(createdWashSession, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all WashSessions")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "WashSessions retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<WashSession>> getAllWashSessions() {
        List<WashSession> WashSessions = WashSessionService.getAllWashSessions();
        return new ResponseEntity<>(WashSessions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a WashSession by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "WashSession retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "WashSession not found")
    })
    public ResponseEntity<WashSession> getWashSessionById(@PathVariable Long id) {
        WashSession WashSession = WashSessionService.getWashSessionById(id);
        if (WashSession != null) {
            return new ResponseEntity<>(WashSession, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a WashSession by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "WashSession updated successfully"),
        @ApiResponse(responseCode = "404", description = "WashSession not found")
    })
    public ResponseEntity<WashSession> updateWashSession(@PathVariable Long id, @RequestBody WashSession WashSession) {
        WashSession updatedWashSession = WashSessionService.updateWashSessionById(id, WashSession);
        if (updatedWashSession != null) {
            return new ResponseEntity<>(updatedWashSession, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a WashSession by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "WashSession deleted successfully"),
        @ApiResponse(responseCode = "404", description = "WashSession not found")
    })
    public ResponseEntity<Void> deleteWashSession(@PathVariable Long id) {
        if (WashSessionService.getWashSessionById(id) != null) {
            WashSessionService.deleteWashSessionById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    

    
}