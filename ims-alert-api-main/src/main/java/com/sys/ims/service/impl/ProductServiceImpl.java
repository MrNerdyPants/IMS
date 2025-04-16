package com.sys.ims.service.impl;

import com.sys.ims.dto.ProductDto;
import com.sys.ims.exception.BaseException;
import com.sys.ims.model.Category;
import com.sys.ims.model.Company;
import com.sys.ims.model.Manufacturer;
import com.sys.ims.model.Product;
import com.sys.ims.model.ProductAttachment;
import com.sys.ims.model.ProductModuler;
import com.sys.ims.model.ProductParam;
import com.sys.ims.model.ProductPart;
import com.sys.ims.model.ProductSerial;
import com.sys.ims.model.ProductType;
import com.sys.ims.model.ProductVideo;
import com.sys.ims.model.User;
import com.sys.ims.repository.CategoryRepository;
import com.sys.ims.repository.CompanyRepository;
import com.sys.ims.repository.ManufacturerRepository;
import com.sys.ims.repository.ProductAttachmentRepository;
import com.sys.ims.repository.ProductModulerRepository;
import com.sys.ims.repository.ProductParamRepository;
import com.sys.ims.repository.ProductPartRepository;
import com.sys.ims.repository.ProductRepository;
import com.sys.ims.repository.ProductSerialRepository;
import com.sys.ims.repository.ProductTypeRepository;
import com.sys.ims.repository.ProductVideoRepository;
import com.sys.ims.repository.UserRepository;
import com.sys.ims.service.ProductService;
import com.sys.ims.util.ApiResponse;
import com.sys.ims.util.Constants;
import com.sys.ims.util.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductSerialRepository productSerialRepository;
    @Autowired
    private ProductModulerRepository productModulerRepository;
    @Autowired
    private ProductParamRepository paramRepository;
    @Autowired
    private ProductPartRepository partRepository;
    @Autowired
    private ProductAttachmentRepository attachmentRepository;
    @Autowired
    private ProductVideoRepository productVideoRepository;
    @Autowired
    private ManufacturerRepository manufacturerRepository;
    @Autowired
    private ProductTypeRepository productTypeRepository;
    @Autowired
    private Utility utility;

    public ApiResponse createProduct(ProductDto productDto) {
        Product product = new Product();
        if (Utility.isNotNull(productDto) && Utility.isNotNullAndEmpty(productDto.getId())) {
            Optional<Product> result = productRepository.findById(Integer.parseInt(productDto.getId()));
            if (result.isPresent()) {
                // if (Utility.nameExist(productDto, result.get(), productRepository, false,
                // true)) {
                // throw new
                // BaseException.NameExistException(Constants.API_RESPONSE_NAME_ALREADY_IN_USE);
                // }
                // if (Utility.modelExist(productDto, result.get(), productRepository, false)) {
                // throw new
                // BaseException.NameExistException(Constants.API_RESPONSE_MODEL_TITLE_ALREADY_IN_USE);
                // }
                utility.copyNonNullProperties(productDto, result.get());
                result.get().setCategory(findCategoryById(productDto.getCategory()));
                result.get().setSubCategory(findCategoryById(productDto.getSubCategory()));
                result.get().setManufacturer(findManufacturerById(productDto.getManufacturer()));
                result.get().setProductType(findProductTypeById(productDto.getProductType()));
                if (productDto.getMudulers() != null) {
                    for (int i = 0; i < productDto.getMudulers().size(); i++) {
                        String modulerId = productDto.getMudulers().get(i).getId();
                        ProductModuler temp = result.get().getMudulers().stream().filter(
                                moduler -> moduler.getId() == Integer.parseInt(modulerId.isEmpty() ? "0" : modulerId))
                                .findFirst().orElse(new ProductModuler());
                        utility.copyNonNullProperties(productDto.getMudulers().get(i), temp);
                        temp.setProduct(result.get());
                        result.get().getMudulers().add(temp);
                    }
                }
                if (productDto.getParts() != null) {
                    for (int i = 0; i < productDto.getParts().size(); i++) {
                        String partId = productDto.getParts().get(i).getId();
                        ProductPart temp = result.get().getParts().stream().filter(
                                part -> part.getId() == Integer.parseInt(partId.isEmpty() ? "0" : partId))
                                .findFirst().orElse(new ProductPart());
                        utility.copyNonNullProperties(productDto.getParts().get(i), temp);
                        temp.setProduct(result.get());
                        result.get().getParts().add(temp);
                    }
                }
                if (productDto.getParameters() != null) {
                    for (int i = 0; i < productDto.getParameters().size(); i++) {
                        String parameterId = productDto.getParameters().get(i).getId();
                        ProductParam temp = result.get().getParameters().stream().filter(
                                parameter -> parameter.getId() == Integer
                                        .parseInt(parameterId.isEmpty() ? "0" : parameterId))
                                .findFirst().orElse(new ProductParam());
                        utility.copyNonNullProperties(productDto.getParameters().get(i), temp);
                        temp.setProduct(result.get());
                        result.get().getParameters().add(temp);
                    }
                }
                if (productDto.getDocuments() != null) {
                    for (int i = 0; i < productDto.getDocuments().size(); i++) {
                        String attachmentId = productDto.getDocuments().get(i).getId();
                        ProductAttachment temp = result.get().getDocuments().stream()
                                .filter(attach -> attach.getId() == Integer
                                        .parseInt(attachmentId.isEmpty() ? "0" : attachmentId))
                                .findFirst().orElse(new ProductAttachment());
                        utility.copyNonNullProperties(productDto.getDocuments().get(i), temp);
                        byte[] imageBytes = Base64.getDecoder().decode(productDto.getDocuments().get(i).getFileData()); // Decode
                                                                                                                        // base64
                                                                                                                        // string
                        temp.setFileData(imageBytes);
                        temp.setProduct(result.get());
                        result.get().getDocuments().add(temp);
                    }
                }

                if (productDto.getVideoLinks() != null) {
                    for (int i = 0; i < productDto.getVideoLinks().size(); i++) {
                        String documentId = productDto.getVideoLinks().get(i).getId();
                        ProductVideo temp = result.get().getVideoLinks().stream().filter(
                                docs -> docs.getId() == (Integer.parseInt(documentId.isEmpty() ? "0" : documentId)))
                                .findFirst().orElse(new ProductVideo());
                        utility.copyNonNullProperties(productDto.getVideoLinks().get(i), temp);
                        temp.setProduct(result.get());
                        result.get().getVideoLinks().add(temp);
                    }
                }
                product.setUpdateBy(findUserById(productDto.getUpdatedBy()));
                productRepository.save(result.get());
                return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS, null);
            }
            return utility.buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_RECORD_NOT_FOUND,
                    null);
        } else {
            // if (Utility.nameExist(productDto, product, productRepository, true, true)) {
            // throw new
            // BaseException.NameExistException(Constants.API_RESPONSE_NAME_ALREADY_IN_USE);
            // }
            // if (Utility.modelExist(productDto, product, productRepository, true)) {
            // throw new
            // BaseException.NameExistException(Constants.API_RESPONSE_MODEL_TITLE_ALREADY_IN_USE);
            // }

            utility.copyNonNullProperties(productDto, product);
            product.setManufacturer(findManufacturerById(productDto.getManufacturer()));
            product.setCategory(findCategoryById(productDto.getCategory()));
            product.setSubCategory(findCategoryById(productDto.getSubCategory()));
            product.setProductType(findProductTypeById(productDto.getProductType()));
            if (productDto.getMudulers() != null) {
                for (int i = 0; i < productDto.getMudulers().size(); i++) {
                    ProductModuler temp = new ProductModuler();
                    utility.copyNonNullProperties(productDto.getMudulers().get(i), temp);
                    temp.setProduct(product);
                    product.getMudulers().add(temp);
                }
            }
            if (productDto.getParts() != null) {
                for (int i = 0; i < productDto.getParts().size(); i++) {
                    ProductPart temp = new ProductPart();
                    utility.copyNonNullProperties(productDto.getParts().get(i), temp);
                    temp.setProduct(product);
                    product.getParts().add(temp);
                }
            }
            if (productDto.getParameters() != null) {
                for (int i = 0; i < productDto.getParameters().size(); i++) {
                    ProductParam temp = new ProductParam();
                    utility.copyNonNullProperties(productDto.getParameters().get(i), temp);
                    temp.setProduct(product);
                    product.getParameters().add(temp);
                }
            }
            if (productDto.getDocuments() != null)
                for (int i = 0; i < productDto.getDocuments().size(); i++) {
                    ProductAttachment temp = new ProductAttachment();
                    utility.copyNonNullProperties(productDto.getDocuments().get(i), temp);
                    byte[] imageBytes = Base64.getDecoder().decode(productDto.getDocuments().get(i).getFileData());
                    temp.setFileData(imageBytes);
                    temp.setProduct(product);
                    product.getDocuments().add(temp);
                }
            if (productDto.getVideoLinks() != null) {
                for (int i = 0; i < productDto.getVideoLinks().size(); i++) {
                    ProductVideo temp = new ProductVideo();
                    utility.copyNonNullProperties(productDto.getVideoLinks().get(i), temp);
                    temp.setProduct(product);
                    product.getVideoLinks().add(temp);
                }
            }

            product.setCreatedBy(findUserById(productDto.getCreatedBy()));
            product.setCompany(findCompanyById(productDto.getCompanyId()));
            product = productRepository.save(product);
            return utility.buildApiResponse(HttpStatus.OK.value(), Constants.API_RESPONSE_SUCCESS,
                    Utility.isNotNull(product) ? product.getId() : null);
        }
    }

    public List<Product> getAllProductsByType(String type, String companyId) {
        List<Product> products = productRepository.findAllByProductTypeAndCompany(findProductTypeById(type),
                findCompanyById(companyId));
        return products;
    }

    public List<Product> getAllProducts(String companyId) {
        List<Product> products = productRepository.findAllByCompanyIsNullOrCompany(findCompanyById(companyId));
        return products;
    }

    public List<ProductType> getAllProductTypes(String companyId) {
        List<ProductType> productTypes = productTypeRepository.findAll();
        // if (company.isPresent()) {
        // List<Category> categories =
        // categoryRepository.findAllByParentIdIn(company.get().getCategory());
        // for (Category category : categories) {
        // productTypes.addAll(category.getTypes());
        // }
        // }
        return productTypes;
    }

    public List<ProductType> getAllProductTypes(String natureType, String companyId) {
        List<ProductType> productTypes = productTypeRepository.findByTypeNature(natureType);
        // if (company.isPresent()) {
        // List<Category> categories =
        // categoryRepository.findAllByParentIdIn(company.get().getCategory());
        // for (Category category : categories) {
        // productTypes.addAll(category.getTypes());
        // }
        // }
        return productTypes;
    }

    public Product getProductById(String id) {
        Optional<Product> product = productRepository.findById(Integer.parseInt(id));
        if (product.isPresent())
            return product.get();
        return null;
    }

    public Boolean deleteProduct(String productId) {
        int id = Integer.parseInt(productId);
        productRepository.deleteById(id);
        Boolean flag = productRepository.existsById(id);
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

    ProductType findProductTypeById(String categoryId) {
        if (categoryId != null && !categoryId.isEmpty()) {
            Optional<ProductType> category = productTypeRepository.findById(Integer.parseInt(categoryId));
            return category.get();
        }
        return null;
    }
}
