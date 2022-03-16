package io.github.nosiguapo.ap4medecins.controllers;

import io.github.nosiguapo.ap4medecins.entities.Medecin;
import io.github.nosiguapo.ap4medecins.services.MedecinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
// Map the request to a certain url
@RequestMapping("/gsb/medecins")
public class MedecinController {
    private final MedecinService medecinService;

    @Autowired
    public MedecinController(MedecinService medecinService) {
        this.medecinService = medecinService;
    }

    // We are using the standard pre-defined mapping for this task
    @GetMapping()
    public List<Medecin> getAll() {
        return medecinService.getAllMedecins();
    }

    // We want to display the informations concerning a specific doctor, we get the mapping of the id suppiled after the default mapping
    @GetMapping("/{id}")
    // Getting the id precised in the url and turning it into a variable
    public Medecin get(@PathVariable("id") Long id) {
        // If no doctor is found, return error 404
        return medecinService.getMedecinByid(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("lname/{lname}")
    public List<Medecin> getByName(@PathVariable("lname") String name) {
        return medecinService.getMedecinByNom(name);
    }

    @GetMapping("fname/{fname}")
    public List<Medecin> getByFName(@PathVariable("fname") String name) {
        return medecinService.getMedecinByPrenom(name);
    }

    @GetMapping("spec/{spec}")
    public List<Medecin> getBySectorOfActivity(@PathVariable("spec") String spec) {
        return medecinService.getMedecinBySpeciality(spec);
    }

    // Delete doctor
    @DeleteMapping("/{id}")
    // No need to precise the pathvariable when only a single one is present
    public boolean delete(@PathVariable Long id) {
        // We check if the doctor exists before executing the POST request
        get(id);
        return medecinService.deleteMedecinById(id);
    }

    // Edit doctor
    @PutMapping("/{id}")
    public Optional<Medecin> editDoctor(@Valid @PathVariable Long id, @RequestBody Medecin newDoctor) {
        return Optional.ofNullable(medecinService.getMedecinByid(id).map(medecin -> {
            medecin.setNom(newDoctor.getNom());
            medecin.setPrenom(newDoctor.getPrenom());
            medecin.setSpec(newDoctor.getSpec());
            medecin.setAdresse(newDoctor.getAdresse());
            medecin.setTel(newDoctor.getTel());
            medecin.setDepartement(newDoctor.getDepartement());
            return medecinService.save(medecin);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }
}
