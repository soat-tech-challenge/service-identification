package br.com.grupo63.serviceidentification.gateway.repository.entity;

import br.com.grupo63.serviceidentification.entity.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class PersistenceEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Access(AccessType.FIELD)
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Basic
    @Column(name = "deleted", nullable = false)
    protected boolean deleted = false;

    @Column(name = "creation_date", nullable = false)
    @LastModifiedDate
    private LocalDateTime creationDate;

    @Column(name = "last_update_date", nullable = false)
    @LastModifiedDate
    private LocalDateTime lastUpdateDate;

    public PersistenceEntity(Entity entity) {
        this.id = entity.getId();
        this.deleted = entity.isDeleted();
    }
}
