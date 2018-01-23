package demo.xml.sax;

import demo.xml.stax.StAXMenuParserTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParserFactory;

class MenuSaxHandlerTest {
    @Test
    @SneakyThrows
    @DisplayName("parse method works correctly")
    void parse() {
//        XMLReader reader = XMLReaderFactory.createXMLReader();
        XMLReader reader = SAXParserFactory.newDefaultInstance().newSAXParser().getXMLReader();
        MenuSaxHandler handler = new MenuSaxHandler();
        reader.setContentHandler(handler);
        reader.parse(new InputSource("./src/main/resources/breakfast-menu.xml"));

        StAXMenuParserTest.foodsTest(handler.getFoodList());
    }
}