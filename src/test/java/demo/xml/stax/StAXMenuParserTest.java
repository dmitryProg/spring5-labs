package demo.xml.stax;

import demo.xml.sax.Food;
import demo.xml.sax.stax.StAXMenuParser;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.xml.stream.XMLInputFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class StAXMenuParserTest {

    @Test
    @SneakyThrows
    @DisplayName("process method works correctly")
    void process() {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        try (InputStream input = new FileInputStream(
                "./src/main/resources/breakfast-menu.xml")) {

            foodsTest(StAXMenuParser.process(
                    inputFactory.createXMLStreamReader(input)));


        }

    }

    public static void foodsTest(Iterable<Food> foods) {
        //noinspection unchecked
        val foodIterator = (Iterator<Food>) Mockito.mock(Iterator.class);
        Mockito.when(foodIterator.next())
                .thenReturn(new Food(
                        1,
                        "Belgian Waffles",
                        "$5.95",
                        "two of our famous Belgian Waffles with plenty of real maple syrup",
                        650))
                .thenReturn(new Food(2,
                        "Strawberry Belgian Waffles",
                        "$7.95",
                        "light Belgian waffles covered with strawberries and whipped cream",
                        900));

        for (Food food : foods)
            assertThat(food, is(foodIterator.next()));
    }
}