package com.rr.store.controller;

import com.rr.store.util.MerchLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final MerchLoader merchLoader;

    public AdminController(MerchLoader merchLoader) {
        this.merchLoader = merchLoader;
    }

    /**
     * Endpoint to reload merch.json and sync it with the database
     */
    @PostMapping("/reload-merch")
    public ResponseEntity<String> reloadMerch() {
        try {
            merchLoader.loadMerchData();
            return ResponseEntity.ok("Merch data reloaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to reload merch data: " + e.getMessage());
        }
    }
}
