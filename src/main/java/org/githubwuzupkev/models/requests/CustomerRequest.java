package org.githubwuzupkev.models.requests;


public record CustomerRequest(String firstName,
                              String lastName,
                              String homeAddress,String identityCardNumber,
                              String dateBirth,
                              String phoneNumber,
                              String email) {

}
