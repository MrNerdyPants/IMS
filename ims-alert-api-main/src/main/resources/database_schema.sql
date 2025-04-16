--
-- PostgreSQL database dump
--

-- Dumped from database version 17.2
-- Dumped by pg_dump version 17.0

-- Started on 2025-02-27 14:53:53

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 217 (class 1259 OID 101887)
-- Name: city; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.city (
    city_id integer NOT NULL,
    city_nme character varying(255),
    country_id integer,
    province_id integer
);


ALTER TABLE public.city OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 101892)
-- Name: company; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.company (
    company_id integer NOT NULL,
    address character varying(255),
    auth_person character varying(255),
    email character varying(255),
    file_nme character varying(255),
    file_size character varying(255),
    file_typ character varying(255),
    gst_nbr character varying(255),
    land_line character varying(255),
    login_id character varying(255),
    login_password character varying(255),
    logo oid,
    company_nme character varying(255),
    ntn_nbr character varying(255),
    office_address character varying(255),
    secp_nbr character varying(255),
    city_id integer,
    country_id integer,
    province_id integer
);


ALTER TABLE public.company OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 101899)
-- Name: company_phone; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.company_phone (
    phone_id integer NOT NULL,
    phone_nbr character varying(255),
    company_id integer
);


ALTER TABLE public.company_phone OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 101904)
-- Name: country; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.country (
    country_id integer NOT NULL,
    coounty_nme character varying(255)
);


ALTER TABLE public.country OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 101909)
-- Name: icon_library; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.icon_library (
    id character varying(255) NOT NULL,
    name character varying(255)
);


ALTER TABLE public.icon_library OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 101916)
-- Name: ims_activity; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_activity (
    activity_id integer NOT NULL,
    action_by character varying(255),
    prepared_dte timestamp without time zone,
    activity_desc character varying(255),
    activity_profile character varying(255),
    company_id integer,
    prepared_by integer
);


ALTER TABLE public.ims_activity OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 101923)
-- Name: ims_category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_category (
    category_id integer NOT NULL,
    category_active_ind character varying(255),
    category_code character varying(255),
    category_name character varying(255),
    company_id integer,
    ref_category_id integer
);


ALTER TABLE public.ims_category OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 101930)
-- Name: ims_checklist; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_checklist (
    checklist_id integer NOT NULL,
    active_ind character varying(255),
    category_id integer,
    checklist_category_id integer,
    company_id integer,
    created_by integer,
    issue_type_id integer,
    checklist_type_id integer,
    updated_by integer
);


ALTER TABLE public.ims_checklist OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 101935)
-- Name: ims_checklist_category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_checklist_category (
    checklist_category_id integer NOT NULL,
    title character varying(255)
);


ALTER TABLE public.ims_checklist_category OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 101940)
-- Name: ims_checklist_list; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_checklist_list (
    checklist_list_id integer NOT NULL,
    title character varying(255),
    checklist_id integer
);


ALTER TABLE public.ims_checklist_list OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 101945)
-- Name: ims_checklist_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_checklist_type (
    checklist_type_id integer NOT NULL,
    title character varying(255)
);


ALTER TABLE public.ims_checklist_type OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 101950)
-- Name: ims_company_category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_company_category (
    company_category_id integer NOT NULL,
    category_id integer,
    company_id integer
);


ALTER TABLE public.ims_company_category OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 101955)
-- Name: ims_contract; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_contract (
    contract_id integer NOT NULL,
    contract_dte date,
    contract_expiry date,
    contract_period character varying(255),
    company_id integer,
    created_by integer,
    customer_id integer,
    updated_by integer
);


ALTER TABLE public.ims_contract OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 101960)
-- Name: ims_contract_detail; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_contract_detail (
    contract_detail_id integer NOT NULL,
    amount character varying(255),
    no_of_visits character varying(255),
    payment_term character varying(255),
    contract_detail_type character varying(255),
    contract_id integer NOT NULL
);


ALTER TABLE public.ims_contract_detail OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 101967)
-- Name: ims_customer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_customer (
    customer_id integer NOT NULL,
    address character varying(255),
    auth_person character varying(255),
    email character varying(255),
    file_nme character varying(255),
    file_size character varying(255),
    file_type character varying(255),
    gst_nbr character varying(255),
    land_line character varying(255),
    login_id character varying(255),
    login_password character varying(255),
    logo oid,
    mobile_no character varying(255),
    customer_nme character varying(255),
    ntn_nbr character varying(255),
    office_address character varying(255),
    scep_nbr character varying(255),
    customer_short_nme character varying(255),
    city_id integer,
    company_id integer,
    country_id integer,
    prepared_by integer,
    site_id integer,
    province_id integer,
    updated_by integer
);


