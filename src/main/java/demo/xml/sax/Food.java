package demo.xml.sax;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import static lombok.AccessLevel.PUBLIC;

@Value
@RequiredArgsConstructor(access = PUBLIC)
@Builder
public class Food {
    private int id;
    private String name;
    private String price;
    private String description;
    private int calories;
}
