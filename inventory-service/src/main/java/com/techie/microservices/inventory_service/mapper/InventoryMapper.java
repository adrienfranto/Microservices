package com.techie.microservices.inventory_service.mapper;

import com.techie.microservices.inventory_service.dto.InventoryDto;
import com.techie.microservices.inventory_service.model.Inventory;

public class InventoryMapper {

    public static InventoryDto toDto(Inventory inventory) {
        return new InventoryDto(
                inventory.getId(),
                inventory.getSkuCode(),
                inventory.getQuantity()
        );
    }

    public static Inventory toEntity(InventoryDto dto) {
        Inventory inventory = new Inventory();
        inventory.setId(dto.getId());
        inventory.setSkuCode(dto.getSkuCode());
        inventory.setQuantity(dto.getQuantity());
        return inventory;
    }
}
