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

    // Name query
    public List<Medecin> getMedecinByNom(String nom){
        return medecinRepository.findByNomOrderByNomAsc(nom);
    }
    public List<Medecin> getMedecinByPrenom(String prenom){
        return medecinRepository.findByPrenomOrderByPrenomAsc(prenom);
    }
//    public List<Medecin> getMedecinByCompleteName(String request){
//        return medecinRepository.findByNomAndPrenomOrderByNomAsc(request);
//    }

    public List<Medecin> getMedecinBySpeciality(String spec){
        return medecinRepository.findBySpecOrderByNomAsc(spec);
    }

    public Medecin addMedecin(Medecin newMedecin){
        return medecinRepository.save(newMedecin);
    }

    public boolean deleteMedecinById(Long id){
        medecinRepository.deleteById(id);
        return true;
    }
}
