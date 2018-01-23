package lab.model.simple;

import lab.model.Country;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Country")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleCountry implements Country {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String codeName;

    public SimpleCountry(String name, String codeName) {
        this.name = name;
        this.codeName = codeName;
    }
}
