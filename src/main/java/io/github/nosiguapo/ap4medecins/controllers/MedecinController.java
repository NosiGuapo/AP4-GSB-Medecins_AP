package io.github.nosiguapo.ap4medecins.controllers;

import io.github.nosiguapo.ap4medecins.entities.Medecin;
import io.github.nosiguapo.ap4medecins.services.MedecinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    @GetMapping("/name")
    public List<Medecin> getByFullName(@RequestParam(defaultValue = "") String name) {
        return medecinService.getMedecinByNom(name);
    }

    @GetMapping("/spec")
    public List<Medecin> getDoctorsBySectorOfActivity(@RequestParam(defaultValue = "") String spec){
        return medecinService.getMedecinsBySpeciality(spec);
    }

    @GetMapping("/specs")
    public List<String> getAllSpecs(){
        return medecinService.getAllSpecs();
    }

    // Delete doctor
    @DeleteMapping("/{id}")
    // No need to precise the pathvariable when only a single one is present
    public boolean delete(@PathVariable Long id) {
        // We check if the doctor exists before executing the POST request
        get(id);
        return medecinService.deleteMedecinById(id);
    }

    @PostMapping(consumes = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public Medecin create(@RequestBody Medecin newMedecin) {
        return medecinService.addMedecin(newMedecin);
    }

    // Edit doctor
    @PutMapping()
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Medecin edit(@RequestBody Medecin medecin) {
        get(medecin.getId());
        return medecinService.editMedecin(medecin);
    }
}
