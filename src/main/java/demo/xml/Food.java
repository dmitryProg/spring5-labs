package demo.xml;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import static lombok.AccessLevel.PUBLIC;

@Value
@Builder
@RequiredArgsConstructor(access = PUBLIC)
public class Food {
    private int id;
    private String name;
    private String price;
    private String description;
    private int calories;
}
