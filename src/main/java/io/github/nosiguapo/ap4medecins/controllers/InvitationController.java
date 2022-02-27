package io.github.nosiguapo.ap4medecins.controllers;

import io.github.nosiguapo.ap4medecins.entities.Invitation;
import io.github.nosiguapo.ap4medecins.services.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/gsb/invitations")
public class InvitationController {
    private final InvitationService invitationService;

    @Autowired
    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @GetMapping()
    public List<Invitation> getAll(){
        return invitationService.getAllInvitations();
    }

    @GetMapping("/{id}")
    public Invitation get(@PathVariable("id") Long id){
        if (invitationService.getInvitationById(id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return invitationService.getInvitationById(id).get();
        }
    }
}
