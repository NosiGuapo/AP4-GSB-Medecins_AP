package io.github.nosiguapo.ap4medecins.controllers;

import io.github.nosiguapo.ap4medecins.entities.Departement;
import io.github.nosiguapo.ap4medecins.services.DepartementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gsb/departement")
public class DepartementController {
    private final DepartementService departementService;

    @Autowired
    public DepartementController(DepartementService departementService) {
        this.departementService = departementService;
    }

    @RequestMapping()
    public List<Departement> getAll(){
        return departementService.getAllDepartements();
    }
}
