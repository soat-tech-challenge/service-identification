package br.com.grupo63.serviceidentification.gateway.client.entity;

import br.com.grupo63.serviceidentification.entity.client.Client;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@DynamoDBTable(tableName = "clients")
public class ClientPersistenceEntity {

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    private String id;

    @DynamoDBAttribute
    private boolean deleted;

    @DynamoDBAttribute
    private String nationalId;

    public ClientPersistenceEntity(Client client) {
        this.id = client.getId();
        this.nationalId = client.getNationalId();
        this.deleted = client.isDeleted();
    }

    public Client toModel() {
        return new Client(this.getId(), this.isDeleted(), this.getNationalId());
    }
}
