package com.demo.authorization;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data@NoArgsConstructor@Getter@Setter
@Table
@Entity
public class Mapping {

    @Id
    @SequenceGenerator(
            name = "role_mapping_sequence",
            sequenceName = "role_mapping_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "role_mapping_sequence"
    )
    int id;
    @Column(unique = true)
    String username;
    Role role;

    public Mapping(String username, Role role) {
        this.username = username;
        this.role = role;
    }
}
