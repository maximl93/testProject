package com.example.testApp.service;

import com.example.testApp.dto.subscription.SubscriptionCreateDTO;
import com.example.testApp.dto.subscription.SubscriptionDTO;
import com.example.testApp.dto.subscription.SubscriptionUpdateDTO;
import com.example.testApp.exception.ResourceNotFoundException;
import com.example.testApp.mapper.SubscriptionMapper;
import com.example.testApp.model.Subscription;
import com.example.testApp.repository.SubscriptionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private SubscriptionMapper subscriptionMapper;

    @Autowired
    private ObjectMapper om;

    public SubscriptionDTO findById(long subId) {
        log.info("Поиск подписки по id: {}", subId);
        Subscription subscription = subscriptionRepository.findById(subId)
                .orElseThrow(() -> {
                    log.warn("Подписка с id: {} не найдена", subId);
                    return new ResourceNotFoundException("Подписка с id: " + subId + " не найдена");
                });
        log.debug("Найдена подписка: {}", subscription);
        return subscriptionMapper.map(subscription);
    }

    public SubscriptionDTO create(SubscriptionCreateDTO createDTO) throws JsonProcessingException {
        log.info("Создание новой подписки: {}", om.writeValueAsString(createDTO));
        Subscription subscription = subscriptionMapper.map(createDTO);
        subscriptionRepository.save(subscription);
        log.info("Подписка успешно создана с id: {}", subscription.getId());
        return subscriptionMapper.map(subscription);
    }

    public SubscriptionDTO update(long subId, SubscriptionUpdateDTO updateDTO) {
        log.info("Обновление подписки с id: {}", subId);
        Subscription subscription = subscriptionRepository.findById(subId)
                .orElseThrow(() -> {
                    log.warn("Подписка с id: {} не найдена для обновления", subId);
                    return new ResourceNotFoundException("Подписка с id: " + subId + " не найдена");
                });
        subscriptionMapper.update(updateDTO, subscription);
        subscriptionRepository.save(subscription);
        log.info("Подписка с id: {} успешно обновлена", subId);
        return subscriptionMapper.map(subscription);
    }

    public void deleteById(long subId) {
        log.info("Удаление подписки с id: {}", subId);
        subscriptionRepository.deleteById(subId);
        log.info("Подписка с id: {} удалена", subId);
    }

    public List<SubscriptionDTO> findTopThree() throws JsonProcessingException {
        log.info("Поиск топ-3 популярных подписок");
        List<Subscription> subscriptions = subscriptionRepository.findTop3ByUsersCount(PageRequest.of(0, 3));
        return subscriptions.stream()
                .map(subscriptionMapper::map)
                .toList();
    }
}
