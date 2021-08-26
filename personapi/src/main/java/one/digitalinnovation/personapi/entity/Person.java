package one.digitalinnovation.personapi.entity;
import lombok.Data;


import javax.persistence.*;


@Entity
@Data
public class Person {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String cpf;

    private String birthDate;

    @Column(nullable = false, unique = true)
    private String rg;

    @Column(nullable = false)
    private  String phone;
}
