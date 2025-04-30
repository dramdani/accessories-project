package com.example.controller;

import com.example.model.Accessories;
import com.example.service.AccessoriesService;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class AccessoriesController {

    @Inject
    private AccessoriesService service;

    private String code;
    private String category;
    private String supplier;

    public List<Accessories> getAccessories() {
        return service.search(code, category, supplier);
    }

    public List<String> getCategories() {
        return service.findAllCategories();
    }

    public List<String> getSuppliers() {
        return service.findAllSuppliers();
    }

    // Getters and setters for code, category, supplier
}