ALTER TABLE public.ims_customer OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 101974)
-- Name: ims_customer_category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_customer_category (
    customer_category_id integer NOT NULL,
    category_id integer,
    customer_id integer
);


ALTER TABLE public.ims_customer_category OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 101979)
-- Name: ims_customer_phone; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_customer_phone (
    customer_phone_id integer NOT NULL,
    phone_no character varying(255),
    customer_id integer
);


ALTER TABLE public.ims_customer_phone OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 101984)
-- Name: ims_department; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_department (
    department_id integer NOT NULL,
    department_nme character varying(255)
);


ALTER TABLE public.ims_department OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 101989)
-- Name: ims_designation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_designation (
    designation_id integer NOT NULL,
    designation_nme character varying(255)
);


ALTER TABLE public.ims_designation OWNER TO postgres;

--
-- TOC entry 236 (class 1259 OID 101994)
-- Name: ims_employee; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_employee (
    employee_id integer NOT NULL,
    email character varying(255),
    login_id character varying(255),
    login_pswd character varying(255),
    employee_nme character varying(255),
    company_id integer,
    created_by integer,
    department_id integer,
    designation_id integer,
    site_id integer,
    updated_by integer
);


ALTER TABLE public.ims_employee OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 102001)
-- Name: ims_employee_phone; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_employee_phone (
    employee_phone_id integer NOT NULL,
    phone_no character varying(255),
    employee_id integer
);


ALTER TABLE public.ims_employee_phone OWNER TO postgres;

--
-- TOC entry 238 (class 1259 OID 102006)
-- Name: ims_feature; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_feature (
    ims_feature_id integer NOT NULL,
    feature_icon character varying(255),
    feature_model character varying(255),
    feature_nme character varying(255),
    ref_category_id character varying(255),
    category_id integer,
    product_id integer
);


ALTER TABLE public.ims_feature OWNER TO postgres;

--
-- TOC entry 239 (class 1259 OID 102013)
-- Name: ims_hazard; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_hazard (
    hazard_id integer NOT NULL,
    hazard_cde character varying(255),
    hazard_desc character varying(255),
    category_id integer,
    hazard_type_id integer
);


ALTER TABLE public.ims_hazard OWNER TO postgres;

--
-- TOC entry 240 (class 1259 OID 102020)
-- Name: ims_hazard_corrective; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_hazard_corrective (
    corrective_id integer NOT NULL,
    corrective character varying(255),
    hazard_id integer
);


ALTER TABLE public.ims_hazard_corrective OWNER TO postgres;

--
-- TOC entry 241 (class 1259 OID 102025)
-- Name: ims_hazard_symtom; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_hazard_symtom (
    symtom_id integer NOT NULL,
    symtom character varying(255),
    hazard_id integer
);


ALTER TABLE public.ims_hazard_symtom OWNER TO postgres;

--
-- TOC entry 242 (class 1259 OID 102030)
-- Name: ims_hazard_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_hazard_type (
    hazard_type_id integer NOT NULL,
    title character varying(255)
);


ALTER TABLE public.ims_hazard_type OWNER TO postgres;

--
-- TOC entry 243 (class 1259 OID 102035)
-- Name: ims_help; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_help (
    help_id integer NOT NULL,
    help_attachment character varying(255),
    help_desc character varying(255),
    title character varying(255)
);


ALTER TABLE public.ims_help OWNER TO postgres;

--
-- TOC entry 244 (class 1259 OID 102042)
-- Name: ims_issue_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_issue_type (
    issue_type_id integer NOT NULL,
    created_by character varying(255),
    issue_nme character varying(255),
    updated_by character varying(255)
);


ALTER TABLE public.ims_issue_type OWNER TO postgres;

--
-- TOC entry 245 (class 1259 OID 102049)
-- Name: ims_maintenance; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_maintenance (
    maintenance_id integer NOT NULL,
    amount character varying(255),
    check_in character varying(255),
    check_out character varying(255),
    complaint_no character varying(255),
    maintenance_conveyed character varying(255),
    maintenance_dte character varying(255),
    hold_reason character varying(255),
    maintenance_knowledge character varying(255),
    maintenance_lvl character varying(255),
    no_of_visits character varying(255),
    part_life character varying(255),
    part_replaced character varying(255),
    part_dte character varying(255),
    part_title character varying(255),
    remark character varying(255),
    maintenence_status character varying(255),
    video_attached character varying(255),
    visit_from character varying(255),
    visit_to character varying(255),
    company_id integer,
    complaint_send_to integer,
    contract_id integer,
    created_by integer,
    customer_id integer,
    employee_id integer,
    product_id integer,
    updated_by integer
);


