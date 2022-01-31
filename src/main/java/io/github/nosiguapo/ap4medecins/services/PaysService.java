package io.github.nosiguapo.ap4medecins.services;

import io.github.nosiguapo.ap4medecins.entities.Pays;
import io.github.nosiguapo.ap4medecins.repositories.PaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
