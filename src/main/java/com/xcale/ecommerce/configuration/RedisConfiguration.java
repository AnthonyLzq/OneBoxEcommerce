package com.xcale.ecommerce.configuration;

import com.xcale.ecommerce.dbo.CartDBO;
import io.lettuce.core.ClientOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfiguration {
  @Value("${spring.redis.host}")
  private String redisHost;

  @Value("${spring.redis.port}")
  private int redisPort;

  @Value("${spring.redis.user}")
  private String redisUser;

  @Value("${spring.redis.password}")
  private String redisPassword;

  @Bean
  public RedisStandaloneConfiguration redisStandaloneConfiguration() {
    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisHost, redisPort);
    redisStandaloneConfiguration.setUsername(redisUser);
    redisStandaloneConfiguration.setPassword(redisPassword);

    return redisStandaloneConfiguration;
  }

  @Bean
  public ClientOptions clientOptions() {
    return ClientOptions
      .builder()
      .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
      .autoReconnect(true)
      .build();
  }

  @Bean
  public RedisConnectionFactory connectionFactory(RedisStandaloneConfiguration redisStandaloneConfiguration) {
    LettuceClientConfiguration configuration = LettuceClientConfiguration
      .builder()
      .clientOptions(clientOptions())
      .build();

    return new LettuceConnectionFactory(redisStandaloneConfiguration, configuration);
  }

  @Bean
  @ConditionalOnMissingBean(name = "redisTemplate")
  @Primary
  public RedisTemplate<String, CartDBO> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, CartDBO> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);

    return template;
  }
}
