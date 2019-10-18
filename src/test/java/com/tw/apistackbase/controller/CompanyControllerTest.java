package com.tw.apistackbase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.core.CompanyProfile;
import com.tw.apistackbase.core.Employee;
import com.tw.apistackbase.repository.CompanyRepository;
import com.tw.apistackbase.service.CompanyService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.declaresMethod;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CompanyController.class)
@ActiveProfiles(profiles = "test")
class CompanyControllerTest {

    @Autowired
    private CompanyController companyController;

    @MockBean
    private CompanyRepository companyRepository;

    @MockBean
    private CompanyService companyService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void get_all() throws Exception {
        //given
        when(companyService.findAll(new PageRequest(0,5))).thenReturn(Collections.singleton(new Company()));
        //when
        ResultActions result = mvc.perform(get("/companies/all"));
        //then
        result.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void get_company() throws Exception {
        //given
        Company company = new Company();
        company.setName("Chloe");
        company.setId(1L);

        when(companyService.findByNameContaining("Chloe")).thenReturn(Optional.of(company));
        //when
        ResultActions result = mvc.perform(get("/companies?name=Chloe"));
        //then
        result.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void post_company() throws Exception {
        //when
        ResultActions result = mvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Company())));
        //then
        result.andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void delete_company() throws Exception {
        //given
        Company company = new Company();
        company.setName("Chloe");
        company.setId(1L);

        when(companyService.findById(1L)).thenReturn(Optional.of(company));
        //when
        ResultActions result = mvc.perform(delete("/companies/1"));
        //then
        result.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void update_Company() throws Exception {
        //given
        Company company = new Company();
        when(companyService.findById(1L)).thenReturn(Optional.of(company));
        //when
        ResultActions result = mvc.perform(patch("/companies/1", company)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(company)));
        //then
        result.andExpect(status().isOk())
                .andDo(print());
    }
}