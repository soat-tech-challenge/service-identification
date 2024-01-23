package br.com.grupo63.serviceidentification.gateway.client;

import br.com.grupo63.serviceidentification.gateway.repository.IJpaRepository;
import br.com.grupo63.serviceidentification.gateway.client.entity.ClientPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientJpaRepository extends JpaRepository<ClientPersistenceEntity, String>, IJpaRepository<ClientPersistenceEntity> {

    Optional<ClientPersistenceEntity> findByNationalIdAndDeletedFalse(String nationalId);

}
