package com.example.application_mobile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Site {
    private int id;
    private String name;
    private String address;
    private int isActive;
    private String contactNumber;
    private int siteManagerId;

    public String toString(){
        return name;
    }

}
