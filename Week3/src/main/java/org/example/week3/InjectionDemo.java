package org.example.week3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InjectionDemo {


    private final MessageService constructorService;


    @Autowired
    private MessageService fieldService;

    private MessageService setterService;
    private MessageService methodService;

    public InjectionDemo(MessageService constructorService) {
        this.constructorService = constructorService;
        System.out.println("1. Constructor injection");
    }


    @Autowired
    public void setSetterService(MessageService setterService) {
        this.setterService = setterService;


        System.out.println("Field injection este gata: " + (fieldService != null));
        System.out.println("Setter injection");
    }


    @Autowired
    public void configure(MessageService methodService) {
        this.methodService = methodService;
        System.out.println("Method injection");
    }

    public void printMessages() {
        System.out.println("Constructor: " + constructorService.getMessage());
        System.out.println("Field: " + fieldService.getMessage());
        System.out.println("Setter: " + setterService.getMessage());
        System.out.println("Method: " + methodService.getMessage());
    }
}