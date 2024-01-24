package br.com.grupo63.serviceidentification.gateway.client;

import br.com.grupo63.serviceidentification.gateway.repository.IJpaRepository;
import br.com.grupo63.serviceidentification.gateway.client.entity.ClientPersistenceEntity;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@EnableScan
public interface ClientJpaRepository extends CrudRepository<ClientPersistenceEntity, String>, IJpaRepository<ClientPersistenceEntity> {

    Optional<ClientPersistenceEntity> findByNationalIdAndDeletedFalse(String nationalId);

}
