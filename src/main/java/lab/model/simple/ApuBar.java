package lab.model.simple;

import lab.model.Bar;
import lab.model.CustomerBrokenException;
import lab.model.Drink;
import lab.model.Person;
import org.springframework.stereotype.Component;

@Component
public class ApuBar implements Bar {

    @Override
	public Drink sellSquishee(Person person)  {
        if (person.isBroke())
            throw new CustomerBrokenException();

        System.out.println(("Here is your Squishee \n"));
        return new Squishee("Usual Squishee");
    }
}