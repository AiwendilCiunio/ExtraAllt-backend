package com.stocksimulator.stocksimulator.company;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stocksimulator.stocksimulator.dto.CompanyDTO;

@RestController
@RequestMapping("api/companies")
@CrossOrigin(origins = "${app.cors.allowed-origins}")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public List<CompanyDTO> getAllActiveCompanies() {
        return companyService.findActiveCompanies().stream().map(CompanyDTO::fromEntity).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable long id) {
        Company company = companyService.getById(id);
        return ResponseEntity.ok(CompanyDTO.fromEntity(company));
    }
}
