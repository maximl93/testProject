package com.example.testApp.repository;

import com.example.testApp.model.Subscription;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findByTitle(String title);
    List<Subscription> findSubscriptionsBySubscribersId(Long subscriberId);

    @Query("SELECT s FROM Subscription s LEFT JOIN s.subscribers u GROUP BY s ORDER BY COUNT(u) DESC")
    List<Subscription> findTop3ByUsersCount(Pageable pageable);

}
