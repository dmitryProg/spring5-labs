package ioc;

import lab.model.simple.SimpleCountry;
import lab.model.Person;
import lab.model.simple.UsualPerson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class HelloWorldTest {

    private static final String APPLICATION_CONTEXT_XML_FILE_NAME = "ioc.xml";

    private BeanFactory context =
            new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_XML_FILE_NAME);

    @Test
    void testInitPerson() {
        assertThat(context.getBean("person"), is(getExpectedPerson()));
    }

    static Person getExpectedPerson() {
        return new UsualPerson(
                1,
                "John Smith",
                35,
                1.78f,
                true,
                false,
                new SimpleCountry(1L, "Russia", "RU"),
                Arrays.asList("asd@asd.ru", "+7-234-456-67-89"));
    }
}
