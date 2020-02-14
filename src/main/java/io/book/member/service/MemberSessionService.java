package io.book.member.service;

import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
class MemberSessionService {

    private static final String SESSION_PREFIX_KEY = "sessions:%s";
    private static final Duration SESSION_EXPIRE_DURATION = Duration.ofHours(1L);

    private final ReactiveStringRedisTemplate reactiveStringRedisTemplate;

    MemberSessionService(final ReactiveStringRedisTemplate reactiveStringRedisTemplate) {
        this.reactiveStringRedisTemplate = reactiveStringRedisTemplate;
    }

    void set(final long id) {
        final String sessionKey = getSessionKey(id);
        reactiveStringRedisTemplate.opsForValue().set(sessionKey, sessionKey, SESSION_EXPIRE_DURATION).subscribe();
    }

    boolean isExist(final long id) {
        final String sessionKey = getSessionKey(id);
        return reactiveStringRedisTemplate.opsForValue().get(sessionKey).blockOptional().isPresent();
    }

    static String getSessionKey(final long id) {
        return String.format(SESSION_PREFIX_KEY, id);
    }
}
