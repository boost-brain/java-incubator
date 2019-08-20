package com.boostbrain.entities;

import org.springframework.data.annotation.Id;

public class MainEntity {
    @Id

    private long id;
    private String someText;
    private String someData;
}
