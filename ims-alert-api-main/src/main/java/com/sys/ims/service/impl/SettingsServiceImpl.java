package com.sys.ims.service.impl;

import com.sys.ims.dto.*;
import com.sys.ims.exception.BaseException;
import com.sys.ims.model.*;
import com.sys.ims.repository.*;
import com.sys.ims.service.SettingsService;
import com.sys.ims.service.UserService;
import com.sys.ims.util.ApiResponse;
import com.sys.ims.util.Constants;
import com.sys.ims.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.cert.PKIXRevocationChecker.Option;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.transaction.Transactional;

@Service
public class SettingsServiceImpl implements SettingsService {

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ActivityRepository activityRepository;
	@Autowired
	private FeatureRepository featureRepository;
	@Autowired
	private UnitRepository unitRepository;
	@Autowired
	private HazardTypeRepository hazardTypeRepository;
	@Autowired
	private HazardRepository hazardRepository;
	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private ManufacturerRepository manufacturerRepository;
	@Autowired
	private ManufacturerCategoryRepository manufacturerCategoryRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private VendorRepository vendorRepository;
	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private DesignationRepository designationRepository;
	@Autowired
	private IconLibraryRepository iconLibraryRepository;
	@Autowired
	private CheckListRepository CheckListRepository;
	@Autowired
	private CheckListCategoryRepository checkListCategoryRepository;
	@Autowired
	private CheckListTypeRepository checkListTypeRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private CompanyCategoryRepository companyCategoryRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UserRepository usersRepository;
	@Autowired
	private HelpRepository helpRepository;
	@Autowired
	private SiteRepository siteRepository;
	@Autowired
	private ProductTypeRepository productTypeRepository;
	@Autowired
	private SymtomRepository symtomRepository;
	@Autowired
	private CorrectiveRepository correctiveRepository;
	@Autowired
	private IssueTypeRepository issueTypeRepository;
	@Autowired
	UserService userService;
	@Autowired
	private Utility utility;