ALTER TABLE public.ims_maintenance OWNER TO postgres;

--
-- TOC entry 246 (class 1259 OID 102056)
-- Name: ims_maintenance_claim; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_maintenance_claim (
    maintenance_claim_id character varying(255) NOT NULL,
    checklist_id character varying(255) NOT NULL,
    maintenance_id character varying(255) NOT NULL,
    max_range character varying(255) NOT NULL,
    min_range character varying(255) NOT NULL,
    product character varying(255) NOT NULL,
    searial character varying(255) NOT NULL,
    std_val character varying(255) NOT NULL,
    title character varying(255) NOT NULL,
    maintenance_claim_typ character varying(255) NOT NULL,
    maintenance_claim_val character varying(255) NOT NULL
);


ALTER TABLE public.ims_maintenance_claim OWNER TO postgres;

--
-- TOC entry 247 (class 1259 OID 102063)
-- Name: ims_maintenance_detail; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_maintenance_detail (
    maintenance_detail_id integer NOT NULL,
    max_range character varying(255),
    min_range character varying(255),
    searial character varying(255),
    std_val character varying(255),
    title character varying(255),
    maintenance_detail_typ character varying(255),
    maintenance_detail_val character varying(255),
    checklstlst_id integer,
    maintenance_id integer,
    product_id integer
);


ALTER TABLE public.ims_maintenance_detail OWNER TO postgres;

--
-- TOC entry 248 (class 1259 OID 102070)
-- Name: ims_maintenance_param; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_maintenance_param (
    maintenance_param_id character varying(255) NOT NULL,
    checklist_id character varying(255) NOT NULL,
    maintenance_id character varying(255) NOT NULL,
    max_range character varying(255) NOT NULL,
    min_range character varying(255) NOT NULL,
    product character varying(255) NOT NULL,
    searial character varying(255) NOT NULL,
    std_val character varying(255) NOT NULL,
    title character varying(255) NOT NULL,
    maintenance_param_typ character varying(255) NOT NULL,
    maintenance_param_val character varying(255) NOT NULL
);


ALTER TABLE public.ims_maintenance_param OWNER TO postgres;

--
-- TOC entry 249 (class 1259 OID 102077)
-- Name: ims_maintenance_part; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_maintenance_part (
    maintenance_part_id character varying(255) NOT NULL,
    checklist_id character varying(255) NOT NULL,
    maintenance_id character varying(255) NOT NULL,
    max_range character varying(255) NOT NULL,
    min_range character varying(255) NOT NULL,
    product character varying(255) NOT NULL,
    searial character varying(255) NOT NULL,
    std_val character varying(255) NOT NULL,
    title character varying(255) NOT NULL,
    maintenance_part_typ character varying(255) NOT NULL,
    maintenance_part_val character varying(255) NOT NULL
);


ALTER TABLE public.ims_maintenance_part OWNER TO postgres;

--
-- TOC entry 250 (class 1259 OID 102084)
-- Name: ims_maintenance_sclst; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_maintenance_sclst (
    maintenance_sclst_id character varying(255) NOT NULL,
    checklist_id character varying(255) NOT NULL,
    maintenance_id character varying(255) NOT NULL,
    max_range character varying(255) NOT NULL,
    min_range character varying(255) NOT NULL,
    product character varying(255) NOT NULL,
    searial character varying(255) NOT NULL,
    std_val character varying(255) NOT NULL,
    title character varying(255) NOT NULL,
    maintenance_sclst_typ character varying(255) NOT NULL,
    maintenance_sclst_val character varying(255) NOT NULL
);


ALTER TABLE public.ims_maintenance_sclst OWNER TO postgres;

--
-- TOC entry 251 (class 1259 OID 102091)
-- Name: ims_maintenance_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_maintenance_user (
    maintenance_user_id character varying(255) NOT NULL,
    maintenance_id character varying(255) NOT NULL,
    ref_user_id character varying(255)
);


ALTER TABLE public.ims_maintenance_user OWNER TO postgres;

--
-- TOC entry 252 (class 1259 OID 102098)
-- Name: ims_manufacturer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_manufacturer (
    manufacturer_id integer NOT NULL,
    address character varying(255),
    file_nme character varying(255),
    file_size character varying(255),
    file_type character varying(255),
    introduce character varying(255),
    land_line character varying(255),
    logo oid,
    mobile_no character varying(255),
    manufacturer_nme character varying(255),
    manufacturer_short_nme character varying(255),
    web_link character varying(255),
    city_id integer,
    company_id integer,
    country_id integer,
    created_by integer,
    updated_by integer
);


