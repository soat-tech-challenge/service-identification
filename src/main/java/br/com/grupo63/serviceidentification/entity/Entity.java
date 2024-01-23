package br.com.grupo63.serviceidentification.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class Entity implements Serializable {

    @NotNull(message = "order.create.idNotNull")
    @Min(value = 1, message = "order.create.idNotNull")
    protected Long id;

    protected boolean deleted = false;

    public void delete() {
        this.deleted = true;
    }

}
