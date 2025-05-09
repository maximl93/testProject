package com.example.testApp.controller;

import com.example.testApp.dto.subscription.SubscriptionCreateDTO;
import com.example.testApp.dto.subscription.SubscriptionDTO;
import com.example.testApp.dto.subscription.SubscriptionUpdateDTO;
import com.example.testApp.dto.user.UserDTO;
import com.example.testApp.service.SubscriptionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private ObjectMapper om;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public SubscriptionDTO create(@Valid @RequestBody SubscriptionCreateDTO createDTO) throws JsonProcessingException {
        log.info("Вызов POST /subscriptions для создания новой подписки в БД с параметрами: {}", om.writeValueAsString(createDTO));
        return subscriptionService.create(createDTO);
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SubscriptionDTO findById(@PathVariable("id") long subId) {
        log.info("Вызов GET /subscriptions/{} для получения подписки из БД с id: {}", subId, subId);
        return subscriptionService.findById(subId);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SubscriptionDTO update(@PathVariable("id") long id, @Valid @RequestBody SubscriptionUpdateDTO updateDTO) throws JsonProcessingException {
        log.info("Вызов PUT /subscriptions/{} для обновления данных подписки с id: {} новыми параметрами: {}", id, id, om.writeValueAsString(updateDTO));
        return subscriptionService.update(id, updateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") long id) {
        log.info("Вызов DELETE /subscriptions/{} для удаления подписки из БД с id: {}", id, id);
        subscriptionService.deleteById(id);
    }

    @GetMapping("/top")
    @ResponseStatus(HttpStatus.OK)
    public List<SubscriptionDTO> findTopThree() throws JsonProcessingException {
        log.info("Вызов GET /subscriptions/top для получения трех самых популярных подписок");
        return subscriptionService.findTopThree();
    }
}