ALTER TABLE public.ims_manufacturer OWNER TO postgres;

--
-- TOC entry 253 (class 1259 OID 102105)
-- Name: ims_mnfcturr_category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_mnfcturr_category (
    mnfcturr_category_id integer NOT NULL,
    category_id integer NOT NULL,
    manufacturer_id integer NOT NULL
);


ALTER TABLE public.ims_mnfcturr_category OWNER TO postgres;

--
-- TOC entry 254 (class 1259 OID 102110)
-- Name: ims_modular; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_modular (
    modular_id integer NOT NULL,
    barcode character varying(255),
    serial character varying(255),
    title character varying(255),
    warrenty character varying(255),
    product_id integer
);


ALTER TABLE public.ims_modular OWNER TO postgres;

--
-- TOC entry 255 (class 1259 OID 102117)
-- Name: ims_notification; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_notification (
    notification_id integer NOT NULL,
    created_at date,
    notification_date date,
    notification_msg character varying(255),
    notification_status character varying(255),
    company_id integer,
    created_by integer,
    created_for integer,
    updated_by integer
);


ALTER TABLE public.ims_notification OWNER TO postgres;

--
-- TOC entry 256 (class 1259 OID 102124)
-- Name: ims_param; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_param (
    param_id integer NOT NULL,
    param_desc character varying(255),
    max_rang character varying(255),
    min_rang character varying(255),
    param_val character varying(255),
    title character varying(255),
    product_id integer
);


ALTER TABLE public.ims_param OWNER TO postgres;

--
-- TOC entry 257 (class 1259 OID 102131)
-- Name: ims_part; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_part (
    part_id integer NOT NULL,
    part_date date,
    part_left character varying(255),
    part_period character varying(255),
    price character varying(255),
    part_reading character varying(255),
    title character varying(255),
    unit character varying(255),
    part_val character varying(255),
    product_id integer
);


ALTER TABLE public.ims_part OWNER TO postgres;

--
-- TOC entry 258 (class 1259 OID 102138)
-- Name: ims_prod_attacment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_prod_attacment (
    prod_attachment_id integer NOT NULL,
    file_type character varying(255),
    file_desc character varying(255),
    file_data oid,
    file_name character varying(255),
    file_size character varying(255),
    product_id integer
);


ALTER TABLE public.ims_prod_attacment OWNER TO postgres;

--
-- TOC entry 259 (class 1259 OID 102145)
-- Name: ims_prod_serial; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_prod_serial (
    ims_serial_id integer NOT NULL,
    title character varying(255),
    product_id integer
);


ALTER TABLE public.ims_prod_serial OWNER TO postgres;

--
-- TOC entry 260 (class 1259 OID 102150)
-- Name: ims_prod_video; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_prod_video (
    prod_video_id integer NOT NULL,
    video_link character varying(255),
    product_id integer
);


ALTER TABLE public.ims_prod_video OWNER TO postgres;

--
-- TOC entry 261 (class 1259 OID 102155)
-- Name: ims_product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_product (
    product_id integer NOT NULL,
    barcode character varying(255),
    prod_desc character varying(255),
    functional_detail character varying(255),
    model_title character varying(255),
    product_name character varying(255),
    warrenty character varying(255),
    category_id integer,
    company_id integer,
    created_by integer,
    manufacturer_id integer,
    product_type_id integer,
    sub_category_id integer,
    updated_by integer
);


ALTER TABLE public.ims_product OWNER TO postgres;

--
-- TOC entry 262 (class 1259 OID 102162)
-- Name: ims_product_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_product_type (
    product_type_id integer NOT NULL,
    code character varying(255),
    title character varying(255),
    category_id integer
);


ALTER TABLE public.ims_product_type OWNER TO postgres;

--
-- TOC entry 263 (class 1259 OID 102169)
-- Name: ims_purchase; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_purchase (
    purchase_id integer NOT NULL,
    purchase_dte date,
    company_id integer,
    created_by integer,
    manufacturer_id integer,
    updated_by integer,
    vendor_id integer
);


ALTER TABLE public.ims_purchase OWNER TO postgres;

--
-- TOC entry 264 (class 1259 OID 102174)
-- Name: ims_purchase_detail; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_purchase_detail (
    purchase_detail_id integer NOT NULL,
    prod_model character varying(255),
    qty character varying(255),
    serial_no character varying(255),
    product integer,
    product_typ integer,
    purchase_id integer
);


