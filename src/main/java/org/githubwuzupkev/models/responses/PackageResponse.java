package org.githubwuzupkev.models.responses;

import org.githubwuzupkev.models.entities.ServiceEntity;

import java.util.List;
import java.util.Set;

public record PackageResponse(int id, String title, String skuCode, List<ServiceEntity> services, Double price)  {
}
