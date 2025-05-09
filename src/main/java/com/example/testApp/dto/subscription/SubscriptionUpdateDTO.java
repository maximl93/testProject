package com.example.testApp.dto.subscription;

import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class SubscriptionUpdateDTO {
    private JsonNullable<String> title;
}
