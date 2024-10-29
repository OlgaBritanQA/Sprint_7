package org.example.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Courier {
    private String login;
    private String password;
    private String firstName;

    public Courier(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
