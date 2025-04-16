CREATE TABLE COUNTRY(
    COUNTRY_ID NUMBER,
    COOUNTY_NME VARCHAR2(64),
    PREPARED_DTE DATE DEFAULT SYSDATE,
    CONSTRAINT PK_COUNTRY_CONTRYID PRIMARY KEY(COUNTRY_ID)
);

CREATE SEQUENCE SEQ_COUNTRY_ID CACHE 2;

CREATE TABLE PROVINCE(
    PROVINCE_ID NUMBER,
    PROVINCE_NME VARCHAR2(64),
    COUNTRY_ID NUMBER,    
    CONSTRAINT PK_PROVINCE_PROVINCEID PRIMARY KEY(PROVINCE_ID),
    CONSTRAINT FK_PROVINCE_CONTRYID FOREIGN KEY(COUNTRY_ID) REFERENCES COUNTRY(COUNTRY_ID)
);

CREATE SEQUENCE SEQ_PROVINCE_ID CACHE 2;

CREATE TABLE CITY(
    CITY_ID NUMBER,
    CITY_NME VARCHAR2(64),
    COUNTRY_ID NUMBER,
    PROVINCE_ID NUMBER,
    PREPARED_DTE DATE DEFAULT SYSDATE,
    CONSTRAINT PK_CITY_CTYID PRIMARY KEY(CITY_ID),
    CONSTRAINT FK_CITY_PROVINCEID FOREIGN KEY(PROVINCE_ID) REFERENCES PROVINCE(PROVINCE_ID),
    CONSTRAINT FK_CITY_COUNTRYID FOREIGN KEY(COUNTRY_ID) REFERENCES COUNTRY(COUNTRY_ID)    
);

CREATE SEQUENCE SEQ_CITY_ID CACHE 2;

CREATE TABLE COMPANY(
    COMPANY_ID NUMBER,
    COUNTRY_ID NUMBER,
    PROVINCE_ID NUMBER,
    CITY_ID NUMBER,
    COMPANY_NME VARCHAR(256),
    ADDRESS VARCHAR2(512),
    OFFICE_ADDRESS VARCHAR2(512),
    LAND_LINE VARCHAR2(32),
    AUTH_PERSON VARCHAR2(128),
    EMAIL VARCHAR2(256),
    NTN_NBR VARCHAR2(16),
    GST_NBR VARCHAR2(16),
    SECP_NBR VARCHAR2(16),
    LOGIN_ID VARCHAR2(256),
    LOGIN_PASSWORD VARCHAR2(512),
    LOGO VARCHAR2(256),
    FILE_NME VARCHAR2(256),
    FILE_SIZE VARCHAR2(32),
    FILE_TYP VARCHAR2(8),
    CONSTRAINT PK_COMPANY_COMPNYID PRIMARY KEY(COMPANY_ID),
    CONSTRAINT FK_COMPANY_PROVINCEID FOREIGN KEY(PROVINCE_ID) REFERENCES PROVINCE(PROVINCE_ID),
    CONSTRAINT FK_COMPANY_COUNTRYID FOREIGN KEY(COUNTRY_ID) REFERENCES COUNTRY(COUNTRY_ID)   ,
    CONSTRAINT FK_COMPANY_CITYID FOREIGN KEY(CITY_ID) REFERENCES CITY(CITY_ID)   
);

CREATE SEQUENCE SEQ_COMPANY_ID CACHE 2;

CREATE TABLE IMS_ACTIVITY(
    ACTIVITY_ID NUMBER,
    ACTIVITY_DESC VARCHAR2(256),
    ACTION_BY VARCHAR2(128),
    ACTIVITY_PROFILE VARCHAR2(128),
    PREPARED_BY VARCHAR2(128),
    PREPARED_DTE DATE,
    COMPANY_ID NUMBER,
    CONSTRAINT PK_ACTIVITY_ACTIVITYID PRIMARY KEY(ACTIVITY_ID),
    CONSTRAINT FK_ACTIVITY_COMPANYID FOREIGN KEY(COMPANY_ID) REFERENCES COMPANY(COMPANY_ID)
);

CREATE SEQUENCE SEQ_ACTIVITY_ID CACHE 2;

CREATE TABLE SITE(
    SITE_ID NUMBER,
    SITE_NME VARCHAR2(64),
    ADDRESS VARCHAR2(256),
    COUNTRY_ID NUMBER,
    PROVINCE_ID NUMBER,
    CITY_ID NUMBER,
    PREPARED_BY VARCHAR2(128),
    UPDATED_BY VARCHAR2(128),
    COMPANY_ID NUMBER,
    CONSTRAINT PK_SITE_SITEID PRIMARY KEY(SITE_ID),
    CONSTRAINT FK_SITE_COUNTRYID FOREIGN KEY(COUNTRY_ID) REFERENCES COUNTRY(COUNTRY_ID),
    CONSTRAINT FK_SITE_PROVINCEID FOREIGN KEY(PROVINCE_ID) REFERENCES PROVINCE(PROVINCE_ID),
    CONSTRAINT FK_SITE_CITYID FOREIGN KEY(CITY_ID) REFERENCES CITY(CITY_ID),
    CONSTRAINT FK_SITE_COMPANYID FOREIGN KEY(COMPANY_ID) REFERENCES COMPANY(COMPANY_ID)
);

CREATE SEQUENCE SEQ_SITE_ID CACHE 2;

CREATE TABLE WEB_USERS(
    USER_ID NUMBER,
    USER_NAME VARCHAR2(128),
    LOGIN_ID VARCHAR2(256),
    USER_PASSWORD VARCHAR2(256),
    USER_EMAIL VARCHAR2(128),
    USER_TYPE VARCHAR2(32),
    USER_PROFILE VARCHAR2(64),
    USER_REF_ID VARCHAR2(32),
    FILE_NME VARCHAR2(128),
    FILE_SIZE VARCHAR2(16),
    FILE_TYPE VARCHAR2(8),
    COMPANY_ID NUMBER,
    CONSTRAINT PK_USER_USERID PRIMARY KEY(USER_ID),
    CONSTRAINT UK_USER_LOGINID UNIQUE(LOGIN_ID),
    CONSTRAINT FK_USER_COMPID FOREIGN KEY(COMPANY_ID) REFERENCES COMPANY(COMPANY_ID)
);

CREATE SEQUENCE SEQ_USER_ID CACHE 2;

CREATE TABLE IMS_CUSTOMER(
    CUSTOMER_ID NUMBER,
    CUSTOMER_NME VARCHAR2(128),
    CUSTOMER_SHORT_NME VARCHAR2(128),
    COUNTRY_ID NUMBER,
    PROVINCE_ID NUMBER,    
    CITY_ID NUMBER,
    SITE_ID NUMBER,
    ADDRESS VARCHAR2(256),
    OFFICE_ADDRESS VARCHAR2(256),
    MOBILE_NO VARCHAR2(20),
    LAND_LINE VARCHAR2(20),
    PHONE VARCHAR2(256),
    AUTH_PERSON VARCHAR2(128),
    EMAIL VARCHAR2(128),
    NTN_NBR VARCHAR2(16),
    GST_NBR VARCHAR2(16),
    SCEP_NBR VARCHAR2(16),
    LOGIN_ID VARCHAR2(128),
    LOGIN_PASSWORD VARCHAR2(256),
    LOGO VARCHAR2(128),
    FILE_NME VARCHAR2(128),
    FILE_SIZE VARCHAR2(20),
    FILE_TYPE VARCHAR2(8),
    PREPARED_BY VARCHAR2(128),
    UPDATED_BY VARCHAR2(128),
    COMPANY_ID NUMBER,
    CONSTRAINT PK_CUSTOMER_CUSTOMERID PRIMARY KEY(CUSTOMER_ID),
    CONSTRAINT FK_CUSTOMER_CONTRYID FOREIGN KEY(COUNTRY_ID) REFERENCES COUNTRY(COUNTRY_ID),
    CONSTRAINT FK_CUSTOMER_PROVINCEID FOREIGN KEY(PROVINCE_ID) REFERENCES PROVINCE(PROVINCE_ID),
    CONSTRAINT FK_CUSTOMER_CITYID FOREIGN KEY(CITY_ID) REFERENCES CITY(CITY_ID),
    CONSTRAINT FK_CUSTOMER_SITEID FOREIGN KEY(SITE_ID) REFERENCES SITE(SITE_ID),
    CONSTRAINT FK_CUSTOMER_COMPID FOREIGN KEY(COMPANY_ID) REFERENCES COMPANY(COMPANY_ID)
);

CREATE SEQUENCE SEQ_CUSTOMER_ID CACHE 2;

