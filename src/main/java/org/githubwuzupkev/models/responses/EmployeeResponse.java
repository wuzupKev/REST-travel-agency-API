package org.githubwuzupkev.models.responses;

import java.util.List;

public record EmployeeResponse(int id,
                               String firstName,
                               String lastName,
                               String homeAddress, String identityCardNumber,
                               String dateBirth,
                               String phoneNumber,
                               String professionalPosition,
                               CredentialResponse crendentials) {
}
