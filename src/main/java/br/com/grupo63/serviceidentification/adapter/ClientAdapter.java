package br.com.grupo63.serviceidentification.adapter;

import br.com.grupo63.serviceidentification.controller.dto.ClientControllerDTO;
import br.com.grupo63.serviceidentification.entity.client.Client;

public class ClientAdapter {

    public static void fillEntity(ClientControllerDTO dto, Client entity) {
        entity.setId(dto.getId());
        entity.setNationalId(dto.getNationalId());
    }

}