	@Override
	public ApiResponse saveCategory(CategoryDto categoryDto) {
		Category entity = new Category();
		if (Utility.isNotNull(categoryDto) && Utility.isNotNullAndEmpty(categoryDto.getId())) {
			Optional<Category> result = categoryRepository.findById(Integer.parseInt(categoryDto.getId()));
			if (result.isPresent()) {
				// Find Parent Code
				utility.copyNonNullProperties(categoryDto, result.get());
				if (result.get().getParent() != null && categoryDto.getParentId() != null
						&& !categoryDto.getParentId().isEmpty()) {
					Optional<Category> parent = categoryRepository
							.findById(Integer.parseInt(categoryDto.getParentId()));
					result.get().setParent(parent.get());
				}
				if (categoryDto.getTypes() != null) {
					for (int i = 0; i < categoryDto.getTypes().size(); i++) {
						ProductTypeDto tempDto = categoryDto.getTypes().get(i);
						ProductType temp = result.get().getTypes().stream()
								.filter(pt -> !tempDto.getId().isEmpty()
										&& (pt.getId() == Integer.parseInt(tempDto.getId())))
								.findFirst()
								.orElse(new ProductType());
						utility.copyNonNullProperties(tempDto, temp);
						temp.setCategory(result.get());
						result.get().getTypes().add(temp);
					}
				}
				categoryRepository.save(result.get());
				return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS, null);
			}
			return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND,
					null);
		} else {
			utility.copyNonNullProperties(categoryDto, entity);
			if (categoryDto.getParentId() != null
					&& !categoryDto.getParentId().isEmpty()) {
				Optional<Category> parent = categoryRepository
						.findById(Integer.parseInt(categoryDto.getParentId()));
				entity.setParent(parent.get());
			}

			if (categoryDto.getTypes() != null) {
				for (int i = 0; i < categoryDto.getTypes().size(); i++) {
					ProductType temp = new ProductType();
					utility.copyNonNullProperties(categoryDto.getTypes().get(i), temp);
					temp.setCategory(entity);
					entity.getTypes().add(temp);
				}
			}
			entity = categoryRepository.save(entity);
			return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS,
					Utility.isNotNull(entity) ? entity.getId() : null);
		}
	}

	@Override
	public List<Category> getAllCategory() {
		return categoryRepository.findAll();
	}

	@Override
	public List<Category> getCategory() {
		return categoryRepository.findAllByParentIsNull();
	}

	@Override
	public List<Category> getCategoryByCompany(String companyId) {
		return companyCategoryRepository.findAllCategoryByCompanyId(companyId).stream()
				.map(compCat -> compCat.getCategory()).collect(Collectors.toList());
	}

	@Override
	public List<Category> getSubCategory() {
		List<Category> categories = categoryRepository.findAllByParentIdIsNotNull();
		for (Category category : categories) {
			category.setParent(categoryRepository.findById(category.getParent().getId()).get());
		}
		return categories;
	}

	@Override
	public List<Category> getSubCategoryByParent(String id) {
		List<Category> categories = categoryRepository.findAllByParentId(id);
		return categories;
	}

	@Override
	public Boolean deleteCategory(String categoryId) {
		int id = Integer.parseInt(categoryId);
		categoryRepository.deleteById(id);
		Boolean flag = categoryRepository.existsById(id);
		return !flag;
	}

	@Override
	public ApiResponse saveIssueType(IssueTypeDto dto) {
		IssueType entity = new IssueType();
		// Optional<Company> company =
		// companyRepository.findById(Integer.parseInt(dto.getCompanyId()));
		// if (company.isPresent()) {
		// entity.setCompany(company.get());
		// }

		if (Utility.isNotNull(dto) && Utility.isNotNullAndEmpty(dto.getId())) {
			Optional<IssueType> result = issueTypeRepository.findById(Integer.parseInt(dto.getId()));
			if (result.isPresent()) {
				// if (Utility.nameExist(dto, result.get(), issueTypeRepository, false, true)) {
				// throw new
				// BaseException.NameExistException(Constants.API_RESPONSE_NAME_ALREADY_IN_USE);
				// }
				utility.copyNonNullProperties(dto, result.get());
				issueTypeRepository.save(result.get());
				return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS, null);
			}
			return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND,
					null);
		} else {
			// if (Utility.nameExist(dto, entity, issueTypeRepository, true, true)) {
			// throw new
			// BaseException.NameExistException(Constants.API_RESPONSE_NAME_ALREADY_IN_USE);
			// }
			utility.copyNonNullProperties(dto, entity);
			entity = issueTypeRepository.save(entity);
			return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS,
					Utility.isNotNull(entity) ? entity.getId() : null);
		}
	}

	@Override
	public List<IssueType> getAllIssueType(String companyId) {
		Optional<Company> company = companyRepository.findById(Integer.parseInt(companyId));
		return issueTypeRepository.findAll();
	}

	public Boolean deleteIssueType(String issueTypeId) {
		int id = Integer.parseInt(issueTypeId);
		issueTypeRepository.deleteById(id);
		Boolean flag = issueTypeRepository.existsById(id);
		return !flag;
	}

	@Override
	public List<Activity> getActivity(String companyId) {
		return activityRepository.findAllByCompanyOrderByCreatedAtDesc(companyId);
	}

	@Override
	public ApiResponse saveFeature(FeatureDto featureDto) {
		Feature feature = new Feature();
		if (Utility.isNotNull(featureDto) && Utility.isNotNullAndEmpty(featureDto.getId())) {
			Optional<Feature> result = featureRepository.findById(Integer.parseInt(featureDto.getId()));
			if (result.isPresent()) {
				// if (Utility.nameExist(featureDto, result.get(), featureRepository, false,
				// false)) {
				// throw new
				// BaseException.NameExistException(Constants.API_RESPONSE_NAME_ALREADY_IN_USE);
				// }
				utility.copyNonNullProperties(featureDto, result.get());
				featureRepository.save(result.get());
				return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS, null);
			}
			return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND,
					null);
		} else {
			// if (Utility.nameExist(featureDto, feature, featureRepository, true, false)) {
			// throw new
			// BaseException.NameExistException(Constants.API_RESPONSE_NAME_ALREADY_IN_USE);
			// }
			utility.copyNonNullProperties(featureDto, feature);
			feature = featureRepository.save(feature);
			return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS,
					Utility.isNotNull(feature) ? feature.getId() : null);
		}
	}

	@Override
	public List<Feature> getFeature() {
		List<Feature> features = featureRepository.findAll();
		for (Feature feature : features) {
			if (Utility.isNotNullAndEmpty(feature.getSubCategory())
					&& categoryRepository.existsById(Integer.parseInt(feature.getSubCategory()))) {
				feature.setCategory(categoryRepository.findById(Integer.parseInt(feature.getSubCategory())).get());
			}
			// if (Utility.isNotNullAndEmpty(feature.getModel()) &&
			// productRepository.existsById(feature.getModel())) {
			// feature.setProduct(productRepository.findById(feature.getModel()).get());
			// }
		}
		return features;
	}

	@Override
	public List<IconLibrary> getIconLibrary() {
		return iconLibraryRepository.findAll();
	}

	@Override
	public List<Feature> getFeatureBySubCategory(String id) {
		return featureRepository.findAllBySubCategoryOrSubCategoryIsNull(Integer.parseInt(id));
	}

	@Override
	public Boolean deleteFeature(String featureId) {
		int id = Integer.parseInt(featureId);
		featureRepository.deleteById(id);
		Boolean flag = featureRepository.existsById(id);
		return !flag;
	}

	@Override
	public ApiResponse saveUnit(UnitDto dto) {
		Unit entity = new Unit();
		if (Utility.isNotNull(dto) && Utility.isNotNullAndEmpty(dto.getId())) {
			Optional<Unit> result = unitRepository.findById(Integer.parseInt(dto.getId()));
			if (result.isPresent()) {
				utility.copyNonNullProperties(dto, result.get());
				unitRepository.save(result.get());
				return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS, null);
			}
			return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND,
					null);
		} else {
			utility.copyNonNullProperties(dto, entity);
			entity = unitRepository.save(entity);
			return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS,
					Utility.isNotNull(entity) ? entity.getId() : null);
		}
	}

	@Override
	public List<Unit> getUnit() {
		return unitRepository.findAll();
	}

	@Override
	public Boolean deleteUnit(String unitId) {
		int id = Integer.parseInt(unitId);
		unitRepository.deleteById(id);
		Boolean flag = unitRepository.existsById(id);
		return !flag;
	}

	@Override
	public ApiResponse saveHazardType(HazardTypeDto dto) {
		HazardType entity = new HazardType();
		if (Utility.isNotNull(dto) && Utility.isNotNullAndEmpty(dto.getId())) {
			Optional<HazardType> result = hazardTypeRepository.findById(Integer.parseInt(dto.getId()));
			if (result.isPresent()) {
				utility.copyNonNullProperties(dto, result.get());
				hazardTypeRepository.save(result.get());
				return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS, null);
			}
			return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND,
					null);
		} else {
			utility.copyNonNullProperties(dto, entity);
			entity = hazardTypeRepository.save(entity);
			return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS,
					Utility.isNotNull(entity) ? entity.getId() : null);
		}
	}

	@Override
	public List<HazardType> getHazardType() {
		return hazardTypeRepository.findAll();
	}

	@Override
	public Boolean deleteHazardType(String unitId) {
		int id = Integer.parseInt(unitId);
		hazardTypeRepository.deleteById(id);
		Boolean flag = hazardTypeRepository.existsById(id);
		return !flag;
	}

	@Override
	public ApiResponse saveHazard(HazardDto dto) {
		Hazard entity = new Hazard();
		if (Utility.isNotNull(dto) && Utility.isNotNullAndEmpty(dto.getId())) {
			Optional<Hazard> result = hazardRepository.findById(Integer.parseInt(dto.getId()));
			if (result.isPresent()) {
				utility.copyNonNullProperties(dto, result.get());
				// Set Hazard Type
				if (dto.getType() != null && !dto.getType().isEmpty()) {
					Optional<HazardType> type = hazardTypeRepository.findById(Integer.parseInt(dto.getType()));
					result.get().setType(type.get());
				}

				// Set Category
				if (dto.getCategoryId() != null && !dto.getCategoryId().isEmpty()) {
					Optional<Category> category = categoryRepository.findById(Integer.parseInt(dto.getCategoryId()));
					result.get().setCategory(category.get());
				}
				// Add Symtoms
				if (dto.getSymtoms() != null && !dto.getSymtoms().isEmpty()) {
					for (int i = 0; i < dto.getSymtoms().size(); i++) {
						HazardSymtomDto tempDto = dto.getSymtoms().get(i);
						Symtom temp = result.get().getSymtoms().stream()
								.filter(pt -> !tempDto.getId().isEmpty()
										&& (pt.getId() == Integer.parseInt(tempDto.getId())))
								.findFirst()
								.orElse(new Symtom());
						utility.copyNonNullProperties(tempDto, temp);
						temp.setHazard(result.get());
						result.get().getSymtoms().add(temp);
					}
				}
				// Add Corerective
				if (dto.getCorrectives() != null && !dto.getCorrectives().isEmpty()) {
					for (int i = 0; i < dto.getCorrectives().size(); i++) {
						HazardCorrectiveDto tempDto = dto.getCorrectives().get(i);
						Corrective temp = result.get().getCorrectives().stream()
								.filter(pt -> !tempDto.getId().isEmpty()
										&& (pt.getId() == Integer.parseInt(tempDto.getId())))
								.findFirst()
								.orElse(new Corrective());
						utility.copyNonNullProperties(tempDto, temp);
						temp.setHazard(result.get());
						result.get().getCorrectives().add(temp);
					}
				}

				hazardRepository.save(result.get());
				return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS, null);
			}
			return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND,
					null);
		} else {
			utility.copyNonNullProperties(dto, entity);
			// Set Hazard Type
			if (dto.getType() != null && !dto.getType().isEmpty()) {
				Optional<HazardType> type = hazardTypeRepository.findById(Integer.parseInt(dto.getType()));
				entity.setType(type.get());
			}
			// Set Category
			if (dto.getCategoryId() != null && !dto.getCategoryId().isEmpty()) {
				Optional<Category> category = categoryRepository.findById(Integer.parseInt(dto.getCategoryId()));
				entity.setCategory(category.get());
			}
			// Add Symtoms
			if (dto.getSymtoms() != null && !dto.getSymtoms().isEmpty()) {
				for (int i = 0; i < dto.getSymtoms().size(); i++) {
					HazardSymtomDto tempDto = dto.getSymtoms().get(i);
					Symtom temp = new Symtom();
					utility.copyNonNullProperties(tempDto, temp);
					temp.setHazard(entity);
					entity.getSymtoms().add(temp);
				}
			}
			// Add Corerective
			if (dto.getCorrectives() != null && !dto.getCorrectives().isEmpty()) {
				for (int i = 0; i < dto.getCorrectives().size(); i++) {
					HazardCorrectiveDto tempDto = dto.getCorrectives().get(i);
					Corrective temp = new Corrective();
					utility.copyNonNullProperties(tempDto, temp);
					temp.setHazard(entity);
					entity.getCorrectives().add(temp);
				}
			}
			entity = hazardRepository.save(entity);
			return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS,
					Utility.isNotNull(entity) ? entity.getId() : null);
		}
	}

	@Override
	public List<Hazard> getHazard() {
		List<Hazard> hazards = hazardRepository.findAll();
		for (Hazard hazard : hazards) {
			if (Utility.isNotNullAndEmpty(hazard.getCategory().getId())) {
				hazard.setCategory(categoryRepository.findById(hazard.getCategory().getId()).get());
			}
		}
		return hazards;
	}

	@Override
	public Boolean deleteHazard(String hazrdId) {
		int id = Integer.parseInt(hazrdId);
		hazardRepository.deleteById(id);
		Boolean flag = hazardRepository.existsById(id);
		return !flag;
	}

	@Override
	public ApiResponse saveCountry(CountryDto dto) {
		Country entity = new Country();
		if (Utility.isNotNull(dto) && Utility.isNotNullAndEmpty(dto.getId())) {
			Optional<Country> result = countryRepository.findById(Integer.parseInt(dto.getId()));
			if (result.isPresent()) {
				// if (Utility.nameExist(dto, result.get(), countryRepository, false, false)) {
				// throw new
				// BaseException.NameExistException(Constants.API_RESPONSE_NAME_ALREADY_IN_USE);
				// }
				utility.copyNonNullProperties(dto, result.get());
				countryRepository.save(result.get());
				return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS, null);
			}
			return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND,
					null);
		} else {
			// if (Utility.nameExist(dto, entity, countryRepository, true, false)) {
			// throw new
			// BaseException.NameExistException(Constants.API_RESPONSE_NAME_ALREADY_IN_USE);
			// }
			utility.copyNonNullProperties(dto, entity);
			entity = countryRepository.save(entity);
			return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS,
					Utility.isNotNull(entity) ? entity.getId() : null);
		}
	}

	@Override
	public List<Country> getCountry() {
		return countryRepository.findAll();
	}

	@Override
	public Boolean deleteCountry(String id) {
		countryRepository.deleteById(Integer.parseInt(id));
		Boolean flag = countryRepository.existsById(Integer.parseInt(id));
		return !flag;
	}

	@Override
	public ApiResponse saveState(StateDto dto) {
		State entity = new State();
		if (Utility.isNotNull(dto) && Utility.isNotNullAndEmpty(dto.getId())) {
			Optional<State> result = stateRepository.findById(Integer.parseInt(dto.getId()));
			if (result.isPresent()) {
				// if (Utility.nameExist(dto, result.get(), stateRepository, false, false)) {
				// throw new
				// BaseException.NameExistException(Constants.API_RESPONSE_NAME_ALREADY_IN_USE);
				// }
				utility.copyNonNullProperties(dto, result.get());
				Optional<Country> country = countryRepository.findById(Integer.parseInt(dto.getCountryId()));
				result.get().setCountry(country.get());
				stateRepository.save(result.get());
				return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS, null);
			}
			return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND,
					null);
		} else {
			// if (Utility.nameExist(dto, entity, stateRepository, true, false)) {
			// throw new
			// BaseException.NameExistException(Constants.API_RESPONSE_NAME_ALREADY_IN_USE);
			// }
			utility.copyNonNullProperties(dto, entity);
			Optional<Country> country = countryRepository.findById(Integer.parseInt(dto.getCountryId()));
			entity.setCountry(country.get());
			entity = stateRepository.save(entity);
			return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS,
					Utility.isNotNull(entity) ? entity.getId() : null);
		}
	}

	@Override
	public List<State> getState() {
		List<State> states = stateRepository.findAll();
		for (State state : states) {
			if (Utility.isNotNullAndEmpty(state.getCountry().getId())) {
				state.setCountry(countryRepository.findById(state.getCountry().getId()).get());
			}
		}
		return states;
	}

	@Override
	public List<State> getStateByCountry(String countryId) {
		return stateRepository.findByCountryId(Integer.parseInt(countryId));
	}

	@Override
	public Boolean deleteState(String id) {
		stateRepository.deleteById(Integer.parseInt(id));
		Boolean flag = stateRepository.existsById(Integer.parseInt(id));
		return !flag;
	}

	@Override
	public ApiResponse saveCity(CityDto dto) {
		City entity = new City();
		if (Utility.isNotNull(dto) && Utility.isNotNullAndEmpty(dto.getId())) {
			Optional<City> result = cityRepository.findById(Integer.parseInt(dto.getId()));
			if (result.isPresent()) {
				// if (Utility.nameExist(dto, result.get(), cityRepository, false, false)) {
				// throw new
				// BaseException.NameExistException(Constants.API_RESPONSE_NAME_ALREADY_IN_USE);
				// }
				Optional<Country> country = countryRepository.findById(Integer.parseInt(dto.getCountryId()));
				Optional<State> state = stateRepository.findById(Integer.parseInt(dto.getStateId()));
				utility.copyNonNullProperties(dto, result.get());
				result.get().setState(state.get());
				result.get().setCountry(country.get());
				cityRepository.save(result.get());
				return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS, null);
			}
			return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND,
					null);
		} else {
			// if (Utility.nameExist(dto, entity, cityRepository, true, false)) {
			// throw new
			// BaseException.NameExistException(Constants.API_RESPONSE_NAME_ALREADY_IN_USE);
			// }
			Optional<Country> country = countryRepository.findById(Integer.parseInt(dto.getCountryId()));
			Optional<State> state = stateRepository.findById(Integer.parseInt(dto.getStateId()));
			utility.copyNonNullProperties(dto, entity);
			entity.setState(state.get());
			entity.setCountry(country.get());
			entity = cityRepository.save(entity);
			return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS,
					Utility.isNotNull(entity) ? entity.getId() : null);
		}

	}

	@Override
	public List<City> getCity() {
		List<City> cities = cityRepository.findAll();
		for (City city : cities) {
			if (city.getCountry() != null && Utility.isNotNullAndEmpty(city.getCountry().getId())) {
				city.setCountry(countryRepository.findById(city.getCountry().getId()).get());
			}
			if (city.getState() != null && Utility.isNotNullAndEmpty(city.getState().getId())) {
				city.setState(stateRepository.findById(city.getState().getId()).get());
			}
		}
		return cities;
	}

	@Override
	public List<City> getCityByState(String id) {
		return cityRepository.findAllByStateId(Integer.parseInt(id));
	}

	@Override
	public List<City> getCityByCountry(String id) {
		return cityRepository.findAllByCountryId(Integer.parseInt(id));
	}

	@Override
	public Boolean deleteCity(String id) {
		cityRepository.deleteById(Integer.parseInt(id));
		Boolean flag = cityRepository.existsById(Integer.parseInt(id));
		return !flag;
	}

	@Override
	public ApiResponse saveManufacturer(ManufacturerDto dto) {
		Manufacturer entity = new Manufacturer();
		if (Utility.isNotNull(dto) && Utility.isNotNullAndEmpty(dto.getId())) {
			Optional<Manufacturer> result = manufacturerRepository.findById(Integer.parseInt(dto.getId()));
			if (result.isPresent()) {
				// if (Utility.nameExist(dto, result.get(), manufacturerRepository, false,
				// true)) {
				// throw new
				// BaseException.NameExistException(Constants.API_RESPONSE_NAME_ALREADY_IN_USE);
				// }
				// if (Utility.mobileNoExist(dto, result.get(), manufacturerRepository, false,
				// true)) {
				// throw new
				// BaseException.ContactExistException(Constants.API_RESPONSE_CONTACT_ALREADY_IN_USE);
				// }
				// if (Utility.shortNameExist(dto, result.get(), manufacturerRepository, false,
				// true)) {
				// throw new
				// BaseException.NameExistException(Constants.API_RESPONSE_SHORT_NAME_ALREADY_IN_USE);
				// }
				utility.copyNonNullProperties(dto, result.get());
				// Find the Country exist then set
				if (dto.getCountryId() != null && !dto.getCountryId().isEmpty()) {
					Optional<Country> country = countryRepository.findById(Integer.parseInt(dto.getCountryId()));
					result.get().setCountry(country.isPresent() ? country.get() : null);
				}
				if (dto.getCityId() != null && !dto.getCityId().isEmpty()) {
					Optional<City> city = cityRepository.findById(Integer.parseInt(dto.getCityId()));
					result.get().setCity(city.isPresent() ? city.get() : null);
				}
				// Manufacturer Contains Logo Information
				if (dto.getFileNme() != null && !dto.getFileNme().isEmpty()) {
					byte[] imageBytes = Base64.getDecoder().decode(dto.getLogo()); // Decode base64 string
					result.get().setFileNme(dto.getFileNme());
					result.get().setFileSize(dto.getFileSize());
					result.get().setFileType(dto.getFileType());
					result.get().setLogo(imageBytes);
				}
				if (dto.getProduct() != null && !dto.getProduct().isEmpty()) {
					for (String categoryId : dto.getProduct()) {
						ManufacturerCategory category = result.get().getProduct().stream()
								.filter(pd -> pd.getCategory().getId() == Integer.parseInt(categoryId))
								.findFirst().orElse(new ManufacturerCategory());
						category.setCategory(findCategoryById(categoryId));
						category.setManufacturer(result.get());
						result.get().getProduct().add(category);
					}
				}
				result.get().setUpdateBy(findUserById(dto.getUpdatedBy()));
				manufacturerRepository.save(result.get());
				return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS, null);
			}
			return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND,
					null);
		} else {
			// if (Utility.nameExist(dto, entity, manufacturerRepository, true, true)) {
			// throw new
			// BaseException.NameExistException(Constants.API_RESPONSE_NAME_ALREADY_IN_USE);
			// }
			// if (Utility.mobileNoExist(dto, entity, manufacturerRepository, true, true)) {
			// throw new
			// BaseException.ContactExistException(Constants.API_RESPONSE_CONTACT_ALREADY_IN_USE);
			// }
			// if (Utility.shortNameExist(dto, entity, manufacturerRepository, true, true))
			// {
			// throw new
			// BaseException.NameExistException(Constants.API_RESPONSE_SHORT_NAME_ALREADY_IN_USE);
			// }

			utility.copyNonNullProperties(dto, entity);
			if (dto.getCountryId() != null && !dto.getCountryId().isEmpty()) {
				Optional<Country> country = countryRepository.findById(Integer.parseInt(dto.getCountryId()));
				entity.setCountry(country.isPresent() ? country.get() : null);
			}
			if (dto.getCityId() != null && !dto.getCityId().isEmpty()) {
				Optional<City> city = cityRepository.findById(Integer.parseInt(dto.getCityId()));
				entity.setCity(city.isPresent() ? city.get() : null);
			}
			if (dto.getFileNme() != null && !dto.getFileNme().isEmpty()) {
				byte[] imageBytes = Base64.getDecoder().decode(dto.getLogo()); // Decode base64 string
				entity.setFileNme(dto.getFileNme());
				entity.setFileSize(dto.getFileSize());
				entity.setFileType(dto.getFileType());
				entity.setLogo(imageBytes);
			}
			// Map with Category
			if (dto.getProduct() != null && !dto.getProduct().isEmpty()) {
				for (String categoryId : dto.getProduct()) {
					ManufacturerCategory category = new ManufacturerCategory();
					category.setCategory(findCategoryById(categoryId));
					category.setManufacturer(entity);
					entity.getProduct().add(category);
				}
			}

			entity.setCompany(findCompanyById(dto.getCompanyId()));
			entity.setCreatedBy(findUserById(dto.getCreatedBy()));
			entity = manufacturerRepository.save(entity);
			return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS,
					Utility.isNotNull(entity) ? entity.getId() : null);
		}
	}

	@Override
	public List<Manufacturer> getManufacturer() {
		return manufacturerRepository.findAll();
	}

	public List<Manufacturer> getManufacturerByCompany(String id) {
		List<Manufacturer> manufacturers = manufacturerRepository.findAllByCompany(findCompanyById(id));
		return manufacturers;
	}

	@Override
	public List<Manufacturer> getManufacturerByCategory(String categoryId, String companyId) {
		int categoryIdInt = Integer.parseInt(categoryId);
		// Fetch all ManufacturerCategory entries
		List<ManufacturerCategory> manufacturerCategories = manufacturerCategoryRepository.findAll();
		// Filter ManufacturerCategories by categoryId and then get the corresponding
		// Manufacturers
		List<Manufacturer> manufacturers = manufacturerCategories.stream()
				.filter(manufacturerCategory -> manufacturerCategory.getCategory().getId() == categoryIdInt) // Filter
																												// by
																												// categoryId
				.map(ManufacturerCategory::getManufacturer) // Get the Manufacturer from ManufacturerCategory
				.filter(manufacturer -> manufacturer.getCompany().getId() == Integer.parseInt(companyId)) // Filter by
																											// companyId
				.distinct() // Ensure unique manufacturers
				.collect(Collectors.toList());

		return manufacturers;
	}

	@Override
	public Boolean deleteManufacturer(String manufacturerId) {
		int id = Integer.parseInt(manufacturerId);
		manufacturerRepository.deleteById(id);
		Boolean flag = manufacturerRepository.existsById(id);
		return !flag;
	}

	@Override
	public ApiResponse saveCustomer(CustomerDto dto) {
		Customer entity = new Customer();
		if (Utility.isNotNull(dto) && Utility.isNotNullAndEmpty(dto.getId())) {
			Optional<Customer> result = customerRepository.findById(Integer.parseInt(dto.getId()));
			if (result.isPresent()) {
				// if (Utility.nameExist(dto, result.get(), customerRepository, false, true)) {
				// throw new
				// BaseException.NameExistException(Constants.API_RESPONSE_NAME_ALREADY_IN_USE);
				// }
				// if (Utility.shortNameExist(dto, result.get(), customerRepository, false,
				// true)) {
				// throw new
				// BaseException.NameExistException(Constants.API_RESPONSE_SHORT_NAME_ALREADY_IN_USE);
				// }
				// if (Utility.phoneExist(dto, result.get(), customerRepository, false, true)) {
				// throw new
				// BaseException.NameExistException(Constants.API_RESPONSE_CONTACT_ALREADY_IN_USE);
				// }
				// if (Utility.emailExist(dto, result.get(), customerRepository, false, true)) {
				// throw new
				// BaseException.NameExistException(Constants.API_RESPONSE_EMAIL_ALREADY_IN_USE);
				// }
				utility.copyNonNullProperties(dto, result.get());
				result.get().setCountry(findCountryById(dto.getCountryId()));
				result.get().setState(findStateById(dto.getStateId()));
				result.get().setCity(findCityById(dto.getCityId()));
				result.get().setSite(findSiteById(dto.getSiteId()));
				result.get().setUpdateBy(findUserById(dto.getUpdatedBy()));
				if (dto.getPhone() != null && !dto.getPhone().isEmpty()) {
					for (String contactNo : dto.getPhone()) {
						CustomerContact sp = result.get().getPhone().stream()
								.filter(contact -> contact.getPhoneNo().equals(contactNo)).findFirst()
								.orElse(new CustomerContact());
						sp.setCustomer(result.get());
						sp.setPhoneNo(contactNo);
						result.get().getPhone().add(sp);
					}
				}

				if (dto.getCategory() != null && !dto.getCategory().isEmpty()) {
					for (String contactNo : dto.getCategory()) {
						CustomerCategory sp = result.get().getCategory().stream()
								.filter(contact -> contact.getCategory().getId() == Integer.parseInt(contactNo))
								.findFirst()
								.orElse(new CustomerCategory());
						sp.setCustomer(result.get());
						sp.setCategory(findCategoryById(contactNo));
						result.get().getCategory().add(sp);
					}
				}

				if (dto.getLogo() != null && !dto.getLogo().isEmpty()) {
					byte[] imageBytes = Base64.getDecoder().decode(dto.getLogo()); // Decode base64 string
					result.get().setLogo(imageBytes);
				}
				// result.get().setLogo(null);
				customerRepository.save(result.get());
				return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS, null);
			}
			return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND,
					null);
		} else {
			// if (Utility.nameExist(dto, entity, customerRepository, true, true)) {
			// throw new
			// BaseException.NameExistException(Constants.API_RESPONSE_NAME_ALREADY_IN_USE);
			// }
			// if (Utility.shortNameExist(dto, entity, customerRepository, true, true)) {
			// throw new
			// BaseException.NameExistException(Constants.API_RESPONSE_SHORT_NAME_ALREADY_IN_USE);
			// }
			// if (Utility.phoneExist(dto, entity, customerRepository, true, true)) {
			// throw new
			// BaseException.NameExistException(Constants.API_RESPONSE_CONTACT_ALREADY_IN_USE);
			// }
			// if (Utility.emailExist(dto, entity, customerRepository, true, true)) {
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
				userDto.setType("customer");
				List<Group> groups = groupRepository.findAllByName("customer");
				if (groups.size() > 0) {
					userDto.setGroups(
							groups.stream().map(group -> String.valueOf(group.getId())).collect(Collectors.toList()));
				}
				if (usernameExist(userDto, users, true)) {
					throw new BaseException.EmailExistException(Constants.API_RESPONSE_LOGIN_ALREADY_IN_USE);
				}
			}
			entity.setLoginId(dto.getLoginId());
			entity.setLoginPassword(dto.getLoginPassword());
			entity.setCountry(findCountryById(dto.getCountryId()));
			entity.setCity(findCityById(dto.getCityId()));
			entity.setState(findStateById(dto.getStateId()));
			entity.setSite(findSiteById(dto.getSiteId()));
			entity.setCreatedBy(findUserById(dto.getCreatedBy()));
			entity.setCompanyId(findCompanyById(dto.getCompanyId()));
			if (dto.getPhone() != null && !dto.getPhone().isEmpty()) {
				for (String contactNo : dto.getPhone()) {
					CustomerContact sp = new CustomerContact();
					sp.setCustomer(entity);
					sp.setPhoneNo(contactNo);
					entity.getPhone().add(sp);
				}
			}

			if (dto.getCategory() != null && !dto.getCategory().isEmpty()) {
				for (String contactNo : dto.getCategory()) {
					CustomerCategory sp = new CustomerCategory();
					sp.setCustomer(entity);
					sp.setCategory(findCategoryById(contactNo));
					entity.getCategory().add(sp);
				}
			}
			// Attachement Skips
			if (dto.getLogo() != null && !dto.getLogo().isEmpty()) {
				byte[] imageBytes = Base64.getDecoder().decode(dto.getLogo()); // Decode base64 string
				entity.setLogo(imageBytes);
			}
			entity = customerRepository.save(entity);
			if (Utility.isNotNullAndEmpty(dto.getLoginId()) && Utility.isNotNullAndEmpty(dto.getLoginPassword())) {
				userDto.setRefId(String.valueOf(entity.getId()));
				userService.saveUser(userDto);
			}
			return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS,
					Utility.isNotNull(entity) ? entity.getId() : null);
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
	public List<Customer> getCustomer() {
		List<Customer> customers = customerRepository.findAll();
		return customers;
	}

	@Override
	public List<Customer> getCustomerByCompany(String id) {
		List<Customer> customers = customerRepository.findAllByCompanyId(Integer.parseInt(id));
		return customers;
	}

	@Override
	public Boolean deleteCustomer(String customerId) {
		int id = Integer.parseInt(customerId);
		customerRepository.deleteById(id);
		Boolean flag = customerRepository.existsById(id);
		return !flag;
	}

	@Override
	public ApiResponse saveVendor(VendorDto dto) {
		Vendor entity = new Vendor();
		if (Utility.isNotNull(dto) && Utility.isNotNullAndEmpty(dto.getId())) {
			Optional<Vendor> result = vendorRepository.findById(Integer.parseInt(dto.getId()));
			if (result.isPresent()) {
				// if (Utility.nameExist(dto, result.get(), vendorRepository, false, true)) {
				// throw new
				// BaseException.NameExistException(Constants.API_RESPONSE_NAME_ALREADY_IN_USE);
				// }
				// if (Utility.shortNameExist(dto, result.get(), vendorRepository, false, true))
				// {
				// throw new
				// BaseException.NameExistException(Constants.API_RESPONSE_SHORT_NAME_ALREADY_IN_USE);
				// }
				// if (Utility.phoneExist(dto, result.get(), vendorRepository, false, true)) {
				// throw new
				// BaseException.NameExistException(Constants.API_RESPONSE_CONTACT_ALREADY_IN_USE);
				// }
				// if (Utility.emailExist(dto, result.get(), vendorRepository, false, true)) {
				// throw new
				// BaseException.NameExistException(Constants.API_RESPONSE_EMAIL_ALREADY_IN_USE);
				// }
				utility.copyNonNullProperties(dto, result.get());
				result.get().setCountry(findCountryById(dto.getCountryId()));
				result.get().setState(findStateById(dto.getStateId()));
				result.get().setCity(findCityById(dto.getCityId()));
				result.get().setSite(findSiteById(dto.getSiteId()));
				result.get().setUpdateBy(findUserById(dto.getUpdatedBy()));
				if (dto.getPhone() != null && !dto.getPhone().isEmpty()) {
					for (String contactNo : dto.getPhone()) {
						VendorContact sp = result.get().getPhone().stream()
								.filter(contact -> contact.getPhoneNo().equals(contactNo)).findFirst()
								.orElse(new VendorContact());
						sp.setVendor(result.get());
						sp.setPhoneNo(contactNo);
						result.get().getPhone().add(sp);
					}
				}

				if (dto.getCategory() != null && !dto.getCategory().isEmpty()) {
					for (String contactNo : dto.getCategory()) {
						VendorCategory sp = result.get().getCategory().stream()
								.filter(contact -> contact.getCategory().getId() == Integer.parseInt(contactNo))
								.findFirst()
								.orElse(new VendorCategory());
						sp.setVendor(result.get());
						sp.setCategory(findCategoryById(contactNo));
						result.get().getCategory().add(sp);
					}
				}

				if (dto.getLogo() != null && !dto.getLogo().isEmpty()) {
					byte[] imageBytes = Base64.getDecoder().decode(dto.getLogo()); // Decode base64 string
					result.get().setLogo(imageBytes);
				}
				vendorRepository.save(result.get());
				return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS, null);
			}
			return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND,
					null);
		} else {
			// if (Utility.nameExist(dto, entity, vendorRepository, true, true)) {
			// throw new
			// BaseException.NameExistException(Constants.API_RESPONSE_NAME_ALREADY_IN_USE);
			// }
			// if (Utility.shortNameExist(dto, entity, vendorRepository, true, true)) {
			// throw new
			// BaseException.NameExistException(Constants.API_RESPONSE_SHORT_NAME_ALREADY_IN_USE);
			// }
			// if (Utility.phoneExist(dto, entity, vendorRepository, true, true)) {
			// throw new
			// BaseException.NameExistException(Constants.API_RESPONSE_CONTACT_ALREADY_IN_USE);
			// }
			// if (Utility.emailExist(dto, entity, vendorRepository, true, true)) {
			// throw new
			// BaseException.NameExistException(Constants.API_RESPONSE_EMAIL_ALREADY_IN_USE);
			// }
			utility.copyNonNullProperties(dto, entity);
			entity.setCountry(findCountryById(dto.getCountryId()));
			entity.setCity(findCityById(dto.getCityId()));
			entity.setState(findStateById(dto.getStateId()));
			entity.setSite(findSiteById(dto.getSiteId()));
			entity.setCreatedBy(findUserById(dto.getCreatedBy()));
			entity.setCompany(findCompanyById(dto.getCompanyId()));
			if (dto.getPhone() != null && !dto.getPhone().isEmpty()) {
				for (String contactNo : dto.getPhone()) {
					VendorContact sp = new VendorContact();
					sp.setVendor(entity);
					sp.setPhoneNo(contactNo);
					entity.getPhone().add(sp);
				}
			}

			if (dto.getCategory() != null && !dto.getCategory().isEmpty()) {
				for (String contactNo : dto.getCategory()) {
					VendorCategory sp = new VendorCategory();
					sp.setVendor(entity);
					sp.setCategory(findCategoryById(contactNo));
					entity.getCategory().add(sp);
				}
			}
			// Attachement Skips
			if (dto.getLogo() != null && !dto.getLogo().isEmpty()) {
				byte[] imageBytes = Base64.getDecoder().decode(dto.getLogo()); // Decode base64 string
				entity.setLogo(imageBytes);
			}
			entity = vendorRepository.save(entity);
			return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS,
					Utility.isNotNull(entity) ? entity.getId() : null);
		}
	}

	@Override
	public List<Vendor> getVendor() {
		List<Vendor> vendors = vendorRepository.findAll();
		return vendors;
	}

	@Override
	public List<Vendor> getVendorByCompany(String id) {
		List<Vendor> vendors = vendorRepository.findAllByCompanyId(id);
		return vendors;
	}

	@Override
	public Boolean deleteVendor(String vendorId) {
		int id = Integer.parseInt(vendorId);
		vendorRepository.deleteById(id);
		Boolean flag = vendorRepository.existsById(id);
		return !flag;
	}

	@Override
	public List<Department> getDepartment() {
		return departmentRepository.findAll();
	}

	@Override
	public List<Designation> getDesignation() {
		return designationRepository.findAll();
	}

	@Override
	public ApiResponse saveCheckListType(CheckListTypeDto dto) {
		CheckListType entity = new CheckListType();
		if (Utility.isNotNull(dto) && Utility.isNotNullAndEmpty(dto.getId())) {
			Optional<CheckListType> result = checkListTypeRepository.findById(Integer.parseInt(dto.getId()));
			if (result.isPresent()) {
				// if (Utility.nameExist(dto, result.get(), countryRepository, false, false)) {
				// throw new
				// BaseException.NameExistException(Constants.API_RESPONSE_NAME_ALREADY_IN_USE);
				// }
				utility.copyNonNullProperties(dto, result.get());
				checkListTypeRepository.save(result.get());
				return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS, null);
			}
			return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND,
					null);
		} else {
			// if (Utility.nameExist(dto, entity, countryRepository, true, false)) {
			// throw new
			// BaseException.NameExistException(Constants.API_RESPONSE_NAME_ALREADY_IN_USE);
			// }
			utility.copyNonNullProperties(dto, entity);
			entity = checkListTypeRepository.save(entity);
			return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS,
					Utility.isNotNull(entity) ? entity.getId() : null);
		}
	}

	@Override
	public List<CheckListType> getCheckListType() {
		return checkListTypeRepository.findAll();
	}

	@Override
	public Boolean deleteCheckListType(String id) {
		checkListTypeRepository.deleteById(Integer.parseInt(id));
		Boolean flag = checkListTypeRepository.existsById(Integer.parseInt(id));
		return !flag;
	}

	@Override
	public ApiResponse saveCheckListCategory(CheckListCategoryDto dto) {
		CheckListCategory entity = new CheckListCategory();
		if (Utility.isNotNull(dto) && Utility.isNotNullAndEmpty(dto.getId())) {
			Optional<CheckListCategory> result = checkListCategoryRepository.findById(Integer.parseInt(dto.getId()));
			if (result.isPresent()) {
				// if (Utility.nameExist(dto, result.get(), countryRepository, false, false)) {
				// throw new
				// BaseException.NameExistException(Constants.API_RESPONSE_NAME_ALREADY_IN_USE);
				// }
				utility.copyNonNullProperties(dto, result.get());
				checkListCategoryRepository.save(result.get());
				return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS, null);
			}
			return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND,
					null);
		} else {
			// if (Utility.nameExist(dto, entity, countryRepository, true, false)) {
			// throw new
			// BaseException.NameExistException(Constants.API_RESPONSE_NAME_ALREADY_IN_USE);
			// }
			utility.copyNonNullProperties(dto, entity);
			entity = checkListCategoryRepository.save(entity);
			return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS,
					Utility.isNotNull(entity) ? entity.getId() : null);
		}
	}

	@Override
	public List<CheckListCategory> getCheckListCategory() {
		return checkListCategoryRepository.findAll();
	}

	@Override
	public Boolean deleteCheckListCategory(String listId) {
		int id = Integer.parseInt(listId);
		checkListCategoryRepository.deleteById(id);
		Boolean flag = checkListCategoryRepository.existsById(id);
		return !flag;
	}

	@Override
	public ApiResponse saveCheckList(CheckListDto dto) {
		CheckList entity = new CheckList();
		if (Utility.isNotNull(dto) && Utility.isNotNullAndEmpty(dto.getId())) {
			Optional<CheckList> result = CheckListRepository.findById(Integer.parseInt(dto.getId()));
			if (result.isPresent()) {
				utility.copyNonNullProperties(dto, result.get());
				// Find ChecklistType
				if (dto.getType() != null && !dto.getType().isEmpty()) {
					Optional<CheckListType> category = checkListTypeRepository
							.findById(Integer.parseInt(dto.getType()));
					result.get().setType(category.get());
				}
				// Find CheckListCategory
				if (dto.getCheckListCategoryId() != null && !dto.getCheckListCategoryId().isEmpty()) {
					Optional<CheckListCategory> category = checkListCategoryRepository
							.findById(Integer.parseInt(dto.getCheckListCategoryId()));
					result.get().setCheckListCategory(category.get());

				}
				// Find IssueType
				if (dto.getProductType() != null && !dto.getProductType().isEmpty()) {
					Optional<IssueType> category = issueTypeRepository.findById(Integer.parseInt(dto.getProductType()));
					result.get().setIssueType(category.get());
				}
				// Find Category
				if (dto.getCategoryId() != null && !dto.getCategoryId().isEmpty()) {
					Optional<Category> category = categoryRepository.findById(Integer.parseInt(dto.getCategoryId()));
					result.get().setCategory(category.get());
				}
				// Update CheckList List of Titles
				if (dto.getList() != null && !dto.getList().isEmpty()) {
					for (CheckListListDto temp : dto.getList()) {
						CheckListList title = result.get().getList().stream()
								.filter(pt -> temp.getId() != null && !temp.getId().isEmpty()
										&& pt.getId() == Integer.parseInt(temp.getId()))
								.findFirst()
								.orElse(new CheckListList());

						utility.copyNonNullProperties(temp, title);
						title.setCheckList(result.get());
						result.get().getList().add(title);
					}
				}
				// Set Updated By
				result.get().setUpdateBy(this.findUserById(dto.getUpdatedBy()));
				CheckListRepository.save(result.get());
				return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS, null);
			}
			return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND,
					null);
		} else {
			utility.copyNonNullProperties(dto, entity);
			// Find ChecklistType
			if (dto.getType() != null && !dto.getType().isEmpty()) {
				Optional<CheckListType> category = checkListTypeRepository
						.findById(Integer.parseInt(dto.getType()));
				entity.setType(category.get());
			}
			// Find CheckListCategory
			if (dto.getCheckListCategoryId() != null && !dto.getCheckListCategoryId().isEmpty()) {
				Optional<CheckListCategory> category = checkListCategoryRepository
						.findById(Integer.parseInt(dto.getCheckListCategoryId()));
				entity.setCheckListCategory(category.get());
			}
			// Find IssueType
			if (dto.getProductType() != null && !dto.getProductType().isEmpty()) {
				Optional<IssueType> category = issueTypeRepository.findById(Integer.parseInt(dto.getProductType()));
				entity.setIssueType(category.get());
			}
			// Find Category
			if (dto.getCategoryId() != null && !dto.getCategoryId().isEmpty()) {
				Optional<Category> category = categoryRepository.findById(Integer.parseInt(dto.getCategoryId()));
				entity.setCategory(category.get());
			}
			// Update CheckList List of Titles
			if (dto.getList() != null && !dto.getList().isEmpty()) {
				for (CheckListListDto temp : dto.getList()) {
					CheckListList title = new CheckListList();
					utility.copyNonNullProperties(temp, title);
					title.setCheckList(entity);
					entity.getList().add(title);
				}
			}
			entity.setCompany(findCompanyById(dto.getCompanyId()));
			entity.setCreatedBy(findUserById(dto.getCreatedBy()));
			entity = CheckListRepository.save(entity);
			return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS,
					Utility.isNotNull(entity) ? entity.getId() : null);
		}
	}

	@Override
	public List<CheckList> getAllCheckList(String type, String companyId) {
		List<Category> categories = companyCategoryRepository.findAllCategoryByCompanyId(companyId).stream()
				.map(compCat -> compCat.getCategory()).collect(Collectors.toList());
		List<Integer> ids = categories.stream().map(c -> c.getId()).collect(Collectors.toList());
		List<CheckList> checkLists = CheckListRepository
				.findAllByCompanyIdAndTypeAndCategoryIdIn(companyId, type, ids);
		return checkLists;
	}

	@Override
	public List<CheckList> getAllCheckList(String companyId) {
		List<CheckList> CheckLists = CheckListRepository.findAllByCompany(findCompanyById(companyId));
		for (CheckList checkList : CheckLists) {
			if (Utility.isNotNullAndEmpty(checkList.getCategory().getId())
					&& categoryRepository.existsById(checkList.getCategory().getId())) {
				checkList
						.setCategory(categoryRepository.findById(checkList.getCategory().getId()).get());
			}
			if (Utility.isNotNullAndEmpty(checkList.getCheckListCategory().getId())
					&& checkListCategoryRepository.existsById(checkList.getCheckListCategory().getId())) {
				checkList.setCheckListCategory(
						checkListCategoryRepository.findById(checkList.getCheckListCategory().getId()).get());
			}
		}
		return CheckLists;
	}

	@Override
	public List<CheckList> getAllStaffCheckList(String type, String category, String categoryId, String companyId) {
		List<CheckList> CheckLists = new ArrayList<>();
		CheckLists
				.addAll(CheckListRepository.findAllByCompanyIdAndTypeAndCategoryId(companyId, type, ""));
		CheckLists.addAll(
				CheckListRepository.findAllByCompanyIdAndTypeAndCategoryId(companyId, type, categoryId));
		for (CheckList CheckList : CheckLists) {
			if (Utility.isNotNullAndEmpty(CheckList.getCategory().getId())
					&& categoryRepository.existsById(CheckList.getCategory().getId())) {
				CheckList
						.setCategory(categoryRepository.findById(CheckList.getCategory().getId()).get());
			}
			if (Utility.isNotNullAndEmpty(CheckList.getCheckListCategory().getId())
					&& checkListCategoryRepository.existsById(CheckList.getCheckListCategory().getId())) {
				CheckList.setCheckListCategory(
						checkListCategoryRepository.findById(CheckList.getCheckListCategory().getId()).get());
			}
		}
		return CheckLists;
	}

	@Override
	public List<CheckListCategory> getAllCheckListCategory() {
		return checkListCategoryRepository.findAll();
	}

	@Override
	public Boolean deleteCheckList(String checklistId) {
		int id = Integer.parseInt(checklistId);
		CheckListRepository.deleteById(id);
		Boolean flag = CheckListRepository.existsById(id);
		return !flag;
	}

	@Override
	public ApiResponse saveHelp(HelpDto dto) {
		Help entity = new Help();
		if (Utility.isNotNull(dto) && Utility.isNotNullAndEmpty(dto.getId())) {
			Optional<Help> result = helpRepository.findById(Integer.parseInt(dto.getId()));
			if (result.isPresent()) {
				utility.copyNonNullProperties(dto, result.get());
				helpRepository.save(result.get());
				return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS, null);
			}
			return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND,
					null);
		} else {
			utility.copyNonNullProperties(dto, entity);
			entity = helpRepository.save(entity);
			return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS,
					Utility.isNotNull(entity) ? entity.getId() : null);
		}
	}

	@Override
	public ApiResponse saveDepartment(DepartmentDto dto) {
		Department entity = new Department();
		if (Utility.isNotNull(dto) && Utility.isNotNullAndEmpty(dto.getId())) {
			Optional<Department> result = departmentRepository.findById(Integer.parseInt(dto.getId()));
			if (result.isPresent()) {
				utility.copyNonNullProperties(dto, result.get());
				departmentRepository.save(result.get());
				return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS, null);
			}
			return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND,
					null);
		} else {
			utility.copyNonNullProperties(dto, entity);
			entity = departmentRepository.save(entity);
			return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS,
					Utility.isNotNull(entity) ? entity.getId() : null);
		}
	}

	@Override
	public Boolean deleteDepartment(String id) {
		departmentRepository.deleteById(Integer.parseInt(id));
		Boolean flag = departmentRepository.existsById(Integer.parseInt(id));
		return !flag;
	}

	@Override
	public ApiResponse saveDesignation(DesignationDto dto) {
		Designation entity = new Designation();
		if (Utility.isNotNull(dto) && Utility.isNotNullAndEmpty(dto.getId())) {
			Optional<Designation> result = designationRepository.findById(Integer.parseInt(dto.getId()));
			if (result.isPresent()) {
				utility.copyNonNullProperties(dto, result.get());
				designationRepository.save(result.get());
				return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS, null);
			}
			return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND,
					null);
		} else {
			utility.copyNonNullProperties(dto, entity);
			entity = designationRepository.save(entity);
			return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS,
					Utility.isNotNull(entity) ? entity.getId() : null);
		}
	}

	@Override
	public Boolean deleteDesignation(String id) {
		designationRepository.deleteById(Integer.parseInt(id));
		Boolean flag = designationRepository.existsById(Integer.parseInt(id));
		return !flag;
	}

	@Override
	public ApiResponse saveCategoryProductType(CategoryDto dto) {
		if (dto.getId() != null && !dto.getId().isEmpty()) {
			List<ProductType> types = new ArrayList<>();
			if (dto.getTypes() != null && !dto.getTypes().isEmpty()) {
				for (ProductTypeDto pdto : dto.getTypes()) {
					ProductType productType = new ProductType();
					utility.copyNonNullProperties(pdto, productType);
					productType.setCategory(findCategoryById(dto.getId()));
					types.add(productType);
				}
				productTypeRepository.saveAll(types);
				return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS, null);
			}
		}
		return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND,
				null);
	}

	@Override
	public ApiResponse saveProductType(ProductTypeDto dto) {
		ProductType entity = new ProductType();
		if (Utility.isNotNull(dto) && Utility.isNotNullAndEmpty(dto.getId())) {
			Optional<ProductType> result = productTypeRepository.findById(Integer.parseInt(dto.getId()));
			if (result.isPresent()) {
				utility.copyNonNullProperties(dto, result.get());
				result.get().setCategory(findCategoryById(dto.getCategoryId()));
				productTypeRepository.save(result.get());
				return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS, null);
			}
			return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND,
					null);
		} else {
			utility.copyNonNullProperties(dto, entity);
			entity.setCategory(findCategoryById(dto.getCategoryId()));
			entity = productTypeRepository.save(entity);
			return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS,
					Utility.isNotNull(entity) ? entity.getId() : null);
		}
	}

	@Override
	public Boolean deleteProductType(String producTypeId) {
		int id = Integer.parseInt(producTypeId);
		ProductType type = productTypeRepository.findById(id).get();
		try {
			productTypeRepository.deleteByIdAndCategoryId(id, type.getCategory().getId());
			return !productTypeRepository.existsById(id);
		} catch (Exception e) {
			// Log the exception
			System.err.println("Error deleting product type: " + e.getMessage());
			return false; // Indicate failure
		}
	}

	@Override
	public Boolean deleteSymtoms(String producTypeId) {
		int id = Integer.parseInt(producTypeId);
		symtomRepository.deleteById(id);
		Boolean flag = symtomRepository.existsById(id);
		return !flag;
	}

	@Override
	public Boolean deleteCorrectives(String producTypeId) {
		int id = Integer.parseInt(producTypeId);
		correctiveRepository.deleteById(id);
		Boolean flag = correctiveRepository.existsById(id);
		return !flag;
	}

	Company findCompanyById(String companyId) {
		if (companyId != null && !companyId.isEmpty()) {
			Optional<Company> company = companyRepository.findById(Integer.parseInt(companyId));
			return company.get();
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

	Category findCategoryById(String categoryId) {
		if (categoryId != null && !categoryId.isEmpty()) {
			Optional<Category> category = categoryRepository.findById(Integer.parseInt(categoryId));
			return category.get();
		}
		return null;
	}

	Manufacturer findManufacturerById(String categoryId) {
		if (categoryId != null && !categoryId.isEmpty()) {
			Optional<Manufacturer> category = manufacturerRepository.findById(Integer.parseInt(categoryId));
			return category.get();
		}
		return null;
	}

	public void removeCategoriesFromManufacturer(int manufacturerId, List<Integer> categoryIds) {
		// Fetch the Manufacturer
		Optional<Manufacturer> manufacturerOpt = manufacturerRepository.findById(manufacturerId);
		if (manufacturerOpt.isPresent()) {
			Manufacturer manufacturer = manufacturerOpt.get();
			// Iterate over the list of category IDs
			for (Integer categoryId : categoryIds) {
				Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
				if (categoryOpt.isPresent()) {
					Category category = categoryOpt.get();
					// Find the ManufacturerCategory entry
					ManufacturerCategory manufacturerCategory = manufacturerCategoryRepository
							.findByManufacturerAndCategory(manufacturer, category);
					if (manufacturerCategory != null) {
						// Remove the entry
						manufacturerCategoryRepository.delete(manufacturerCategory);
					} else {
						// Handle the case where the entry does not exist
						System.out.println("ManufacturerCategory not found for Manufacturer ID: " + manufacturerId
								+ " and Category ID: " + categoryId);
					}
				} else {
					// Handle the case where the Category does not exist
					System.out.println("Category not found for ID: " + categoryId);
				}
			}
		} else {
			// Handle the case where the Manufacturer does not exist
			System.out.println("Manufacturer not found for ID: " + manufacturerId);
		}
	}

	// List<ManufacturerCategory> findManufacturerCategoryById(String categoryId) {
	// if (categoryId != null && !categoryId.isEmpty()) {
	// Optional<Category> category =
	// categoryRepository.findById(Integer.parseInt(categoryId));
	// return category.get();
	// }
	// return null;
	// }

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
}
