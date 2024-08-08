package org.githubwuzupkev.controllers;

import lombok.RequiredArgsConstructor;
import org.githubwuzupkev.exceptions.IdNotFoundException;
import org.githubwuzupkev.models.requests.EmployeeRequest;
import org.githubwuzupkev.models.responses.EmployeeResponse;
import org.githubwuzupkev.services.EmployeeInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeInfoService employeeInfoService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<EmployeeResponse>> getAllPacks() {
        return ResponseEntity.ok(this.employeeInfoService.getAllEmployeeInfo());
    }

    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmployeeResponse> getServiceById(@PathVariable int id) throws IdNotFoundException {
        return ResponseEntity.ok(employeeInfoService.getEmployeeById(id));
    }

    @PutMapping("/update-employee/{id}")
    public ResponseEntity<EmployeeResponse> updateService(@PathVariable int id, @RequestBody EmployeeRequest employeeRequest) throws IdNotFoundException {
        return ResponseEntity.ok(this.employeeInfoService.updateEmployeeById(id, employeeRequest));
    }
}