CREATE TABLE IMS_CUSTOMER_CATEGORY(
    CUSTOMER_CATEGORY_ID NUMBER,
    CATEGORY_ID NUMBER,
    CUSTOMER_ID NUMBER,
    CONSTRAINT PK_CUSTC_CUSTCTGRYID PRIMARY KEY(CUSTOMER_CATEGORY_ID),
    CONSTRAINT FK_CUSTC_CATGRYID FOREIGN KEY(CATEGORY_ID) REFERENCES IMS_CATEGORY(CATEGORY_ID),
    CONSTRAINT FK_CUSTC_CUSTCID FOREIGN KEY(CUSTOMER_ID) REFERENCES IMS_CUSTOMER(CUSTOMER_ID)
);
CREATE SEQUENCE SEQ_CUSTOMER_CATEGORY_ID CACHE 2;

CREATE TABLE IMS_CUSTOMER_PHONE(
   CUSTOMER_PHONE_ID NUMBER,
   CUSTOMER_ID NUMBER,
   PHONE_NO VARCHAR2(20),
   CONSTRAINT PK_CUSTP_CUSTPHONEID PRIMARY KEY (CUSTOMER_PHONE_ID),
   CONSTRAINT FK_CUSTP_CUSTPID FOREIGN KEY(CUSTOMER_ID) REFERENCES IMS_CUSTOMER(CUSTOMER_ID)
);
CREATE SEQUENCE SEQ_CUSTOMER_PHONE_ID CACHE 2;



CREATE TABLE IMS_VENDOR_CATEGORY(
    VENDOR_CATEGORY_ID NUMBER,
    CATEGORY_ID NUMBER,
    VENDOR_ID NUMBER,
    CONSTRAINT PK_VNDRC_VNDRCTGRYID PRIMARY KEY(VENDOR_CATEGORY_ID),
    CONSTRAINT FK_VNDRC_CATGRYID FOREIGN KEY(CATEGORY_ID) REFERENCES IMS_CATEGORY(CATEGORY_ID),
    CONSTRAINT FK_VNDRC_VNDRID FOREIGN KEY(VENDOR_ID) REFERENCES IMS_VENDOR(VENDOR_ID)
);

CREATE SEQUENCE SEQ_VENDOR_CATEGORY_ID CACHE 2;

CREATE TABLE IMS_VENDOR_PHONE(
   VENDOR_PHONE_ID NUMBER,
   VENDOR_ID NUMBER,
   PHONE_NO VARCHAR2(20),
   CONSTRAINT PK_VNDRP_CUSTPHONEID PRIMARY KEY (VENDOR_PHONE_ID),
   CONSTRAINT FK_VNDRP_CUSTPID FOREIGN KEY(VENDOR_ID) REFERENCES IMS_VENDOR(VENDOR_ID)
);

CREATE SEQUENCE SEQ_VENDOR_PHONE_ID CACHE 2;


CREATE TABLE IMS_CONTRACT(
     CONTRACT_ID NUMBER NOT NULL,
     CONTRACT_DTE DATE,
     CONTRACT_EXPIRY DATE,
     CONTRACT_PERIOD VARCHAR2(128),
     CUSTOMER_ID NUMBER,
     CREATED_BY NUMBER,
     UPDATED_BY NUMBER,
     COMPANY_ID NUMBER,
     CONSTRAINT PK_CONTACT_CONTACTID PRIMARY KEY(CONTRACT_ID),
     CONSTRAINT FK_CONTACT_CUTOMERID FOREIGN KEY(CUSTOMER_ID) REFERENCES IMS_CUSTOMER(CUSTOMER_ID),
     CONSTRAINT FK_CONTRACT_CREATEDBY FOREIGN KEY(CREATED_BY) REFERENCES WEB_USERS(USER_ID),
     CONSTRAINT FK_CONTRACT_UPDATEDBY FOREIGN KEY(UPDATED_BY) REFERENCES WEB_USERS(USER_ID),
     CONSTRAINT FK_CONTRACT_COMPANYID FOREIGN KEY(COMPANY_ID) REFERENCES COMPANY(COMPANY_ID)
);

CREATE SEQUENCE SEQ_CONTRACT_ID CACHE 2;

CREATE TABLE IMS_CONTRACT_DETAIL(
    CONTRACT_DETAIL_ID NUMBER NOT NULL,
    CONTRACT_ID NUMBER NOT NULL,
    CONTRACT_DETAIL_TYPE VARCHAR2(64),
    PAYMENT_TERM VARCHAR2(128),
    NO_OF_VISITS NUMBER,
    AMOUNT NUMBER,
    CONSTRAINT PK_CNTRTDTL_CONTRACTDETAILID PRIMARY KEY(CONTRACT_DETAIL_ID),
    CONSTRAINT FK_CNTRTDTL_CONTRACTID FOREIGN KEY(CONTRACT_ID) REFERENCES IMS_CONTRACT(CONTRACT_ID)
);

CREATE SEQUENCE SEQ_CONTRACT_DETAIL_ID CACHE 2;

CREATE TABLE RIGHT(
    RIGHT_ID NUMBER,
    PARENT_ID NUMBER,
    RIGHT_TXT VARCHAR2(250),
    ICON VARCHAR2(128),
    SORT NUMBER(6,2) ,
    URL VARCHAR2(250),
    ACTIVE_IND VARCHAR2(1) DEFAULT 'Y',
    DISPLAY_IND VARCHAR2(1) DEFAULT 'Y',
    CONSTRAINT PK_RIGHT_RIGHTID PRIMARY KEY(RIGHT_ID)
);

CREATE SEQUENCE SEQ_RIGHT_ID CACHE 2;

------------------------- 29-10-2024

CREATE TABLE IMS_ROLE(
    ROLE_ID NUMBER,
    ROLE_NME VARCHAR2(64),
    ACTIVE_IND VARCHAR2(1) DEFAULT 'Y',
    CONSTRAINT PK_ROLE_ROLEID PRIMARY KEY(ROLE_ID)
);

CREATE SEQUENCE SEQ_ROLE_ID CACHE 2;

CREATE TABLE IMS_USER_ROLE(
    USER_ROLE_ID NUMBER,
    USER_ID NUMBER,
    ROLE_ID NUMBER,
    CONSTRAINT PK_USERROLE_USERROLEID PRIMARY KEY(USER_ROLE_ID),
    CONSTRAINT FK_USERROLE_USERID FOREIGN KEY(USER_ID) REFERENCES WEB_USERS(USER_ID),
    CONSTRAINT FK_USERROLE_ROLEID FOREIGN KEY(ROLE_ID) REFERENCES IMS_ROLE(ROLE_ID)
);

CREATE SEQUENCE SEQ_USER_ROLE_ID CACHE 2;

--------------------- 30-10-2024

-- Old DB updates

ALTER TABLE RIGHTS ADD(
   CONSTRAINT UK_RIGHTS_RIGHTID UNIQUE (RIGHT_ID) 
);


ALTER TABLE ROLE_RIGHTS ADD (
    CAN_EXPORT VARCHAR2(1),
    RIGHT_ID NUMBER,
    CONSTRAINT FK_ROLERIGHT_RIGHTID  FOREIGN KEY(RIGHT_ID) REFERENCES RIGHTS(RIGHT_ID)
);

--- New DB Setup


CREATE TABLE ROLE_RIGHT(
    ROLE_RIGHT_ID NUMBER,
    RIGHT_ID NUMBER,
    ROLE_ID NUMBER,
    CAN_EDIT VARCHAR2(1),
    CAN_DELETE VARCHAR2(1),
    CAN_EXPORT VARCHAR2(1),
    CONSTRAINT PK_ROLERIGHT_ROLERIGHTID PRIMARY KEY(ROLE_RIGHT_ID),
    CONSTRAINT FK_ROLERIGHT_RIGHTID FOREIGN KEY(RIGHT_ID) REFERENCES RIGHT(RIGHT_ID),
    CONSTRAINT FK_ROLERIGHT_ROLEID FOREIGN KEY(ROLE_ID) REFERENCES IMS_ROLE(ROLE_ID)
);

CREATE SEQUENCE SEQ_ROLE_RIGHT_ID CACHE 2;

---------------------------------- 30-Oct-2024

ALTER TABLE IMS_ROLE RENAME COLUMN ACTIVE_IND TO ROLE_ACTIVE_IND;

----------------------------------- 11-Nov-2024

CREATE TABLE COMPANY_PHONE(
    PHONE_ID NUMBER,
    COMPANY_ID NUMBER,
    PHONE_NBR VARCHAR2(25),
    CONSTRAINT PK_COMPANYPHONE_PHONEID PRIMARY KEY (PHONE_ID),
    CONSTRAINT UK_COMPANYPHONE_PHONENBR UNIQUE(PHONE_NBR),
    CONSTRAINT FK_COMPANYPHONR_COMPID FOREIGN KEY(COMPANY_ID) REFERENCES COMPANY(COMPANY_ID)
);

