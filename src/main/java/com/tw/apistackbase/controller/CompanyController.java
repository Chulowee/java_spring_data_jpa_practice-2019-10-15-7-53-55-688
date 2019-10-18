package com.tw.apistackbase.controller;

import com.tw.apistackbase.Handler.ControllerExceptionHandler;
import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.service.CompanyService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static java.util.Objects.isNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    public static final String COMPANY_NOT_FOUND = "Company not found!";
    @Autowired
    private CompanyService companyService;

    @GetMapping(value= "/all" , produces = {"application/json"})
    @ResponseStatus(code = HttpStatus.OK)
    public Iterable<Company> list(
            @RequestParam(required = false , defaultValue = "0")  Integer page ,
            @RequestParam(required = false , defaultValue = "5")Integer size ) {
        return companyService.findAll(PageRequest.of(page,size, Sort.by("name").ascending()));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Company> getCompanyByCompanyName(@RequestParam(required = false , defaultValue = "") String name)
            throws NotFoundException {
        Company fetchedCompany = companyService.findByNameContaining(name);
        if(!isNull(fetchedCompany)){
            return new ResponseEntity<>(fetchedCompany, HttpStatus.OK);
        }
        throw new NotFoundException(COMPANY_NOT_FOUND);
    }

    @PostMapping(produces = {"application/json"})
    @ResponseStatus(code = HttpStatus.CREATED)
    public Company add(@RequestBody Company company) {
        return companyService.save(company)  ;
    }

    @DeleteMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<Optional<Company>> deleteCompanyByCompanyID (@PathVariable Long id) throws NotFoundException {
        Optional<Company> fetchedCompany = companyService.findById(id);
        if(fetchedCompany.isPresent()){
            companyService.deleteById(id);
            return new ResponseEntity<>(fetchedCompany, HttpStatus.OK);
        }
        throw new NotFoundException("Cannot delete non existing Company!");
    }

    @PatchMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<Company> update(@PathVariable Long id, @RequestBody Company company) throws NotFoundException {
        Optional<Company> companyToUpdate = companyService.findById(id);
        if (companyToUpdate.isPresent()) {
            Company modifyCompany = companyToUpdate.get();
            modifyCompany.setName(company.getName());
            Company savedCompany = companyService.save(modifyCompany);
            return new ResponseEntity<>(savedCompany, HttpStatus.OK);
        }
        throw new NotFoundException("Cannot update non existing Company!");
    }
}
