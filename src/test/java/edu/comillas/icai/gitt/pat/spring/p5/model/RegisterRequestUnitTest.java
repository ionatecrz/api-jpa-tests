package edu.comillas.icai.gitt.pat.spring.p5.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * TODO#7
 * Añade 2 tests unitarios adicionales que validen diferentes casos
 * (no variaciones del mismo caso) de registro con datos inválidos
 */

class RegisterRequestUnitTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testValidRequest() {
        RegisterRequest registro = new RegisterRequest(
                "Nombre", "nombre@email.com",
                Role.USER, "aaaaaaA1");

        Set<ConstraintViolation<RegisterRequest>> violations =
                validator.validate(registro);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testMissingEmail() {
        // Given ...
        RegisterRequest registro = new RegisterRequest(
                "Name", "",
                Role.USER, "aaaaaaA1");
        // When ...
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registro);
        // Then ...
        boolean hasEmailViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email"));

        assertTrue(hasEmailViolation);
    }

    @Test
    public void testWeakPassword() {
        RegisterRequest registro = new RegisterRequest(
                "Persona", "persona@email.com",
                Role.USER, "123");

        Set<ConstraintViolation<RegisterRequest>> violations =
                validator.validate(registro);

        assertTrue(violations.size() >= 1);
    }
}