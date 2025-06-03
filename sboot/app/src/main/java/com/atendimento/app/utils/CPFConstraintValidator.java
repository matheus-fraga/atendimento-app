package com.atendimento.app.utils;

import br.com.caelum.stella.validation.CPFValidator; // Importa a classe da biblioteca Caelum Stella
import br.com.caelum.stella.validation.InvalidStateException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validador personalizado para CPFs utilizando a biblioteca Caelum Stella.
 */
public class CPFConstraintValidator implements ConstraintValidator<ValidCPF, String> {

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null || cpf.isEmpty()) {
            return false; // CPF é obrigatório
        }

        // Instancia o validador da biblioteca Caelum Stella
        CPFValidator validator = new CPFValidator();

        try {
            // Valida o CPF usando a biblioteca
            validator.assertValid(cpf);
            return true;
        } catch (InvalidStateException e) {
            return false; // CPF inválido
        }
    }
}