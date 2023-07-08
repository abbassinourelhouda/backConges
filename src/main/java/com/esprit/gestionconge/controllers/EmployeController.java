package com.esprit.gestionconge.controllers;

import com.esprit.gestionconge.models.*;
import com.esprit.gestionconge.payload.request.SignupRequest;
import com.esprit.gestionconge.payload.response.MessageResponse;
import com.esprit.gestionconge.repository.EmployeRepository;
import com.esprit.gestionconge.repository.RoleRepository;
import com.esprit.gestionconge.repository.UserRepository;
import com.esprit.gestionconge.services.imp.ContratServiceImp;
import com.esprit.gestionconge.services.imp.EmployeServiceImp;
import com.esprit.gestionconge.services.imp.EquipeServiceImp;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import com.esprit.gestionconge.utils.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/employe")
public class EmployeController {



    @Autowired
    UserRepository userRepository;
    @Autowired
    EmployeRepository employeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private StorageService storage;


    @Autowired
    private EmployeServiceImp employeServiceImp;

    @Autowired
    private EquipeServiceImp equipeServiceImp;

    @Autowired
    private ContratServiceImp contratServiceImp;

    @Autowired
    private JavaMailSender mailSender;


    @GetMapping("/all")

    //@PreAuthorize("hasRole('')")
    public List<Employe> getAllemploye() {
        return employeServiceImp.getAllEmploye();
    }


    @PostMapping ("/save")
    public Employe saveemploye(@RequestBody Employe employe) {
        return employeServiceImp.createEmploye(employe);
    }

    @GetMapping  ("/getone/{id}")
    public Employe getoneemploye(@PathVariable Long id) {
        return employeServiceImp.getEmployeById(id);
    }

    @GetMapping("/allbyequipe")
    public List<Employe> getAllemployebyequipe(String nom) {
        return employeServiceImp.getEmployebyequipe(nom);
    }
    @PutMapping("/update/{id}")
    public Employe update(@RequestBody Employe e, @PathVariable Long id) {

        Employe e1 = employeServiceImp.getEmployeById(id);
        if (e1 != null) {
            e.setId(id);
            e.setEquipe(e1.getEquipe());
            e.setContrat(e1.getContrat());
            e.setImage(e1.getImage());
            e.setRoles(e1.getRoles());
            e.setPassword(e1.getPassword());

            return employeServiceImp.updateEmploye(e);
        }
        else{
            throw new RuntimeException("FAIL!");
        }

    }

    @DeleteMapping("/delete/{id}")
    public HashMap<String,String> deleteEmploye(@PathVariable Long id) {
        HashMap message= new HashMap();
        try{
            employeServiceImp.deleteEmploye(id);
            message.put("etat","employe deleted");
            return message;
        }
        catch (Exception e) {
            message.put("etat","employe not deleted");
            return message;
        }
    }


    @PostMapping("/signup/{idequipe}/{idcontrat}/{email}")
    public ResponseEntity<?> registerEmploye(  SignupRequest signUpRequest,@PathVariable String email, @PathVariable Long idequipe, @PathVariable Long idcontrat,@RequestParam(value = "file") MultipartFile file) throws MessagingException {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        Employe employe = new Employe(signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),signUpRequest.getNumcin(),signUpRequest.getNom(),signUpRequest.getPrenom(),signUpRequest.getAdresse(),signUpRequest.getTel());

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();



        Role empRole = roleRepository.findByName(ERole.ROLE_EMPLOYE)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(empRole);


        employe.setRoles(roles);



        //MAIL
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setSubject("information de votre compte");
        helper.setFrom(email);
        helper.setTo(signUpRequest.getEmail());
        //   helper.setText("<HTML><body> <a href=\"http://localhost:4200/reset/"
        //       + userExisting.getPasswordResetToken()+ "\">Forget Password<a/></body></HTML>", true);
        helper.setText("<HTML><body>votre username: " + signUpRequest.getUsername()+" et password: "+ signUpRequest.getPassword()+"</body></HTML>",true);
        mailSender.send(message);

        Equipe e= equipeServiceImp.getEquipeById(idequipe);
        employe.setEquipe(e);

        Contrat c= contratServiceImp.getContratById(idcontrat);
        employe.setContrat(c);
        String fileName= storage.store(file);
        employe.setImage(fileName);

        employeRepository.save(employe);

        return ResponseEntity.ok(new MessageResponse("Employe registered successfully and mail is send!"));
    }


    //affichage de l'image
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storage.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                        + file.getFilename() + "\"")
                .body(file);
    }
    @PutMapping("/updatephoto/{Id}")
    public User update(@RequestParam MultipartFile file, @PathVariable Long Id) {
        User u = userRepository.findById(Id).get();
        if (u != null) {
            u.setId(Id);
            String fileName= storage.store(file);
            u.setImage(fileName);
            return userRepository.saveAndFlush(u);
        }
        else{
            throw new RuntimeException("FAIL!");
        }

    }
}
