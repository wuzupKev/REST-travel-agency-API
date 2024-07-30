package org.githubwuzupkev.models.requests;

public record SaleRequest(String dniCustomer,
                          String paymentMethod,
                          String dniEmployee,
                          String skucodePackage) {
}
