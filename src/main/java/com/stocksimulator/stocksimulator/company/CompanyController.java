package com.stocksimulator.stocksimulator.company;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stocksimulator.stocksimulator.dto.CompanyDTO;

@RestController
@RequestMapping("api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    public List<CompanyDTO> getAllActiveCompanies() {
        return companyService.findActiveCompanies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable long id) {
        CompanyDTO company = companyService.getById(id);
        return ResponseEntity.ok(company);
    }
}