ALTER TABLE public.ims_purchase_detail OWNER TO postgres;

--
-- TOC entry 265 (class 1259 OID 102181)
-- Name: ims_purchasedetail_moduler; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_purchasedetail_moduler (
    purchasedetail_moduler_id integer NOT NULL,
    moduler_nme character varying(255),
    moduler_serial character varying(255),
    purchase_detail_id integer
);


ALTER TABLE public.ims_purchasedetail_moduler OWNER TO postgres;

--
-- TOC entry 266 (class 1259 OID 102188)
-- Name: ims_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_role (
    role_id integer NOT NULL,
    role_active_ind character varying(255),
    role_nme character varying(255)
);


ALTER TABLE public.ims_role OWNER TO postgres;

--
-- TOC entry 267 (class 1259 OID 102195)
-- Name: ims_sales; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_sales (
    sales_id integer NOT NULL,
    sale_dte date,
    company_id integer,
    created_by integer,
    customer_id integer,
    updated_by integer
);


ALTER TABLE public.ims_sales OWNER TO postgres;

--
-- TOC entry 268 (class 1259 OID 102200)
-- Name: ims_sales_detail; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_sales_detail (
    sales_detail_id integer NOT NULL,
    prod_model character varying(255),
    reference_no character varying(255),
    serial_no character varying(255),
    product integer,
    product_typ integer,
    sales_id integer
);


ALTER TABLE public.ims_sales_detail OWNER TO postgres;

--
-- TOC entry 269 (class 1259 OID 102207)
-- Name: ims_salesdetail_moduler; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_salesdetail_moduler (
    salesdetail_moduler_id integer NOT NULL,
    moduler_nme character varying(255),
    moduler_serial character varying(255),
    sales_detail_id integer
);


ALTER TABLE public.ims_salesdetail_moduler OWNER TO postgres;

--
-- TOC entry 270 (class 1259 OID 102214)
-- Name: ims_site_phone; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_site_phone (
    site_phone_id integer NOT NULL,
    phone_no character varying(255),
    site_id integer
);


ALTER TABLE public.ims_site_phone OWNER TO postgres;

--
-- TOC entry 271 (class 1259 OID 102219)
-- Name: ims_user_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_user_role (
    user_role_id integer NOT NULL,
    role_id integer,
    user_id integer
);


ALTER TABLE public.ims_user_role OWNER TO postgres;

--
-- TOC entry 272 (class 1259 OID 102224)
-- Name: ims_user_site; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_user_site (
    user_site_id integer NOT NULL,
    site_id integer,
    user_id integer
);


ALTER TABLE public.ims_user_site OWNER TO postgres;

--
-- TOC entry 273 (class 1259 OID 102229)
-- Name: ims_vendor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_vendor (
    vendor_id integer NOT NULL,
    address character varying(255),
    auth_person character varying(255),
    email character varying(255),
    file_nme character varying(255),
    file_size character varying(255),
    file_type character varying(255),
    gst_nbr character varying(255),
    land_line character varying(255),
    login_id character varying(255),
    login_password character varying(255),
    logo oid,
    mobile_no character varying(255),
    vendor_nme character varying(255),
    ntn_nbr character varying(255),
    office_address character varying(255),
    scep_nbr character varying(255),
    vendor_short_nme character varying(255),
    city_id integer,
    company_id integer,
    country_id integer,
    prepared_by integer,
    site_id integer,
    province_id integer,
    updated_by integer
);


ALTER TABLE public.ims_vendor OWNER TO postgres;

--
-- TOC entry 274 (class 1259 OID 102236)
-- Name: ims_vendor_category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_vendor_category (
    vendor_category_id integer NOT NULL,
    category_id integer,
    vendor_id integer
);


ALTER TABLE public.ims_vendor_category OWNER TO postgres;

--
-- TOC entry 275 (class 1259 OID 102241)
-- Name: ims_vendor_phone; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ims_vendor_phone (
    vendor_phone_id integer NOT NULL,
    phone_no character varying(255),
    vendor_id integer
);


ALTER TABLE public.ims_vendor_phone OWNER TO postgres;

--
-- TOC entry 276 (class 1259 OID 102246)
-- Name: maintenance_claim_check_list; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.maintenance_claim_check_list (
    maintenance_claim_maintenance_claim_id character varying(255) NOT NULL,
    check_list character varying(255)
);


ALTER TABLE public.maintenance_claim_check_list OWNER TO postgres;

