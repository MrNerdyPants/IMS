package com.sys.ims.util;

import com.sys.ims.model.Activity;
import com.sys.ims.model.Company;
import com.sys.ims.model.User;
import com.sys.ims.repository.ActivityRepository;
import com.sys.ims.repository.ProductRepository;
import com.sys.ims.service.impl.UserDetailsImpl;
import org.apache.commons.text.WordUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.*;

@Component
public class Utility {


    static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";

//    private final AppInitializer initializer;
    private final ApiResponse apiResponse;
    Map<String, String> mimeType;
    private final static String secret = Constants.SECRET_KEY;
    private static SecretKeySpec secretKey;
    private static byte[] key;

    public Utility(ApiResponse apiResponse) {
//        this.initializer = initializer;
        this.apiResponse = apiResponse;
//        mimeType = new HashMap<>(3);
//        mimeType.put("doc", "application/msword");
//        mimeType.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
//        mimeType.put("pdf", "application/pdf");
    }
//
//    public static Pageable getPagination(Pagination pagination, boolean isNativeQuery) {
//        if (isNotNullAndEmpty(pagination.getSort()) && isNativeQuery) {
//            Iterator<Sort> sorts;
//            List<Sort> sortList = new ArrayList<>();
//            Sort s;
//            for (String sortBy : pagination.getSort()) {
//                String parsedSort = parseFieldToNative(sortBy);
//                s = pagination.getOrderBy().equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(Sort.Order.desc(parsedSort)) : Sort.by(Sort.Order.asc(parsedSort));
//                sortList.add(s);
//            }
//            Sort group = null;
//            int index = 1;
//            for (Sort ss : sortList) {
//                if (index == 1) group = ss;
//                else group = ss.and(ss);
//                index++;
//            }
//            return PageRequest.of(pagination.getCurrentPage(), pagination.getPageSize(), group);
//        } else if (isNotNullAndEmpty(pagination.getSort())) {
//            Iterator<Sort> sorts;
//            List<Sort> sortList = new ArrayList<>();
//            Sort s;
//            for (String sortBy : pagination.getSort()) {
//                s = pagination.getOrderBy().equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(Sort.Order.desc(sortBy)) : Sort.by(Sort.Order.asc(sortBy));
//                sortList.add(s);
//            }
//            Sort group = null;
//            int index = 1;
//            for (Sort ss : sortList) {
//                if (index == 1) group = ss;
//                else group = ss.and(ss);
//                index++;
//            }
//            return PageRequest.of(pagination.getCurrentPage(), pagination.getPageSize(), group);
//        } else {
//            return PageRequest.of(pagination.getCurrentPage(), pagination.getPageSize());
//        }
//    }
//
//    public static Pageable getFilterINPagination(Pagination pagination, boolean isNativeQuery) {
//        if (isNotNullAndEmpty(pagination.getSort()) && isNativeQuery) {
//            Iterator<Sort> sorts;
//            List<Sort> sortList = new ArrayList<>();
//            Sort s;
//            for (String sortBy : pagination.getSort()) {
//                String parsedSort = parseFieldToNative(sortBy);
//                s = pagination.getOrderBy().equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(Sort.Order.desc(parsedSort)) : Sort.by(Sort.Order.asc(parsedSort));
//                sortList.add(s);
//            }
//            Sort group = null;
//            int index = 1;
//            for (Sort ss : sortList) {
//                if (index == 1) group = ss;
//                else group = ss.and(ss);
//                index++;
//            }
//            return PageRequest.of(pagination.getCurrentPage(), pagination.getPageSize(), group);
//        } else if (isNotNullAndEmpty(pagination.getSort())) {
//            Iterator<Sort> sorts;
//            List<Sort> sortList = new ArrayList<>();
//            Sort s;
//            for (String sortBy : pagination.getSort()) {
//                s = pagination.getOrderBy().equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(Sort.Order.desc(sortBy)) : Sort.by(Sort.Order.asc(sortBy));
//                sortList.add(s);
//            }
//            Sort group = null;
//            int index = 1;
//            for (Sort ss : sortList) {
//                if (index == 1) group = ss;
//                else group = ss.and(ss);
//                index++;
//            }
//            return PageRequest.of(pagination.getCurrentPage(), pagination.getPageSize(), group);
//        } else {
//            return PageRequest.of(pagination.getCurrentPage(), pagination.getPageSize());
//        }
//    }

