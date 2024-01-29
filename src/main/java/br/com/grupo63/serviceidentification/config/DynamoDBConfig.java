package br.com.grupo63.serviceidentification.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableDynamoDBRepositories
        (basePackages = "br.com.grupo63.serviceidentification.gateway")
public class DynamoDBConfig {
    @Value("${app.aws.dynamodb.endpoint}")
    private String awsDynamoDBEndpoint;

    @Value("${app.aws.access-key}")
    private String awsAccessKey;

    @Value("${app.aws.secret-key}")
    private String awsSecretKey;

    @Value("${app.aws.session-token}")
    private String awsSessionToken;

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        AmazonDynamoDB amazonDynamoDB
                = new AmazonDynamoDBClient(awsCredentials());

        if (!StringUtils.isEmpty(awsDynamoDBEndpoint)) {
            amazonDynamoDB.setEndpoint(awsDynamoDBEndpoint);
        }

        return amazonDynamoDB;
    }

    @Bean
    public AWSCredentials awsCredentials() {
        if (awsSessionToken == null || awsSessionToken.isBlank()) {
            return new BasicAWSCredentials(
                    awsAccessKey, awsSecretKey);
        }
        return new BasicSessionCredentials(awsAccessKey, awsSecretKey, awsSessionToken);
    }
}
