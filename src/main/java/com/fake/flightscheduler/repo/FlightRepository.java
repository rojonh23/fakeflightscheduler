package com.fake.flightscheduler.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fake.flightscheduler.entity.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, String> {

	List<Flight> findByAirlineIn(List<String> airlines);

}
