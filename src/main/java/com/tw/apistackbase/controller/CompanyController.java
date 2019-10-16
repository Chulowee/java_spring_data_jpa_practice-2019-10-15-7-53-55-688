package com.tw.apistackbase.controller;

import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping(produces = {"application/json"})
    public Iterable<Company> list() {
        return companyRepository.findAll();
    }

    @PostMapping(produces = {"application/json"})
    public Company add(@RequestBody Company company) {
       return companyRepository.save(company);
    }

    @DeleteMapping(value = "/{name}" , produces = {"application/json"})
    public ResponseEntity<String> delete(@PathVariable String name) {
        Company companyToDelete = companyRepository.findCompanyByName(name);
        companyRepository.deleteById(companyToDelete.getId());
        return ResponseEntity.ok("Employee/s has been deleted!");
    }

    @PutMapping(value = "/{id}" , produces = {"application/json"})
    public Company update (@PathVariable Long id , @RequestBody Company company) {
        companyRepository.findById(id).ifPresent(company1 -> company.setId(id));
        return companyRepository.save(company);
    }
}
