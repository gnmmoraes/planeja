package com.github.gnmmoraes.planeja.common.validation;

import java.util.ArrayList;
import java.util.List;


public class ValidationResult {

    private List<CampoInvalido> camposInvalido;

    private ValidationResult(List<CampoInvalido> camposInvalido) {
        this.camposInvalido = camposInvalido;
    }

    public static ValidationResult novo() {
        return new ValidationResult(new ArrayList<>());
    }

    public void add(CampoInvalido campoInvalido) {
        this.camposInvalido.add(campoInvalido);
    }

    public List<CampoInvalido> getCamposInvalido() {
        return camposInvalido;
    }

    public boolean isValido() {
        return !camposInvalido.isEmpty();
    }
}