--
-- TOC entry 277 (class 1259 OID 102251)
-- Name: maintenance_parameter_check_list; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.maintenance_parameter_check_list (
    maintenance_parameter_maintenance_param_id character varying(255) NOT NULL,
    check_list character varying(255)
);


ALTER TABLE public.maintenance_parameter_check_list OWNER TO postgres;

--
-- TOC entry 278 (class 1259 OID 102256)
-- Name: maintenance_part_check_list; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.maintenance_part_check_list (
    maintenance_part_maintenance_part_id character varying(255) NOT NULL,
    check_list character varying(255)
);


ALTER TABLE public.maintenance_part_check_list OWNER TO postgres;

--
-- TOC entry 279 (class 1259 OID 102261)
-- Name: maintenance_staff_check_list_check_list; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.maintenance_staff_check_list_check_list (
    maintenance_staff_check_list_maintenance_sclst_id character varying(255) NOT NULL,
    check_list character varying(255)
);


ALTER TABLE public.maintenance_staff_check_list_check_list OWNER TO postgres;

--
-- TOC entry 280 (class 1259 OID 102266)
-- Name: province; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.province (
    province_id integer NOT NULL,
    province_nme character varying(255),
    country_id integer
);


ALTER TABLE public.province OWNER TO postgres;

--
-- TOC entry 281 (class 1259 OID 102271)
-- Name: rights; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rights (
    right_id integer NOT NULL,
    active_ind character varying(255),
    display_ind character varying(255),
    icon character varying(255),
    right_txt character varying(255),
    sort integer,
    url character varying(255),
    parent_id integer
);


ALTER TABLE public.rights OWNER TO postgres;

--
-- TOC entry 282 (class 1259 OID 102278)
-- Name: role_right; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.role_right (
    role_right_id integer NOT NULL,
    can_delete character varying(255),
    can_edit character varying(255),
    can_export character varying(255),
    role_id integer,
    right_id integer
);


ALTER TABLE public.role_right OWNER TO postgres;

--
-- TOC entry 287 (class 1259 OID 102309)
-- Name: seq_activity_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_activity_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_activity_id OWNER TO postgres;

--
-- TOC entry 288 (class 1259 OID 102310)
-- Name: seq_category_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_category_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_category_id OWNER TO postgres;

--
-- TOC entry 289 (class 1259 OID 102311)
-- Name: seq_checklist_category_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_checklist_category_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_checklist_category_id OWNER TO postgres;

--
-- TOC entry 290 (class 1259 OID 102312)
-- Name: seq_checklist_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_checklist_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_checklist_id OWNER TO postgres;

--
-- TOC entry 291 (class 1259 OID 102313)
-- Name: seq_checklist_list_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_checklist_list_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_checklist_list_id OWNER TO postgres;

--
-- TOC entry 292 (class 1259 OID 102314)
-- Name: seq_checklist_type_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_checklist_type_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_checklist_type_id OWNER TO postgres;

--
-- TOC entry 293 (class 1259 OID 102315)
-- Name: seq_city_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_city_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_city_id OWNER TO postgres;

--
-- TOC entry 294 (class 1259 OID 102316)
-- Name: seq_company_category_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_company_category_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_company_category_id OWNER TO postgres;

--
-- TOC entry 295 (class 1259 OID 102317)
-- Name: seq_company_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_company_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_company_id OWNER TO postgres;

--
-- TOC entry 296 (class 1259 OID 102318)
-- Name: seq_contract_detail_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_contract_detail_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_contract_detail_id OWNER TO postgres;

--
-- TOC entry 297 (class 1259 OID 102319)
-- Name: seq_contract_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_contract_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_contract_id OWNER TO postgres;

--
-- TOC entry 298 (class 1259 OID 102320)
-- Name: seq_corrective_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_corrective_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_corrective_id OWNER TO postgres;

--
-- TOC entry 299 (class 1259 OID 102321)
-- Name: seq_country_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_country_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_country_id OWNER TO postgres;

--
-- TOC entry 300 (class 1259 OID 102322)
-- Name: seq_customer_category_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_customer_category_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_customer_category_id OWNER TO postgres;

--
-- TOC entry 301 (class 1259 OID 102323)
-- Name: seq_customer_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_customer_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_customer_id OWNER TO postgres;

--
-- TOC entry 302 (class 1259 OID 102324)
-- Name: seq_customer_phone_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_customer_phone_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_customer_phone_id OWNER TO postgres;

--
-- TOC entry 303 (class 1259 OID 102325)
-- Name: seq_department_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_department_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_department_id OWNER TO postgres;