CREATE SEQUENCE SEQ_PHONE_ID CACHE 2;

-------------------------------  01-Jan-2025

CREATE TABLE IMS_CATEGORY(
    CATEGORY_ID NUMBER NOT NULL,
    CATEGORY_NAME VARCHAR2(64),
    CATEGORY_CODE VARCHAR2(8),
    REF_CATEGORY_ID NUMBER,
    CATEGORY_ACTIVE_IND VARCHAR2(1),
    CONSTRAINT PK_IMSCATEGORY_COMPCATEGORYID PRIMARY KEY(CATEGORY_ID),
    CONSTRAINT FK_IMSCATEGORY_REFCATGORYID FOREIGN KEY(REF_CATEGORY_ID) REFERENCES IMS_CATEGORY(CATEGORY_ID)
);

ALTER TABLE IMS_CATEGORY ADD (
    COMPANY_ID NUMBER,
    CONSTRAINT FK_IMSCATEGORY_COMPANYID FOREIGN KEY(COMPANY_ID) REFERENCES COMPANY(COMPANY_ID)
);

CREATE SEQUENCE SEQ_CATEGORY_ID CACHE 2;

ALTER TABLE COMPANY ADD (
    CATEGORY_ID NUMBER,
    CONSTRAINT FK_COMPANY_CATEGORYID FOREIGN KEY(CATEGORY_ID) REFERENCES IMS_CATEGORY(CATEGORY_ID)
);


CREATE TABLE IMS_USER_SITE(
    USER_SITE_ID NUMBER NOT NULL,
    USER_ID NUMBER,
    SITE_ID NUMBER,    
    CONSTRAINT PK_USERSITE_USERSITEID PRIMARY KEY(USER_SITE_ID),
    CONSTRAINT FK_USERSITE_USERID FOREIGN KEY(USER_ID) REFERENCES WEB_USERS(USER_ID),
    CONSTRAINT FK_USERSITE_SITEID FOREIGN KEY(SITE_ID) REFERENCES SITE(SITE_ID)
);

CREATE SEQUENCE SEQ_USER_SITE_ID CACHE 2;

CREATE TABLE USER_RIGHT(
    USER_RIGHT_ID NUMBER NOT NULL,
    USER_ID NUMBER,
    RIGHT_ID NUMBER,    
    CONSTRAINT PK_USERRIGHT_USERRIGHTID PRIMARY KEY(USER_RIGHT_ID),
    CONSTRAINT FK_USERRIGHT_USERID FOREIGN KEY(USER_ID) REFERENCES WEB_USERS(USER_ID),
    CONSTRAINT FK_USERRIGHT_RIGHTID FOREIGN KEY(RIGHT_ID) REFERENCES RIGHT(RIGHT_ID)
);

CREATE SEQUENCE SEQ_USER_RIGHT_ID CACHE 2;


-----------------------------


CREATE TABLE IMS_CATEGORY_TYPE(
    CATEGORY_TYPE_ID NUMBER NOT NULL,
    TITLE VARCHAR2(64),
    CODE VARCHAR2(32),
    CONSTRAINT PK_CATEGORYTYPE_CATGRYTYPEID PRIMARY KEY(CATEGORY_TYPE_ID)
);

CREATE SEQUENCE SEQ_CATEGORY_TYPE_ID CACHE 2;

CREATE TABLE IMS_PRODUCT_TYPE(
    PRODUCT_TYPE_ID NUMBER NOT NULL,
    CATEGORY_ID NUMBER NOT NULL,
    TITLE VARCHAR2(64),
    CODE VARCHAR2(32),
    CONSTRAINT PK_PROD_PRODTYPEID PRIMARY KEY(PRODUCT_TYPE_ID),
    CONSTRAINT FK_PROD_CATEGRYTYPID FOREIGN KEY(CATEGORY_ID) REFERENCES IMS_CATEGORY(CATEGORY_ID)
);

CREATE SEQUENCE SEQ_PRODUCT_TYPE_ID CACHE 2;

CREATE TABLE IMS_ISSUE_TYPE(
   ISSUE_TYPE_ID NUMBER NOT NULL,
   ISSUE_NME VARCHAR2(64),
   CREATED_BY VARCHAR2(64),
   UPDATED_BY VARCHAR2(64),
   COMPANY_ID NUMBER,
   CONSTRAINT PK_ISSUETYPE_ISSUETYPID PRIMARY KEY(ISSUE_TYPE_ID),
   CONSTRAINT FK_ISSUETYPE_COMPANYID FOREIGN KEY(COMPANY_ID) REFERENCES COMPANY(COMPANY_ID)
);

CREATE SEQUENCE SEQ_ISSUE_TYPE_ID CACHE 2;


CREATE TABLE IMS_FEATURE(
    IMS_FEATURE_ID NUMBER,
    FEATURE_NME VARCHAR2(256),
    FEATURE_MODEL VARCHAR2(128),
    REF_CATEGORY_ID VARCHAR2(128),
    FEATURE_ICON VARCHAR2(128),
    CATEGORY_ID NUMBER,
    PRODUCT_ID NUMBER,
    CONSTRAINT PK_FEATURE_FEATUREID PRIMARY KEY(IMS_FEATURE_ID)
);

CREATE SEQUENCE SEQ_FEATURE_ID CACHE 2;


CREATE TABLE UNIT (
  UNIT_ID NUMBER NOT NULL,
  TITLE VARCHAR2(8)
);

CREATE SEQUENCE SEQ_UNIT_ID CACHE 2;


CREATE TABLE IMS_HAZARD_TYPE (
  HAZARD_TYPE_ID NUMBER NOT NULL,
  TITLE VARCHAR2(256),
  CONSTRAINT PK_HAZARDTYPE_HAZARDTYPEID PRIMARY KEY(HAZARD_TYPE_ID)
);
CREATE SEQUENCE SEQ_HAZARD_TYPE_ID CACHE 2;

CREATE TABLE IMS_HAZARD(
    HAZARD_ID NUMBER,
    CATEGORY_ID NUMBER,
    HAZARD_CDE VARCHAR2(32),
    HAZARD_TYPE_ID NUMBER,
    HAZARD_DESC VARCHAR2(1000),
    COMPANY_ID NUMBER,
    CONSTRAINT PK_HAZARD_HAZARDID PRIMARY KEY(HAZARD_ID),
    CONSTRAINT FK_HAZARD_CATEGORYID FOREIGN KEY(CATEGORY_ID) REFERENCES IMS_CATEGORY(CATEGORY_ID),
    CONSTRAINT FK_HAZARD_HAZARDTYPEID FOREIGN KEY(HAZARD_TYPE_ID) REFERENCES IMS_HAZARD_TYPE(HAZARD_TYPE_ID),
    CONSTRAINT FK_HAZARD_COMPANYID FOREIGN KEY(COMPANY_ID) REFERENCES COMPANY(COMPANY_ID)
);

CREATE SEQUENCE SEQ_HAZARD_ID CACHE 2;

CREATE TABLE IMS_HAZARD_SYMTOM(
  SYMTOM_ID NUMBER NOT NULL,
  HAZARD_ID NUMBER,
  SYMTOM VARCHAR2(1000),
  CONSTRAINT PK_SYMTOM_SYSTOMID PRIMARY KEY(SYMTOM_ID),
  CONSTRAINT FK_SYMTOM_HAZARDID FOREIGN KEY(HAZARD_ID) REFERENCES IMS_HAZARD(HAZARD_ID)
);

CREATE SEQUENCE SEQ_SYMTOM_ID CACHE 2;

CREATE TABLE IMS_HAZARD_CORRECTIVE(
  CORRECTIVE_ID NUMBER NOT NULL,
  HAZARD_ID NUMBER,
  CORRECTIVE VARCHAR2(1000),
  CONSTRAINT PK_CORRECTIVE_CORRECTIVEID PRIMARY KEY(CORRECTIVE_ID),
  CONSTRAINT FK_CORRECTIVE_HAZARDID FOREIGN KEY(HAZARD_ID) REFERENCES IMS_HAZARD(HAZARD_ID)
);

CREATE SEQUENCE SEQ_CORRECTIVE_ID CACHE 2;

