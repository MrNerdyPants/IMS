package com.sys.ims.filters;

import com.sys.ims.model.Company;
import com.sys.ims.model.User;
import com.sys.ims.repository.ActivityRepository;
import com.sys.ims.repository.UserRepository;
import com.sys.ims.util.JwtUtil;
import com.sys.ims.util.Utility;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestControllerAdvice
public class RequestInterceptor extends RequestBodyAdviceAdapter {
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private Utility utility;
	@Autowired
	private ActivityRepository activityRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean supports(MethodParameter methodParameter, Type type,
			Class<? extends HttpMessageConverter<?>> aClass) {
		String methodName = methodParameter.getMethod().getName();
		if (methodName.equals("authenticate")) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) {

		// First find the session of user like user and validate that user have session
		// or expired
		String token = jwtUtil
				.parseJwt(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
		if (token != null && !token.isEmpty()) {
			boolean updatedBy = false, updatedByField = false, createdByField = false, companyField = false;
			Claims claims = jwtUtil.getClaims(token);
			// Fetch User Information & Find his Company Information
			String userId = claims.get("id").toString();
			Optional<User> user = userRepository.findById(Integer.parseInt(userId));
			Company company = user.get().getCompany();
			// Succefully Fetch Infromation of User & Company
			System.out.println(company.getName() + " --- " + user.get().getUsername());
			// Now update host class set these information like company, createdBy,
			// updatedBy but host class not known then first of find them through proper
			// package Name
			String classname = body.getClass().getSimpleName(); // Which will be simple Dto Class
			// First find the actual Data class by removing suffix of Dto
			if (classname.endsWith("Dto")) {
				classname = classname.substring(0, classname.length() - 3); // Remove Suffix 'Dto'
				// Now find the class from its package which are basically located into package
				// 'com.sys.ims.model'. So find class first then
				Class<?> hostClassDto = body.getClass();
				try {
					Class<?> hostClass = Class.forName("com.sys.ims.model." + classname);
					// Check hostclass have some fields like as company or createdBy or updatedBy
					Field idField = hostClassDto.getDeclaredField("id");
					idField.setAccessible(true);
					Object isValuObject = idField.get(hostClassDto);
					if (isValuObject != null && !((String) isValuObject).isEmpty()
							&& !(isValuObject instanceof Number && ((Number) isValuObject).intValue() == 0)) {
						updatedBy = true;
					}

					Field[] fields = hostClass.getFields();
					for (Field field : fields) {
						if (field.getName().equals("createdBy")) {
							Object checkObject = field.get(hostClassDto); // Get the value of the 'updatedBy' field
							if (checkObject != null) {
								createdByField = true; // Mark that we found a valid updatedBy
							}

						}
						if (field.getName().equals("updatedBy")) {
							Object checkObject = field.get(hostClassDto); // Get the value of the 'updatedBy' field
							if (checkObject != null) {
								updatedByField = true; // Mark that we found a valid updatedBy
							}
						}
						if (field.getName().equals("company")) {
							Object checkObject = field.get(hostClassDto); // Get the value of the 'updatedBy' field
							if (checkObject != null) {
								companyField = true; // Mark that we found a valid updatedBy
							}
						}
					}
					// Set Values Accordingly For final Result Set Information
					if (updatedBy) {
						// In update we need only update updatedBy only
						if (updatedByField) {
							updateUserField(hostClass, "updatedBy", user.get());
						}
					} else {
						// For New DataInformation we need user as well as his company information
						if (createdByField) {
							updateUserField(hostClass, "createdBy", user.get());
						}
						if (companyField) {
							updateCompanyField(hostClass, "updatedBy", company);
						}
					}
				} catch (ClassNotFoundException e) {
					System.out.println("Class not found: " + e.getMessage());
				} catch (NoSuchFieldException e) {
					System.out.println("Field not found: " + e.getMessage());
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// Update Activity Information for Log Audit
			String message = "Create or Update operation is perform on the " + classname + " by " + user.get().getName() + ".";
			utility.recordActivity(message, user.get(), company, user.get().getProfile(), activityRepository);
		}
		return body;
	}

	// @Override
	// public Object afterBodyRead(Object body, HttpInputMessage inputMessage,
	// MethodParameter parameter, Type targetType,
	// Class<? extends HttpMessageConverter<?>> converterType) {
	// Object dto = new Object();
	// String operation = "assign", fullName = "user", profile = "", companyId = "";
	// if (!(body instanceof List<?>)) {
	// classname = body.getClass().getSimpleName();
	// // String[] classnameArr = classname.split(".");
	// // classname = classnameArr[classnameArr.length-2];
	// classname = classname.replace("Dto", "");
	// if (body.equals("companyId")) {
	// try {
	// companyId = body.getClass().getField("companyId").toString();
	// } catch (NoSuchFieldException | SecurityException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }

	// }
	// dto = body;
	// String token = jwtUtil
	// .parseJwt(((ServletRequestAttributes)
	// RequestContextHolder.getRequestAttributes()).getRequest());
	// if (token != null) {
	// Claims claims = jwtUtil.getClaims(token);
	// if (dto.getId() == null) {
	// dto.setCreatedBy(claims.get("id").toString());
	// }
	// if (dto.getId() != null) {
	// dto.setUpdateBy(claims.get("id").toString());
	// }
	// dto.setFullName(claims.get("fullName").toString());
	// fullName = claims.get("fullName").toString();
	// Optional<User> user = userRepository.findById((Integer) claims.get("id"));
	// if (user.isPresent()) {
	// if (user.get().getProfile() != null) {
	// profile = user.get().getProfile();
	// }
	// }
	// companyId = (claims.get("companyId") != null
	// && Utility.isNotNullAndEmpty(claims.get("companyId").toString())
	// ? claims.get("companyId").toString()
	// : null);
	// dto.setCompanyId(companyId);
	// operation = (Utility.isNotNullAndEmpty(dto.getId()) ? "update" : "create");
	// }

	// }

	// String message = operation + " operation is perform on the " + classname + "
	// by " + fullName + ".";
	// utility.recordActivity(message, fullName, companyId,
	// profile,activityRepository);
	// return body;
	// }

	public static void updateUserField(Object obj, String userFieldName, User user) {
		try {
			// Get the class of the object
			Class<?> hostClass = obj.getClass();
			// Access the field by name
			Field field = hostClass.getDeclaredField(userFieldName);
			field.setAccessible(true); // Make it accessible if it's private
			field.set(obj, user); // Update the value
		} catch (NoSuchFieldException e) {
			System.out.println("Field not found: " + userFieldName);
		} catch (IllegalAccessException e) {
			System.out.println("Cannot access field: " + userFieldName);
		}
	}

	public static void updateCompanyField(Object obj, String companyFieldName, Company company) {
		try {
			// Get the class of the object
			Class<?> hostClass = obj.getClass();
			// Access the field by name
			Field field = hostClass.getDeclaredField(companyFieldName);
			field.setAccessible(true); // Make it accessible if it's private
			field.set(obj, company); // Update the value
		} catch (NoSuchFieldException e) {
			System.out.println("Field not found: " + companyFieldName);
		} catch (IllegalAccessException e) {
			System.out.println("Cannot access field: " + companyFieldName);
		}
	}
}