    public static boolean isNotNullAndEmpty(String value) {
        return value != null && !value.isEmpty();
    }
    
    public static boolean isNotNullAndEmpty(int value) {
        return value != 0;
    }
    

    public static boolean isNotNullAndEmpty(List<?> value) {
        return value != null && !value.isEmpty();
    }

    public static boolean isNotNullAndEmpty(Object[] value) {
        return value != null && value.length > 0;
    }

    public static boolean isNotNull(Object value) {
        return value != null;
    }

    public static String parseFieldToNative(String value) {
        if (isNotNullAndEmpty(value)) {
            String regex = "([A-Z][a-z]+)";
            String replacement = "_$1";
            return value.replaceAll(regex, replacement).toLowerCase();
        }
        return null;
    }
    public static String convertToTitleCase(String value) {
        if (isNotNullAndEmpty(value)) {
            String regex = "([A-Z][a-z]+)";
            String replacement = " $1";
            return WordUtils.capitalizeFully(value.replaceAll(regex, replacement));
        }
        return null;
    }
    public static String parseFieldToFirstCap(String value) {
        if (isNotNullAndEmpty(value)) {
            String replacement = value.substring(0,1).toUpperCase()+value.substring(1);
            return replacement;
        }
        return null;
    }
    public static String generateMethodName(String value) {
        if (isNotNullAndEmpty(value)) {
            return "get"+parseFieldToFirstCap(value);
        }
        return null;
    }
    public static void getToLowerCase(String[] data) {
        if (isNotNullAndEmpty(data)) {
            for (int i = 0; i < data.length; i++) {
                data[i].toLowerCase();
            }
        }
    }

    public static void getToUpperCase(String[] data) {
        if (isNotNullAndEmpty(data)) {
            for (int i = 0; i < data.length; i++) {
                data[i].toUpperCase();
            }
        }
    }

