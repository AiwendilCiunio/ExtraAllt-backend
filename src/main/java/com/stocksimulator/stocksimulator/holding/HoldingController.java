package com.stocksimulator.stocksimulator.holding;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stocksimulator.stocksimulator.dto.HoldingCreateDTO;
import com.stocksimulator.stocksimulator.dto.HoldingUpdateDTO;
import com.stocksimulator.stocksimulator.user.User;
import com.stocksimulator.stocksimulator.user.UserService;

import jakarta.validation.Valid;

@RequestMapping("/api/holdings")
@RestController
public class HoldingController {

    private final HoldingService holdingService;
    private final UserService userService;

    public HoldingController(HoldingService holdingService, UserService userService) {
        this.holdingService = holdingService;
        this.userService = userService;
    }

    // MVP: skip login, return all holdings from DB
    // TODO: only get holdings for logged in user
    @GetMapping
    public ResponseEntity<List<Holding>> getAllHoldings() {
        List<Holding> holdings = holdingService.getAllHoldings();
        if (holdings.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(holdings);
    }

    // MVP: no user info
    // TODO: user info
    @PostMapping
    public ResponseEntity<Holding> createHolding(@RequestBody @Valid HoldingCreateDTO dto) {

        // TODO: check availableShares of Company, if available reduce qty
        // TODO: get actual logged in user
        User user = userService.getDemoUser().orElseThrow(() -> new RuntimeException("demo user not found"));
        Holding holding = holdingService.createHolding(dto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(holding);
    }

    // MVP: no user info
    // TODO: user info
    @PatchMapping
    public ResponseEntity<Holding> updateHolding(@RequestBody @Valid HoldingUpdateDTO dto) {
        Holding holding = holdingService.updateHolding(dto);
        return ResponseEntity.ok(holding);
    }
}
