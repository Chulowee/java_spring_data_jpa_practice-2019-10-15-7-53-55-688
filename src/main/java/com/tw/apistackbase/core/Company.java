package com.tw.apistackbase.core;

import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne( cascade = CascadeType.ALL)
    private CompanyProfile profile;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company() {
    }

    public Company(String name) {
        this.name = name;
    }

    public CompanyProfile getProfile() {
        return profile;
    }

    public void setProfile(CompanyProfile profile) {
        this.profile = profile;
    }
}
