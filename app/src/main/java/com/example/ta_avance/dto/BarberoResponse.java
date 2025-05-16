// BarberoResponse.java
package com.example.ta_avance.dto;

import java.util.List;

public class BarberoResponse {
    private int status;
    private String message;
    private List<Barbero> data;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Barbero> getData() {
        return data;
    }
}
