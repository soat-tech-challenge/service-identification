package br.com.grupo63.serviceidentification.gateway.repository;

import java.util.List;
import java.util.Optional;

public interface IJpaRepository<E> {

    Optional<E> findByIdAndDeletedFalse(String id);

    List<E> findByDeletedFalse();

}
