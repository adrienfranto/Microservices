package com.techie.microservices.inventory_service.controller;

import com.techie.microservices.inventory_service.dto.InventoryDto;
import com.techie.microservices.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@RequestParam String skuCode,@RequestParam Integer quantity){
        return inventoryService.isStock(skuCode,quantity);
    }

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryDto create(@RequestBody InventoryDto dto) {
        return inventoryService.createInventory(dto);
    }

    // READ all
    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryDto> getAll() {
        return inventoryService.getAllInventories();
    }

    // READ one
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryDto getOne(@PathVariable Long id) {
        return inventoryService.getInventory(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
    }

    // UPDATE
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryDto update(@PathVariable Long id, @RequestBody InventoryDto dto) {
        return inventoryService.updateInventory(id, dto);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
    }

}
