package by.mariayuran.my_project_boot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.parsers.SAXParser;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String password;
    private String email;
    private String role;
    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Bucket bucket;
}
