package org.example.model;

import lombok.*;
import org.example.annotation.DatabaseField;
import org.example.annotation.FieldType;


@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class User {

    @DatabaseField(type = FieldType.UNIQUE)
    private final Long account;

    @DatabaseField
    private final String name;

    @DatabaseField
    private final Double value;

}
