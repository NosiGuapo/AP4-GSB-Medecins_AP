package io.github.nosiguapo.ap4medecins.services;

import io.github.nosiguapo.ap4medecins.entities.Departement;
import io.github.nosiguapo.ap4medecins.entities.Pays;
import io.github.nosiguapo.ap4medecins.repositories.DepartementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DepartementService {
    private final DepartementRepository departementRepository;

    @Autowired
    public DepartementService(DepartementRepository departementRepository) {
        this.departementRepository = departementRepository;
    }

    public List<Departement> getAllDepartements(){
        return departementRepository.findAllByOrderByNomAsc();
    }

    public List<Departement> getDepartementsByCountryId(Long id){
        return departementRepository.findDepartementByPaysId(id);
    }

    // The result can be null, Optional parametter must be specified to handle this kind of potential error
    public Optional<Departement> getDepartementById(Long id){
        return departementRepository.findById(id);
    }

    @Transactional
    public Departement addDepartement(Departement departement){
        Long departementId = departementRepository.findMaxId()+1;
        departement.setId(departementId);
        return departementRepository.save(departement);
    }
}
