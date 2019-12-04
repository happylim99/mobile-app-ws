package com.sean.ws.ui.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sean.ws.service.AddressService;
import com.sean.ws.service.UserService;
import com.sean.ws.shared.Utils;
import com.sean.ws.shared.dto.AddressDto;
import com.sean.ws.shared.dto.UserDto;
import com.sean.ws.ui.model.request.UserDetailsRequestModel;
import com.sean.ws.ui.model.response.AddressesRest;
import com.sean.ws.ui.model.response.DataRest;
import com.sean.ws.ui.model.response.OperationStatusModel;
import com.sean.ws.ui.model.response.RequestOperationStatus;
import com.sean.ws.ui.model.response.UserRest;

@EnableHypermediaSupport(type = { HypermediaType.HAL })
@RestController
@RequestMapping("/users") //http:localhost:8080/users
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	AddressService addressService;
	
	@Autowired
	PagedResourcesAssembler<AddressesRest> pagedResourcesAssembler;
	
	@Autowired
	Utils utils;
	/*
	@Autowired
	private ServletContext context;
	context.getPath();
	*/
	
	@GetMapping("/hello")
	public String hello()
	{
		return "hello";
	}
	
	@GetMapping(path="/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public UserRest getUser(@PathVariable String id)
	{		
		//UserRest returnValue = new UserRest();
		
		UserDto userDto = userService.getUserByUserId(id);
		ModelMapper modelMapper = new ModelMapper();
		UserRest returnValue = modelMapper.map(userDto, UserRest.class);
		//BeanUtils.copyProperties(userDto, returnValue);
		
		return returnValue;
	}
	
	@PostMapping(
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
			)
	public UserRest CreateUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception
	{
		//UserRest returnValue = new UserRest();
		
		if(userDetails.getFirstName().isEmpty()) throw new NullPointerException("null pointer exception");
		//if(userDetails.getFirstName().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		
		//UserDto userDto = new UserDto();
		//Bean Utils.copyProperties doesn't do a good job when working with objects that contain other objects
		//BeanUtils.copyProperties(userDetails, userDto);
		
		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);
		
		UserDto createdUser = userService.createUser(userDto);
		//BeanUtils.copyProperties(createdUser, returnValue);
		UserRest returnValue = modelMapper.map(createdUser, UserRest.class);
		
		return returnValue;
	}
	
	@PutMapping(path="/{id}", 
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
			)
	public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) throws Exception
	{
		UserRest returnValue = new UserRest();
		
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		
		UserDto updatedUser = userService.updateUser(id, userDto);
		BeanUtils.copyProperties(updatedUser, returnValue);
		
		return returnValue;
	}
	
	@DeleteMapping(path="/{id}",
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
			)
	public OperationStatusModel deleteUser(@PathVariable String id)
	{
		OperationStatusModel returnValue = new OperationStatusModel();
		
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		
		userService.deleteUser(id);
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}
	
	@GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public List<UserRest> getUsers(@RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="limit", defaultValue="25") int limit)
	{
		List<UserRest> returnValue = new ArrayList<>();
		
		List<UserDto> users = userService.getUsers(page, limit);
		
		for(UserDto userDto : users) {
			UserRest userModel = new UserRest();
			BeanUtils.copyProperties(userDto, userModel);
			returnValue.add(userModel);
		}
		
		return returnValue;
	}
	
	@GetMapping(path="/all", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	//public Page<UserRest> getAllUsers(@RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="limit", defaultValue="25") int limit, @RequestParam(value="sort", defaultValue="firstName;asc", required=false) String[] sort)
	//public Resources<UserRest> getAllUsers(@RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="limit", defaultValue="25") int limit)
	public Resources<UserRest> getAllUsers(@RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="limit", defaultValue="25") int limit, @RequestParam(value="sort", defaultValue="firstName;asc", required=false) String[] sort, HttpServletRequest request)
	{
		
		int thisPage = page-1;
		
		Sort allSorts = utils.sortProcessor(sort);
		
		Pageable pageable = PageRequest.of(thisPage, limit, allSorts);
		
		Page<UserDto> users = userService.getAllUsers2(pageable);
		
		Boolean isFirst = users.isFirst();
		Boolean isLast = users.isLast();
		int totalPages = users.getTotalPages();
		int totalElements = (int)users.getTotalElements();
		int betweenSize = 3;
		List<Link> pageInfo = utils.paginationLinks(isFirst, isLast, totalPages, totalElements, page, limit, sort, betweenSize);
    	/*
		List<Object> paginationLinks = new ArrayList<Object>();
		paginationLinks.add(isFirst);
		paginationLinks.add(isLast);
		paginationLinks.add(totalPages);
		paginationLinks.add(totalElements);
		paginationLinks.add(page);
		paginationLinks.add(limit);
		paginationLinks.add(sort);
		paginationLinks.add(betweenSize);
		*/
		Object[] obj = {isFirst, isLast, totalPages, totalElements, page, limit, sort, betweenSize};
		
		//String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
		//System.out.println(baseUrl);
		
		PageImpl<UserRest> pageImpl = new PageImpl<UserRest>(users.stream().map(user -> new UserRest(user, obj)).collect(Collectors.toList()), pageable, totalElements);
		
		//return pageImpl;
		return new Resources<>(pageImpl, pageInfo);
		//return new Resources<>(pageImpl, utils.pageInfo());
	}
	
	@GetMapping(path="/all/constructor/pagination", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	//public Page<UserRest> getAllUsers(@RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="limit", defaultValue="25") int limit, @RequestParam(value="sort", defaultValue="firstName;asc", required=false) String[] sort)
	//public Resources<UserRest> getAllUsers(@RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="limit", defaultValue="25") int limit)
	public DataRest getResponse(@RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="limit", defaultValue="25") int limit, @RequestParam(value="sort", defaultValue="firstName;asc", required=false) String[] sort, HttpServletRequest request)
	{
		int thisPage = page-1;
		
		Sort allSorts = utils.sortProcessor(sort);
		
		Pageable pageable = PageRequest.of(thisPage, limit, allSorts);
		
		Page<UserDto> users = userService.getAllUsers2(pageable);
		
		Boolean isFirst = users.isFirst();
		Boolean isLast = users.isLast();
		int totalPages = users.getTotalPages();
		int totalElements = (int)users.getTotalElements();
		int betweenSize = 3;
		List<Link> pageInfo = utils.paginationLinks(isFirst, isLast, totalPages, totalElements, page, limit, sort, betweenSize);
    	
		Object[] obj = {isFirst, isLast, totalPages, totalElements, page, limit, sort, betweenSize};
		
		PageImpl<UserRest> pageImpl = new PageImpl<UserRest>(users.stream().map(user -> new UserRest(user, obj)).collect(Collectors.toList()), pageable, totalElements);
		//return new DataRest(pageImpl);
		
		List<UserRest> listUsers = pageImpl.getContent();
		Resources<UserRest> usersResource = new Resources<>(listUsers);
		return new DataRest(usersResource, obj);
		//Resources<UserRest> userResources = new Resources<>(pageImpl);
		//return new DataRest(userResources);
	}
	
	@GetMapping(path="/{id}/addresses2", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
	public List<AddressesRest> getUserAddresses(@RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="limit", defaultValue="25") int limit, @PathVariable String id)
	{
		List<AddressesRest> returnValue = new ArrayList<>();
		
		List<AddressDto> addressDto = addressService.getAddresses(page, limit, id);
		
		if(addressDto != null && !addressDto.isEmpty())
		{
			java.lang.reflect.Type listType = new TypeToken<List<AddressesRest>>() {}.getType();
			returnValue = new ModelMapper().map(addressDto, listType);
			
			for (AddressesRest addressRest : returnValue) {
				Link addressLink = linkTo(methodOn(UserController.class).getUserAddress(id, addressRest.getAddressId()))
						.withSelfRel();
				addressRest.add(addressLink);

				Link userLink = linkTo(methodOn(UserController.class).getUser(id)).withRel("user");
				addressRest.add(userLink);
			}
		}
		
		return returnValue;
	}
	
	@GetMapping(path="/{id}/addresses", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
	public Page<AddressesRest> getUserAddresses2(@RequestParam(value="page", defaultValue="1", required=false) int page, @RequestParam(value="limit", defaultValue="25", required=false) int limit, @RequestParam(value="sort", defaultValue="city;asc", required=false) String[] sort, @PathVariable String id)
	//public Resources<AddressesRest> getUserAddresses2(@RequestParam(value="page", defaultValue="1", required=false) int page, @RequestParam(value="limit", defaultValue="25", required=false) int limit, @RequestParam(value="sort", defaultValue="city;asc", required=false) String[] sort, @PathVariable String id)
	{
		//Page<AddressesRest> returnValue = new ArrayList<>();
		Sort allSorts = utils.sortProcessor(sort);
		
		Pageable pageable = PageRequest.of(page, limit, allSorts);
		
		Page<AddressDto> addresses = addressService.getAllAddresses(id, pageable);
		
		if(addresses != null && !addresses.isEmpty())
		{
			//java.lang.reflect.Type listType = new TypeToken<List<AddressesRest>>() {}.getType();
			//returnValue = new ModelMapper().map(addressDto, listType);
			//Pageable pageable = PageRequest.of(page, limit);
			int totalElements = (int) addresses.getTotalElements();
			
			PageImpl<AddressesRest> pageImpl = new PageImpl<AddressesRest>(addresses.stream().map(address -> new AddressesRest(address)).collect(Collectors.toList()), pageable, totalElements);
			
			return pageImpl;
			//return new Resources<>(pageImpl);
		}
		
		return null;
	}
	
	@GetMapping(path="/{id}/addresses/{address}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	//public Page<AddressesRest> getUserAddress(@PathVariable String id, @PathVariable String address)
	public Resource<AddressesRest> getUserAddress(@PathVariable String id, @PathVariable String address)
	{		
		//UserRest returnValue = new UserRest();
		
		
		AddressDto addressDto = addressService.getAddressByAddressId(address);
		ModelMapper modelMapper = new ModelMapper();
		AddressesRest returnValue = modelMapper.map(addressDto, AddressesRest.class);
		/*
		List<Link> links = new ArrayList<Link>(Arrays.asList(
				new Link("http://localhost:8080/mobile-app-ws/users")
				));
		returnValue.add(links);
		*/
		//BeanUtils.copyProperties(userDto, returnValue);
		
		//Link addressLink = linkTo(UserController.class).slash(id).slash("addresses").slash(address).withSelfRel();
		//Link userLink = linkTo(UserController.class).slash(id).withRel("user");
		//Link addressesLink = linkTo(UserController.class).slash(id).slash("addresses").withRel("addresses");
		
		Link addressLink = linkTo(methodOn(UserController.class).getUserAddress(id, address)).withSelfRel();
		Link userLink = linkTo(methodOn(UserController.class).getUser(id)).withRel("user");
		Link addressesLink = linkTo(methodOn(UserController.class).getUserAddresses(1, 3, id)).withRel("addresses");
		
		returnValue.add(addressLink);
		returnValue.add(addressLink);
		returnValue.add(userLink);
		returnValue.add(addressesLink);
		return new Resource<>(returnValue);
	}
	
	/*
     * http://localhost:8080/mobile-app-ws/users/email-verification?token=sdfsdf
     * */
    @GetMapping(path = "/email-verification", produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public OperationStatusModel verifyEmailToken(@RequestParam(value = "token") String token) {

        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.VERIFY_EMAIL.name());
        
        boolean isVerified = userService.verifyEmailToken(token);
        
        if(isVerified)
        {
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        } else {
            returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
        }
        
        return returnValue;
    }

}
