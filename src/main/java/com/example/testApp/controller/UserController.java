package com.example.testApp.controller;

import com.example.testApp.dto.subscription.SubscriptionAddDTO;
import com.example.testApp.dto.subscription.SubscriptionDTO;
import com.example.testApp.dto.user.UserCreateDTO;
import com.example.testApp.dto.user.UserDTO;
import com.example.testApp.dto.user.UserUpdateDTO;
import com.example.testApp.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper om;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@Valid @RequestBody UserCreateDTO createDTO) throws JsonProcessingException {
        log.info("Вызов POST /users для создания нового пользователя в БД с параметрами: {}", om.writeValueAsString(createDTO));
        return userService.create(createDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO findById(@PathVariable("id") long id) {
        log.info("Вызов GET /users/{} для получения пользователя из БД с id: {}", id, id);
        return userService.findById(id);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO update(@PathVariable("id") long id, @Valid @RequestBody UserUpdateDTO updateDTO) throws JsonProcessingException {
        log.info("Вызов PUT /users/{} для обновления данных пользователя с id: {} новыми параметрами: {}", id, id, om.writeValueAsString(updateDTO));
        return userService.update(id, updateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") long id) {
        log.info("Вызов DELETE /users/{} для удаления пользователя с id: {}", id, id);
        userService.deleteById(id);
    }

    @PostMapping("/{id}/subscriptions")
    @ResponseStatus(HttpStatus.OK)
    public SubscriptionDTO addSubscription(@PathVariable("id") long id,@Valid @RequestBody SubscriptionAddDTO addDTO) throws JsonProcessingException {
        log.info("Вызов POST /users/{}/subscriptions для добавления подписки {} пользователю с id: {}", id, om.writeValueAsString(addDTO), id);
        return userService.addUserSubscription(id, addDTO);
    }

    @GetMapping("/{id}/subscriptions")
    @ResponseStatus(HttpStatus.OK)
    public List<SubscriptionDTO> findUserSubscriptions(@PathVariable("id") long id) {
        log.info("Вызов GET /users/{}/subscriptions для получения списка подписок пользователя с id: {}", id, id);
        return userService.findUserSubscriptions(id);
    }

    @DeleteMapping("/{id}/subscriptions/{sub_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserSubscription(@PathVariable("id") long userId, @PathVariable("sub_id") long subId) {
        log.info("Вызов DELETE /users/{}/subscriptions/{} для удаления подписки с id: {} у пользователя с id: {}", userId, subId, subId, userId);
        userService.deleteUserSubscription(userId, subId);
    }
}
