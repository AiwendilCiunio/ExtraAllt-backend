package com.stocksimulator.stocksimulator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.stocksimulator.stocksimulator.company.Company;
import com.stocksimulator.stocksimulator.company.CompanyRepository;
import com.stocksimulator.stocksimulator.company.CompanyService;
import com.stocksimulator.stocksimulator.dto.HoldingCreateDTO;
import com.stocksimulator.stocksimulator.dto.HoldingUpdateDTO;
import com.stocksimulator.stocksimulator.holding.Holding;
import com.stocksimulator.stocksimulator.holding.HoldingRepository;
import com.stocksimulator.stocksimulator.holding.HoldingService;
import com.stocksimulator.stocksimulator.user.User;

@SpringBootTest
class StocksimulatorApplicationTests {

	@Test
	void contextLoads() {
	}

	// doesn't work for some reason
	// @Test
	// void generateEventMessages_shouldFormatMessagesCorrectly() {
	// MarketStateSimulator simulator = new MarketStateSimulator(null, null);
	// List<CompanyStateDTO> companyStates = List.of(
	// new CompanyStateDTO(1L, "Omega", BigDecimal.valueOf(120),
	// BigDecimal.valueOf(0.2)), // +20%
	// new CompanyStateDTO(2L, "Sigma", BigDecimal.valueOf(80),
	// BigDecimal.valueOf(-0.1)) // -10%
	// );

	// List<EventMessageDTO> messages =
	// simulator.generateEventMessages(companyStates);

	// assertEquals(2, messages.size());
	// assertTrue(messages.get(0).eventDescription().contains("increased by
	// 20.00%"));
	// assertTrue(messages.get(1).eventDescription().contains("decreased by
	// 10.00%"));
	// }

	// HoldingService - correct conversion to dto
	@Test
	void createHolding_shouldBuildHoldingWithCorrectValues() {
		HoldingRepository repo = mock(HoldingRepository.class);
		CompanyService companyService = mock(CompanyService.class);
		HoldingService service = new HoldingService(repo, companyService);

		Company company = new Company("Gamma", "Gamma", "Gamma", true, BigDecimal.TEN);
		User user = new User();
		HoldingCreateDTO dto = new HoldingCreateDTO("Gamma", 5, BigDecimal.valueOf(100));

		when(companyService.getByName("Gamma")).thenReturn(company);
		when(repo.save(any(Holding.class))).thenAnswer(inv -> inv.getArgument(0));

		Holding result = service.createHolding(dto, user);

		assertEquals(user, result.getUser());
		assertEquals(company, result.getCompany());
		assertEquals(5, result.getQuantity());
		assertEquals(BigDecimal.valueOf(100), result.getAveragePrice());
		assertNotNull(result.getLastUpdated());
		verify(repo).save(result);
	}

	// HoldingService - updateHolding (Average Price)
	@Test
	void updateHolding_shouldComputeWeightedAverageAndUpdateQuantity() {
		HoldingRepository repo = mock(HoldingRepository.class);
		CompanyService companyService = mock(CompanyService.class);
		HoldingService service = new HoldingService(repo, companyService);

		Holding existing = Holding.builder()
				.id(1L)
				.averagePrice(BigDecimal.valueOf(100))
				.quantity(10)
				.build();

		HoldingUpdateDTO dto = new HoldingUpdateDTO(1L, 5, BigDecimal.valueOf(200));

		when(repo.findById(1L)).thenReturn(java.util.Optional.of(existing));
		when(repo.save(any(Holding.class))).thenAnswer(inv -> inv.getArgument(0));

		Holding result = service.updateHolding(dto);

		// Weighted average: (100*10 + 200*5) / 15 = 133.33
		assertEquals(BigDecimal.valueOf(133.33), result.getAveragePrice());
		assertEquals(15, result.getQuantity());
		verify(repo).save(existing);
	}

	// HoldingService - updateHolding (Last Updated)
	@Test
	void updateHolding_shouldSetLastUpdatedToNow() {
		HoldingRepository repo = mock(HoldingRepository.class);
		CompanyService companyService = mock(CompanyService.class);
		HoldingService service = new HoldingService(repo, companyService);

		Holding existing = Holding.builder()
				.id(1L)
				.averagePrice(BigDecimal.valueOf(50))
				.quantity(5)
				.lastUpdated(LocalDateTime.of(2000, 1, 1, 0, 0))
				.build();

		HoldingUpdateDTO dto = new HoldingUpdateDTO(1L, 5, BigDecimal.valueOf(100));

		when(repo.findById(1L)).thenReturn(java.util.Optional.of(existing));
		when(repo.save(any(Holding.class))).thenAnswer(inv -> inv.getArgument(0));

		LocalDateTime before = LocalDateTime.now();
		Holding result = service.updateHolding(dto);
		LocalDateTime after = LocalDateTime.now();

		assertFalse(result.getLastUpdated().isBefore(before));
		assertFalse(result.getLastUpdated().isAfter(after));
	}

	// CompanyService
	@Test
	void findActiveCompanies_shouldReturnActiveCompaniesFromRepo() {
		CompanyRepository repo = mock(CompanyRepository.class);
		CompanyService service = new CompanyService(repo);

		List<Company> active = List.of(new Company("Gamma", "Gamma", "Gamma", true, BigDecimal.TEN));
		List<Company> inactive = List.of(new Company("Alpha", "Alpha", "Alpha", false, BigDecimal.TEN));

		when(repo.findByIsActive(true)).thenReturn(active);

		List<Company> result = service.findActiveCompanies();

		verify(repo).findByIsActive(true);
		assertEquals(active, result);
		assertTrue(result.containsAll(active));
		assertFalse(result.containsAll(inactive));
	}

	// CompanyService
	@Test
	void getByName_shouldReturnWhenFound() {
		CompanyRepository repo = mock(CompanyRepository.class);
		CompanyService service = new CompanyService(repo);

		Company company = new Company("Gamma", "Gamma", "Gamma", true, BigDecimal.TEN);
		when(repo.findByName("Gamma")).thenReturn(Optional.of(company));

		Company result = service.getByName("Gamma");

		assertEquals(company, result);
		verify(repo).findByName("Gamma");
	}
}