CREATE TABLE IMS_MANUFACTURER(
     MANUFACTURER_ID NUMBER NOT NULL,
     MANUFACTURER_NME VARCHAR2(64),
     MANUFACTURER_SHORT_NME VARCHAR2(64),
     COUNTRY_ID NUMBER,
     CITY_ID NUMBER,
     ADDRESS VARCHAR2(64),
     MOBILE_NO VARCHAR2(20),
     LAND_LINE VARCHAR2(20),
     WEB_LINK VARCHAR2(500),
     INTRODUCE VARCHAR2(500),
     LOGO BLOB,
     FILE_NME VARCHAR2(64),
     FILE_SIZE VARCHAR2(15),
     FILE_TYPE VARCHAR2(32),
     CREATED_BY NUMBER,
     UPDATED_BY NUMBER,
     COMPANY_ID NUMBER,
     CONSTRAINT PK_MANUFCTURER_MANUFACTURERID PRIMARY KEY(MANUFACTURER_ID),
     CONSTRAINT FK_MANUFCTURER_COUNTRYID FOREIGN KEY(COUNTRY_ID) REFERENCES COUNTRY(COUNTRY_ID),
     CONSTRAINT FK_MANUFCTURER_CITYID FOREIGN KEY(CITY_ID) REFERENCES CITY(CITY_ID),
     CONSTRAINT FK_MANUFCTURER_CREATEDBY FOREIGN KEY(CREATED_BY) REFERENCES WEB_USERS(USER_ID),
     CONSTRAINT FK_MANUFCTURER_UPDATEDBY FOREIGN KEY(UPDATED_BY) REFERENCES WEB_USERS(USER_ID),
     CONSTRAINT FK_MANUFCTURER_COMPANYID FOREIGN KEY(COMPANY_ID) REFERENCES COMPANY(COMPANY_ID)
);

CREATE SEQUENCE SEQ_MANUFACTURER_ID CACHE 2;




CREATE TABLE IMS_VENDOR(
   VENDOR_ID NUMBER,
   VENDOR_NME VARCHAR2(128),
   VENDOR_SHORT_NME VARCHAR2(128),
   COUNTRY_ID NUMBER,
   PROVINCE_ID NUMBER,
   CITY_ID NUMBER,
   SITE_ID NUMBER,
   ADDRESS VARCHAR2(256),
   OFFICE_ADDRESS VARCHAR2(256),
   MOBILE_NO VARCHAR2(20),
   LAND_LINE VARCHAR2(20),
   PHONE VARCHAR2(256),
   AUTH_PERSON VARCHAR2(128),
   EMAIL VARCHAR2(128),
   NTN_NBR VARCHAR2(16),
   GST_NBR VARCHAR2(16),
   SCEP_NBR VARCHAR2(16),
   LOGIN_ID VARCHAR2(128),
   LOGIN_PASSWORD VARCHAR2(256),
   LOGO VARCHAR2(128),
   FILE_NME VARCHAR2(128),
   FILE_SIZE VARCHAR2(20),
   FILE_TYPE VARCHAR2(8),
   PREPARED_BY VARCHAR2(128),
   UPDATED_BY VARCHAR2(128),
   COMPANY_ID NUMBER,
   CONSTRAINT PK_VENDOR_VENDORID PRIMARY KEY(VENDOR_ID),
   CONSTRAINT FK_VENDOR_CONTRYID FOREIGN KEY(COUNTRY_ID) REFERENCES COUNTRY(COUNTRY_ID),
   CONSTRAINT FK_VENDOR_PROVINCEID FOREIGN KEY(PROVINCE_ID) REFERENCES PROVINCE(PROVINCE_ID),
   CONSTRAINT FK_VENDOR_CITYID FOREIGN KEY(CITY_ID) REFERENCES CITY(CITY_ID),
   CONSTRAINT FK_VENDOR_SITEID FOREIGN KEY(SITE_ID) REFERENCES SITE(SITE_ID),
   CONSTRAINT FK_VENDOR_COMPID FOREIGN KEY(COMPANY_ID) REFERENCES COMPANY(COMPANY_ID)
);

CREATE SEQUENCE SEQ_VENDOR_ID CACHE 2;


CREATE TABLE IMS_DEPARTMENT(
   DEPARTMENT_ID NUMBER,
   DEPARTMENT_NME VARCHAR2(64),
   CONSTRAINT PK_DEPARTMENT_DEPARTMENTID PRIMARY KEY(DEPARTMENT_ID)
);

CREATE SEQUENCE SEQ_DEPARTMENT_ID CACHE 2;


INSERT INTO IMS_DEPARTMENT VALUES(SEQ_DEPARTMENT_ID.NEXTVAL, 'DEPARTMENT 01');
INSERT INTO IMS_DEPARTMENT VALUES(SEQ_DEPARTMENT_ID.NEXTVAL, 'DEPARTMENT 02');
INSERT INTO IMS_DEPARTMENT VALUES(SEQ_DEPARTMENT_ID.NEXTVAL, 'DEPARTMENT 03');
INSERT INTO IMS_DEPARTMENT VALUES(SEQ_DEPARTMENT_ID.NEXTVAL, 'DEPARTMENT 04');


CREATE TABLE IMS_DESIGNATION(
    DESIGNATION_ID NUMBER,
    DESIGNATION_NME VARCHAR2(64),
    CONSTRAINT PK_DESIGNATION_DESIGNATIONID PRIMARY KEY(DESIGNATION_ID)
);

CREATE SEQUENCE SEQ_DESIGNATION_ID CACHE 2;


INSERT INTO IMS_DESIGNATION VALUES(SEQ_DESIGNATION_ID.NEXTVAL, 'DESIGNATION 01');
INSERT INTO IMS_DESIGNATION VALUES(SEQ_DESIGNATION_ID.NEXTVAL, 'DESIGNATION 02');
INSERT INTO IMS_DESIGNATION VALUES(SEQ_DESIGNATION_ID.NEXTVAL, 'DESIGNATION 03');
INSERT INTO IMS_DESIGNATION VALUES(SEQ_DESIGNATION_ID.NEXTVAL, 'DESIGNATION 04');


CREATE TABLE IMS_CHECKLIST_TYPE(
    CHECKLIST_TYPE_ID NUMBER,
    TITLE VARCHAR2(128),
    CONSTRAINT PK_CHCKTYP_CHCKTYPID PRIMARY KEY(CHECKLIST_TYPE_ID)
);
CREATE SEQUENCE SEQ_CHECKLIST_TYPE_ID CACHE 2;

CREATE TABLE IMS_CHECKLIST_CATEGORY(
    CHECKLIST_CATEGORY_ID NUMBER,
    TITLE VARCHAR2(128),
    CONSTRAINT PK_CHCKTYP_CHCKCTGRYID PRIMARY KEY(CHECKLIST_CATEGORY_ID)
);
CREATE SEQUENCE SEQ_CHECKLIST_CATEGORY_ID CACHE 2;

CREATE TABLE IMS_CHECKLIST(
   CHECKLIST_ID NUMBER NOT NULL,
   CHECKLIST_TYPE_ID NUMBER,
   CHECKLIST_CATEGORY_ID NUMBER,
   ISSUE_TYPE_ID NUMBER,
   CATEGORY_ID NUMBER,
   ACTIVE_IND VARCHAR2(1),
   CREATED_BY NUMBER,
   UPDATED_BY NUMBER,
   COMPANY_ID NUMBER,
    CONSTRAINT PK_CHKL_CHECKLISTID PRIMARY KEY(CHECKLIST_ID),
    CONSTRAINT FK_CHCKLST_ISSUETYPID FOREIGN KEY(ISSUE_TYPE_ID) REFERENCES IMS_ISSUE_TYPE(ISSUE_TYPE_ID)
    CONSTRAINT FK_CHKL_CHECKLISTTYPEID FOREIGN KEY(CHECKLIST_TYPE_ID) REFERENCES IMS_CHECKLIST_TYPE(CHECKLIST_TYPE_ID),
    CONSTRAINT FK_CHKL_CHECKLISTCATEGORYID FOREIGN KEY(CHECKLIST_CATEGORY_ID) REFERENCES IMS_CHECKLIST_CATEGORY(CHECKLIST_CATEGORY_ID),
    CONSTRAINT FK_CHKL_CATEGORYID FOREIGN KEY(CATEGORY_ID) REFERENCES IMS_CATEGORY(CATEGORY_ID),
    CONSTRAINT FK_CHKL_CREATEDBY FOREIGN KEY(CREATED_BY) REFERENCES WEB_USERS(USER_ID),
    CONSTRAINT FK_CHKL_UPDATEDBY FOREIGN KEY(UPDATED_BY) REFERENCES WEB_USERS(USER_ID),
    CONSTRAINT FK_CHKL_COMPANYID FOREIGN KEY(COMPANY_ID) REFERENCES COMPANY(COMPANY_ID)
);

CREATE SEQUENCE SEQ_CHECKLIST_ID CACHE 2;

