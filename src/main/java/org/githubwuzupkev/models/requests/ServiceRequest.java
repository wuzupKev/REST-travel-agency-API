package org.githubwuzupkev.models.requests;

import org.githubwuzupkev.models.enums.ServiceEnum;

public record ServiceRequest(String name, String description, String destination,
                             String productDate, Double price, ServiceEnum serviceType) {
}
