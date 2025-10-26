package com.stocksimulator.stocksimulator.holding;

import java.util.List;

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
    public List<Holding> getAllHoldings() {
        return holdingService.getAllHoldings();
    }

    // MVP: no user info
    // TODO: user info
    @PostMapping
    public Holding createHolding(@RequestBody @Valid HoldingCreateDTO dto) {
        // TODO: get actual logged in user
        User user = userService.getDemoUser().orElseThrow(() -> new RuntimeException("demo user not found"));
        return holdingService.createHolding(dto, user);
    }

    // MVP: no user info
    // TODO: user info
    @PatchMapping
    public Holding updateHolding(@RequestBody @Valid HoldingUpdateDTO dto) {
        return holdingService.updateHolding(dto);
    }
}
