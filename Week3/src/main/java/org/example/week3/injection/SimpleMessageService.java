package org.example.week3.injection;

import org.springframework.stereotype.Service;

@Service
public class SimpleMessageService implements MessageService {

    @Override
    public String getMessage() {
        return "Salut din MessageService!";
    }
}