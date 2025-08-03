package com.example.clients.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Client extends Person {
    private String password;
    private Boolean status;
}
