package org.githubwuzupkev.models.requests;

public record EmployeeRequest(String firstName,
                              String lastName,
                              String homeAddress,String identityCardNumber,
                              String dateBirth,
                              String phoneNumber,
                              String email,
                              String professionalPosition,
                              double salary) {
}
