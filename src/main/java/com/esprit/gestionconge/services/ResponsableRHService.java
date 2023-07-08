package com.esprit.gestionconge.services;

import com.esprit.gestionconge.models.ResponsableRH;

import java.util.List;

public interface ResponsableRHService {

    ResponsableRH createResponsableRH(ResponsableRH responsableRH);
    ResponsableRH updateResponsableRH(ResponsableRH responsableRH);
    void deleteResponsableRH(Long id);
    ResponsableRH getResponsableRHById(Long id);
    List<ResponsableRH> getAllResponsableRH();
}
