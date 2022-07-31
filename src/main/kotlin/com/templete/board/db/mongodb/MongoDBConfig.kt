package com.templete.board.account

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory


@Configuration
public class MongoDBConfig
{
    @Value("\${mongodb.connectionString}")
    private var connectionString : String = ""

    @Bean
    fun mongoDatabaseFactory(): MongoDatabaseFactory? {
        return SimpleMongoClientDatabaseFactory(connectionString)
    }

    @Bean
    fun mongoTemplate(): MongoTemplate? {
        return MongoTemplate(mongoDatabaseFactory()!!)
    }
}