CREATE TABLE IMS_CHECKLIST_LIST(
    CHECKLIST_LIST_ID NUMBER,
    CHECKLIST_ID NUMBER,
    TITLE VARCHAR2(500),
    ACTIVE_IND VARCHAR2(1),
    CONSTRAINT PK_CHCKLL_CHCKLLID PRIMARY KEY(CHECKLIST_LIST_ID),
    CONSTRAINT FK_CHCKLL_CHCKLID FOREIGN KEY(CHECKLIST_ID) REFERENCES IMS_CHECKLIST(CHECKLIST_ID)
);

CREATE SEQUENCE SEQ_CHECKLIST_LIST_ID CACHE 2;


CREATE TABLE IMS_HELP(
    HELP_ID NUMBER NOT NULL,
    TITLE VARCHAR2(256),
    HELP_DESC VARCHAR2(1000),
    HELP_ATTACHMENT VARCHAR2(500),
    CONSTRAINT PK_CCLST_CSTMRCHKLSTID PRIMARY KEY(CUSTOMER_CHECKLIST_ID),
    CONSTRAINT FK_CCLST_CATEGORYID FOREIGN KEY(CATEGORY_ID) REFERENCES IMS_CATEGORY(CATEGORY_ID),
    CONSTRAINT FK_CCLST_CSTMRCHKLSTCTGRYID FOREIGN KEY(CHECK_LIST_CATEGORY_ID) REFERENCES IMS_CHECK_LIST_CATEGORY(CHECK_LIST_CATEGORY_ID),
    CONSTRAINT FK_CCLST_CREATEDBY FOREIGN KEY(CREATED_BY) REFERENCES WEB_USERS(USER_ID),
    CONSTRAINT FK_CCLST_UPDATEDBY FOREIGN KEY(UPDATED_BY) REFERENCES WEB_USERS(USER_ID),
    CONSTRAINT FK_CCLST_COMPANYID FOREIGN KEY(COMPANY_ID) REFERENCES COMPANY(COMPANY_ID)
);

CREATE SEQUENCE SEQ_HELP_ID CACHE 2;

CREATE TABLE IMS_HELP(
     HELP_ID NUMBER NOT NULL,
     TITLE VARCHAR2(256),
     HELP_DESC VARCHAR2(1000),
     HELP_ATTACHMENT VARCHAR2(500),
     CONSTRAINT PK_HELP_HELPID PRIMARY KEY(HELP_ID)
);

CREATE SEQUENCE SEQ_HELP_ID CACHE 2;

CREATE TABLE IMS_SALES(
    SALES_ID NUMBER NOT NULL,
    SALE_DTE DATE,
    CUSTOMER_ID NUMBER,
    CREATED_BY NUMBER,
    UPDATED_BY NUMBER,
    COMPANY_ID NUMBER,
    CONSTRAINT PK_SALES_SALESID PRIMARY KEY(SALES_ID),
    CONSTRAINT FK_SALES_CUSTOMERID FOREIGN KEY(CUSTOMER_ID) REFERENCES IMS_CUSTOMER(CUSTOMER_ID),
    CONSTRAINT FK_SALES_CREATEDBY FOREIGN KEY(CREATED_BY) REFERENCES WEB_USERS(USER_ID),
    CONSTRAINT FK_SALES_UPDATEDBY FOREIGN KEY(UPDATED_BY) REFERENCES WEB_USERS(USER_ID),
    CONSTRAINT FK_SALES_COMPANYID FOREIGN KEY(COMPANY_ID) REFERENCES COMPANY(COMPANY_ID)
);

CREATE SEQUENCE SEQ_SALES_ID CACHE 2;

CREATE TABLE IMS_SALES_DETAIL(
    SALES_DETAIL_ID NUMBER NOT NULL,
    SALES_ID NUMBER NOT NULL,
    PRODUCT_TYP VARCHAR2(32),
    PRODUCT VARCHAR2(64),
    PROD_MODEL VARCHAR2(32),
    SERIAL_NO VARCHAR2(64),
    REFERENCE_NO NUMBER,
    CONSTRAINT PK_SALESDETAIL_SALESDETAILID PRIMARY KEY(SALES_DETAIL_ID),
    CONSTRAINT FK_SALESDETAIL_SALESID FOREIGN KEY(SALES_ID) REFERENCES IMS_SALES(SALES_ID)
);

CREATE SEQUENCE SEQ_SALES_DETAIL_ID CACHE 2;


CREATE TABLE IMS_SALESDETAIL_MODULER(
    SALESDETAIL_MODULER_ID NUMBER NOT NULL,
    SALES_DETAIL_ID NUMBER,
    MODULER_NME NUMBER,
    MODULER_SERIAL NUMBER,
    CONSTRAINT PK_SLDMDL_SALESDETAILMODULERID PRIMARY KEY(SALESDETAIL_MODULER_ID),
    CONSTRAINT FK_SLDMDL_SALESDETAILID FOREIGN KEY(SALES_DETAIL_ID) REFERENCES IMS_SALES_DETAIL(SALES_DETAIL_ID)
);

CREATE SEQUENCE SEQ_SALESDETAIL_MODULER_ID CACHE 2;


CREATE TABLE IMS_PURCHASE(
     PURCHASE_ID NUMBER NOT NULL,
     PURCHASE_DTE DATE,
     VENDOR_ID NUMBER,
     MANUFACTURER_ID NUMBER,
     CREATED_BY NUMBER,
     UPDATED_BY NUMBER,
     COMPANY_ID NUMBER,
     CONSTRAINT PK_PURCHASE_PURCHASEID PRIMARY KEY(PURCHASE_ID),
     CONSTRAINT FK_PURCHASE_VENDORID FOREIGN KEY(VENDOR_ID) REFERENCES IMS_VENDOR(VENDOR_ID),
     CONSTRAINT FK_PURCHASE_MANUFACTURERID FOREIGN KEY(MANUFACTURER_ID) REFERENCES IMS_MANUFACTURER(MANUFACTURER_ID),
     CONSTRAINT FK_PURCHASE_CREATEDBY FOREIGN KEY(CREATED_BY) REFERENCES WEB_USERS(USER_ID),
     CONSTRAINT FK_PURCHASE_UPDATEDBY FOREIGN KEY(UPDATED_BY) REFERENCES WEB_USERS(USER_ID),
     CONSTRAINT FK_PURCHASE_COMPANYID FOREIGN KEY(COMPANY_ID) REFERENCES COMPANY(COMPANY_ID)
);

CREATE SEQUENCE SEQ_PURCHASE_ID CACHE 2;

CREATE TABLE IMS_PURCHASE_DETAIL(
    PURCHASE_DETAIL_ID NUMBER NOT NULL,
    PURCHASE_ID NUMBER NOT NULL,
    PRODUCT_TYP VARCHAR2(32),
    PRODUCT VARCHAR2(64),
    PROD_MODEL VARCHAR2(32),
    SERIAL_NO VARCHAR2(64),
    QTY NUMBER,
    REFERENCE_NO NUMBER,
    CONSTRAINT PK_PD_PURCHASEDETAILID PRIMARY KEY(PURCHASE_DETAIL_ID),
    CONSTRAINT FK_PD_PURCHASEID FOREIGN KEY(PURCHASE_ID) REFERENCES IMS_PURCHASE(PURCHASE_ID)
);

CREATE SEQUENCE SEQ_PURCHASE_DETAIL_ID CACHE 2;


CREATE TABLE IMS_PURCHASEDETAIL_MODULER(
   PURCHASEDETAIL_MODULER_ID NUMBER NOT NULL,
   PURCHASE_DETAIL_ID NUMBER,
   MODULER_NME NUMBER,
   MODULER_SERIAL NUMBER,
   CONSTRAINT PK_PDM_PURCHASEDETAILMODULERID PRIMARY KEY(PURCHASEDETAIL_MODULER_ID),
   CONSTRAINT FK_PDM_PURCHASEDETAILID FOREIGN KEY(PURCHASE_DETAIL_ID) REFERENCES IMS_PURCHASE_DETAIL(PURCHASE_DETAIL_ID)
);

CREATE SEQUENCE SEQ_PURCHASEDETAIL_MODULER_ID CACHE 2;