--
-- TOC entry 304 (class 1259 OID 102326)
-- Name: seq_designation_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_designation_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_designation_id OWNER TO postgres;

--
-- TOC entry 305 (class 1259 OID 102327)
-- Name: seq_employee_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_employee_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_employee_id OWNER TO postgres;

--
-- TOC entry 306 (class 1259 OID 102328)
-- Name: seq_employee_phone_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_employee_phone_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_employee_phone_id OWNER TO postgres;

--
-- TOC entry 307 (class 1259 OID 102329)
-- Name: seq_feature_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_feature_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_feature_id OWNER TO postgres;

--
-- TOC entry 308 (class 1259 OID 102330)
-- Name: seq_hazard_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_hazard_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_hazard_id OWNER TO postgres;

--
-- TOC entry 309 (class 1259 OID 102331)
-- Name: seq_hazard_type_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_hazard_type_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_hazard_type_id OWNER TO postgres;

--
-- TOC entry 310 (class 1259 OID 102332)
-- Name: seq_help_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_help_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_help_id OWNER TO postgres;

--
-- TOC entry 311 (class 1259 OID 102333)
-- Name: seq_issue_type_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_issue_type_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_issue_type_id OWNER TO postgres;

--
-- TOC entry 312 (class 1259 OID 102334)
-- Name: seq_maintenance_claim_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_maintenance_claim_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_maintenance_claim_id OWNER TO postgres;

--
-- TOC entry 313 (class 1259 OID 102335)
-- Name: seq_maintenance_detail_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_maintenance_detail_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_maintenance_detail_id OWNER TO postgres;

--
-- TOC entry 314 (class 1259 OID 102336)
-- Name: seq_maintenance_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_maintenance_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_maintenance_id OWNER TO postgres;

--
-- TOC entry 315 (class 1259 OID 102337)
-- Name: seq_maintenance_param_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_maintenance_param_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_maintenance_param_id OWNER TO postgres;

--
-- TOC entry 316 (class 1259 OID 102338)
-- Name: seq_maintenance_part_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_maintenance_part_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_maintenance_part_id OWNER TO postgres;

--
-- TOC entry 317 (class 1259 OID 102339)
-- Name: seq_maintenance_sclst_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_maintenance_sclst_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_maintenance_sclst_id OWNER TO postgres;

--
-- TOC entry 318 (class 1259 OID 102340)
-- Name: seq_maintenance_user_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_maintenance_user_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_maintenance_user_id OWNER TO postgres;

--
-- TOC entry 319 (class 1259 OID 102341)
-- Name: seq_manufacturer_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_manufacturer_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_manufacturer_id OWNER TO postgres;

--
-- TOC entry 320 (class 1259 OID 102342)
-- Name: seq_mnfcturr_category_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_mnfcturr_category_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_mnfcturr_category_id OWNER TO postgres;

--
-- TOC entry 321 (class 1259 OID 102343)
-- Name: seq_modualr_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_modualr_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_modualr_id OWNER TO postgres;

--
-- TOC entry 322 (class 1259 OID 102344)
-- Name: seq_notification_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_notification_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_notification_id OWNER TO postgres;

--
-- TOC entry 323 (class 1259 OID 102345)
-- Name: seq_param_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_param_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_param_id OWNER TO postgres;

--
-- TOC entry 324 (class 1259 OID 102346)
-- Name: seq_part_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_part_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_part_id OWNER TO postgres;

--
-- TOC entry 325 (class 1259 OID 102347)
-- Name: seq_phone_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_phone_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_phone_id OWNER TO postgres;

--
-- TOC entry 326 (class 1259 OID 102348)
-- Name: seq_prod_attachment_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_prod_attachment_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_prod_attachment_id OWNER TO postgres;

--
-- TOC entry 327 (class 1259 OID 102349)
-- Name: seq_prod_video_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_prod_video_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_prod_video_id OWNER TO postgres;

--
-- TOC entry 328 (class 1259 OID 102350)
-- Name: seq_product_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_product_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_product_id OWNER TO postgres;

--
-- TOC entry 329 (class 1259 OID 102351)
-- Name: seq_product_type_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_product_type_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_product_type_id OWNER TO postgres;

--
-- TOC entry 330 (class 1259 OID 102352)
-- Name: seq_province_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_province_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_province_id OWNER TO postgres;

--
-- TOC entry 331 (class 1259 OID 102353)
-- Name: seq_purchase_detail_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_purchase_detail_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_purchase_detail_id OWNER TO postgres;

