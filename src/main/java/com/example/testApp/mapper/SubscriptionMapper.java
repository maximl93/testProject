package com.example.testApp.mapper;

import com.example.testApp.dto.subscription.SubscriptionCreateDTO;
import com.example.testApp.dto.subscription.SubscriptionDTO;
import com.example.testApp.dto.subscription.SubscriptionUpdateDTO;
import com.example.testApp.model.Subscription;
import com.example.testApp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(
        uses = {JsonNullableMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class SubscriptionMapper {
    public abstract Subscription map (SubscriptionCreateDTO createDTO);
    @Mapping(target = "countSubscribers", source = "subscribers", qualifiedByName = "sizeToInt")
    public abstract SubscriptionDTO map(Subscription subscription);
    public abstract void update(SubscriptionUpdateDTO updateDTO, @MappingTarget Subscription subscription);


    @Named("sizeToInt")
    public int getSubscribersCount(Set<User> subscribers) {
        return subscribers.size();
    }
}