    public static String saveFileName(MultipartFile file, Long id,String oldFile) {
        File temp = new File(Constants.UPLOAD_DIR);
        if (!temp.exists()) {
            temp.mkdir();
        }
        // check if file is empty
        if (file.isEmpty()) {
            //working on it
        }
        if (isNotNullAndEmpty(oldFile)) {
            String oldFileName = decrypt(oldFile);
            File previousFile = new File(Constants.UPLOAD_DIR+oldFileName);
            if (previousFile.isFile() && previousFile.exists()){
                previousFile.delete();
            }
        }
        // normalize the file path
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        // save the file on the local file system
        Path path = null;
        try {
            path = Paths.get(Constants.UPLOAD_DIR + (id != null ? id + "-" : "") + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path.getFileName().toString();
    }

    public boolean addUserIfNotExist(String username, UserDetails userDetails) {
//        if (!isNotNull(initializer.getUserCache().get(username))) {
//            initializer.getUserCache().put(username, userDetails);
//            return true;
//        }
        return false;
    }

    public boolean removeUserIfExist(String username) {
//        if (isNotNull(initializer.getUserCache().get(username))) {
//            initializer.getUserCache().remove(username);
//            return true;
//        }
        return false;
    }

    public boolean isUserExist(String username) {
        return false;
//        return isNotNull(initializer.getUserCache().get(username));
    }

    public String generateFileURL(String fileName) {
        if (isNotNullAndEmpty(fileName)) {
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/profile/download/")
                    .path(fileName)
                    .toUriString();
            return fileDownloadUri;
        }
        return null;
    }

//    public void downloadFile(HttpServletRequest req, HttpServletResponse res, String fileName) {
//        String dataDirectory = Constants.UPLOAD_DIR;
//        String decodeFile = decrypt(fileName);
//        Path file = Paths.get(dataDirectory, decodeFile);
//        if (file.toFile().exists()) {
//            res.setContentType(mimeType.get(FilenameUtils.getExtension(file.toString()).toLowerCase()));
//            res.addHeader("Content-Disposition", "attachment; filename=" + decodeFile);
//            try {
//                Files.copy(file, res.getOutputStream());
//                res.getOutputStream().flush();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
//            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
//            try {
//                OutputStream  responseStream = res.getOutputStream();
//                ObjectMapper mapper = new ObjectMapper();
//                mapper.writeValue(responseStream, buildApiResponse(HttpStatus.NOT_FOUND.value(), Constants.API_RESPONSE_FILE_NOT_FOUND,null));
//                responseStream.flush();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public ApiResponse buildApiResponse(Integer code, String message, Object response) {
        return new ApiResponse(code,message,response);
    }

    public void copyNonNullProperties(Object source, Object destination) {
        String [] ignoreList = getNullPropertyNames(source);
        BeanUtils.copyProperties(source, destination, ignoreList);
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            // check if value of this property is null then add it to the collection
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }



    public static void setKey(final String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(final String strToEncrypt) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getUrlEncoder()
                    .encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(final String strToDecrypt) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getUrlDecoder()
                    .decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    public UserDetailsImpl getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails;
    }

    public void recordActivity(String description,User actionBy,Company companyId,String profile, ActivityRepository activityRepository) {
        Activity activity = new Activity().builder()
                .description(description)
                .actionBy(actionBy.getName())
                .profile(profile)
                .preparedBy(actionBy)
                .companyId(companyId)
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .build();
       activityRepository.save(activity);
    }

//    public static boolean nameExist(GenericDto dto, GenericDto entity, GenericNameRepository repo, boolean isNewEntry) {
//        if (Utility.isNotNullAndEmpty(dto.getName())) {
//            GenericDto response = GenericDto.class.cast(repo.findByNameEqualsIgnoreCase(dto.getName()));
//            if (isNewEntry && response != null) {
//                return true;
//            } else
//                return !isNewEntry && response != null && !response.getId().equals(entity.getId());
//        }
//        return false;
//    }

//    public static boolean nameExist(GenericDto dto, GenericDto entity, GenericRepository repo, boolean isNewEntry, boolean isCompany) {
//        if(Utility.isNotNullAndEmpty(dto.getName()) && Utility.isNotNullAndEmpty(dto.getCompanyId()) && isCompany){
//            GenericDto response = GenericDto.class.cast(repo.findByNameEqualsIgnoreCaseAndCompanyId(dto.getName(),dto.getCompanyId()));
//            if (isNewEntry && response != null) {
//                return true;
//            } else
//                return !isNewEntry && response != null && !response.getId().equals(entity.getId());
//        }else if (Utility.isNotNullAndEmpty(dto.getName())) {
//            GenericDto response = GenericDto.class.cast(repo.findByNameEqualsIgnoreCase(dto.getName()));
//            if (isNewEntry && response != null) {
//                return true;
//            } else
//                return !isNewEntry && response != null && !response.getId().equals(entity.getId());
//        }
//        return false;
//    }

//    public static boolean shortNameExist(GenericDto dto, GenericDto entity, GenericRepository repo, boolean isNewEntry, boolean isCompany) {
//        if(Utility.isNotNullAndEmpty(dto.getShortName()) && Utility.isNotNullAndEmpty(dto.getCompanyId()) && isCompany){
//            GenericDto response = GenericDto.class.cast(repo.findByShortNameEqualsIgnoreCaseAndCompanyId(dto.getShortName(),dto.getCompanyId()));
//            if (isNewEntry && response != null) {
//                return true;
//            } else
//                return !isNewEntry && response != null && !response.getId().equals(entity.getId());
//        }else if (Utility.isNotNullAndEmpty(dto.getShortName())) {
//            GenericDto response = GenericDto.class.cast(repo.findByShortNameEqualsIgnoreCase(dto.getShortName()));
//            if (isNewEntry && response != null) {
//                return true;
//            } else
//                return !isNewEntry && response != null && !response.getId().equals(entity.getId());
//        }
//        return false;
//    }

//    public static boolean mobileNoExist(GenericDto dto, GenericDto entity, GenericRepository repo, boolean isNewEntry, boolean isCompany) {
//        if(Utility.isNotNullAndEmpty(dto.getMobileNo()) && Utility.isNotNullAndEmpty(dto.getCompanyId()) && isCompany){
//            GenericDto response = GenericDto.class.cast(repo.findByMobileNoEqualsIgnoreCaseAndCompanyId(dto.getMobileNo(),dto.getCompanyId()));
//            if (isNewEntry && response != null) {
//                return true;
//            } else
//                return !isNewEntry && response != null && !response.getId().equals(entity.getId());
//        }else if (Utility.isNotNullAndEmpty(dto.getMobileNo())) {
//            GenericDto response = GenericDto.class.cast(repo.findByMobileNoEqualsIgnoreCase(dto.getMobileNo()));
//            if (isNewEntry && response != null) {
//                return true;
//            } else
//                return !isNewEntry && response != null && !response.getId().equals(entity.getId());
//        }
//        return false;
//    }

//    public static boolean emailExist(GenericDto dto, GenericDto entity, GenericRepository repo, boolean isNewEntry, boolean isCompany) {
//        if(Utility.isNotNullAndEmpty(dto.getEmail()) && Utility.isNotNullAndEmpty(dto.getCompanyId()) && isCompany){
//            GenericDto response = GenericDto.class.cast(repo.findByEmailEqualsIgnoreCaseAndCompanyId(dto.getEmail(),dto.getCompanyId()));
//            if (isNewEntry && response != null) {
//                return true;
//            } else
//                return !isNewEntry && response != null && !response.getId().equals(entity.getId());
//        }else if (Utility.isNotNullAndEmpty(dto.getEmail())) {
//            GenericDto response = GenericDto.class.cast(repo.findByEmailEqualsIgnoreCase(dto.getEmail()));
//            if (isNewEntry && response != null) {
//                return true;
//            } else
//                return !isNewEntry && response != null && !response.getId().equals(entity.getId());
//        }
//        return false;
//    }

//    public static boolean modelExist(GenericDto dto, GenericDto entity, ProductRepository repo, boolean isNewEntry) {
//        if(Utility.isNotNullAndEmpty(dto.getModelTitle()) && Utility.isNotNullAndEmpty(dto.getCompanyId())){
//            GenericDto response = GenericDto.class.cast(repo.findByModelTitleEqualsIgnoreCaseAndCompanyId(dto.getModelTitle(),dto.getCompanyId()));
//            if (isNewEntry && response != null) {
//                return true;
//            } else
//                return !isNewEntry && response != null && !response.getId().equals(entity.getId());
//        }
//        return false;
//    }

//    public static boolean phoneExist(GenericDto dto, GenericDto entity, GenericRepository repo, boolean isNewEntry, boolean isCompany) {
//        if(Utility.isNotNullAndEmpty(dto.getPhone()) && Utility.isNotNullAndEmpty(dto.getCompanyId()) && isCompany){
//            List<GenericDto> response = repo.findByPhoneInAndCompanyId(dto.getPhone(),dto.getCompanyId());
//            if (isNewEntry && response != null && response.size() > 0) {
//                return true;
//            }  else {
//                boolean idFlag = false;
//                for(GenericDto genericDto : response){
//                    if(!genericDto.getId().equals(entity.getId())){
//                        idFlag = true;
//                    }
//                }
//                return !isNewEntry && response != null && response.size() > 1 && idFlag;
//            }
//        }else if (Utility.isNotNullAndEmpty(dto.getPhone())) {
//            List<GenericDto> response = repo.findByPhoneIn(dto.getPhone());
//            if (isNewEntry && response != null && response.size() > 0) {
//                return true;
//            } else {
//                boolean idFlag = false;
//                    for(GenericDto genericDto : response){
//                        if(!genericDto.getId().equals(entity.getId())){
//                            idFlag = true;
//                        }
//                    }
//                return !isNewEntry && response != null && response.size() > 1 && idFlag;
//            }
//        }
//        return false;
//    }

//    public static boolean nameExist(GenericDto dto, Object entity, Object repo, boolean isNewEntry) {
//        if (Utility.isNotNullAndEmpty(dto.getName())) {
//            Optional<Object> checkUsername = repo.findByNameEqualsIgnoreCase(dto.getName());
//            if (isNewEntry && checkUsername.isPresent()) {
//                return true;
//            } else
//                return !isNewEntry && !checkUsername.isPresent() && !checkUsername.get().getId().equals(entity.getId());
//        }
//        return false;
//    }
}
