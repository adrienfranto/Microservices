package com.techie.microservices.inventory_service.service;

import com.techie.microservices.inventory_service.dto.InventoryDto;
import com.techie.microservices.inventory_service.mapper.InventoryMapper;
import com.techie.microservices.inventory_service.model.Inventory;
import com.techie.microservices.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public boolean isStock(String skuCode,Integer quantity){
        //Find an inventory for a given skuCode where quantity >= 0
        log.info("Find an inventory for a given skuCode="+ skuCode+ " where quantity = "+quantity);
         return inventoryRepository.existsBySkuCodeAndQuantityIsGreaterThanEqual(skuCode,quantity);
    }

    // CREATE
    public InventoryDto createInventory(InventoryDto dto) {
        Inventory inventory = InventoryMapper.toEntity(dto);
        Inventory saved = inventoryRepository.save(inventory);
        log.info("Inventory enregistrer avec success ,id =  " + inventory.getId());
        return InventoryMapper.toDto(saved);
    }

    // READ all
    public List<InventoryDto> getAllInventories() {
        return inventoryRepository.findAll()
                .stream()
                .map(InventoryMapper::toDto)
                .collect(Collectors.toList());
    }

    // READ one
    public Optional<InventoryDto> getInventory(Long id) {
        return inventoryRepository.findById(id)
                .map(InventoryMapper::toDto);
    }

    // UPDATE
    public InventoryDto updateInventory(Long id, InventoryDto dto) {

        log.info("Inventory modifier avec success ,id =  " + id);
        return inventoryRepository.findById(id)
                .map(inv -> {
                    inv.setSkuCode(dto.getSkuCode());
                    inv.setQuantity(dto.getQuantity());
                    return InventoryMapper.toDto(inventoryRepository.save(inv));
                })
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
    }

    // DELETE
    public void deleteInventory(Long id) {

        log.info("Inventory supprimer avec success ,id =  " + id);
        inventoryRepository.deleteById(id);
    }
}
