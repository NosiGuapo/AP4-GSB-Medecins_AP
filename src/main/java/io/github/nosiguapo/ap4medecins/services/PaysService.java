package io.github.nosiguapo.ap4medecins.services;

import io.github.nosiguapo.ap4medecins.entities.Pays;
import io.github.nosiguapo.ap4medecins.repositories.PaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PaysService {
    private final PaysRepository paysRepository;

    @Autowired
    public PaysService(PaysRepository paysRepository) {
        this.paysRepository = paysRepository;
    }

    public List<Pays> getAllPays(){
        return paysRepository.findAll();
    }

    // The result can be null, Optional parametter must be specified to handle this kind of potential error
    public Optional<Pays> getPaysById(Long id){
        return paysRepository.findById(id);
    }

    public boolean deletePaysById(Long id){
        paysRepository.deleteById(id);
        return true;
    }

    @Transactional
    public Pays addCountry(Pays country){
        // If a country already exists with a similar name, we send an error
        if (paysRepository.findByNomIgnoreCase(country.getNom()).isEmpty()){
//            Long countryId = paysRepository.findMaxId()+1;
//            country.setId(countryId);
            return paysRepository.save(country);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    @Transactional
    public Pays editCountry(Pays country){
        Pays editedPays = paysRepository.getById(country.getId());
        if (paysRepository.findByNomIgnoreCase(country.getNom()).isEmpty()){
            // If a country already exists with a similar name, we send an error
            editedPays.setNom(country.getNom());
            return paysRepository.save(editedPays);
        } else if (Objects.equals(editedPays.getNom(), country.getNom())) {
            // The country name was unchanged, the save operation is useless
            return editedPays;
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}
