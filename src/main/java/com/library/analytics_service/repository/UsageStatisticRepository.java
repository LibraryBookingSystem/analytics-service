package com.library.analytics_service.repository;

import com.library.analytics_service.entity.UsageStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository for UsageStatistic entity
 */
@Repository
public interface UsageStatisticRepository extends JpaRepository<UsageStatistic, Long> {
    
    /**
     * Find statistics by date
     */
    List<UsageStatistic> findByDate(LocalDate date);
    
    /**
     * Find statistics by resource ID
     */
    List<UsageStatistic> findByResourceId(Long resourceId);
    
    /**
     * Find statistics by date and resource ID
     */
    Optional<UsageStatistic> findByDateAndResourceId(LocalDate date, Long resourceId);
    
    /**
     * Find statistics within date range
     */
    @Query("SELECT s FROM UsageStatistic s WHERE s.date BETWEEN :startDate AND :endDate")
    List<UsageStatistic> findStatisticsBetween(@Param("startDate") LocalDate startDate,
                                               @Param("endDate") LocalDate endDate);
    
    /**
     * Find statistics by resource within date range
     */
    @Query("SELECT s FROM UsageStatistic s WHERE s.resourceId = :resourceId " +
           "AND s.date BETWEEN :startDate AND :endDate ORDER BY s.date")
    List<UsageStatistic> findStatisticsByResourceBetween(@Param("resourceId") Long resourceId,
                                                         @Param("startDate") LocalDate startDate,
                                                         @Param("endDate") LocalDate endDate);
}





