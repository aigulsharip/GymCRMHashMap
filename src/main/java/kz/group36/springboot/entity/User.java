package kz.group36.springboot.entity;


import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class User {


    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private Boolean isActive;




}

