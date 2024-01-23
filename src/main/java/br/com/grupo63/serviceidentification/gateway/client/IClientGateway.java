package br.com.grupo63.serviceidentification.gateway.client;

import br.com.grupo63.serviceidentification.entity.client.Client;
import br.com.grupo63.serviceidentification.gateway.IPersistenceEntityGateway;

import java.util.Optional;

public interface IClientGateway extends IPersistenceEntityGateway<Client> {

    Optional<Client> findByNationalId(String nationalId);

}
