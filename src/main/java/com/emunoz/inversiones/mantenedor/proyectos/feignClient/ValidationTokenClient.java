package com.emunoz.inversiones.mantenedor.proyectos.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "acceso", url = "http://localhost:8080")
public interface ValidationTokenClient {

    @GetMapping("/api/V1/validate-token")
    boolean validateToken(@RequestHeader(name = "Authorization") String token);
}
