package com.example.testApp.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(includeFieldNames = true, onlyExplicitlyIncluded = true)
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private long id;

    @NotBlank
    @ToString.Include
    private String firstName;
    @NotBlank
    @ToString.Include
    private String lastName;

    @Email
    @Column(unique = true)
    @ToString.Include
    private String email;

    @ManyToMany(fetch = FetchType.LAZY,
        cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
        }
    )
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "subscription_id")}
    )
    private Set<Subscription> subscriptions = new HashSet<>();


    public void addSubscription(Subscription subscription) {
        this.subscriptions.add(subscription);
        subscription.getSubscribers().add(this);
    }

    public void removeSubscription(long sub_id) {
        Subscription subscription = this.subscriptions.stream()
                .filter(s -> s.getId() == sub_id)
                .findFirst().orElse(null);
        if (subscription != null) {
            this.subscriptions.remove(subscription);
            subscription.getSubscribers().remove(this);
        }
    }
}
