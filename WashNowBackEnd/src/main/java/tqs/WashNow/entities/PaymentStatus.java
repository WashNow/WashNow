package tqs.WashNow.entities;

public enum PaymentStatus {
    AUTHORIZED,     // Payment is approved but not yet finalized
    CAPTURED,       // Funds have been transferred and payment is complete
    PAYMENT_FAILED  // simple, payment failed
}