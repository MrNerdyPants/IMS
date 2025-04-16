package com.sys.ims.service.impl;

import com.sys.ims.dto.*;
import com.sys.ims.exception.BaseException;
import com.sys.ims.model.*;
import com.sys.ims.repository.*;
import com.sys.ims.service.RightService;
import com.sys.ims.service.UserService;
import com.sys.ims.util.ApiResponse;
import com.sys.ims.util.Constants;
import com.sys.ims.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RightServiceImpl implements RightService {

	@Autowired
	private RightRepository rightRepository;
	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private GroupRightRepository groupRightRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private SiteRepository siteRepository;
	@Autowired
	private UserRepository usersRepository;
	@Autowired
	private DesignationRepository designationRepository;
	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private Utility utility;

	@Override
	public ApiResponse saveRight(RightDto rightDto) {
		Right right = new Right();
		if (Utility.isNotNull(rightDto) && Utility.isNotNullAndEmpty(rightDto.getId())) {
			Optional<Right> result = rightRepository.findById(Integer.parseInt(rightDto.getId()));
			if (result.isPresent()) {
				// if (Utility.nameExist(rightDto, result.get(), rightRepository, false, false))
				// {
				// throw new
				// BaseException.NameExistException(Constants.API_RESPONSE_NAME_ALREADY_IN_USE);
				// }
				utility.copyNonNullProperties(rightDto, result.get());
				right.setParent(findRightById(rightDto.getParentId()));
				rightRepository.save(result.get());
				return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS, null);
			}
			return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND,
					null);
		} else {
			// if (Utility.nameExist(rightDto, right, rightRepository, true, false)) {
			// throw new
			// BaseException.NameExistException(Constants.API_RESPONSE_NAME_ALREADY_IN_USE);
			// }
			utility.copyNonNullProperties(rightDto, right);
			right.setParent(findRightById(rightDto.getParentId()));
			right = rightRepository.save(right);
			return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS,
					Utility.isNotNull(right) ? right.getId() : null);
		}
	}

	@Override
	public Boolean deleteRight(String rightId) {
		int id = Integer.parseInt(rightId);
		rightRepository.deleteById(id);
		Boolean flag = rightRepository.existsById(id);
		return !flag;
	}

	@Override
	public List<Group> getGroups() {
		return groupRepository.findAll();
	}

	@Override
	public List<Right> getRights() {
		return rightRepository.findByParentIsNullOrderBySortAsc();
	}

	@Override
	public List<GroupRight> getGroupRights(List<String> groupsId) {
		List<Integer> groupInteger = groupsId.stream().map(id -> Integer.parseInt(id)).collect(Collectors.toList());
		List<Group> groups = groupRepository.findAllByIdIn(groupInteger);
		return groupRightRepository.findByGroupIn(groups);
	}

	@Override
	public List<Right> getParentRights() {
		return rightRepository.findByParentIsNullOrderBySortAsc();
	}

	@Override
	public Integer getRightChildNbr(String id) {
		Integer nbr = 1;
		Optional<Right> right = rightRepository.findTopByParentIdOrderBySortDesc(id);
		if (right.isPresent()) {
			nbr = (right.get().getSort() + 1);
		}

		return nbr;
	}

	@Override
	public ApiResponse saveGroup(GroupDto groupDto) {
		Group group = new Group();
		if (Utility.isNotNull(groupDto) && Utility.isNotNullAndEmpty(groupDto.getId())) {
			Optional<Group> result = groupRepository.findById(Integer.parseInt(groupDto.getId()));
			if (result.isPresent()) {
				// if (Utility.nameExist(groupDto, result.get(), groupRepository, false, false))
				// {
				// throw new
				// BaseException.NameExistException(Constants.API_RESPONSE_NAME_ALREADY_IN_USE);
				// }
				utility.copyNonNullProperties(groupDto, result.get());
				groupRepository.save(result.get());
				return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS, null);
			}
			return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND,
					null);
		} else {
			// if (Utility.nameExist(groupDto, group, groupRepository, true, false)) {
			// throw new
			// BaseException.NameExistException(Constants.API_RESPONSE_NAME_ALREADY_IN_USE);
			// }
			utility.copyNonNullProperties(groupDto, group);
			group = groupRepository.save(group);
			return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS,
					Utility.isNotNull(group) ? group.getId() : null);
		}
	}

	@Override
	public Boolean deleteGroup(String groupId) {
		int id = Integer.parseInt(groupId);
		groupRepository.deleteById(id);
		Boolean flag = groupRepository.existsById(id);
		return !flag;
	}

	@Override
	public ApiResponse saveGroupRight(List<GroupRightDto> groupRightsDto) {
		List<GroupRight> currentGroupRight = groupRightRepository
				.findAllByGroupId(groupRightsDto.get(0).getGroupId());
		List<Integer> roleRights = new ArrayList<>();
		for (GroupRightDto groupRightDto : groupRightsDto) {
			GroupRight groupRight = new GroupRight();
			if (currentGroupRight != null && currentGroupRight.size() > 0) {
				groupRight = currentGroupRight.stream()
						.filter(curRight -> curRight != null && curRight.getRight().getId() != 0
								&& curRight.getRight().getId() == Integer.parseInt(groupRightDto.getRightId()))
						.findFirst().orElse(new GroupRight());
			}
			// Set Right & Group

			utility.copyNonNullProperties(groupRightDto, groupRight);
			groupRight.setGroup(findGroupById(groupRightDto.getGroupId()));
			groupRight.setRight(findRightById(groupRightDto.getRightId()));
			if (groupRight.getRight().getId() > 0) {
				roleRights.add(groupRight.getRight().getId());
			}
			groupRightRepository.save(groupRight);
		}
		// Delete unwanted Records
		for (GroupRightDto groupRightDto : groupRightsDto) {
			if (!roleRights.contains(Integer.parseInt(groupRightDto.getRightId()))) {
				groupRightRepository.deleteByRightIdAndGroupId(Integer.parseInt(groupRightDto.getRightId()),
						Integer.parseInt(groupRightDto.getGroupId()));
			}
		}
		return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS, roleRights);
	}

	@Override
	public ApiResponse saveCompany(CompanyDto companyDto) {
		Company company = new Company();
		if (Utility.isNotNull(companyDto) && Utility.isNotNullAndEmpty(companyDto.getId())) {
			Optional<Company> result = companyRepository.findById(Integer.parseInt(companyDto.getId()));
			if (result.isPresent()) {
				// if (Utility.nameExist(companyDto, result.get(), companyRepository, false,
				// false)) {
				// throw new
				// BaseException.NameExistException(Constants.API_RESPONSE_NAME_ALREADY_IN_USE);
				// }
				// if (Utility.phoneExist(companyDto, result.get(), companyRepository, false,
				// false)) {
				// throw new
				// BaseException.NameExistException(Constants.API_RESPONSE_CONTACT_ALREADY_IN_USE);
				// }
				// if (Utility.emailExist(companyDto, result.get(), companyRepository, false,
				// false)) {
				// throw new
				// BaseException.NameExistException(Constants.API_RESPONSE_EMAIL_ALREADY_IN_USE);
				// }
				utility.copyNonNullProperties(companyDto, result.get());
				result.get().setCountry(findCountryById(companyDto.getCountryId()));
				result.get().setState(findStateById(companyDto.getStateId()));
				result.get().setCity(findCityById(companyDto.getCityId()));
				if (companyDto.getPhone() != null && !companyDto.getPhone().isEmpty()) {
					for (String phone : companyDto.getPhone()) {
						CompanyContact cc = result.get().getPhone().stream()
								.filter(contact -> contact.getContactNo().equals(phone)).findFirst()
								.orElse(new CompanyContact());
						cc.setCompany(result.get());
						cc.setContactNo(phone);
						result.get().getPhone().add(cc);
					}
				}
				if (companyDto.getCategory() != null && !companyDto.getCategory().isEmpty()) {
					for (String categoryId : companyDto.getCategory()) {
						CompanyCategory cc = result.get().getCategory().stream()
								.filter(cat -> cat.getCategory().getId() == Integer.parseInt(categoryId)).findFirst()
								.orElse(new CompanyCategory());
						cc.setCategory(findCategoryById(categoryId));
						cc.setCompany(result.get());
						result.get().getCategory().add(cc);
					}
				}
				if (companyDto.getLogo() != null && !companyDto.getLogo().isEmpty()) {
					byte[] imageBytes = Base64.getDecoder().decode(companyDto.getLogo()); // Decode base64 string
					result.get().setLogo(imageBytes);
				}
				companyRepository.save(result.get());
				return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS, null);
			}
			return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND,
					null);
		} else {
			UserDto userDto = new UserDto();
			// if (Utility.nameExist(companyDto, company, companyRepository, true, false)) {
			// throw new
			// BaseException.NameExistException(Constants.API_RESPONSE_NAME_ALREADY_IN_USE);
			// }
			// if (Utility.phoneExist(companyDto, company, companyRepository, true, false))
			// {
			// throw new
			// BaseException.ContactExistException(Constants.API_RESPONSE_CONTACT_ALREADY_IN_USE);
			// }
			// if (Utility.emailExist(companyDto, company, companyRepository, true, false))
			// {
			// throw new
			// BaseException.NameExistException(Constants.API_RESPONSE_EMAIL_ALREADY_IN_USE);
			// }
			utility.copyNonNullProperties(companyDto, company);
			if (Utility.isNotNullAndEmpty(companyDto.getLoginId())
					&& Utility.isNotNullAndEmpty(companyDto.getLoginPassword())) {
				User users = new User();
				utility.copyNonNullProperties(companyDto, userDto);
				userDto.setUsername(companyDto.getLoginId());
				userDto.setPassword(companyDto.getLoginPassword());
				userDto.setType("company");
				List<Group> groups = groupRepository.findAllByName("company");
				if (groups.size() > 0) {
					userDto.setGroups(
							groups.stream().map(group -> String.valueOf(group.getId())).collect(Collectors.toList()));
				}
				if (usernameExist(userDto, users, true)) {
					throw new BaseException.EmailExistException(Constants.API_RESPONSE_LOGIN_ALREADY_IN_USE);
				}
			}
			if (companyDto.getLogo() != null && !companyDto.getLogo().isEmpty()) {
				byte[] imageBytes = Base64.getDecoder().decode(companyDto.getLogo()); // Decode base64 string
				company.setLogo(imageBytes);
			}
			company.setCountry(findCountryById(companyDto.getCountryId()));
			company.setState(findStateById(companyDto.getStateId()));
			company.setCity(findCityById(companyDto.getCityId()));
			if (companyDto.getPhone() != null && !companyDto.getPhone().isEmpty()) {
				for (String phone : companyDto.getPhone()) {
					CompanyContact cc = new CompanyContact();
					cc.setCompany(company);
					cc.setContactNo(phone);
					company.getPhone().add(cc);
				}
			}

			if (companyDto.getCategory() != null && !companyDto.getCategory().isEmpty()) {
				for (String categoryId : companyDto.getCategory()) {
					CompanyCategory cc = new CompanyCategory();
					cc.setCategory(findCategoryById(categoryId));
					cc.setCompany(company);
					company.getCategory().add(cc);
				}
			}
			company = companyRepository.save(company);
			if (Utility.isNotNullAndEmpty(companyDto.getLoginId())
					&& Utility.isNotNullAndEmpty(companyDto.getLoginPassword())) {
				userDto.setCompanyId(String.valueOf(company.getId()));
				userDto.setRefId(String.valueOf(company.getId())); 
				userService.saveUser(userDto);
			}
			return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS,
					Utility.isNotNull(company) ? company.getId() : null);
		}
	}

	public boolean usernameExist(UserDto usersDto, User users, boolean isNewEntry) {
		if (Utility.isNotNullAndEmpty(usersDto.getUsername())) {
			Optional<User> checkUsername = usersRepository.findByUsername(usersDto.getUsername());
			if (isNewEntry && checkUsername.isPresent()) {
				return true;
			} else
				return !isNewEntry && checkUsername == null && users.getId() == 0;
		}
		return false;
	}

	@Override
	public List<Company> getCompanies() {
		List<Company> companies = companyRepository.findAll();
		return companies;
	}

	@Override
	public Company getCompanyById(String companyId) {
		Optional<Company> comp = companyRepository.findById(Integer.parseInt(companyId));
		return comp.get();
	}

	@Override
	public Boolean deleteCompany(String id) {
		companyRepository.deleteById(Integer.parseInt(id));
		Boolean flag = companyRepository.existsById(Integer.parseInt(id));
		return !flag;
	}

	@Override
	@Transactional
	public ApiResponse saveEmployee(EmployeeDto dto) {
		Employee entity = new Employee();
		int siteId = ((dto.getSiteId() != null && !dto.getSiteId().isEmpty()) ? Integer.parseInt(dto.getSiteId()) : 0);
		if (siteId != 0 && siteRepository.existsById(siteId)) {
			Optional<Site> site = siteRepository.findById(siteId);
			entity.setSite(site.get());
		}
		if (Utility.isNotNull(dto) && Utility.isNotNullAndEmpty(dto.getId())) {
			Optional<Employee> result = employeeRepository.findById(Integer.parseInt(dto.getId()));
			if (result.isPresent()) {
				// if (Utility.nameExist(dto, result.get(), employeeRepository, false, true)) {
				// throw new
				// BaseException.NameExistException(Constants.API_RESPONSE_NAME_ALREADY_IN_USE);
				// }
				// if (Utility.phoneExist(dto, result.get(), employeeRepository, false, true)) {
				// throw new
				// BaseException.NameExistException(Constants.API_RESPONSE_CONTACT_ALREADY_IN_USE);
				// }
				// if (Utility.emailExist(dto, result.get(), employeeRepository, false, true)) {
				// throw new
				// BaseException.NameExistException(Constants.API_RESPONSE_EMAIL_ALREADY_IN_USE);
				// }
				utility.copyNonNullProperties(dto, result.get());
				result.get().setDepartment(findDepartmentById(dto.getDepartment()));
				result.get().setDesignation(findDesignationById(dto.getDesignation()));
				result.get().setSite(findSiteById(dto.getSiteId()));
				result.get().setUpdateBy(findUserById(dto.getUpdatedBy()));
				if (dto.getPhone() != null && !dto.getPhone().isEmpty()) {
					for (String contactNo : dto.getPhone()) {
						EmployeePhone sp = result.get().getPhone().stream()
								.filter(contact -> contact.getPhoneNo().equals(contactNo)).findFirst()
								.orElse(new EmployeePhone());
						sp.setEmployee(result.get());
						sp.setPhoneNo(contactNo);
						result.get().getPhone().add(sp);
					}
				}

				employeeRepository.save(result.get());
				return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS, null);
			}
			return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND,
					null);
		} else {
			// if (Utility.nameExist(dto, entity, employeeRepository, true, true)) {
			// throw new
			// BaseException.NameExistException(Constants.API_RESPONSE_NAME_ALREADY_IN_USE);
			// }
			// if (Utility.phoneExist(dto, entity, employeeRepository, true, true)) {
			// throw new
			// BaseException.NameExistException(Constants.API_RESPONSE_CONTACT_ALREADY_IN_USE);
			// }
			// if (Utility.emailExist(dto, entity, employeeRepository, true, true)) {
			// throw new
			// BaseException.NameExistException(Constants.API_RESPONSE_EMAIL_ALREADY_IN_USE);
			// }
			UserDto userDto = new UserDto();
			utility.copyNonNullProperties(dto, entity);
			if (Utility.isNotNullAndEmpty(dto.getLoginId()) && Utility.isNotNullAndEmpty(dto.getLoginPassword())) {
				User users = new User();
				utility.copyNonNullProperties(dto, userDto);
				userDto.setUsername(dto.getLoginId());
				userDto.setPassword(dto.getLoginPassword());
				userDto.setType("employee");
				List<Group> groups = groupRepository.findAllByName("employee");
				if (groups.size() > 0) {
					userDto.setGroups(
							groups.stream().map(group -> String.valueOf(group.getId())).collect(Collectors.toList()));
				}
				if (usernameExist(userDto, users, true)) {
					throw new BaseException.EmailExistException(Constants.API_RESPONSE_LOGIN_ALREADY_IN_USE);
				}
			}

			entity.setDepartment(findDepartmentById(dto.getDepartment()));
			entity.setDesignation(findDesignationById(dto.getDesignation()));
			entity.setSite(findSiteById(dto.getSiteId()));
			entity.setCreatedBy(findUserById(dto.getCreatedBy()));
			entity.setCompany(findCompanyById(dto.getCompanyId()));
			if (dto.getPhone() != null && !dto.getPhone().isEmpty()) {
				for (String contactNo : dto.getPhone()) {
					EmployeePhone sp = new EmployeePhone();
					sp.setEmployee(entity);
					sp.setPhoneNo(contactNo);
					entity.getPhone().add(sp);
				}
			}
			entity = employeeRepository.save(entity);
			if (Utility.isNotNullAndEmpty(dto.getLoginId()) && Utility.isNotNullAndEmpty(dto.getLoginPassword())) {
				userDto.setRefId(String.valueOf(entity.getId()));
				userService.saveUser(userDto);
			}
			return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS,
					Utility.isNotNull(entity) ? entity.getId() : null);
		}
	}

	@Override
	public List<Employee> getEmployee() {
		List<Employee> employees = employeeRepository.findAll();
		for (Employee employee : employees) {
			employee.setSite(Utility.isNotNullAndEmpty(employee.getSite().getId())
					? siteRepository.findById(employee.getSite().getId()).get()
					: null);
		}
		return employees;
	}

	@Override
	public List<Employee> getEmployeeByCompany(String id) {
		List<Employee> employees = employeeRepository.findAllByCompanyId(id);
		for (Employee employee : employees) {
			employee.setSite(Utility.isNotNullAndEmpty(employee.getSite().getId())
					? siteRepository.findById(employee.getSite().getId()).get()
					: null);
		}
		return employees;
	}

	@Override
	public Boolean deleteEmployee(String employeeId) {
		int id = Integer.parseInt(employeeId);
		employeeRepository.deleteById(id);
		Boolean flag = employeeRepository.existsById(id);
		return !flag;
	}

	@Override
	public ApiResponse saveSite(SiteDto dto) {
		Site entity = new Site();
		if (Utility.isNotNull(dto) && Utility.isNotNullAndEmpty(dto.getId())) {
			Optional<Site> result = siteRepository.findById(Integer.parseInt(dto.getId()));
			if (result.isPresent()) {
				// if (Utility.nameExist(dto, result.get(), siteRepository, false, true)) {
				// throw new
				// BaseException.NameExistException(Constants.API_RESPONSE_NAME_ALREADY_IN_USE);
				// }
				utility.copyNonNullProperties(dto, result.get());
				result.get().setCountry(findCountryById(dto.getCountryId()));
				result.get().setState(findStateById(dto.getStateId()));
				result.get().setCity(findCityById(dto.getCityId()));
				if (dto.getPhone() != null && !dto.getPhone().isEmpty()) {
					result.get().setPhone(new ArrayList<>());
					for (String contactNo : dto.getPhone()) {
						SitePhone sp = result.get().getPhone().stream()
								.filter(contact -> contact.getPhoneNo().equals(contactNo)).findFirst()
								.orElse(new SitePhone());
						sp.setSite(entity);
						sp.setPhoneNo(contactNo);
						result.get().getPhone().add(sp);
					}
				}
				siteRepository.save(result.get());
				return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS, null);
			}
			return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND,
					null);
		} else {
			// if (Utility.nameExist(dto, entity, siteRepository, true, true)) {
			// throw new
			// BaseException.NameExistException(Constants.API_RESPONSE_NAME_ALREADY_IN_USE);
			// }
			utility.copyNonNullProperties(dto, entity);
			// get Company and set
			entity.setCountry(findCountryById(dto.getCountryId()));
			entity.setState(findStateById(dto.getStateId()));
			entity.setCity(findCityById(dto.getCityId()));
			if (dto.getPhone() != null && !dto.getPhone().isEmpty()) {
				entity.setPhone(new ArrayList<>());
				for (String contactNo : dto.getPhone()) {
					SitePhone sp = new SitePhone();
					sp.setSite(entity);
					sp.setPhoneNo(contactNo);
					entity.getPhone().add(sp);
				}
			}
			entity = siteRepository.save(entity);
			return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS,
					Utility.isNotNull(entity) ? entity.getId() : null);
		}
	}

	@Override
	public List<Site> getSites(String companyId) {
		List<Site> companies = siteRepository.findAllByCompanyId(companyId);
		return companies;
	}

	@Override
	public Boolean deleteSite(String deleteSiteId) {
		int id = Integer.parseInt(deleteSiteId);
		siteRepository.deleteById(id);
		Boolean flag = siteRepository.existsById(id);
		return !flag;
	}

	Category findCategoryById(String categoryId) {
		if (categoryId != null && !categoryId.isEmpty()) {
			Optional<Category> category = categoryRepository.findById(Integer.parseInt(categoryId));
			return category.get();
		}
		return null;
	}

	Group findGroupById(String categoryId) {
		if (categoryId != null && !categoryId.isEmpty()) {
			Optional<Group> category = groupRepository.findById(Integer.parseInt(categoryId));
			return category.get();
		}
		return null;
	}

	Right findRightById(String categoryId) {
		if (categoryId != null && !categoryId.isEmpty()) {
			Optional<Right> category = rightRepository.findById(Integer.parseInt(categoryId));
			return category.get();
		}
		return null;
	}

	Country findCountryById(String categoryId) {
		if (categoryId != null && !categoryId.isEmpty()) {
			Optional<Country> category = countryRepository.findById(Integer.parseInt(categoryId));
			return category.get();
		}
		return null;
	}

	State findStateById(String categoryId) {
		if (categoryId != null && !categoryId.isEmpty()) {
			Optional<State> category = stateRepository.findById(Integer.parseInt(categoryId));
			return category.get();
		}
		return null;
	}

	City findCityById(String categoryId) {
		if (categoryId != null && !categoryId.isEmpty()) {
			Optional<City> category = cityRepository.findById(Integer.parseInt(categoryId));
			return category.get();
		}
		return null;
	}

	Site findSiteById(String categoryId) {
		if (categoryId != null && !categoryId.isEmpty()) {
			Optional<Site> category = siteRepository.findById(Integer.parseInt(categoryId));
			return category.get();
		}
		return null;
	}

	User findUserById(String userId) {
		if (userId != null && !userId.isEmpty()) {
			Optional<User> user = usersRepository.findById(Integer.parseInt(userId));
			return user.get();
		}
		return null;
	}

	Company findCompanyById(String companyId) {
		if (companyId != null && !companyId.isEmpty()) {
			Optional<Company> company = companyRepository.findById(Integer.parseInt(companyId));
			return company.get();
		}
		return null;
	}

	Department findDepartmentById(String companyId) {
		if (companyId != null && !companyId.isEmpty()) {
			Optional<Department> company = departmentRepository.findById(Integer.parseInt(companyId));
			return company.get();
		}
		return null;
	}

	Designation findDesignationById(String companyId) {
		if (companyId != null && !companyId.isEmpty()) {
			Optional<Designation> company = designationRepository.findById(Integer.parseInt(companyId));
			return company.get();
		}
		return null;
	}

}
