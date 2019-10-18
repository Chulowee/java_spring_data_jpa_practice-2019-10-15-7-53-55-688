package com.tw.apistackbase.controller;

import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.service.CompanyService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.util.Objects.isNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private static final String COMPANY_NOT_FOUND = "Company not found!";
    private static final String CANNOT_DELETE_NON_EXISTING_COMPANY = "Cannot delete non existing Company!";
    private static final String CANNOT_UPDATE_NON_EXISTING_COMPANY = "Cannot update non existing Company!";
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

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public Company add(@RequestBody Company company) {
        return companyService.save(company)  ;
    }

    @DeleteMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Company> deleteCompanyByCompanyID(@RequestParam(required = false , defaultValue = "") long id)
            throws NotFoundException {
        Company fetchedCompany = companyService.findById(id);
        if(!isNull(fetchedCompany)){
            companyService.deleteById(id);
            return new ResponseEntity<>(fetchedCompany, HttpStatus.OK);
        }
        throw new NotFoundException(CANNOT_DELETE_NON_EXISTING_COMPANY);
    }

    @PatchMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Company> update(@RequestParam(required = false , defaultValue = "") long id,
                                          @RequestBody Company company) throws NotFoundException {
        Company companyToUpdate = companyService.findById(id);
        if (!isNull(companyToUpdate)) {
            companyToUpdate.setName(company.getName());
            Company savedCompany = companyService.save(companyToUpdate);
            return new ResponseEntity<>(savedCompany, HttpStatus.OK);
        }
        throw new NotFoundException(CANNOT_UPDATE_NON_EXISTING_COMPANY);
    }
}
