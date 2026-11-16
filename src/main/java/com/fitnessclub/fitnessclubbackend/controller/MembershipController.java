package com.fitnessclub.fitnessclubbackend.controller;

import com.fitnessclub.fitnessclubbackend.model.membership.Membership;
import com.fitnessclub.fitnessclubbackend.service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memberships")
public class MembershipController {

    private final MembershipService membershipService;

    @Autowired
    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    /**
     * GET /api/memberships — получить все абонементы
     */
    @GetMapping
    public ResponseEntity<List<Membership>> getAllMemberships() {
        return ResponseEntity.ok(membershipService.getAllMemberships());
    }

    /**
     * GET /api/memberships/{id} — получить абонемент по ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Membership> getMembershipById(@PathVariable Long id) {
        return membershipService.getMembershipById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/memberships — создать новый абонемент
     */
    @PostMapping
    public ResponseEntity<Membership> createMembership(@RequestBody Membership membership) {
        Membership created = membershipService.createMembership(membership);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * PUT /api/memberships/{id} — обновить абонемент
     */
    @PutMapping("/{id}")
    public ResponseEntity<Membership> updateMembership(@PathVariable Long id, @RequestBody Membership membership) {
        try {
            Membership updated = membershipService.updateMembership(id, membership);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /api/memberships/{id} — удалить абонемент
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMembership(@PathVariable Long id) {
        membershipService.deleteMembership(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/memberships/filter?visitsLeft=10 — фильтрация по остатку визитов
     */
    @GetMapping("/filter")
    public ResponseEntity<List<Membership>> getMembershipsByVisitsLeft(@RequestParam int visitsLeft) {
        return ResponseEntity.ok(membershipService.getMembershipsByVisitsLeftLessThan(visitsLeft));
    }
}