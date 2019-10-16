package com.tw.apistackbase.repository;

import com.tw.apistackbase.core.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Long> {

    Company findCompanyByName(String name);

    Company findCompanyById(Long id);
}
