package com.booking.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String name;

	@Column(unique = true)
	@NotNull(message = "Email Already Exist in System !! ")
	private String email;

	@Size(max = 10, min = 10, message = "Invalid Phone No")
	private String mobileno;

	private String password;

	private String role;

	private boolean enabled;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Booking> bookings;

	public User() {
		super();
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

//	public List<BusBook> getBusBooks() {
//		return busBooks;
//	}
//
//	public void setBusBooks(List<BusBook> busBooks) {
//		this.busBooks = busBooks;
//	}
//
//	public List<FlightBook> getFlightBooks() {
//		return flightBooks;
//	}
//
//	public void setFlightBooks(List<FlightBook> flightBooks) {
//		this.flightBooks = flightBooks;
//	}
//
//	public List<TrainBook> getTrainBooks() {
//		return trainBooks;
//	}
//
//	public void setTrainBooks(List<TrainBook> trainBooks) {
//		this.trainBooks = trainBooks;
//	}
//
//	public List<HotelBook> getHotelBooks() {
//		return hotelBooks;
//	}
//
//	public void setHotelBooks(List<HotelBook> hotelBooks) {
//		this.hotelBooks = hotelBooks;
//	}
//
//	public List<HolidayPackageBook> getHolidayPackageBooks() {
//		return holidayPackageBooks;
//	}
//
//	public void setHolidayPackageBooks(List<HolidayPackageBook> holidayPackageBooks) {
//		this.holidayPackageBooks = holidayPackageBooks;
//	}

}
