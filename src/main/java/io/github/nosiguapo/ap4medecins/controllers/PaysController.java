package io.github.nosiguapo.ap4medecins.controllers;

import io.github.nosiguapo.ap4medecins.entities.Departement;
import io.github.nosiguapo.ap4medecins.entities.Medecin;
import io.github.nosiguapo.ap4medecins.services.DepartementService;
import io.github.nosiguapo.ap4medecins.services.MedecinService;
import io.github.nosiguapo.ap4medecins.entities.Pays;
import io.github.nosiguapo.ap4medecins.services.PaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
// Map the request to a certain url
@RequestMapping("/gsb/pays")
public class PaysController {
    private final PaysService paysService;
    private final MedecinService medecinService;
    private final DepartementService departementService;

    @Autowired
    public PaysController(PaysService paysService, MedecinService medecinService, DepartementService departementService) {
        this.paysService = paysService;
        this.medecinService = medecinService;
        this.departementService = departementService;
    }

    // We are using the standard pre-defined mapping for this task
    @GetMapping()
    public List<Pays> getAll() {
        return paysService.getAllPays();
    }

    @GetMapping("/{id}")
    // Getting the id precised in the url and turning it into a variable
    public Pays get(@PathVariable("id") Long id) {
        if (paysService.getPaysById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return paysService.getPaysById(id).get();
        }
    }

    @GetMapping("/{id}/medecins")
    public List<Medecin> getMedecinsOfPays(@PathVariable("id") Long id) {
        Optional<Pays> paysOptional = paysService.getPaysById(id);
        List<Departement> departementList = departementService.getDepartementsByCountryId(id);

        if (departementList.isEmpty() || paysOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            List<Medecin> medecinsByDepartements = new ArrayList<>();
            for (Departement d : departementList){
                medecinsByDepartements.addAll(medecinService.getMedecinsByDepartement(d.getId()));
            }
            return medecinsByDepartements;
        }
    }

    @GetMapping("/{id}/departements")
    public List<Departement> getDepartementsOfPays(@PathVariable Long id){
        get(id);
        return departementService.getDepartementsByCountryId(id);
    }

    @DeleteMapping("/{id}")
    // No need to precise the pathvariable when only a single one is present
    public boolean delete(@PathVariable Long id) {
        get(id);
        return paysService.deletePaysById(id);
    }

    @PostMapping(consumes = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public Pays create(@RequestBody Pays newCountry) {
        return paysService.addCountry(newCountry);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Pays edit(@RequestBody Pays country) {
        get(country.getId());
        return paysService.editCountry(country);
    }
}
