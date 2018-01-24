package demo.xml.stax;

import demo.xml.MenuTagName;
import demo.xml.Food;
import demo.xml.Food.FoodBuilder;
import lombok.SneakyThrows;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class StAXMenuParser {

    @SneakyThrows
    public static List<Food> process(XMLStreamReader reader) {
        List<Food> menu = new ArrayList<>();
        FoodBuilder foodBuilder = null;
        MenuTagName elementName = null;
        while (reader.hasNext()) {
            int type = reader.next(); // определение типа "прочтённого" элемента (тега)
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    elementName = MenuTagName.getElementTagName(reader.getLocalName());
                    if (elementName == MenuTagName.FOOD)
                        foodBuilder = Food.builder()
                                .id(parseInt(
                                        reader.getAttributeValue(null,
                                                "id")));
                    break;
                case XMLStreamConstants.CHARACTERS:
                    String text = reader.getText().trim();
                    if (!text.isEmpty())
                        switch (elementName) {
                            case NAME:
                                foodBuilder.name(text);
                                break;
                            case PRICE:
                                foodBuilder.price(text);
                                break;
                            case DESCRIPTION:
                                foodBuilder.description(text);
                                break;
                            case CALORIES:
                                foodBuilder.calories(parseInt(text));
                        }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    elementName = MenuTagName.getElementTagName(reader.getLocalName());
                    if (elementName == MenuTagName.FOOD)
                        menu.add(foodBuilder.build());
            }
        }
        return menu;
    }
}
