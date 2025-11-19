package com.study_group_service.study_group_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Exchange 이름들
    public static final String STUDY_EVENTS_EXCHANGE = "study.events.exchange";
    public static final String USER_EVENTS_EXCHANGE = "user.events.exchange";
    public static final String COMMUNITY_EVENTS_EXCHANGE = "community.events.exchange";
    public static final String CHAT_EVENTS_EXCHANGE = "chat.events.exchange";

    // Queue 이름들
    public static final String STUDY_ROOM_QUEUE = "study.room.queue";
    public static final String USER_PROFILE_QUEUE = "user.profile.queue";
    public static final String COMMUNITY_POST_QUEUE = "community.post.queue";
    public static final String CHAT_MESSAGE_QUEUE = "chat.message.queue";

    // Routing Key들
    public static final String STUDY_ROOM_CREATED = "study.room.created";
    public static final String STUDY_ROOM_JOINED = "study.room.joined";
    public static final String STUDY_ROOM_LEFT = "study.room.left";
    public static final String USER_REGISTERED = "user.registered";
    public static final String USER_PROFILE_UPDATED = "user.profile.updated";
    public static final String COMMUNITY_POST_CREATED = "community.post.created";
    public static final String COMMUNITY_COMMENT_CREATED = "community.comment.created";
    public static final String CHAT_MESSAGE_SENT = "chat.message.sent";

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    // RabbitMQ 리스너 자동 시작 비활성화
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        factory.setAutoStartup(false); // 자동 시작 비활성화
        return factory;
    }

    // Study Events Exchange
    @Bean
    public TopicExchange studyEventsExchange() {
        return new TopicExchange(STUDY_EVENTS_EXCHANGE);
    }

    @Bean
    public Queue studyRoomQueue() {
        return QueueBuilder.durable(STUDY_ROOM_QUEUE).build();
    }

    @Bean
    public Binding studyRoomBinding() {
        return BindingBuilder
                .bind(studyRoomQueue())
                .to(studyEventsExchange())
                .with("study.room.*");
    }

    // User Events Exchange
    @Bean
    public TopicExchange userEventsExchange() {
        return new TopicExchange(USER_EVENTS_EXCHANGE);
    }

    @Bean
    public Queue userProfileQueue() {
        return QueueBuilder.durable(USER_PROFILE_QUEUE).build();
    }

    @Bean
    public Binding userProfileBinding() {
        return BindingBuilder
                .bind(userProfileQueue())
                .to(userEventsExchange())
                .with("user.*");
    }

    // Community Events Exchange
    @Bean
    public TopicExchange communityEventsExchange() {
        return new TopicExchange(COMMUNITY_EVENTS_EXCHANGE);
    }

    @Bean
    public Queue communityPostQueue() {
        return QueueBuilder.durable(COMMUNITY_POST_QUEUE).build();
    }

    @Bean
    public Binding communityPostBinding() {
        return BindingBuilder
                .bind(communityPostQueue())
                .to(communityEventsExchange())
                .with("community.*");
    }

    // Chat Events Exchange
    @Bean
    public TopicExchange chatEventsExchange() {
        return new TopicExchange(CHAT_EVENTS_EXCHANGE);
    }

    @Bean
    public Queue chatMessageQueue() {
        return QueueBuilder.durable(CHAT_MESSAGE_QUEUE).build();
    }

    @Bean
    public Binding chatMessageBinding() {
        return BindingBuilder
                .bind(chatMessageQueue())
                .to(chatEventsExchange())
                .with("chat.*");
    }
}
