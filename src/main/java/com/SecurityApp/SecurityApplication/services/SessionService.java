package com.SecurityApp.SecurityApplication.services;

import com.SecurityApp.SecurityApplication.entities.Session;
import com.SecurityApp.SecurityApplication.entities.User;
import com.SecurityApp.SecurityApplication.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final int SESSION_LIMIT=2;
    public void generateNewSession(User user,String refreshToken){
        List<Session> userSessions=sessionRepository.findByUser(user);

        if(userSessions.size()==SESSION_LIMIT){//already 2 session exists
            userSessions.sort(Comparator.comparing(Session::getLastUsedAt));//comparing the last session

            Session leastRecentlyUsedSession=userSessions.getFirst();
            sessionRepository.delete(leastRecentlyUsedSession);
        }//if we reached our limits of our session
        Session newSession=Session.builder()
                .user(user)
                .refreshToken(refreshToken).build();
        sessionRepository.save(newSession);
    }
    public boolean validSession(String refreshToken){
        Session session=sessionRepository.findByRefreshToken(refreshToken).orElseThrow(()->new SessionAuthenticationException("Session not found for refreshtoken: "+refreshToken));

        session.setLastUsedAt(LocalDateTime.now());
        sessionRepository.save(session);

        return false;
    }
}
