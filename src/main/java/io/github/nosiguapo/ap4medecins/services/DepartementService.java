package io.github.nosiguapo.ap4medecins.services;

import io.github.nosiguapo.ap4medecins.entities.Departement;
import io.github.nosiguapo.ap4medecins.repositories.DepartementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
