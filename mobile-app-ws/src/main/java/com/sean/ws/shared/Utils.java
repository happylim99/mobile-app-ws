package com.sean.ws.shared;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.sean.ws.ui.controller.UserController;

@Component
public class Utils {
	
	private final Random RANDOM = new SecureRandom();
	
	private static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	private static final String lower = "abcdefghijklmnopqrstuvwxyz";
	
	private static final String digits = "0123456789";
	
	private static final String ALPHABET = upper + lower + digits;
	
    //private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	
    public String generateUserId(int length) {
        return generateRandomString(length);
    }

	public String generateAddressId(int length) {
        return generateRandomString(length);
    }
    
    private String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return new String(returnValue);
    }
    
    public List<Link> pageInfo()
    {
    	List<Link> pageInfo = new ArrayList<>();
    	pageInfo.add(linkTo(methodOn(UserController.class).getUser("bbb")).withSelfRel());
    	pageInfo.add(linkTo(methodOn(UserController.class).getUser("ccc")).withRel("next"));
    	return pageInfo;
    }
    
    public Sort sortProcessor(String[] sort)
    {
    	Sort allSorts = Sort.by(
				Arrays.stream(sort)
					.map(srt -> srt.split(";", 2))
					.map(array -> orderDirection(array[0], array[1]).ignoreCase()
					).collect(Collectors.toList())
				);
    	return allSorts;
    }
    
    private Order orderDirection(String sortKey, String sortOrder)
	{
		if(sortOrder.equalsIgnoreCase("ASC")) {
			return Order.asc(sortKey);
		} else {
			return Order.desc(sortKey);
		}
	}
    
    public List<Link> paginationLinks(Boolean isFirst, Boolean isLast, int totalPages, int totalElements, int page, int limit, String[] sort, int betweenSize)
    {
    	String joinedString = null;
		if(sort.length > 1) {
			joinedString = String.join("&sort=", sort);
		}
		String[] sortStringArray = new String[] {joinedString};
		
		List<Link> pageInfo = new ArrayList<>();
		if(!isFirst) {
			pageInfo.add(linkTo(methodOn(UserController.class).getAllUsers(1, limit, sortStringArray)).withRel("first"));
		}
		if(page > 0) {
			int prePage = page-1;
			pageInfo.add(linkTo(methodOn(UserController.class).getAllUsers(prePage, limit, sortStringArray)).withRel("previous"));
		}
		if(totalPages > 1) {
			int halfSize = betweenSize/2;
			int extraSize = 0;
			if((betweenSize%2)==0) {
				extraSize = -1;
			}
			int endNumber = page + halfSize + extraSize;
			if(endNumber < betweenSize) {
				endNumber = betweenSize;
			}
			if(totalPages < endNumber) {
				endNumber = totalPages;
			}
			int startNumber = page - halfSize;
			if((endNumber - startNumber) < betweenSize) {
				startNumber = endNumber - betweenSize + 1;
			}
			if(startNumber < 1) {
				startNumber = 1;
			}
			for(int i = startNumber; i <= endNumber; i++) {
				pageInfo.add(linkTo(methodOn(UserController.class).getAllUsers(i, limit, sortStringArray)).withRel("between"));
			}
		}
		if(page < totalPages) {
			int nextPage = page+1;
			pageInfo.add(linkTo(methodOn(UserController.class).getAllUsers(nextPage, limit, sortStringArray)).withRel("next"));
		}
		if(!isLast) {
			pageInfo.add(linkTo(methodOn(UserController.class).getAllUsers(totalPages, limit, sortStringArray)).withRel("last"));
		}
		
		return pageInfo;
    }

}
