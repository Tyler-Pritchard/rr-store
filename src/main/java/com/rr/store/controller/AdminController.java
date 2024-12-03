package com.rr.store.controller;

import com.rr.store.domain.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * Endpoint to reload merch.json and sync it with the database.
     * Delegates the business logic to the AdminService.
     */
    @PostMapping("/reload-merch")
    public ResponseEntity<String> reloadMerch() {
        try {
            adminService.reloadMerch();
            return ResponseEntity.ok("Merch data reloaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to reload merch data: " + e.getMessage());
        }
    }
}
