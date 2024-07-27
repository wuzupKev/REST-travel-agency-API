package org.githubwuzupkev.models.responses;

import org.githubwuzupkev.models.entities.CustomerEntity;
import org.githubwuzupkev.models.entities.EmployeeEntity;
import org.githubwuzupkev.models.entities.PackageEntity;
import org.githubwuzupkev.models.entities.PaymentMethodEntity;
import org.githubwuzupkev.models.requests.PackageRequest;

public record SaleResponse(int id, String saleDate, String skuCode, CustomerEntity Customer,
                           PaymentMethodEntity paymentMethod,
                           EmployeeEntity dniEmployee,
                           PackageEntity packageRequest, Double price) {

}
