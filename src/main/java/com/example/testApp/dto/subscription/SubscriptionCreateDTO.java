package com.example.testApp.dto.subscription;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionCreateDTO {
    @NotBlank
    private String title;
}
