package one.digitalinnovation.personapi.dto;




import lombok.Data;



import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;




@Data
public class PersonDto {

    @NotEmpty
    @Size(min = 2, max = 100)
    private String firstName;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String lastName;

    @NotEmpty
    private String cpf;

    @NotEmpty
    private String birthDate;

    @NotEmpty
    private String rg;

    @NotEmpty
    private String phone;
}





