package io.github.nosiguapo.ap4medecins.controllers;

import io.github.nosiguapo.ap4medecins.entities.Medecin;
import io.github.nosiguapo.ap4medecins.services.MedecinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    @GetMapping("/{id}")
    // Getting the id precised in the url and turning it into a variable
    public Medecin get(@PathVariable("id") Long id){
        // If no doctor is found, return error 404
        if (medecinService.getMedecinByid(id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return medecinService.getMedecinByid(id).get();
        }
    }
}
