package com.example.testApp.mapper;

import com.example.testApp.dto.subscription.SubscriptionCreateDTO;
import com.example.testApp.dto.subscription.SubscriptionDTO;
import com.example.testApp.dto.subscription.SubscriptionUpdateDTO;
import com.example.testApp.model.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        uses = {JsonNullableMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class SubscriptionMapper {
    public abstract Subscription map (SubscriptionCreateDTO createDTO);
    public abstract SubscriptionDTO map(Subscription subscription);
    public abstract void update(SubscriptionUpdateDTO updateDTO, @MappingTarget Subscription subscription);
}
