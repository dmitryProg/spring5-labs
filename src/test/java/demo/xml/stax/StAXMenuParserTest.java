package demo.xml.stax;

import lab.common.function.ExceptionalFunction;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileWriter;
import java.io.InputStream;

import static demo.xml.sax.MenuSaxHandlerTest.foodsTest;

class StAXMenuParserTest {

    @Test
    @SneakyThrows
    @DisplayName("parse method works correctly")
    void parse() {
        val inputFactory = XMLInputFactory.newInstance();
        try (InputStream input = StAXMenuParserTest.class.getResourceAsStream("/breakfast-menu.xml")) {
            foodsTest(StAXMenuParser.process(
                    inputFactory.createXMLStreamReader(
                            input)));
        }
    }

    @Test
    @SneakyThrows
    @DisplayName("write method works correctly")
    void write() {
//        val xmlOutputFactory = XMLOutputFactory.newInstance();
//        FileWriter fileWriter = ExceptionalFunction.supply(FileWriter::new, "output2.xml")
//                .getOrThrowUnchecked();
//
//        Closeable.with(xmlOutputFactory.createXMLStreamWriter(fileWriter), writer -> {
//            writer.writeStartDocument();
//            writer.writeStartElement("document");
//            writer.writeStartElement("data");
//            writer.writeAttribute("name", "value");
//            writer.writeCharacters("content");
//            writer.writeEndElement();
//            writer.writeEndElement();
//            writer.writeEndDocument();
//
//            writer.flush();
//        }, XMLStreamWriter::close);
    }
}