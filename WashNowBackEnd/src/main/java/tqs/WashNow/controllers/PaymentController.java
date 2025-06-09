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
import tqs.WashNow.services.PaymentService;
import tqs.WashNow.entities.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.List;

/**
 * REST controller that provides endpoints for managing payments.
 * It includes operations for creating, retrieving, updating, and deleting payments.
 * 
 * Endpoints:
 * - POST /api/Payments: Create a new payment.
 * - GET /api/Payments: Retrieve all payments.
 * - GET /api/Payments/{id}: Retrieve a payment by its ID.
 * - PUT /api/Payments/{id}: Update a payment by its ID.
 * - DELETE /api/Payments/{id}: Delete a payment by its ID.
 * 
 * Dependencies:
 * - PaymentService: A service layer dependency for handling payment-related operations.
 * 
 * HTTP Status Codes:
 * - 201: Payment created successfully.
 * - 200: Request processed successfully (ex: retrieving payments).
 * - 204: Payment deleted successfully.
 * - 400: Invalid request data.
 * - 404: Payment not found.
 * - 500: Internal server error.
 * 
 * @see PaymentService
 * @see Payment
 */
@RestController
@RequestMapping("/api/Payments")
@Tag(name = "Payment", description = "Payment API")
public class PaymentController {

    private final PaymentService PaymentService;

    public PaymentController(PaymentService PaymentService) {
        this.PaymentService = PaymentService;
    }

    @PostMapping
    @Operation(summary = "Create a new Payment")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Payment created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<Payment> createPayment(@RequestBody Payment Payment) {
        Payment createdPayment = PaymentService.createPayment(Payment);
        return new ResponseEntity<>(createdPayment, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all Payments")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Payments retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> Payments = PaymentService.getAllPayments();
        return new ResponseEntity<>(Payments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Payment by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Payment retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Payment Payment = PaymentService.getPaymentById(id);
        if (Payment != null) {
            return new ResponseEntity<>(Payment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Payment by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Payment updated successfully"),
        @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    public ResponseEntity<Payment> updatePayment(@PathVariable Long id, @RequestBody Payment Payment) {
        Payment updatedPayment = PaymentService.updatePaymentById(id, Payment);
        if (updatedPayment != null) {
            return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Payment by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Payment deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        if (PaymentService.getPaymentById(id) != null) {
            PaymentService.deletePaymentById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    

    
}