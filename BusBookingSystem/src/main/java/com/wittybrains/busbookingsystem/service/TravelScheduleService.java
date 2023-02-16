package com.wittybrains.busbookingsystem.service;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wittybrains.busbookingsystem.model.TravelSchedule;
import com.wittybrains.busbookingsystem.repository.TravelScheduleRepository;

@Service
public class TravelScheduleService {

    @Autowired
    private TravelScheduleRepository scheduleRepository;

    public List<TravelSchedule> getAvailableSchedules(String source, String destination, LocalDate searchDate) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime searchDateTime = LocalDateTime.of(searchDate, LocalTime.MIDNIGHT);
        LocalDateTime minimumDateTime;

        if (searchDateTime.isBefore(currentDateTime)) {
            // cannot search for past schedules
            throw new IllegalArgumentException("Cannot search for schedules in the past");
        } else if (searchDateTime.toLocalDate().equals(currentDateTime.toLocalDate())) {
            // search for schedules at least 1 hour from now
            currentDateTime = currentDateTime.plusHours(1);
        }

        List<TravelSchedule> travelScheduleList = scheduleRepository
                .findBySourceAndDestinationAndEstimatedArrivalTimeAfter(source, destination, currentDateTime);
        return travelScheduleList;
    }
}
