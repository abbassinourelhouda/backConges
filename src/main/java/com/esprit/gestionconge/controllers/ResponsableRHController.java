package com.esprit.gestionconge.controllers;

import com.esprit.gestionconge.models.ERole;
import com.esprit.gestionconge.models.ResponsableRH;
import com.esprit.gestionconge.models.Role;
import com.esprit.gestionconge.payload.request.SignupRequest;
import com.esprit.gestionconge.payload.response.MessageResponse;
import com.esprit.gestionconge.repository.ResponsableRHRepository;
import com.esprit.gestionconge.repository.RoleRepository;
import com.esprit.gestionconge.repository.UserRepository;
import com.esprit.gestionconge.services.imp.ResponsableRHServiceImp;
import javax.validation.Valid;

import com.esprit.gestionconge.utils.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/responsablerh")
@CrossOrigin("*")
public class ResponsableRHController {
    @Autowired
    private ResponsableRHServiceImp responsableRHServiceImp;

    @Autowired
    UserRepository userRepository;
    @Autowired
    ResponsableRHRepository responsableRHRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private StorageService storage;


    @GetMapping("/all")
    public List<ResponsableRH> getAllresponsablerh() {
        return responsableRHServiceImp.getAllResponsableRH();
    }
    @PostMapping("/save")
    public ResponsableRH saveresponsablerh(@RequestBody ResponsableRH responsableRH) {
        return responsableRHServiceImp.createResponsableRH(responsableRH);
    }

    @GetMapping  ("/getone/{id}")
    public ResponsableRH getoneresponsablerh(@PathVariable Long id) {
        return responsableRHServiceImp.getResponsableRHById(id);
    }

    @PutMapping("/update/{id}")
    public ResponsableRH update(@RequestBody ResponsableRH responsableRH, @PathVariable Long id) {

        ResponsableRH rh = responsableRHServiceImp.getResponsableRHById(id);
        if (rh != null) {
            responsableRH.setId(id);
            return responsableRHServiceImp.updateResponsableRH(responsableRH);
        }
        else{
            throw new RuntimeException("FAIL!");
        }

    }

    @DeleteMapping("/delete/{id}")
    public HashMap<String,String> deleteresponsablerh(@PathVariable Long id) {
        HashMap message= new HashMap();
        try{
            responsableRHServiceImp.deleteResponsableRH(id);
            message.put("etat","Responsable RH  deleted");
            return message;
        }
        catch (Exception e) {
            message.put("etat","Responsable RH not deleted");
            return message;
        }
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerRH(@Valid  SignupRequest signUpRequest, @RequestParam(value = "file") MultipartFile file) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        ResponsableRH rh = new ResponsableRH(signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),signUpRequest.getMatricule(),signUpRequest.getService());

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();



        Role rhRole = roleRepository.findByName(ERole.ROLE_RESPONSABLERH)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(rhRole);




        rh.setRoles(roles);
        String fileName= storage.store(file);
        rh.setImage(fileName);
        responsableRHRepository.save(rh);

        return ResponseEntity.ok(new MessageResponse("ResponsableRH registered successfully!"));
    }
}