CREATE TABLE IMS_MAINTENANCE(
    MAINTENANCE_ID NUMBER NOT NULL,
    MAINTENANCE_DTE DATE,
    COMPLAINT_NO VARCHAR2(64),
    CUSTOMER_ID NUMBER,
    EMPLOYEE_ID NUMBER,
    PRODUCT_ID NUMBER,
    COMPLAINT_SEND_TO NUMBER,
    MAINTENENCE_STATUS VARCHAR2(256),
    CHECK_IN DATE,
    CHECK_OUT DATE,
    VISIT_FROM VARCHAR2(500),
    VISIT_TO VARCHAR2(500),
    HOLD_REASON VARCHAR2(1000),
    CREATED_BY NUMBER,
    UPDATED_BY NUMBER,
    COMPANY_ID NUMBER,
    PART_REPLACED VARCHAR2(128),
    VIDEO_ATTACHED VARCHAR2(500),
    PART_TITLE VARCHAR2(128),
    PART_DTE DATE,
    PART_LIFE VARCHAR2(5),
    AMOUNT NUMBER,
    REMARK VARCHAR2(500),
    NO_OF_VISITS NUMBER,
    CONTRACT_ID NUMBER,
    MAINTENANCE_LVL VARCHAR2(8),
    MAINTENANCE_KNOWLEDGE VARCHAR2(32),
    MAINTENANCE_CONVEYED VARCHAR2(32),
    CONSTRAINT PK_MNTNNC_MAINTENANCEID PRIMARY KEY(MAINTENANCE_ID),
    CONSTRAINT FK_MNTNNC_CUSTOMERID FOREIGN KEY(CUSTOMER_ID) REFERENCES IMS_CUSTOMER(CUSTOMER_ID),
    CONSTRAINT FK_MNTNNC_COMPLNTSNDTO FOREIGN KEY(COMPLAINT_SEND_TO) REFERENCES WEB_USERS(USER_ID),
    CONSTRAINT FK_MNTNNC_CREATEDBY FOREIGN KEY(CREATED_BY) REFERENCES WEB_USERS(USER_ID),
    CONSTRAINT FK_MNTNNC_UPDATEDBY FOREIGN KEY(UPDATED_BY) REFERENCES WEB_USERS(USER_ID),
    CONSTRAINT FK_MNTNNC_COMPANYID FOREIGN KEY(COMPANY_ID) REFERENCES COMPANY(COMPANY_ID)
);

CREATE SEQUENCE SEQ_MAINTENANCE_ID CACHE 2;

CREATE TABLE IMS_MAINTENANCE_DETAIL(
   MAINTENANCE_DETAIL_ID NUMBER NOT NULL,
   MAINTENANCE_ID NUMBER NOT NULL,
   PRODUCT VARCHAR2(128),
   SEARIAL VARCHAR2(32),
   CHECKLIST_ID NUMBER,
   TITLE VARCHAR2(128),
   MAINTENANCE_DETAIL_TYP VARCHAR2(32),
   MAINTENANCE_DETAIL_VAL NUMBER,
   STD_VAL NUMBER,
   MIN_RANGE NUMBER,
   MAX_RANGE NUMBER,
   CONSTRAINT PK_MNTNNDT_DETAILID PRIMARY KEY(MAINTENANCE_DETAIL_ID),
   CONSTRAINT FK_MNTNNDT_MAINTENANCEID FOREIGN KEY(MAINTENANCE_ID) REFERENCES IMS_MAINTENANCE(MAINTENANCE_ID)
);

CREATE SEQUENCE SEQ_MAINTENANCE_DETAIL_ID CACHE 2;

CREATE TABLE IMS_MANTANENECE_DTL_CHECKLIST(
  MAINTENANCE_DETAIL_CHECKLIST NUMBER NOT NULL,
  MAINTENANCE_DETAIL_ID NUMBER NOT NULL,
  CHECK_LIST_ID NUMBER NOT NULL,
  CONSTRAINT PK_MNTNCKLS_DTLCKLS PRIMARY KEY(MAINTENANCE_DETAIL_CHECKLIST),
  CONSTRAINT FK_MNTNCKLS_CHKLS FOREIGN KEY(CHECK_LIST_ID) REFERENCES IMS_CUSTOMER_CHECKLIST(CUSTOMER_CHECKLIST_ID)
);

CREATE SEQUENCE SEQ_MANTANENECE_DTL_CKLST_ID CACHE 2;


CREATE TABLE IMS_MAINTENANCE_CLAIM(
      MAINTENANCE_CLAIM_ID NUMBER NOT NULL,
      MAINTENANCE_ID NUMBER NOT NULL,
      PRODUCT VARCHAR2(128),
      SEARIAL VARCHAR2(32),
      CHECKLIST_ID NUMBER,
      TITLE VARCHAR2(128),
      MAINTENANCE_CLAIM_TYP VARCHAR2(32),
      MAINTENANCE_CLAIM_VAL NUMBER,
      STD_VAL NUMBER,
      MIN_RANGE NUMBER,
      MAX_RANGE NUMBER,
      CONSTRAINT PK_MNTNCLID_CLAIMID PRIMARY KEY(MAINTENANCE_CLAIM_ID),
      CONSTRAINT FK_MNTNCLID_MAINTENANCEID FOREIGN KEY(MAINTENANCE_ID) REFERENCES IMS_MAINTENANCE(MAINTENANCE_ID)
);

CREATE SEQUENCE SEQ_MAINTENANCE_CLAIM_ID CACHE 2;

CREATE TABLE IMS_MANTANENECE_CLM_CHECKLIST(
      MAINTENANCE_CLAIM_CHECKLIST_ID NUMBER NOT NULL,
      MAINTENANCE_CLAIM_ID NUMBER NOT NULL,
      CHECK_LIST_ID NUMBER NOT NULL,
      CONSTRAINT PK_MNTNCLCK_CLMCKID PRIMARY KEY(MAINTENANCE_CLAIM_CHECKLIST_ID),
      CONSTRAINT FK_MNTNCLCK_CHKLS FOREIGN KEY(CHECK_LIST_ID) REFERENCES IMS_CUSTOMER_CHECKLIST(CUSTOMER_CHECKLIST_ID)
);

CREATE SEQUENCE SEQ_MANTANENECE_CLM_CKLST_ID CACHE 2;


CREATE TABLE IMS_MAINTENANCE_PARAM(
      MAINTENANCE_PARAM_ID NUMBER NOT NULL,
      MAINTENANCE_ID NUMBER NOT NULL,
      PRODUCT VARCHAR2(128),
      SEARIAL VARCHAR2(32),
      CHECKLIST_ID NUMBER,
      TITLE VARCHAR2(128),
      MAINTENANCE_PARAM_TYP VARCHAR2(32),
      MAINTENANCE_PARAM_VAL NUMBER,
      STD_VAL NUMBER,
      MIN_RANGE NUMBER,
      MAX_RANGE NUMBER,
      CONSTRAINT PK_MNTNPRM_PARAMID PRIMARY KEY(MAINTENANCE_PARAM_ID),
      CONSTRAINT FK_MNTNPRM_MAINTENANCEID FOREIGN KEY(MAINTENANCE_ID) REFERENCES IMS_MAINTENANCE(MAINTENANCE_ID)
);

CREATE SEQUENCE SEQ_MAINTENANCE_PARAM_ID CACHE 2;

CREATE TABLE IMS_MANTANENECE_PRM_CHECKLIST(
      MAINTENANCE_PARAM_CHECKLIST_ID NUMBER NOT NULL,
      MAINTENANCE_PARAM_ID NUMBER NOT NULL,
      CHECK_LIST_ID NUMBER NOT NULL,
      CONSTRAINT PK_MNTNPRMCK_CLMCKID PRIMARY KEY(MAINTENANCE_PARAM_CHECKLIST_ID),
      CONSTRAINT FK_MNTNPRMCK_CHKLS FOREIGN KEY(CHECK_LIST_ID) REFERENCES IMS_CUSTOMER_CHECKLIST(CUSTOMER_CHECKLIST_ID)
    );

CREATE SEQUENCE SEQ_MANTANENECE_PRM_CKLST_ID CACHE 2;

CREATE TABLE IMS_MAINTENANCE_PART(
     MAINTENANCE_PART_ID NUMBER NOT NULL,
     MAINTENANCE_ID NUMBER NOT NULL,
     PRODUCT VARCHAR2(128),
     SEARIAL VARCHAR2(32),
     CHECKLIST_ID NUMBER,
     TITLE VARCHAR2(128),
     MAINTENANCE_PART_TYP VARCHAR2(32),
     MAINTENANCE_PART_VAL NUMBER,
     STD_VAL NUMBER,
     MIN_RANGE NUMBER,
     MAX_RANGE NUMBER,
     CONSTRAINT PK_MNTNPRT_PARTID PRIMARY KEY(MAINTENANCE_PART_ID),
     CONSTRAINT FK_MNTNPRT_MAINTENANCEID FOREIGN KEY(MAINTENANCE_ID) REFERENCES IMS_MAINTENANCE(MAINTENANCE_ID)
);

