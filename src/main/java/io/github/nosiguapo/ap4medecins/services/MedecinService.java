package io.github.nosiguapo.ap4medecins.services;

import io.github.nosiguapo.ap4medecins.entities.Departement;
import io.github.nosiguapo.ap4medecins.entities.Medecin;
import io.github.nosiguapo.ap4medecins.entities.Pays;
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
        return medecinRepository.findAllByOrderByNomAsc();
    }

    // The result can be null, Optional parametter must be specified to handle this kind of potential error
    public Optional<Medecin> getMedecinByid(Long id){
        return medecinRepository.findById(id);
    }

    public List<Medecin> getMedecinByNom(String nom){
//        "%" are mentionned inside the services in order to be more flexible with the like method we want to use
//        return medecinRepository.findByNomLike("%"+nom+"%");
        return medecinRepository.findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(nom, nom);
    }

    public List<Medecin> getMedecinByDepartement(Departement departement) {
        return medecinRepository.findByDepartement(departement);
    }

    public List<Medecin> getMedecinByPays(Pays pays) {
/*
        return medecinRepository.findByPays(pays);
*/
        return null;
    }

    // A list of all domains will be used in order to choose, a single speciality will be sent
    // Since a precise result will be sent, a "LIKE" is unnecessary
    public List<Medecin> getMedecinsBySpeciality(String specName){
        return medecinRepository.findBySpecIgnoreCase(specName);
    }

    public List<String> getAllSpecs(){
        return medecinRepository.findAllSpecs();
    }

    public Medecin addMedecin(Medecin newMedecin){
        return medecinRepository.save(newMedecin);
    }

    public boolean deleteMedecinById(Long id){
        medecinRepository.deleteById(id);
        return true;
    }

    // Edit doctor
    public Medecin save(Medecin newDoctor) {
        return medecinRepository.save(newDoctor);
    }
}
