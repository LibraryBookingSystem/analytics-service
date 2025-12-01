package com.library.analytics_service.listener;

import com.library.analytics_service.config.RabbitMQConfig;
import com.library.analytics_service.dto.BookingEvent;
import com.library.analytics_service.service.AnalyticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ listener for booking events
 */
@Component
public class BookingEventListener {
    
    private static final Logger logger = LoggerFactory.getLogger(BookingEventListener.class);
    
    private final AnalyticsService analyticsService;
    
    public BookingEventListener(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }
    
    /**
     * Listen to booking.created events
     */
    @RabbitListener(queues = RabbitMQConfig.BOOKING_CREATED_QUEUE)
    public void handleBookingCreated(BookingEvent event) {
        logger.info("Received booking.created event for booking: {}", event.getId());
        analyticsService.recordEvent(
            "booking.created",
            event.getUserId(),
            event.getResourceId(),
            event.getId()
        );
    }
    
    /**
     * Listen to booking.updated events
     */
    @RabbitListener(queues = RabbitMQConfig.BOOKING_UPDATED_QUEUE)
    public void handleBookingUpdated(BookingEvent event) {
        logger.info("Received booking.updated event for booking: {}", event.getId());
        analyticsService.recordEvent(
            "booking.updated",
            event.getUserId(),
            event.getResourceId(),
            event.getId()
        );
    }
    
    /**
     * Listen to booking.canceled events
     */
    @RabbitListener(queues = RabbitMQConfig.BOOKING_CANCELED_QUEUE)
    public void handleBookingCanceled(BookingEvent event) {
        logger.info("Received booking.canceled event for booking: {}", event.getId());
        analyticsService.recordEvent(
            "booking.canceled",
            event.getUserId(),
            event.getResourceId(),
            event.getId()
        );
    }
    
    /**
     * Listen to booking.checked_in events
     */
    @RabbitListener(queues = RabbitMQConfig.BOOKING_CHECKED_IN_QUEUE)
    public void handleBookingCheckedIn(BookingEvent event) {
        logger.info("Received booking.checked_in event for booking: {}", event.getId());
        analyticsService.recordEvent(
            "booking.checked_in",
            event.getUserId(),
            event.getResourceId(),
            event.getId()
        );
    }
    
    /**
     * Listen to booking.no_show events
     */
    @RabbitListener(queues = RabbitMQConfig.BOOKING_NO_SHOW_QUEUE)
    public void handleBookingNoShow(BookingEvent event) {
        logger.info("Received booking.no_show event for booking: {}", event.getId());
        analyticsService.recordEvent(
            "booking.no_show",
            event.getUserId(),
            event.getResourceId(),
            event.getId()
        );
    }
}

