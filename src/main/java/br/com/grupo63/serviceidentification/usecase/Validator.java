package br.com.grupo63.serviceidentification.usecase;

import br.com.grupo63.serviceidentification.exception.ValidationException;
import jakarta.validation.ConstraintViolation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class Validator {

    private final jakarta.validation.Validator validator;

    public <T> Set<ConstraintViolation<T>> validate(T entity, Class<?>... groups) throws ValidationException {
        Set<ConstraintViolation<T>> violations = validator.validate(entity, groups);

        if (!violations.isEmpty()) {
            throw new ValidationException(
                    violations.stream().collect(Collectors.toList()).get(0).getMessage(),
                    violations.stream().collect(Collectors.toList()).get(0).getMessage());
        }

        return violations;
    }

}
