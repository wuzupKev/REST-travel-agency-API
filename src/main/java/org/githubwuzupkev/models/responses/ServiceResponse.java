package org.githubwuzupkev.models.responses;

public record ServiceResponse(int id,String name,String skuCode,String description,String destination,
                              String productDate,Double price,String serviceType) {
}