--
-- TOC entry 332 (class 1259 OID 102354)
-- Name: seq_purchase_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_purchase_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_purchase_id OWNER TO postgres;

--
-- TOC entry 333 (class 1259 OID 102355)
-- Name: seq_purchasedetail_moduler_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_purchasedetail_moduler_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_purchasedetail_moduler_id OWNER TO postgres;

--
-- TOC entry 334 (class 1259 OID 102356)
-- Name: seq_right_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_right_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_right_id OWNER TO postgres;

--
-- TOC entry 335 (class 1259 OID 102357)
-- Name: seq_role_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_role_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_role_id OWNER TO postgres;

--
-- TOC entry 336 (class 1259 OID 102358)
-- Name: seq_role_right_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_role_right_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_role_right_id OWNER TO postgres;

--
-- TOC entry 337 (class 1259 OID 102359)
-- Name: seq_sales_detail_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_sales_detail_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_sales_detail_id OWNER TO postgres;

--
-- TOC entry 338 (class 1259 OID 102360)
-- Name: seq_sales_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_sales_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_sales_id OWNER TO postgres;

--
-- TOC entry 339 (class 1259 OID 102361)
-- Name: seq_salesdetail_moduler_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_salesdetail_moduler_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_salesdetail_moduler_id OWNER TO postgres;

--
-- TOC entry 340 (class 1259 OID 102362)
-- Name: seq_serial_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_serial_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_serial_id OWNER TO postgres;

--
-- TOC entry 341 (class 1259 OID 102363)
-- Name: seq_site_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_site_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_site_id OWNER TO postgres;

--
-- TOC entry 342 (class 1259 OID 102364)
-- Name: seq_site_phone_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_site_phone_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_site_phone_id OWNER TO postgres;

--
-- TOC entry 343 (class 1259 OID 102365)
-- Name: seq_symtom_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_symtom_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_symtom_id OWNER TO postgres;

--
-- TOC entry 344 (class 1259 OID 102366)
-- Name: seq_unit_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_unit_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_unit_id OWNER TO postgres;

--
-- TOC entry 345 (class 1259 OID 102367)
-- Name: seq_user_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_user_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_user_id OWNER TO postgres;

--
-- TOC entry 346 (class 1259 OID 102368)
-- Name: seq_user_right_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_user_right_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_user_right_id OWNER TO postgres;

--
-- TOC entry 347 (class 1259 OID 102369)
-- Name: seq_user_role_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_user_role_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_user_role_id OWNER TO postgres;

--
-- TOC entry 348 (class 1259 OID 102370)
-- Name: seq_user_site_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_user_site_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_user_site_id OWNER TO postgres;

--
-- TOC entry 349 (class 1259 OID 102371)
-- Name: seq_vendor_category_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_vendor_category_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_vendor_category_id OWNER TO postgres;

--
-- TOC entry 350 (class 1259 OID 102372)
-- Name: seq_vendor_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_vendor_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_vendor_id OWNER TO postgres;

--
-- TOC entry 351 (class 1259 OID 102373)
-- Name: seq_vendor_phone_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_vendor_phone_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_vendor_phone_id OWNER TO postgres;

--
-- TOC entry 283 (class 1259 OID 102285)
-- Name: site; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.site (
    site_id integer NOT NULL,
    address character varying(255),
    company_id character varying(255),
    prepared_by character varying(255),
    site_nme character varying(255),
    updated_by character varying(255),
    city_id integer,
    country_id integer,
    province_id integer
);


ALTER TABLE public.site OWNER TO postgres;

--
-- TOC entry 284 (class 1259 OID 102292)
-- Name: unit; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.unit (
    unit_id integer NOT NULL,
    title character varying(255)
);


ALTER TABLE public.unit OWNER TO postgres;

--
-- TOC entry 285 (class 1259 OID 102297)
-- Name: user_right; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_right (
    user_right_id integer NOT NULL,
    right_id integer,
    user_id integer
);


ALTER TABLE public.user_right OWNER TO postgres;

--
-- TOC entry 286 (class 1259 OID 102302)
-- Name: web_users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.web_users (
    user_id integer NOT NULL,
    user_email character varying(255),
    file_nme character varying(255),
    file_size character varying(255),
    file_type character varying(255),
    user_name character varying(255),
    user_password character varying(255),
    user_profile character varying(255),
    user_ref_id character varying(255),
    user_type character varying(255),
    login_id character varying(255),
    company_id integer
);


ALTER TABLE public.web_users OWNER TO postgres;

-- Completed on 2025-02-27 14:53:53

--
-- PostgreSQL database dump complete
--

