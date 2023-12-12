package com.emunoz.inversiones.mantenedor.proyectos.services;

import com.emunoz.inversiones.mantenedor.proyectos.feignClient.ValidationTokenClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ValidationTokenService {


    private final ValidationTokenClient validationTokenClient;

    public ValidationTokenService(ValidationTokenClient validationTokenClient) {
        this.validationTokenClient = validationTokenClient;
    }

    public boolean validateToken(String token) {

        return validationTokenClient.validateToken(token);

    }
}
