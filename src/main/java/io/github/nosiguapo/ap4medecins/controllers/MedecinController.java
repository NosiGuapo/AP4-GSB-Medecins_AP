package io.github.nosiguapo.ap4medecins.controllers;

import io.github.nosiguapo.ap4medecins.entities.Medecin;
import io.github.nosiguapo.ap4medecins.services.MedecinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<Medecin> getAll(){
        return medecinService.getAllMedecins();
    }

    // We want to display the informations concerning a specific doctor, we get the mapping of the id suppiled after the default mapping
    @GetMapping("/id/{id}")
    // Getting the id precised in the url and turning it into a variable
    public Medecin get(@PathVariable("id") Long id){
        // If no doctor is found, return error 404
        return medecinService.getMedecinByid(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("lname/{lname}")
    public List<Medecin> getByName(@PathVariable("lname") String name){
        return medecinService.getMedecinByNom(name);
    }

    @GetMapping("fname/{fname}")
    public List<Medecin> getByFName(@PathVariable("fname") String name){
        return medecinService.getMedecinByPrenom(name);
    }

    @GetMapping("spec/{spec}")
    public List<Medecin> getBySectorOfActivity(@PathVariable("spec") String spec){
        return medecinService.getMedecinBySpeciality(spec);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") Long id){
        // We check if the doctor exists before executing the POST request
        get(id);
        return medecinService.deleteMedecinById(id);
    }
}
