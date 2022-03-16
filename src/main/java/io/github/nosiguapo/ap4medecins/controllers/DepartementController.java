package io.github.nosiguapo.ap4medecins.controllers;

import io.github.nosiguapo.ap4medecins.entities.Departement;
import io.github.nosiguapo.ap4medecins.entities.Medecin;
import io.github.nosiguapo.ap4medecins.services.DepartementService;
import io.github.nosiguapo.ap4medecins.services.MedecinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gsb/departements")
public class DepartementController {
    private final DepartementService departementService;
    private final MedecinService medecinService;

    @Autowired
    public DepartementController(DepartementService departementService, MedecinService medecinService) {
        this.departementService = departementService;
        this.medecinService = medecinService;
    }

    @RequestMapping()
    public List<Departement> getAll() {
        return departementService.getAllDepartements();
    }

    @GetMapping("/{id}")
    public Departement get(@PathVariable("id") Long id) {
        return departementService.getDepartementById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/medecins")
    public List<Medecin> getMedecinsOfDepartement(@PathVariable("id") Long id) {
        Optional<Departement> departementOptional = departementService.getDepartementById(id);
        if (departementOptional.isPresent()) {
            return medecinService.getMedecinByDepartement(departementOptional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
