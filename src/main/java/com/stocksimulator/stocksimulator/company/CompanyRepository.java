package com.stocksimulator.stocksimulator.company;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findByIsActive(boolean b);

}
