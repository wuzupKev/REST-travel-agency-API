package org.githubwuzupkev.models.requests;

import org.githubwuzupkev.models.auth.UserEntity;

import java.util.List;

public record EmployeeRequest(String firstName,
                              String lastName,
                              String homeAddress, String identityCardNumber,
                              String dateBirth,
                              String phoneNumber,
                              String professionalPosition,
                              double salary, String email, String password, List<String> roles) {
}
