package org.githubwuzupkev.models.responses;

public record CustomerResponse(int id,String firstName,
                               String lastName,
                               String homeAddress,String identityCardNumber,
                               String dateBirth,
                               String phoneNumber,
                               String email) {
}
