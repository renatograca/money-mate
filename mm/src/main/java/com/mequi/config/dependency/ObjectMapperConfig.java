package com.mequi.config.dependency;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class ObjectMapperConfig extends AbstractModule {

  @Provides
  @Singleton
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();

    // Configurações gerais
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // Ignorar propriedades desconhecidas
    objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);                // JSON formatado
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);   // ISO para datas
    objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

    // Excluir propriedades nulas na serialização
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    return objectMapper;
  }
}
