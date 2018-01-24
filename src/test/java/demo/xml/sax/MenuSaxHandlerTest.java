package demo.xml.sax;

import demo.xml.Food;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MenuSaxHandlerTest {

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

    @Test
    @SneakyThrows
    @DisplayName("parse method works correctly")
    void parse() {
        XMLReader reader =
                //XMLReaderFactory.createXMLReader();
                SAXParserFactory.newDefaultInstance().newSAXParser().getXMLReader();
        MenuSaxHandler handler = new MenuSaxHandler();
        reader.setContentHandler(handler);
        try (InputStream inputStream = MenuSaxHandlerTest.class.getResourceAsStream("/breakfast-menu.xml")) {
            reader.parse(new InputSource(inputStream));
        }

        foodsTest(handler.getFoodList());
    }
}