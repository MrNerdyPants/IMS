package com.sys.ims.service.impl;

import com.sys.ims.dto.ContractDto;
import com.sys.ims.dto.MaintenanceDto;
import com.sys.ims.dto.PurchaseDto;
import com.sys.ims.dto.SalesDto;
import com.sys.ims.model.*;
import com.sys.ims.repository.*;
import com.sys.ims.service.BusinessService;
import com.sys.ims.util.ApiResponse;
import com.sys.ims.util.Constants;
import com.sys.ims.util.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BusinessServiceImpl implements BusinessService {

	@Autowired
	private SalesRepository salesRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductTypeRepository productTypeRepository;
	@Autowired
	private PurchaseRepository purchaseRepository;
	@Autowired
	private VendorRepository vendorRepository;
	@Autowired
	private ContractRepository contractRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ManufacturerRepository manufacturerRepository;
	@Autowired
	private FeatureRepository featureRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private MudularRepository purchaseMudular;

	@Autowired
	private SaleDetailModulerRepository saleModular;

	@Autowired
	private Utility utility;

	@Transactional
	public ApiResponse saveSales(SalesDto dto) {
		Sales entity = new Sales();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		// Date orderDate = (java.sql.Date) new Date();
		// try {
		// orderDate = formatter.parse(dto.getDate());
		// } catch (Exception e) {
		// // TODO: handle exception
		// }
		if (Utility.isNotNull(dto) && Utility.isNotNullAndEmpty(dto.getId())) {
			Optional<Sales> result = salesRepository.findById(Integer.parseInt(dto.getId()));
			if (result.isPresent()) {
				// Sale
				utility.copyNonNullProperties(dto, result.get());
				result.get().setUpdatedBy(findUserById(dto.getUpdatedBy()));
				result.get().setCustomer(findCustomerById(dto.getCustomer()));
				result.get().setDate(dto.getDate());
				// Parse the date string and set it in the entity
				// if (dto.getDate() != null && !dto.getDate().isEmpty()) {
				// try {
				// LocalDate orderDate = LocalDate.parse(dto.getDate().toString(), formatter);
				// result.get().setDate(java.sql.Date.valueOf(orderDate)); // Convert LocalDate
				// to java.sql.Date
				// } catch (Exception e) {
				// // Handle parsing exception (e.g., log the error)
				// System.out.println("Error parsing date: " + e.getMessage());
				// }
				// }
				// Sale Details
				if (dto.getDetail() != null) {
					for (int i = 0; i < dto.getDetail().size(); i++) {
						String detailId = dto.getDetail().get(i).getId();
						SalesDetail temp = result.get().getDetail().stream().filter(
								detail -> detailId != null
										&& detail.getId() == Integer.parseInt(detailId.isEmpty() ? "0" : detailId))
								.findFirst().orElse(new SalesDetail());
						utility.copyNonNullProperties(dto.getDetail().get(i), temp);
						// Product & ProductType
						temp.setSale(result.get());
						temp.setProductType(findProductTypeById(dto.getDetail().get(i).getProductType()));
						temp.setProduct(findProductById(dto.getDetail().get(i).getProduct()));
						// Sale Detail Moduler
						if (dto.getDetail().get(i).getMudulerSerial() != null) {
							for (int j = 0; j < dto.getDetail().get(i).getMudulerSerial().size(); j++) {
								String modulerId = dto.getDetail().get(i).getMudulerSerial().get(j).getId();
								SalesDetailModular temp2 = temp.getMudulerSerial().stream().filter(
										moduler -> modulerId != null && moduler.getId() == Integer
												.parseInt(modulerId.isEmpty() ? "0" : modulerId))
										.findFirst().orElse(new SalesDetailModular());
								utility.copyNonNullProperties(dto.getDetail().get(i).getMudulerSerial().get(j), temp2);
								temp2.setSalesDetail(temp);
								temp.getMudulerSerial().add(temp2);
							}
						} else if (temp.getMudulerSerial() != null && !temp.getMudulerSerial().isEmpty()) {
							deleteAllSaleMudularByDetail(temp.getMudulerSerial());
						}
						result.get().getDetail().add(temp);
					}
				}
				salesRepository.save(result.get());
				return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS, null);
			}
			return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND,
					null);
		} else {
			utility.copyNonNullProperties(dto, entity);
			entity.setCompany(findCompanyById(dto.getCompanyId()));
			entity.setCreatedBy(findUserById(dto.getCreatedBy()));
			entity.setCustomer(findCustomerById(dto.getCustomer()));
			entity.setDate(dto.getDate());
			// try {
			// LocalDate orderDate = LocalDate.parse(dto.getDate().toString(), formatter);
			// entity.setDate(java.sql.Date.valueOf(orderDate)); // Convert LocalDate to
			// java.sql.Date
			// } catch (Exception e) {
			// // Handle parsing exception (e.g., log the error)
			// System.out.println("Error parsing date: " + e.getMessage());
			// }
			if (dto.getDetail() != null) {
				for (int i = 0; i < dto.getDetail().size(); i++) {
					SalesDetail temp = new SalesDetail();
					utility.copyNonNullProperties(dto.getDetail().get(i), temp);
					temp.setProductType(findProductTypeById(dto.getDetail().get(i).getProductType()));
					temp.setProduct(findProductById(dto.getDetail().get(i).getProduct()));
					temp.setSale(entity);
					if (dto.getDetail().get(i).getMudulerSerial() != null) {
						for (int j = 0; j < dto.getDetail().get(i).getMudulerSerial().size(); j++) {
							SalesDetailModular temp2 = new SalesDetailModular();
							utility.copyNonNullProperties(dto.getDetail().get(i).getMudulerSerial().get(j), temp2);
							temp2.setSalesDetail(temp);
							temp.getMudulerSerial().add(temp2);
						}
					}
					entity.getDetail().add(temp);
				}
			}
			entity = salesRepository.save(entity);
			return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS,
					Utility.isNotNull(entity) ? entity.getId() : null);
		}
	}

	public List<Sales> getAllSales() {
		List<Sales> sales = salesRepository.findAll();
		for (Sales sale : sales) {
			sale.setCustomer(customerRepository.findById(sale.getCustomer().getId()).get());
		}
		return sales;
	}

	public List<Sales> getAllSalesByCompany(String id) {
		List<Sales> sales = salesRepository.findAllByCompanyId(id);
		return sales;
	}

	public Sales getSales(String salesId) {
		int id = Integer.parseInt(salesId);
		Optional<Sales> sales = salesRepository.findById(id);
		if (sales.isPresent()) {
			return sales.get();
		}
		return null;
	}

	public List<Product> getProductByCustomer(String id) {
		List<Sales> sales = salesRepository.findAllByCustomer(id);
		List<Integer> productIds = sales.stream().flatMap(sale -> sale.getDetail().stream())
				.map(sale -> sale.getProduct().getId()).collect(Collectors.toList());
		List<Product> products = productRepository.findAllById(productIds);
		return products;
	}

	public Boolean deleteSales(String salesId) {
		int id = Integer.parseInt(salesId);
		salesRepository.deleteById(id);
		Boolean flag = salesRepository.existsById(id);
		return !flag;
	}

	public ApiResponse savePurchase(PurchaseDto dto) {
		Purchase entity = new Purchase();
		if (Utility.isNotNull(dto) && Utility.isNotNullAndEmpty(dto.getId())) {
			Optional<Purchase> result = purchaseRepository.findById(Integer.parseInt(dto.getId()));
			if (result.isPresent()) {
				utility.copyNonNullProperties(dto, result.get());
				result.get().setDate(dto.getDate());
				result.get().setManufacturer(findManufacturerById(dto.getManufacturer()));
				result.get().setVendor(findVendorById(dto.getVendor()));
				result.get().setUpdateBy(findUserById(dto.getUpdatedBy()));
				if (dto.getDetail() != null) {
					for (int i = 0; i < dto.getDetail().size(); i++) {
						String detailId = dto.getDetail().get(i).getId();
						Details temp = result.get().getDetail().stream()
								.filter(detail -> detailId != null
										&& detail.getId() == Integer.parseInt(detailId.isEmpty() ? "0" : detailId))
								.findFirst().orElse(new Details());
						utility.copyNonNullProperties(dto.getDetail().get(i), temp);
						temp.setPurchase(result.get());
						temp.setProduct(findProductById(dto.getDetail().get(i).getProduct()));
						temp.setProductType(findProductTypeById(dto.getDetail().get(i).getProductType()));
						if (dto.getDetail().get(i).getMudulerSerial() != null
								&& !dto.getDetail().get(i).getMudulerSerial().isEmpty()) {
							for (int j = 0; j < dto.getDetail().get(i).getMudulerSerial().size(); j++) {
								String modulerId = dto.getDetail().get(i).getMudulerSerial().get(j).getId();
								Muduler temp2 = temp.getMudulerSerial().stream().filter(
										moduler -> modulerId != null && moduler.getId() == Integer
												.parseInt(modulerId.isEmpty() ? "0" : modulerId))
										.findFirst().orElse(new Muduler());
								utility.copyNonNullProperties(dto.getDetail().get(i).getMudulerSerial().get(j), temp2);
								temp2.setDetail(temp);
								temp.getMudulerSerial().add(temp2);
							}
						} else if (temp.getMudulerSerial() != null && !temp.getMudulerSerial().isEmpty()) {
							deleteAllPurchaseMudularByDetail(temp.getMudulerSerial());
						}
						result.get().getDetail().add(temp);
					}
				}
				purchaseRepository.save(result.get());
				return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS, null);
			}
			return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND,
					null);
		} else {
			utility.copyNonNullProperties(dto, entity);
			// update Company ,User, Customer
			entity.setCompany(findCompanyById(dto.getCompanyId()));
			entity.setDate(dto.getDate());
			entity.setCreatedBy(findUserById(dto.getCreatedBy()));
			// entity.setUpdateBy(findUserById(dto.getUpdatedBy()));
			entity.setManufacturer(findManufacturerById(dto.getManufacturer()));
			entity.setVendor(findVendorById(dto.getVendor()));
			if (dto.getDetail() != null) {
				for (int i = 0; i < dto.getDetail().size(); i++) {
					Details temp = new Details();
					utility.copyNonNullProperties(dto.getDetail().get(i), temp);
					temp.setPurchase(entity);
					temp.setProduct(findProductById(dto.getDetail().get(i).getProduct()));
					temp.setProductType(findProductTypeById(dto.getDetail().get(i).getProductType()));
					if (dto.getDetail().get(i).getMudulerSerial() != null) {
						for (int j = 0; j < dto.getDetail().get(i).getMudulerSerial().size(); j++) {
							Muduler temp2 = new Muduler();
							utility.copyNonNullProperties(dto.getDetail().get(i).getMudulerSerial().get(j), temp2);
							temp2.setDetail(temp);
							temp.getMudulerSerial().add(temp2);
						}
					}
					entity.getDetail().add(temp);
				}
			}
			entity = purchaseRepository.save(entity);
			return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS,
					Utility.isNotNull(entity) ? entity.getId() : null);
		}
	}

	private boolean deleteAllPurchaseMudularByDetail(List<Muduler> mudulers) {
		if (mudulers != null && !mudulers.isEmpty()) {
			purchaseMudular.deleteAllInBatch(mudulers);
			return true;
		}
		return false;
	}

	private boolean deleteAllSaleMudularByDetail(List<SalesDetailModular> mudulers) {
		if (mudulers != null && !mudulers.isEmpty()) {
			saleModular.deleteAllInBatch(mudulers);
			return true;
		}
		return false;
	}

	public List<Purchase> getAllPurchase() {
		List<Purchase> purchases = purchaseRepository.findAll();
		return purchases;
	}

	public List<Purchase> getAllPurchaseByCompany(String id) {
		List<Purchase> purchases = purchaseRepository.findAllByCompanyId(id);
		return purchases;
	}

	public Purchase getPurchase(String id) {
		Optional<Purchase> purchases = purchaseRepository.findById(Integer.parseInt(id));
		if (purchases.isPresent()) {
			return purchases.get();
		}
		return null;
	}

	public Boolean deletePurchase(String purchaseId) {
		int id = Integer.parseInt(purchaseId);
		purchaseRepository.deleteById(id);
		Boolean flag = purchaseRepository.existsById(id);
		return !flag;
	}

	public ApiResponse saveContract(ContractDto dto) {
		Contract entity = new Contract();
		if (Utility.isNotNull(dto) && Utility.isNotNullAndEmpty(dto.getId())) {
			Optional<Contract> result = contractRepository.findById(Integer.parseInt(dto.getId()));
			if (result.isPresent()) {
				utility.copyNonNullProperties(dto, result.get());
				result.get().setUpdateBy(findUserById(dto.getUpdatedBy()));
				result.get().setCustomer(findCustomerById(dto.getCustomer()));
				result.get().setExpiry(dto.getExpiry());
				result.get().setDate(dto.getDate());
				result.get().setPeriod(dto.getPeriod());
				if (result.get().getDetail() == null) {
					result.get().setDetail(new ArrayList<>());
				}
				if (dto.getDetail() != null) {
					for (int i = 0; i < dto.getDetail().size(); i++) {
						String detailId = dto.getDetail().get(i).getId();
						ContractDetail temp = result.get().getDetail().stream()
								.filter(dtl -> dtl.getId() != 0
										&& detailId != null
										&& !detailId.isEmpty()
										&& dtl.getId() == Integer.parseInt(detailId))
								.findFirst().orElse(new ContractDetail());
						utility.copyNonNullProperties(dto.getDetail().get(i), temp);
						temp.setContract(result.get());
						result.get().getDetail().add(temp);
					}
				}
				contractRepository.save(result.get());
				return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS, null);
			}
			return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND,
					null);
		} else {
			utility.copyNonNullProperties(dto, entity);
			entity.setUpdateBy(findUserById(dto.getUpdatedBy()));
			entity.setCustomer(findCustomerById(dto.getCustomer()));
			entity.setExpiry(dto.getExpiry());
			entity.setDate(dto.getDate());
			entity.setPeriod(dto.getPeriod());
			entity.setCompany(findCompanyById(dto.getCompanyId()));
			entity.setCreatedBy(findUserById(dto.getCreatedBy()));
			if (dto.getDetail() != null) {
				for (int i = 0; i < dto.getDetail().size(); i++) {
					ContractDetail temp = new ContractDetail();
					utility.copyNonNullProperties(dto.getDetail().get(i), temp);
					temp.setContract(entity);
					entity.getDetail().add(temp);
				}
			}
			entity = contractRepository.save(entity);
			return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS,
					Utility.isNotNull(entity) ? entity.getId() : null);
		}
	}

	public List<Contract> getAllContract() {
		List<Contract> contracts = contractRepository.findAll();
		for (Contract contract : contracts) {
			if (Utility.isNotNullAndEmpty(contract.getCustomer().getId())) {
				contract.setCustomer(customerRepository.findById(contract.getCustomer().getId()).get());
			}
		}
		return contracts;
	}

	public List<Contract> getAllContractByCompany(String companyId) {
		List<Contract> contracts = contractRepository.findAllByCompanyId(companyId);
		for (Contract contract : contracts) {
			if (Utility.isNotNullAndEmpty(contract.getCustomer().getId())) {
				contract.setCustomer(customerRepository.findById(contract.getCustomer().getId()).get());
			}
		}
		return contracts;
	}

	public Contract getAllContractById(String id) {
		Optional<Contract> contract = contractRepository.findById(Integer.parseInt(id));
		if (contract.isPresent()) {
			return contract.get();
		}
		return null;
	}

	public Boolean deleteContract(String contractId) {
		int id = Integer.parseInt(contractId);
		contractRepository.deleteById(id);
		Boolean flag = contractRepository.existsById(id);
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
			Optional<User> user = userRepository.findById(Integer.parseInt(userId));
			return user.get();
		}
		return null;
	}

	Product findProductById(String userId) {
		if (userId != null && !userId.isEmpty()) {
			Optional<Product> user = productRepository.findById(Integer.parseInt(userId));
			return user.get();
		}
		return null;
	}

	ProductType findProductTypeById(String userId) {
		if (userId != null && !userId.isEmpty()) {
			Optional<ProductType> user = productTypeRepository.findById(Integer.parseInt(userId));
			return user.get();
		}
		return null;
	}

	Customer findCustomerById(String userId) {
		if (userId != null && !userId.isEmpty()) {
			Optional<Customer> user = customerRepository.findById(Integer.parseInt(userId));
			return user.get();
		}
		return null;
	}

	Manufacturer findManufacturerById(String userId) {
		if (userId != null && !userId.isEmpty()) {
			Optional<Manufacturer> user = manufacturerRepository.findById(Integer.parseInt(userId));
			return user.get();
		}
		return null;
	}

	Vendor findVendorById(String companyId) {
		if (companyId != null && !companyId.isEmpty()) {
			Optional<Vendor> company = vendorRepository.findById(Integer.parseInt(companyId));
			return company.get();
		}
		return null;
	}
}
