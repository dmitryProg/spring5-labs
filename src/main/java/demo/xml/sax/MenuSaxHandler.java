package demo.xml.sax;

import demo.xml.Food;
import demo.xml.MenuTagName;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

@Log4j2
public class MenuSaxHandler extends DefaultHandler {
    private List<Food> foodList = new ArrayList<>();
    private Food.FoodBuilder foodBuilder;
    private StringBuilder text;

    public List<Food> getFoodList() {
        return foodList;
    }

    @Override
    public void startDocument() throws SAXException {
        log.info("Parsing started");
    }

    @Override
    public void endDocument() throws SAXException {
        log.info("Parsing ended");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        log.info("startElement -> " + "uri: "
                + uri + ", localName: " + localName + ", qName: " + qName);

        text = new StringBuilder();
        if (qName.equals("food"))
            foodBuilder = Food.builder()
                    .id((parseInt(attributes.getValue("id"))));
    }

    @Override
    public void characters(char[] buffer, int start, int length) {
        text.append(buffer, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) {

        switch (MenuTagName.getElementTagName(qName)) {
            case NAME:
                foodBuilder.name(text.toString());
                break;
            case PRICE:
                foodBuilder.price(text.toString());
                break;
            case DESCRIPTION:
                foodBuilder.description(text.toString().trim());
                break;
            case CALORIES:
                foodBuilder.calories(Integer.parseInt(text.toString()));
                break;
            case FOOD:
                foodList.add(foodBuilder.build());
                foodBuilder = null;
                break;
            case BREAKFAST_MENU:
                break;
            default:
                throw new RuntimeException("unsupported tag!");
        }
    }

    @Override
    public void warning(SAXParseException exception) {
        log.warn("WARNING: line {}: {}", exception.getLineNumber(), exception.getMessage());
    }

    @Override
    public void error(SAXParseException exception) {
        log.error("ERROR: line {}: {}", exception.getLineNumber(), exception.getMessage());
    }

    @SneakyThrows
    public void fatalError(SAXParseException exception) {
        log.fatal("FATAL: line {}: {}", exception.getLineNumber(), exception.getMessage());
        throw exception;
    }

}