CREATE SEQUENCE SEQ_MAINTENANCE_PART_ID CACHE 2;

CREATE TABLE IMS_MANTANENECE_PRT_CHECKLIST(
      MAINTENANCE_PART_CHECKLIST_ID NUMBER NOT NULL,
      MAINTENANCE_PART_ID NUMBER NOT NULL,
      CHECK_LIST_ID NUMBER NOT NULL,
      CONSTRAINT PK_MNTNPRTCK_CLMCKID PRIMARY KEY(MAINTENANCE_PART_CHECKLIST_ID),
      CONSTRAINT FK_MNTNPRTCK_CHKLS FOREIGN KEY(CHECK_LIST_ID) REFERENCES IMS_CUSTOMER_CHECKLIST(CUSTOMER_CHECKLIST_ID)
);

CREATE SEQUENCE SEQ_MANTANENECE_PRT_CKLST_ID CACHE 2;

CREATE TABLE IMS_MAINTENANCE_SCLST(
      MAINTENANCE_SCLST_ID NUMBER NOT NULL,
      MAINTENANCE_ID NUMBER NOT NULL,
      PRODUCT VARCHAR2(128),
      SEARIAL VARCHAR2(32),
      CHECKLIST_ID NUMBER,
      TITLE VARCHAR2(128),
      MAINTENANCE_SCLST_TYP VARCHAR2(32),
      MAINTENANCE_SCLST_VAL NUMBER,
      STD_VAL NUMBER,
      MIN_RANGE NUMBER,
      MAX_RANGE NUMBER,
      CONSTRAINT PK_MNTNSCLST_SCLSTID PRIMARY KEY(MAINTENANCE_SCLST_ID),
      CONSTRAINT FK_MNTNSCLST_MAINTENANCEID FOREIGN KEY(MAINTENANCE_ID) REFERENCES IMS_MAINTENANCE(MAINTENANCE_ID)
);

CREATE SEQUENCE SEQ_MAINTENANCE_SCLST_ID CACHE 2;

CREATE TABLE IMS_MANTANENECE_SCL_CHECKLIST(
      MAINTENANCE_SCL_CHECKLIST_ID NUMBER NOT NULL,
      MAINTENANCE_SCL_ID NUMBER NOT NULL,
      CHECK_LIST_ID NUMBER NOT NULL,
      CONSTRAINT PK_MNTNSCSTCK_CLMCKID PRIMARY KEY(MAINTENANCE_SCL_CHECKLIST_ID),
      CONSTRAINT FK_MNTNSCSTCK_CHKLS FOREIGN KEY(CHECK_LIST_ID) REFERENCES IMS_CUSTOMER_CHECKLIST(CUSTOMER_CHECKLIST_ID)
);

CREATE SEQUENCE SEQ_MANTANENECE_SCL_CKLST_ID CACHE 2;


CREATE TABLE IMS_MAINTENANCE_USER(
     MAINTENANCE_USER_ID NUMBER NOT NULL,
     MAINTENANCE_ID NUMBER NOT NULL,
     REF_USER_ID NUMBER,
     CONSTRAINT PK_MNTNSUSR_USERID PRIMARY KEY(MAINTENANCE_USER_ID),
     CONSTRAINT FK_MNTNSUSR_MAINTENANCEID FOREIGN KEY(MAINTENANCE_ID) REFERENCES IMS_MAINTENANCE(MAINTENANCE_ID),
     CONSTRAINT FK_MNTNSUSR_REFUSERID FOREIGN KEY(REF_USER_ID) REFERENCES WEB_USERS(USER_ID)
);

CREATE SEQUENCE SEQ_MAINTENANCE_USER_ID CACHE 2;


CREATE TABLE IMS_SITE_PHONE(
   SITE_PHONE_ID NUMBER,
   SITE_ID NUMBER,
   PHONE_NO VARCHAR2(20),
   CONSTRAINT PK_SITEPHONE_SITEPHONEID PRIMARY KEY (SITE_PHONE_ID),
   CONSTRAINT FK_SITEPHONE_SITEID FOREIGN KEY(SITE_ID) REFERENCES SITE(SITE_ID)
);

CREATE SEQUENCE SEQ_SITE_PHONE_ID CACHE 2;



CREATE TABLE IMS_EMPLOYEE(
     EMPLOYEE_ID NUMBER NOT NULL,
     EMPLOYEE_NME VARCHAR2(32),
     DEPARTMENT_ID NUMBER,
     DESIGNATION_ID NUMBER,
     SITE_ID NUMBER,
     EMAIL VARCHAR2(128),
     LOGIN_ID VARCHAR2(128),
     LOGIN_PSWD VARCHAR2(128),
     CREATED_BY NUMBER,
     UPDATED_BY NUMBER,
     COMPANY_ID NUMBER,
     CONSTRAINT PK_EMPLOYEE_EMPLOYEEID PRIMARY KEY(EMPLOYEE_ID),
     CONSTRAINT FK_EMPLOYEE_DEPARTMENTID FOREIGN KEY(DEPARTMENT_ID) REFERENCES IMS_DEPARTMENT(DEPARTMENT_ID),
     CONSTRAINT FK_EMPLOYEE_DESIGNATIONID FOREIGN KEY(DESIGNATION_ID) REFERENCES IMS_DESIGNATION(DESIGNATION_ID),
     CONSTRAINT FK_EMPLOYEE_SITEID FOREIGN KEY(SITE_ID) REFERENCES SITE(SITE_ID),
     CONSTRAINT FK_EMPLOYEE_CREATEDBY FOREIGN KEY(CREATED_BY) REFERENCES WEB_USERS(USER_ID),
     CONSTRAINT FK_EMPLOYEE_UPDATEDBY FOREIGN KEY(UPDATED_BY) REFERENCES WEB_USERS(USER_ID),
     CONSTRAINT FK_EMPLOYEE_COMPANYID FOREIGN KEY(COMPANY_ID) REFERENCES COMPANY(COMPANY_ID)
);


CREATE SEQUENCE SEQ_EMPLOYEE_ID CACHE 2;

CREATE TABLE IMS_EMPLOYEE_PHONE(
    EMPLOYEE_PHONE_ID NUMBER,
    EMPLOYEE_ID NUMBER,
    PHONE_NO VARCHAR2(20),
    CONSTRAINT PK_EMPPHONE_EMPLOYEEPHONRID PRIMARY KEY (EMPLOYEE_PHONE_ID),
    CONSTRAINT FK_EMPPHONE_EMPLOYEEID FOREIGN KEY(EMPLOYEE_ID) REFERENCES IMS_EMPLOYEE(EMPLOYEE_ID)
);

CREATE SEQUENCE SEQ_EMPLOYEE_PHONE_ID CACHE 2;



CREATE TABLE IMS_MNFCTURR_CATEGORY(
    MNFCTURR_CATEGORY_ID NUMBER,
    CATEGORY_ID NUMBER,
    MANUFACTURER_ID NUMBER,
    CONSTRAINT PK_MNFCRR_MNFCCTGRYID PRIMARY KEY(MNFCTURR_CATEGORY_ID),
    CONSTRAINT FK_MNFCRR_CATGRYID FOREIGN KEY(CATEGORY_ID) REFERENCES IMS_CATEGORY(CATEGORY_ID),
    CONSTRAINT FK_MNFCRR_MNFCTURR FOREIGN KEY(MANUFACTURER_ID) REFERENCES IMS_MANUFACTURER(MANUFACTURER_ID)
);

CREATE SEQUENCE SEQ_MNFCTURR_CATEGORY_ID CACHE 2;

