package br.com.grupo63.serviceidentification.usecase.client;

import br.com.grupo63.serviceidentification.entity.client.Client;
import br.com.grupo63.serviceidentification.exception.NotFoundException;

import java.util.List;

public interface IClientUseCase {

    Client identify(Client client);

    Client findByNationalId(String nationalId) throws NotFoundException;

    Client create(Client entity);

    Client read(String id) throws NotFoundException;

    List<Client> list();

    Client update(Client entity);

    void delete(Client entity);

}
