package ru.yandex.practicum.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.exception.ConditionsNotMetException;
import ru.yandex.practicum.exception.DuplicatedDataException;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.model.User;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private final Map<Long, User> users = new HashMap<>();

    public Collection<User> findAll() {
        return users.values();
    }

    public User create(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new ConditionsNotMetException("Имейл должен быть указан");
        }
        if (users.containsValue(user)) {
            throw new DuplicatedDataException("Данный имейл уже используется");
        }
        user.setId(getNextId());
        user.setRegistrationDate(Instant.now());
        users.put(user.getId(), user);
        return user;
    }

    public User update(User newUser) {
        if (newUser.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }
        if (users.containsKey(newUser.getId())) {
            User oldUser = users.get(newUser.getId());
            if (newUser.getEmail() != null &&
                    !oldUser.getEmail().equalsIgnoreCase(newUser.getEmail())) {
                if (users.containsValue(newUser)) {
                    throw new DuplicatedDataException("Данный имейл уже используется");
                }
                oldUser.setEmail(newUser.getEmail());
            }
            if (newUser.getUsername() != null && !newUser.getUsername().isBlank()) {
                oldUser.setUsername(newUser.getUsername());
            }
            if (newUser.getPassword() != null && !newUser.getPassword().isBlank()) {
                oldUser.setPassword(newUser.getPassword());
            }
            return oldUser;
        }
        throw new NotFoundException("Пользователь с id = " + newUser.getId() + " не найден");
    }

    public Optional<User> findById(long userId) {
        return Optional.ofNullable(users.get(userId));
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}