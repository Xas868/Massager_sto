package com.javamentor.qa.platform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyTestPj {

    public static void main(String[] args) {
        System.out.println("_________");
    }

    private String firstName;
    private String secondName;
    private int age;

    public void func() {
        setFirstName("Вася");
    }

}
