package com.booking.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.booking.helper.Message;
import com.booking.model.Booking;
import com.booking.model.Room;
import com.booking.model.Type;
import com.booking.service.BookingService;
import com.booking.service.RoomService;
import com.booking.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.booking.model.User;
import com.booking.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserService userService;

	@Autowired
	private TypeService typeService;

	@Autowired
	private RoomService roomService;

	@Autowired
	private BookingService bookingService;


	@ModelAttribute
	public void addcommanData(Model model, Principal principal) {
		String name = principal.getName();
		User user = userService.findUserByEmail(name);
		System.out.println(user);
		model.addAttribute("user", user);

		List<User> listOfUser = userService.GetAllUser();
		model.addAttribute("listOfUser", listOfUser);

		List<Type> listOfType = typeService.GetAllTypes();
		model.addAttribute("listOfType", listOfType);

		List<Room> listOfRoom = roomService.GetAllRooms();
		model.addAttribute("listOfRoom", listOfRoom);

		List<Booking> listOfBooking = bookingService.getAllBooking();
		model.addAttribute("listOfBooking", listOfBooking);

	}

	@GetMapping("/index")
	public String AdminDashboard(Model model) {
		
		long typeCount = typeService.GetTypeCount();
		model.addAttribute("typeCount", typeCount);

		long roomCount = roomService.RoomCount();
		model.addAttribute("roomCount", roomCount);

		long bookingCount = bookingService.BookingCount();
		model.addAttribute("bookingCount", bookingCount);

		long userCount = userService.UserCount();
		model.addAttribute("userCount", userCount);

		model.addAttribute("title", "Admin Dashboard");

		return "admin/dashboard";
	}

	//type
	@GetMapping("/managerType")
	public String managerType(Model model) {

		List<Type> getAllTypeDetails = typeService.GetAllTypes();
		model.addAttribute("getAllTypeDetails",getAllTypeDetails);

		model.addAttribute("title", "Type Manager");

		return "admin/managerType";
	}

	@PostMapping("/addType")
	public String AddType(@ModelAttribute("type") Type type, HttpSession session, Model model) {

		try {
			Type addType = typeService.AddType(type);

			if (addType != null) {
				session.setAttribute("message", new Message(" Type Added Successfully !!", "alert-success"));
			} else {
				session.setAttribute("message", new Message("Server Problem Try Again !!", "alert-danger"));
			}

		} catch (Exception e) {
			// TODO: handle exception
			session.setAttribute("message", new Message("Server Problem Try Again !!", "alert-danger"));
			return "redirect:/admin/managerType";
		}

		return "redirect:/admin/managerType";

	}

	@PostMapping("/editType")
	public String EditType(@ModelAttribute("type") Type type, HttpSession session, Model model) {
		try {
			Type addType = typeService.AddType(type);

			if (addType != null) {

				session.setAttribute("message", new Message(" Type Edit Successfully !!", "alert-success"));
			} else {
				session.setAttribute("message", new Message("Server Problem Try Again !!", "alert-danger"));
			}

		} catch (Exception e) {
			// TODO: handle exception
			session.setAttribute("message", new Message("Server Problem Try Again !!", "alert-danger"));
			return "redirect:/admin/managerType";
		}

		return "redirect:/admin/managerType";

	}

	@GetMapping("/deleteType/{typeId}")
	public String deleteType(@PathVariable("typeId") Integer typeId, Model model, HttpSession session) {
		try{
			typeService.DeleteTypeById(typeId);
			session.setAttribute("message", new Message("Delete Type Successfully !!", "alert-success"));
		} catch (Exception e){
			session.setAttribute("message", new Message("Server Problem Try Again !!", "alert-danger"));
			return "redirect:/admin/managerType";
		}

		return "redirect:/admin/managerType";

	}

	//room
	@GetMapping("/managerRoom")
	public String managerRoom(Model model) {
		List<Room> getAllRoom = roomService.GetAllRooms();
		model.addAttribute("getAllRoom", getAllRoom);

		model.addAttribute("title", "Manager-Rooms");

		return "admin/managerRoom";
	}

	@PostMapping("/addRoom")
	public String AddRoom(@ModelAttribute("room") Room room, @RequestParam("photo") MultipartFile file, Model model, HttpSession session) {

		try {
			room.setImage(file.getOriginalFilename());

			File savefile = new ClassPathResource("static/image").getFile();

			Path path = Paths.get(savefile.getAbsolutePath() + File.separator + file.getOriginalFilename());

			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			Room room1 = roomService.AddRoom(room);

			if (room1 != null) {

				session.setAttribute("message", new Message(" Room Added Successfully !!", "alert-success"));
			} else {
				session.setAttribute("message", new Message("Server Problem Try Again !!", "alert-danger"));
			}

		} catch (Exception e) {
			// TODO: handle exception
			session.setAttribute("message", new Message("Server Problem Try Again !!", "alert-danger"));
			return "redirect:/admin/managerRoom";
		}

		return "redirect:/admin/managerRoom";
	}

	@PostMapping("/editRoom")
	public String EditRoom(@ModelAttribute("room") Room room, @RequestParam("photo") MultipartFile file,
			Model model, HttpSession session) {

		try {

			room.setImage(file.getOriginalFilename());

			File savefile = new ClassPathResource("static/image").getFile();

			Path path = Paths.get(savefile.getAbsolutePath() + File.separator + file.getOriginalFilename());

			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			Room room1 = roomService.AddRoom(room);

			if (room1 != null) {

				session.setAttribute("message", new Message(" Room Edit Successfully !!", "alert-success"));
			} else {
				session.setAttribute("message", new Message("Server Problem Try Again !!", "alert-danger"));
			}

		} catch (Exception e) {
			// TODO: handle exception
			session.setAttribute("message", new Message("Server Problem Try Again !!", "alert-danger"));
			return "redirect:/admin/managerRoom";
		}

		return "redirect:/admin/managerRoom";
	}

	@GetMapping("/deleteRoom/{roomId}")
	public String deleteRoom(@PathVariable("roomId") Integer roomId, Model model, HttpSession session) {
		try{
			roomService.deleteRoom(roomId);
			session.setAttribute("message", new Message(" Delete Room Successfully !!", "alert-success"));
		} catch (Exception e){
			session.setAttribute("message", new Message("Server Problem Try Again !!", "alert-danger"));
			return "redirect:/admin/managerRoom";
		}
		return "redirect:/admin/managerRoom";
	}

	// booking
	@GetMapping("/managerBooking")
	public String bookRoom(Model model){
		List<Booking> allBooking = bookingService.getAllBooking();
		model.addAttribute("allBooking", allBooking);
		model.addAttribute("title", "Booking-Room");
		return "admin/managerBooking";
	}

	@PostMapping("/addBooking")
	public String AddBooking(@ModelAttribute("booking") Booking booking, HttpSession session, Model model) {

		try {
			Booking addBooking = bookingService.addBooking(booking);

			if (addBooking != null) {
				session.setAttribute("message", new Message(" Booking Added Successfully !!", "alert-success"));
			} else {
				session.setAttribute("message", new Message("Server Problem Try Again !!", "alert-danger"));
			}

		} catch (Exception e) {
			// TODO: handle exception
			session.setAttribute("message", new Message("Server Problem Try Again !!", "alert-danger"));
			return "redirect:/admin/managerBooking";
		}

		return "redirect:/admin/managerBooking";

	}

	@PostMapping("/editBooking")
	public String EditType(@ModelAttribute("booking") Booking booking, HttpSession session, Model model) {
		try {
			Booking addBooking = bookingService.addBooking(booking);

			if (addBooking != null) {
				session.setAttribute("message", new Message(" Booking Added Successfully !!", "alert-success"));
			} else {
				session.setAttribute("message", new Message("Server Problem Try Again !!", "alert-danger"));
			}

		} catch (Exception e) {
			// TODO: handle exception
			session.setAttribute("message", new Message("Server Problem Try Again !!", "alert-danger"));
			return "redirect:/admin/managerBooking";
		}

		return "redirect:/admin/managerBooking";
	}

	@GetMapping("/deleteBooking/{bookId}")
	public String deleteBooking(@PathVariable("bookId") Integer bookId, Model model, HttpSession session) {

		bookingService.deleteBooking(bookId);

		return "redirect:/admin/managerBooking";

	}


}
