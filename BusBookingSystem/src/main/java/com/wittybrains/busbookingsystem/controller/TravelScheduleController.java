package com.wittybrains.busbookingsystem.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wittybrains.busbookingsystem.model.Bus;
import com.wittybrains.busbookingsystem.model.Driver;
import com.wittybrains.busbookingsystem.model.TravelSchedule;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.wittybrains.busbookingsystem.dto.TravelScheduleDTO;
import com.wittybrains.busbookingsystem.exception.InvalidSearchDateException;
import com.wittybrains.busbookingsystem.exception.InvalidSourceOrDestinationException;
import com.wittybrains.busbookingsystem.repository.BusRepository;
import com.wittybrains.busbookingsystem.repository.DriverRepository;
import com.wittybrains.busbookingsystem.repository.TravelScheduleRepository;

@RestController
@RequestMapping("/schedules")
public class TravelScheduleController {
	@Autowired
	private TravelScheduleRepository scheduleRepository;
	@Autowired
	private BusRepository busRepository;
	@Autowired
	private DriverRepository driverRepository;
	 //@GetMapping
	//@PostMapping
//	    public TravelSchedule createSchedule(@RequestBody TravelSchedule schedule) {
//		 
//	        return scheduleRepository.save(schedule);
//	    }
	
	 @PostMapping
	 public TravelSchedule createSchedule(@RequestBody TravelScheduleDTO travelScheduleDTO)
	 {
	     // get the driver and bus entities using their IDs
	    // Optional<Driver> optionalDriver = driverRepository.findById(travelScheduleDTO.getDriverId());
	                     
	     Optional<Bus> optionalBus = busRepository.findById(travelScheduleDTO.getBusId());
//	        if(!optionalDriver.isPresent()) {
//	        	System.out.println("driver with id is not found" +travelScheduleDTO.getDriverId());
//	        }
	        if(!optionalBus.isPresent()) {
	        	System.out.println("bus with id is not found" +travelScheduleDTO.getBusId());
	        }
	        
	        TravelSchedule travelschedule=new TravelSchedule();
	      //set the driver and bus entities in the schedule object	     travelschedule.setDriverId(optionalDriver.get())	     travelschedule.setBus(optionalBus.get());
	     travelschedule.setBus(optionalBus.get());
	     //travelschedule.setDriver(optionalDriver.get());
	     //travelschedule.setDepartureTime(travelScheduleDTO.getEstimatedDepartureTime());
	   travelschedule.setDestination(travelScheduleDTO.getDestination());
	   travelschedule.setEstimatedArrivalTime( LocalDateTime.parse(travelScheduleDTO.getEstimatedArrivalTime()));
	   travelschedule.setEstimatedDepartureTime(LocalDateTime.parse(travelScheduleDTO.getEstimatedDepartureTime()));
      travelschedule.setFareAmount(travelScheduleDTO.getFareAmount());
      travelschedule.setSource(travelScheduleDTO.getSource());
      travelschedule.setDate( LocalDate.parse(travelScheduleDTO.getDate()));
     return scheduleRepository.save(travelschedule);
	 }

	
	
	@GetMapping("/journey")
	public List<TravelSchedule> getAvailableSchedules(@RequestParam("source") String source,
			@RequestParam("destination") String destination, @RequestParam("searchDate") String searchDate) {
		LocalDate parsedDate;
		try {
			parsedDate = LocalDate.parse(searchDate);
		} catch (Exception e) {
			throw new InvalidSearchDateException("Invalid search date format");
		}

		LocalDate currentDate = LocalDate.now();

		if (parsedDate.isBefore(currentDate)) {
			throw new InvalidSearchDateException("Search date cannot be in the past");
		}

		LocalDateTime currentDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.now());
		LocalDateTime searchDateTime = LocalDateTime.of(parsedDate, LocalTime.MIDNIGHT);

		if (searchDateTime.isBefore(currentDateTime.plusHours(1))) {
			currentDateTime = currentDateTime.plusHours(1);
		} else {
			currentDateTime = searchDateTime;
		}

		List<TravelSchedule> travelScheduleList = scheduleRepository
				.findBySourceAndDestinationAndEstimatedArrivalTimeAfter(source, destination, currentDateTime);

		if (travelScheduleList.isEmpty()) {
			throw new InvalidSourceOrDestinationException(
					"No schedules found for the specified source and destination");
		}

		return travelScheduleList;
	}
}