CREATE TABLE IMS_PRODUCT(
    PRODUCT_ID NUMBER,
    PRODUCT_NAME VARCHAR2(256),
    PROD_DESC VARCHAR2(256),
    FUNCTIONAL_DETAIL VARCHAR2(256),
    MODEL_TITLE VARCHAR2(256),
    BARCODE VARCHAR2(128),
    WARRENTY VARCHAR2(12),
    CREATED_BY NUMBER,
    UPDATED_BY NUMBER,
    COMPANY_ID NUMBER,
    CATEGORY_ID NUMBER,
    SUB_CATEGORY_ID NUMBER,
    MANUFACTURER_ID NUMBER,
    PRODUCT_TYPE_ID NUMBER,
    PROD_FEATURE_ID NUMBER,
    CONSTRAINT PK_PROD_PRODUCTID PRIMARY KEY(PRODUCT_ID),
    CONSTRAINT FK_PROD_CATEGORYID FOREIGN KEY(CATEGORY_ID) REFERENCES IMS_CATEGORY(CATEGORY_ID),    
    CONSTRAINT FK_PROD_SUBCATEGORYID FOREIGN KEY(SUB_CATEGORY_ID) REFERENCES IMS_CATEGORY(CATEGORY_ID),     
    CONSTRAINT FK_PROD_MANUFACTURERID FOREIGN KEY(MANUFACTURER_ID) REFERENCES IMS_MANUFACTURER(MANUFACTURER_ID),
    CONSTRAINT FK_PROD_PRODUCTTYPEID FOREIGN KEY(PRODUCT_TYPE_ID) REFERENCES IMS_PRODUCT_TYPE(PRODUCT_TYPE_ID),
    CONSTRAINT FK_PROD_PRODFEATUREID FOREIGN KEY(PROD_FEATURE_ID) REFERENCES IMS_FEATURE(IMS_FEATURE_ID),
    CONSTRAINT FK_PROD_CREATEDBY FOREIGN KEY(CREATED_BY) REFERENCES WEB_USERS(USER_ID),
    CONSTRAINT FK_PROD_UPDATEDBY FOREIGN KEY(UPDATED_BY) REFERENCES WEB_USERS(USER_ID),
    CONSTRAINT FK_PROD_COMPANYID FOREIGN KEY(COMPANY_ID) REFERENCES COMPANY(COMPANY_ID)    
);

CREATE SEQUENCE SEQ_PRODUCT_ID CACHE 2;

CREATE TABLE IMS_PROD_SERIAL(
    SERIAL_ID NUMBER,
    PRODUCT_ID NUMBER,
    TITLE VARCHAR2(32),
    CONSTRAINT PK_SERIAL_SERIALID PRIMARY KEY(SERIAL_ID),
    CONSTRAINT FK_SERIAL_PRODUCTID FOREIGN KEY(PRODUCT_ID) REFERENCES IMS_PRODUCT(PRODUCT_ID)
);

CREATE SEQUENCE SEQ_SERIAL_ID CACHE 2;

CREATE TABLE IMS_MODULAR(
    MODULAR_ID NUMBER,
    PRODUCT_ID NUMBER,
    TITLE VARCHAR2(128),
    SERIAL VARCHAR2(32),
    BARCODE VARCHAR2(64),
    WARRENTY VARCHAR2(5),
    CONSTRAINT PK_MODULAR_MODUALRID PRIMARY KEY(MODULAR_ID),
    CONSTRAINT FK_MODULAR_PRODUCTID FOREIGN KEY(PRODUCT_ID) REFERENCES IMS_PRODUCT(PRODUCT_ID)
);

CREATE SEQUENCE SEQ_MODUALR_ID CACHE 2;

CREATE TABLE IMS_PART(
    PART_ID NUMBER,
    PRODUCT_ID NUMBER,
    TITLE VARCHAR2(64),
    PRICE NUMBER,
    PART_LEFT VARCHAR2(128),
    UNIT VARCHAR2(32),
    PART_PERIOD VARCHAR2(32),
    PART_DATE DATE,
    PART_VAL NUMBER,
    PART_READING VARCHAR2(128),
    CONSTRAINT PK_PART_PARTID PRIMARY KEY(PART_ID),
    CONSTRAINT FK_PART_PRODUCTID FOREIGN KEY(PRODUCT_ID) REFERENCES IMS_PRODUCT(PRODUCT_ID)
);

CREATE SEQUENCE SEQ_PART_ID CACHE 2;


CREATE TABLE IMS_PARAM(
    PARAM_ID NUMBER,
    PRODUCT_ID NUMBER,
    TITLE VARCHAR2(64),
    PARAM_DESC VARCHAR2(128),
    PARAM_VAL NUMBER,
    MIN_RANG NUMBER,
    MAX_RANG NUMBER,
    CONSTRAINT PK_PARAM_PARAMID PRIMARY KEY(PARAM_ID),
    CONSTRAINT FK_PARAM_PRODUCTID FOREIGN KEY(PRODUCT_ID) REFERENCES IMS_PRODUCT(PRODUCT_ID)
);

CREATE SEQUENCE SEQ_PARAM_ID CACHE 2;

CREATE TABLE IMS_PROD_ATTACMENT(
    PROD_ATTACHMENT_ID NUMBER,
    PRODUCT_ID NUMBER,
    FILE_DATA BLOB,
    FILE_NAME VARCHAR2(128),
    FILE_TYPE VARCHAR2(128),
    FILE_SIZE NUMBER,
    FILE_DESC VARCHAR2(500),
    CONSTRAINT PK_PRODATTCH_ATTACHMENTID PRIMARY KEY(PROD_ATTACHMENT_ID),
    CONSTRAINT FK_PRODATTCH_PRODUCTID FOREIGN KEY(PRODUCT_ID) REFERENCES IMS_PRODUCT(PRODUCT_ID)
);

CREATE SEQUENCE SEQ_PROD_ATTACHMENT_ID CACHE 2;

CREATE TABLE IMS_PROD_VIDEO(
    PROD_VIDEO_ID NUMBER,
    PRODUCT_ID NUMBER,
    VIDEO_LINK VARCHAR2(256),
    CONSTRAINT PK_PRDVD_VIDEOID PRIMARY KEY(PROD_VIDEO_ID),
    CONSTRAINT FK_PRDVD_PRODUCTID FOREIGN KEY(PRODUCT_ID) REFERENCES IMS_PRODUCT(PRODUCT_ID)
);

CREATE SEQUENCE SEQ_PROD_VIDEO_ID CACHE 2;


ALTER TABLE IMS_SALES_DETAIL ADD (CONSTRAINT FK_SD_PRODUCTTYP FOREIGN KEY(PRODUCT_TYP) REFERENCES IMS_PRODUCT_TYPE(PRODUCT_TYPE_ID),CONSTRAINT FK_SD_PRODUCT FOREIGN KEY(PRODUCT) REFERENCES IMS_PRODUCT(PRODUCT_ID));


CREATE TABLE IMS_NOTIFICATION(
    NOTIFICATION_ID NUMBER, 
    NOTIFICATION_MSG VARCHAR2(500),
    NOTIFICATION_DTE DATE,
    NOTIFICATION_STATUS VARCHAR2(32),
    CREATED_FOR NUMBER,
    CREATED_BY NUMBER,
    CREATED_AT DATE,
    UPDATED_BY NUMBER,
    COMPANY_ID NUMBER,
    CONSTRAINT PK_IMSN_NOTIFICATIONID PRIMARY KEY(NOTIFICATION_ID),
    CONSTRAINT FK_IMSN_CREATEDFOR FOREIGN KEY(NOTIFICATION_ID) REFERENCES WEB_USERS(USER_ID),
    CONSTRAINT FK_IMSN_CREATEDBY FOREIGN KEY(CREATED_BY) REFERENCES WEB_USERS(USER_ID),
    CONSTRAINT FK_IMSN_UPDATEDBY FOREIGN KEY(UPDATED_BY) REFERENCES WEB_USERS(USER_ID),
    CONSTRAINT FK_IMSN_COMPANYID FOREIGN KEY(COMPANY_ID) REFERENCES COMPANY(COMPANY_ID)
);


CREATE SEQUENCE SEQ_NOTIFICATION_ID CACHE 2;


ALTER TABLE IMS_MAINTENANCE_DETAIL RENAME COLUMN PRODUCT TO PRODUCT_ID;
ALTER TABLE IMS_MAINTENANCE_DETAIL MODIFY MAINTENANCE_DETAIL_VAL VARCHAR2(5);
ALTER TABLE IMS_MAINTENANCE_DETAIL ADD (
    CONSTRAINT FK_MNTNNDT_PRODUCTID FOREIGN KEY(PRODUCT_ID) REFERENCES IMS_PRODUCT(PRODUCT_ID),
    CONSTRAINT FK_MNTNNDT_CHKLSTID FOREIGN KEY(CHECKLIST_ID) REFERENCES IMS_CHECKLIST(CHECKLIST_ID)
);


ALTER TABLE IMS_MAINTENANCE_DETAIL RENAME COLUMN CHECKLIST_ID TO CHECKLSTLST_ID;
ALTER TABLE IMS_MAINTENANCE_DETAIL ADD CONSTRAINT FK_MNTNNDT_CHKLSTID FOREIGN KEY(CHECKLSTLST_ID) REFERENCES IMS_CHECKLIST_LIST(CHECKLIST_LIST_ID);
ALTER TABLE IMS_NOTIFICATION RENAME COLUMN NOTIFICATION_DTE TO NOTIFICATION_DATE;


-------------------------  NEW UPDATED -- 24-Mar-2025

ALTER TABLE IMS_PRODUCT_TYPE ADD (TYPE_NATURE VARCHAR2(8));

ALTER TABLE IMS_CATEGORY DROP COLUMN COMPANY_ID ;