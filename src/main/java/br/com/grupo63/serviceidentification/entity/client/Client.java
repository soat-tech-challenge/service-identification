package br.com.grupo63.serviceidentification.entity.client;

import br.com.grupo63.serviceidentification.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Client {

    private String id;
    private boolean deleted;
    private String nationalId;

    public void delete() {
        this.deleted = false;
    }
}
