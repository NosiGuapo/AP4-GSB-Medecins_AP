package io.github.nosiguapo.ap4medecins.controllers;

import io.github.nosiguapo.ap4medecins.entities.Pays;
import io.github.nosiguapo.ap4medecins.services.PaysService;
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
@RequestMapping("/gsb/pays")
public class PaysController {
    private final PaysService paysService;

    @Autowired
    public PaysController(PaysService paysService) {
        this.paysService = paysService;
    }

    // We are using the standard pre-defined mapping for this task
    @GetMapping()
    public List<Pays> getAll(){
        return paysService.getAllPays();
    }

    @GetMapping("/{id}")
    // Getting the id precised in the url and turning it into a variable
    public Pays get(@PathVariable("id") Long id){
        if (paysService.getPaysById(id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return paysService.getPaysById(id).get();
        }
    }
}
