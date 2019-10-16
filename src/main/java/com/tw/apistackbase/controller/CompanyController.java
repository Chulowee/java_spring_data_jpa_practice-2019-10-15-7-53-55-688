package com.tw.apistackbase.controller;

import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;
import java.util.Optional;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyRepository companyRepository;


    @GetMapping(produces = {"application/json"})
    @ResponseStatus(code = HttpStatus.OK)
    public Iterable<Company> list() {
        return companyRepository.findAll();
    }

    @GetMapping(value = "/{name}", produces = {"application/json"})
    @ResponseStatus(code = HttpStatus.OK)
    public Company getCompanyByCompanyName(@PathVariable String name) {
        return companyRepository.findCompanyByName(name);
    }

    @PostMapping(produces = {"application/json"})
    @ResponseStatus(code = HttpStatus.CREATED)
    public Company add(@RequestBody Company company) {
        return companyRepository.save(company)  ;
    }

    @DeleteMapping(value = "/{name}", produces = {"application/json"})
    public ResponseEntity<String> delete(@PathVariable String name) {
        Company companyToDelete = companyRepository.findCompanyByName(name);
        companyRepository.deleteById(companyToDelete.getId());
        return ResponseEntity.ok("Employee/s has been deleted!");
    }

    @PatchMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<Company> update(@PathVariable Long id, @RequestBody Company company) {
        Optional<Company> companyToUpdate = companyRepository.findById(id);
        if (companyToUpdate.isPresent()) {
            Company modifyCompany = companyToUpdate.get();
            modifyCompany.setName(company.getName());
            Company savedCompany = companyRepository.save(modifyCompany);
            return new ResponseEntity<>(savedCompany, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
