package io.github.nosiguapo.ap4medecins.services;

import io.github.nosiguapo.ap4medecins.entities.Medecin;
import io.github.nosiguapo.ap4medecins.repositories.MedecinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedecinService {
    private final MedecinRepository medecinRepository;

    @Autowired
    public MedecinService(MedecinRepository medecinRepository) {
        this.medecinRepository = medecinRepository;
    }

    public List<Medecin> getAllMedecins(){
        return medecinRepository.findAll();
    }

    // The result can be null, Optional parametter must be specified to handle this kind of potential error
    public Optional<Medecin> getMedecinByid(Long id){
        return medecinRepository.findById(id);
    }

    public List<Medecin> getMedecinByNom(String nom){
//        return medecinRepository.findByNomLike("%"+nom+"%");
        return medecinRepository.findByNomContainingIgnoreCase(nom);
    }

    public List<Medecin> getMedecinByPrenom(String prenom){
        return medecinRepository.findByPrenomContainingIgnoreCase(prenom);
    }

    // A list of all domains will be used in order to choose, a single speciality will be sent
    // Since a precise result will be sent, a "LIKE" is unnecessary
    public List<Medecin> getMedecinBySpeciality(String spec){
        return medecinRepository.findBySpec(spec);
    }

    public Medecin addMedecin(Medecin newMedecin){
        return medecinRepository.save(newMedecin);
    }

    public boolean deleteMedecinById(Long id){
        medecinRepository.deleteById(id);
        return true;
    }
}
