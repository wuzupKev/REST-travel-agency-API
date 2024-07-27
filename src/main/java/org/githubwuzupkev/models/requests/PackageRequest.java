package org.githubwuzupkev.models.requests;

import org.githubwuzupkev.models.entities.ServiceEntity;

import java.util.List;
import java.util.Set;

public record PackageRequest(String title, Set<ServiceEntity> services)  {
}
