package com.hall.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.hall.booking.model.Book;
import com.hall.booking.model.Customer;
import com.hall.booking.model.Employee;
import com.hall.booking.model.LoginUser;
import com.hall.booking.model.MarriageHall;
import com.hall.booking.repository.MarriageHallRepository;
import com.hall.booking.services.BookService;
import com.hall.booking.services.EmployeeService;
import com.hall.booking.services.MarriageHallService;

@Controller
public class MarriageHallController {

	@Autowired
	private MarriageHallService marrHallService;

	@Autowired
	private MarriageHallRepository repo;
	// display list of employees
	@GetMapping("/halls")
	public String viewHomePage(Model model) {
		return findPaginated(1, "name", "asc", model);		
	}
	@GetMapping("/halls_user")
	public String viewUserPage(Model model) {
		return findPaginatedsuser(1, "name", "asc", model);		
	}
	@GetMapping("/showNewHallForm")
	public String showNewHallForm(Model model) {
		// create model attribute to bind form data
		MarriageHall hall = new MarriageHall();
		model.addAttribute("hall", hall);
		return "new_hall";
	}
	@Autowired
	BookService bookService;
	@PostMapping("/saveHall")
	public String saveHall(@ModelAttribute("hall") MarriageHall hall,@RequestParam("image") MultipartFile multipartFile) {
		// save employee to database
		 String fileName = org.springframework.util.StringUtils.cleanPath(multipartFile.getOriginalFilename());
	     hall.setPhotos(fileName);
 		
		 MarriageHall savedUser = repo.save(hall);
		 String uploadDir = "user-photos/" + savedUser.getId();
		 
	       // FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		return "redirect:/halls";
	}
	
	@GetMapping("/hall/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
		
		// get employee from the service
		MarriageHall hall = marrHallService.getMarriageHallById(id);
		
		// set employee as a model attribute to pre-populate the form
		model.addAttribute("hall", hall);
		return "update_hall";
	}
	
	
	@GetMapping("/hall/showFormFormBook/{id}")
	public String showFormFormBook(@PathVariable ( value = "id") long id, Model model) {
		
		// get employee from the service
		MarriageHall hall = marrHallService.getMarriageHallById(id);
		
		// set employee as a model attribute to pre-populate the form
		model.addAttribute("hall", hall);
		return "book_hall";
	}
	
	@GetMapping("/deleteHall/{id}")
	public String deleteEmployee(@PathVariable (value = "id") long id) {
		
		// call delete employee method 
		this.marrHallService.deleteMarriageHallById(id);
		return "redirect:/halls";
	}
	@PostMapping("/saveBook")
	public String saveBook(@RequestParam("user") String user,@RequestParam("id") String id,@RequestParam("userdate") String userdate) {
		Book book= new Book();
		book.setUser(""+LoginController.lid);
		book.setHall(id);
		book.setBdate(userdate);
		this.bookService.saveBook(book);
		return "redirect:/halls_user";
		
	}
	
	
	@GetMapping("/hall/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;
		
		Page<MarriageHall> page = marrHallService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<MarriageHall> listHalls = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listHalls", listHalls);
		return "index_hall";
	}
	@GetMapping("user/hall/page/{pageNo}")
	public String findPaginatedsuser(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;
		
		Page<MarriageHall> page = marrHallService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<MarriageHall> listHalls = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listHalls", listHalls);
		return "userhalls";
	}
	
	
	@GetMapping("/hall/showFormForUserReg")
	public String showFormForUserReg(Model model) {
		
		// get employee from the service
		Customer user=new Customer();
		
		// set employee as a model attribute to pre-populate the form
		model.addAttribute("customer", user);
		return "new_customer";
		
	}
	
}
