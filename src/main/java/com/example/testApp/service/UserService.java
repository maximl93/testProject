package com.example.testApp.service;

import com.example.testApp.dto.subscription.SubscriptionAddDTO;
import com.example.testApp.dto.subscription.SubscriptionDTO;
import com.example.testApp.dto.user.UserCreateDTO;
import com.example.testApp.dto.user.UserDTO;
import com.example.testApp.dto.user.UserUpdateDTO;
import com.example.testApp.exception.ResourceNotFoundException;
import com.example.testApp.mapper.SubscriptionMapper;
import com.example.testApp.mapper.UserMapper;
import com.example.testApp.model.Subscription;
import com.example.testApp.model.User;
import com.example.testApp.repository.SubscriptionRepository;
import com.example.testApp.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SubscriptionMapper subscriptionMapper;

    @Autowired
    private ObjectMapper om;

    public UserDTO create(UserCreateDTO createDTO) throws JsonProcessingException {
        log.info("Создание нового пользователя: {}", om.writeValueAsString(createDTO));
        User newUser = userMapper.map(createDTO);
        userRepository.save(newUser);
        log.info("Пользователь успешно создан с id: {}", newUser.getId());
        return userMapper.map(newUser);
    }

    public UserDTO findById(long userId) {
        log.info("Поиск пользователя по id: {}", userId);
        User savedUser = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("Пользователь с id: {} не найден", userId);
                    return new ResourceNotFoundException("Пользователь с id: " + userId + " не найден");
                });
        log.debug("Найден пользователь: {}", savedUser);
        return userMapper.map(savedUser);
    }

    public UserDTO update(long userId, UserUpdateDTO updateDTO) {
        log.info("Обновление пользователя с id: {}", userId);
        User updatedUser = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("Пользователь с id: {} не найден", userId);
                    return new ResourceNotFoundException("Пользователь с id: " + userId + " не найден");
                });
        userMapper.update(updateDTO, updatedUser);
        userRepository.save(updatedUser);
        log.info("Пользователь с id: {} успешно обновлен", userId);
        return userMapper.map(updatedUser);
    }

    public void deleteById(long userId) {
        log.info("Удаление пользователя с id: {}", userId);
        userRepository.deleteById(userId);
        log.info("Пользователь с id: {} успешно удален", userId);
    }

    public SubscriptionDTO addUserSubscription(long userId, SubscriptionAddDTO addDTO) throws JsonProcessingException {
        log.info("Добавляем пользователю с id: {} подписку: {}", userId, om.writeValueAsString(addDTO));
        Subscription subscription = userRepository.findById(userId).map(user -> {
            String title = addDTO.getTitle();
            log.info("Поиск подписки {} в БД", addDTO.getTitle());
            Subscription _subscription = subscriptionRepository.findByTitle(title)
                    .orElseThrow(() -> {
                        log.warn("Подписка с названием: {} не найдена", addDTO.getTitle());
                        return new ResourceNotFoundException("Подписка с названием: " + addDTO.getTitle() + " не найдена");
                    });
            user.addSubscription(_subscription);
            userRepository.save(user);
            log.info("Подписка с id: {} успешно добавлена пользователю с id: {}", _subscription.getId(), userId);
            return _subscription;


        }).orElseThrow(() -> new ResourceNotFoundException("Пользователь с id: " + userId + " не найден"));

        return subscriptionMapper.map(subscription);
    }

    public List<SubscriptionDTO> findUserSubscriptions(long userId) {
        log.info("Поиск всех подписок пользователя с id: {}", userId);
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Пользователь с id: " + userId + " не найден");
        }

        List<SubscriptionDTO> userSubscriptions = subscriptionRepository.findSubscriptionsBySubscribersId(userId).stream()
                .map(subscriptionMapper::map)
                .toList();
        log.info("Успешно найдены все подписки пользователя с id: {}", userId);
        return userSubscriptions;
    }


    public void deleteUserSubscription(long userId, long subId) {
        log.info("Удаляем подписку с id: {} у пользователя с id: {}", subId, userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с id: " + userId + " не найден"));
        user.removeSubscription(subId);
        userRepository.save(user);
        log.info("Подписка с id: {} была успешно удалена у пользователя с id: {}", subId, userId);
    }
}
