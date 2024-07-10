package com.booking.controller;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.booking.model.User;
import com.booking.service.UserService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private TypeService typeService;

	@Autowired
	private RoomService roomService;

	@Autowired
	private BookingService bookingService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;


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
	public String UserIndex(Model model) {
		model.addAttribute("title", "Booking-Website");

		List<User> getAllUser = userService.GetAllUser();

		List<Type> types = typeService.GetAllTypes();
		model.addAttribute("types", types);

		List<Room> rooms = roomService.GetAllRooms();
		model.addAttribute("rooms", rooms);

		List<Booking> getAllBooking = bookingService.getAllBooking();
		model.addAttribute("getAllBooking", getAllBooking);

		return "user/rooms";
	}

	@GetMapping("/findType")
	public String getRooms(Model model) {
		List<Type> typeAndRooms = typeService.GetAllTypes();
		model.addAttribute("typeAndRooms", typeAndRooms);
		return "user/rooms";
	}

	@PostMapping("/findType")
	public String filterRooms(@RequestParam("typeId") int typeId, Model model) {
		List<Room> rooms = roomService.getRoomsByTypeId(typeId);
		model.addAttribute("rooms", rooms);
		return "user/findType";
	}

	@GetMapping("/booking")
	public String managerBooking(Model model){
		List<Booking> getAllBooking = bookingService.getAllBooking();
		model.addAttribute("getAllBooking", getAllBooking);

		model.addAttribute("title", "Manager-Bookings");

		return "user/managerBooking";
	}

	@PostMapping("/addBooking")
	public String AddBooking(@ModelAttribute("booking") Booking booking, HttpSession session, Model model){
		try{
			Booking addBooking = bookingService.addBooking(booking);

			if(addBooking != null){
				session.setAttribute("message", new Message("Booking Successfully!", "alert-success"));
			} else {
				session.setAttribute("message", new Message("Server Problem Try Again !!", "alert-danger"));
			}
		} catch (Exception e){
			session.setAttribute("message", new Message("Server Problem Try Again !!", "alert-danger"));
			return "redirect:/user/managerBooking";
		}
		return "redirect:/user/managerBooking";

	}

	@PostMapping("/editBooking")
	public String EditBooking(@ModelAttribute("booking") Booking booking, HttpSession session, Model model){
		try {
			Booking addBooking = bookingService.addBooking(booking);

			if(addBooking != null){
				session.setAttribute("message", new Message("Booking Successfully!", "alert-success"));
			} else {
				session.setAttribute("message", new Message("Server Problem Try Again !!", "alert-danger"));
			}
		} catch (Exception e){
			session.setAttribute("message", new Message("Server Problem Try Again !!", "alert-danger"));
			return "redirect:/user/rooms";
		}
		return "redirect:/user/rooms";

	}

	@GetMapping("/profile")
	public String getUserProfile(Model model, Principal principal) {
		String email = principal.getName();
		User user = userService.findUserByEmail(email);
		model.addAttribute("user", user);
		return "user-profile";
	}

	@PostMapping("/update")
	public String updateUserProfile(@ModelAttribute("user") User user, HttpSession session, Model model) {
		userService.SaveUser(user);
		session.setAttribute("message", "User information updated successfully.");
		return "redirect:/user/index";
	}

//	@PostMapping("/update")
//	public String updateUserInfo(@ModelAttribute("user") User user,
//								 HttpSession session,
//								 Model model) {
//
//		try{
//			User updateUser = userService.SaveUser(user);
//			if(updateUser != null){
//				session.setAttribute("message", new Message("Update Successfully", "alert-success"));
//			} else{
//				session.setAttribute("message", new Message("Server Problem Try Again !!", "alert-danger"));
//			}
//		} catch (Exception e){
//			session.setAttribute("message", new Message("Server Problem Try Again !!", "alert-danger"));
//			return "redirect:/user/index";
//		}
//		return "redirect:/user/index";
//	}


//		User user = (User) session.getAttribute("user");
//
//		// Cập nhật thông tin người dùng
//		user.setName(name);
//		user.setEmail(email);
//		user.setMobileno(mobileno);
//
//		// Lưu thông tin người dùng đã cập nhật
//		User updatedUser = userService.SaveUser(user);
//
//		// Cập nhật lại thông tin người dùng trong session
//		session.setAttribute("user", updatedUser);
//
//		// Hiển thị thông báo thành công
//		session.setAttribute("msg", "Cập nhật thông tin người dùng thành công!");



	@PostMapping("/change")
	public String ChnagePasword(@RequestParam("old") String old, @RequestParam("new") String NewPassword,
			HttpSession Session, Model model) {

		User user = (User) model.getAttribute("user");

		String upass = user.getPassword();

		if (this.bCryptPasswordEncoder.matches(old, upass)) {

			user.setPassword(this.bCryptPasswordEncoder.encode(NewPassword));

			User saveUser = userService.SaveUser(user);

			Session.setAttribute("msg", "Password Change Successfully !!");

		} else {

			Session.setAttribute("msg", "Invalid Current Password Please Try Again !!");

			return "redirect:/user/index";

		}

		return "redirect:/user/index";

	}

}
