package com.sean.ws.shared;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.sean.ws.security.SecurityConstants;
import com.sean.ws.ui.controller.UserController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

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
			pageInfo.add(linkTo(methodOn(UserController.class).getAllUsers(1, limit, sortStringArray, null)).withRel("first"));
		}
		if(page > 0) {
			int prePage = page-1;
			pageInfo.add(linkTo(methodOn(UserController.class).getAllUsers(prePage, limit, sortStringArray, null)).withRel("previous"));
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
				pageInfo.add(linkTo(methodOn(UserController.class).getAllUsers(i, limit, sortStringArray, null)).withRel("between"));
			}
		}
		if(page < totalPages) {
			int nextPage = page+1;
			pageInfo.add(linkTo(methodOn(UserController.class).getAllUsers(nextPage, limit, sortStringArray, null)).withRel("next"));
		}
		if(!isLast) {
			pageInfo.add(linkTo(methodOn(UserController.class).getAllUsers(totalPages, limit, sortStringArray, null)).withRel("last"));
		}
		
		return pageInfo;
    }
    
    public Map<String, Object[]> linksConstructor(Boolean isFirst, Boolean isLast, int totalPages, int totalElements, int page, int limit, String[] sort, int betweenSize)
    {
    	String joinedString = null;
		if(sort.length > 1) {
			joinedString = String.join("&sort=", sort);
		}
		String[] sortStringArray = new String[] {joinedString};
		
		//List<Object> pageInfo = new ArrayList<>();
		Map<String, Object[]> pageInfo=new HashMap<String, Object[]>();
		
		if(!isFirst) {
			Link link = linkTo(methodOn(UserController.class).getAllUsers(1, limit, sortStringArray, null)).withRel("first");
			pageInfo.put("first", new Object[] {linkToStringProcessor(link)});
		} else {
			pageInfo.put("first", new Object[] {"Null"});
		}
		
		if(page > 0) {
			int prePage = page-1;
			Link link = linkTo(methodOn(UserController.class).getAllUsers(prePage, limit, sortStringArray, null)).withRel("previous");
			pageInfo.put("previous", new Object[] {linkToStringProcessor(link)});
		} else {
			pageInfo.put("previous", new Object[] {"Null"});
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
			Object[] btw = new Object[betweenSize];
			int a = 0;
			for(int i = startNumber; i <= endNumber; i++) {
				Link link = linkTo(methodOn(UserController.class).getAllUsers(i, limit, sortStringArray, null)).withRel("between");
				btw[a] = linkToStringProcessor(link);
				a++;
				//pageInfo.put("between", btw. {linkToStringProcessor(link)});
			}
			pageInfo.put("between", btw);
			//System.out.println(pageInfo.get("between")[0]);
		}
		if(page < totalPages) {
			int nextPage = page+1;
			Link link = linkTo(methodOn(UserController.class).getAllUsers(nextPage, limit, sortStringArray, null)).withRel("next");
			pageInfo.put("next", new Object[] {linkToStringProcessor(link)});
		} else {
			pageInfo.put("next", new Object[] {"Null"});
		}
		
		if(!isLast) {
			Link link = linkTo(methodOn(UserController.class).getAllUsers(totalPages, limit, sortStringArray, null)).withRel("last");
			pageInfo.put("last", new Object[] {linkToStringProcessor(link)});
		} else {
			pageInfo.put("last", new Object[] {"Null"});
		}
		//System.out.println(pageInfo.get("between")[1]);
		return pageInfo;
    }
    
    public static String getUrl(HttpServletRequest req) {

	    String scheme = req.getScheme();             // http
	    String serverName = req.getServerName();     // hostname.com
	    int serverPort = req.getServerPort();        // 80
	    String contextPath = req.getContextPath();   // /mywebapp
	    String servletPath = req.getServletPath();   // /servlet/MyServlet
	    String pathInfo = req.getPathInfo();         // /a/b;c=123
	    String queryString = req.getQueryString();          // d=789

	    // Reconstruct original requesting URL
	    StringBuilder url = new StringBuilder();
	    url.append(scheme).append("://").append(serverName);

	    if (serverPort != 80 && serverPort != 443) {
	        url.append(":").append(serverPort);
	    }

	    url.append(contextPath).append(servletPath);

	    if (pathInfo != null) {
	        url.append(pathInfo);
	    }
	    if (queryString != null) {
	        url.append("?").append(queryString);
	    }
	    return url.toString();
	}
    
    private static String linkToStringProcessor(Link link)
    {
    	String returnValue = link.toString();
    	return returnValue.substring(1, returnValue.lastIndexOf(";")-1);
    }
    
    public static boolean hasTokenExpired(String token) {
		boolean returnValue = false;

		try {
			Claims claims = Jwts.parser()
					.setSigningKey(SecurityConstants.getTokenSecret())
					.parseClaimsJws(token)
					.getBody();

			Date tokenExpirationDate = claims.getExpiration();
			Date todayDate = new Date();

			return tokenExpirationDate.before(todayDate);
		} catch (ExpiredJwtException ex) {
			returnValue = true;
		}

		return returnValue;
	}
    
    public String generateEmailVerificationToken(String userId) {
        String token = Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
                .compact();
        return token;
    }
    
    public static String generatePasswordResetToken(String userId)
    {
        String token = Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.PASSWORD_RESET_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
                .compact();
        return token;
    }
    
    // read file without using @Values
    public static Properties fetchProperties(String theFile){
    	
        Properties properties = new Properties();
        try {
            //File file = ResourceUtils.getFile("classpath:mail.properties");
        	File file = ResourceUtils.getFile(theFile);
            InputStream in = new FileInputStream(file);
            properties.load(in);
        } catch (IOException e) {
        	System.out.println(e.getMessage());
        }
        return properties;
    }
    /*
    public static Properties loadProperties(String resourceFileName) throws IOException {
        Properties configuration = new Properties();
        InputStream inputStream = PropertiesLoader.class
          .getClassLoader()
          .getResourceAsStream(resourceFileName);
        configuration.load(inputStream);
        inputStream.close();
        return configuration;
    }
    */
    public static String plateFormatProcessor(String str)
    {
    	//add a space after the numbers
    	String step1 = str.replaceAll("(-?\\d\\.?\\d*)([^\\d\\s])", "$1 $2");
    	
    	//add a space before the numbers
    	String step2 = step1.replaceAll("([^0-9\\-\\s])(-?\\d\\.?\\d*)", "$1 $2");
    	
    	String returnValue = step2.trim().replaceAll(" +", " ");
    	
    	return returnValue;
    }

}
