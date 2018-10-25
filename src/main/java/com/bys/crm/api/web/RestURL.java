package com.bys.crm.api.web;

public interface RestURL {

	String USER_SIGNUP = "users/signup";
	String USER_GET = "users/{id}";
	String COMPANY_SIGNUP = "users/company/signup";
	
	String FORGOT_PASSWORD = "users/password/forgot";
	String CHANGE_PASSWORD = "users/password/change";
	String NEW_PASSWORD = "users/password/new";
	String VALIDATE_RESET_PASSWORD = "users/password/validate";
	
	String SEND_EMAIL = "employee/{employeeId}/email";
	String SEND_EMAIL_CAMPAIGN = "employee/{employeeId}/email/campaign/{campaignID}";
	// Employee
	String GET_EMPLOYEE_LIST = "employee/{employeeId}/employees";
	String GET_EMPLOYEE_BY_ID = "employee/{employeeId}";
	String CREATE_EMPLOYEE = "users/{id}/employee";
	String GET_EMPLOYEE_BY_BRANCH = "employee/{employeeId}/employees/branch/{branchId}";
	String GET_EMPLOYEES_PAGING = "employee/{employeeId}/employees/{pageNumber}/{pageSize}";
	String EDIT_EMPLOYEE = "employee/{employeeId}/edit";
	String GET_AVATAR_BY_ID = "employee/{employeeId}/avatar/{id}";
	
	// Customer contact
	String CREATE_CONTACT = "employee/{employeeId}/contact";
	String GET_CONTACT_BY_ID = "employee/{employeeId}/contact/{contactId}";
	String EDIT_CONTACT = "employee/{employeeId}/contact/edit";
	String DELETE_CONTACT = "employee/{employeeId}/contact/{contactId}/delete";
	String DELETE_CONTACT_LIST = "employee/{employeeId}/contacts/delete";
	String SEARCH_CONTACT = "employee/{employeeId}/contacts/search/{pageNumber}/{pageSize}";
	String IMPORT_CONTACTS_FROM_EXCEL = "employee/{employeeId}/contacts";
	String SEARCH_CONTACT_BY_NAME = "employee/{employeeId}/contacts";
	String FILTER_CONTACT = "employee/{employeeId}/contacts/filter/{pageNumber}/{pageSize}";
	String CHART_CONTACT = "employee/{employeeId}/contacts/chart/{type}";
	String CHECK_CONTACT_NAME_EXIST = "employee/{employeeId}/contacts/check-name";
	String GET_CONTACT_BY_CUSTOMER_ID = "employee/{employeeId}/contacts/customer/{customerId}";
	
	// Branch
	String GET_BRANCH_LIST = "branchs";
	String GET_BRANCHS_BY_EMPLOYEE = "employee/{employeeId}/branchs";
	
	// Customer
	String CREATE_CUSTOMER = "employee/{employeeId}/customer";
	String GET_CUSTOMER_BY_ID = "employee/{employeeId}/customer/{customerId}";
	String EDIT_CUSTOMER = "employee/{employeeId}/customer/edit";
	String DELETE_CUSTOMER = "employee/{employeeId}/customer/{customerId}/delete";
	String DELETE_CUSTOMER_LIST = "employee/{employeeId}/customers/delete";
	String IMPORT_CUSTOMERS_FROM_EXCEL = "employee/{employeeId}/customers";
	String SEARCH_CUSTOMER = "employee/{employeeId}/customers/search/{pageNumber}/{pageSize}";
	String SEARCH_CUSTOMER_BY_NAME = "employee/{employeeId}/customers";
	String FILTER_CUSTOMER = "employee/{employeeId}/customers/filter/{pageNumber}/{pageSize}";
	String CHART_CUSTOMER = "employee/{employeeId}/customers/chart/{type}";
	String CHECK_CUSTOMER_NAME_EXIST = "employee/{employeeId}/customers/check-name";
	String GET_CUSTOMER_BY_CONTACT_ID = "employee/{employeeId}/customers/contact/{contactId}";

	// GECountrys
	String CREATE_COUNTRY = "employee/{employeeId}/country";
	String GET_COUNTRY_BY_ID = "employee/{employeeId}/country/{countryId}";
	String EDIT_COUNTRY = "employee/{employeeId}/country/edit";
	String DELETE_COUNTRY = "employee/{employeeId}/country/{countryId}/delete";
	String GET_COUNTRY_LIST = "employee/{employeeId}/countries";

	// GEStateProvinces
	String CREATE_PROVINCE = "employee/{employeeId}/province";
	String GET_PROVINCE_BY_ID = "employee/{employeeId}/province/{provinceId}";
	String EDIT_PROVINCE = "employee/{employeeId}/province/edit";
	String DELETE_PROVINCE = "employee/{employeeId}/province/{provinceId}/delete";
	String GET_PROVINCE_LIST = "employee/{employeeId}/provinces";

	// GEDistricts
	String CREATE_DISTRICT = "employee/{employeeId}/district";
	String GET_DISTRICT_BY_ID = "employee/{employeeId}/district/{districtId}";
	String EDIT_DISTRICT = "employee/{employeeId}/district/edit";
	String DELETE_DISTRICT = "employee/{employeeId}/district/{districtId}/delete";
	String GET_DISTRICT_LIST = "employee/{employeeId}/districts";

	// ARProspectCustomers
	String CREATE_PROSPECT = "employee/{employeeId}/prospect";
	String GET_PROSPECT_BY_ID = "employee/{employeeId}/prospect/{prospectId}";
	String EDIT_PROSPECT = "employee/{employeeId}/prospect/edit";
	String DELETE_PROSPECT = "employee/{employeeId}/prospect/{prospectId}/delete";
	String DELETE_PROSPECT_LIST = "employee/{employeeId}/prospects/delete";
	String IMPORT_PROSPECTS_FROM_EXCEL = "employee/{employeeId}/prospects";
	String CHANGE_PROSPECT_TO_CUSTOMER_CONTACT = "employee/{employeeId}/prospect/change";
	String CHANGE_PROSPECT_TO_CUSTOMER_CONTACT_ERP = "employee/{employeeId}/prospect/change/{token}";
	String SEARCH_PROSPECT = "employee/{employeeId}/prospects/search/{pageNumber}/{pageSize}";
	String SEARCH_PROSPECT_BY_NAME = "employee/{employeeId}/prospects";
	String FILTER_PROSPECT = "employee/{employeeId}/prospects/filter/{pageNumber}/{pageSize}";
	String GET_DELETED_PROSPECT_BY_ID = "employee/{employeeId}/deleted-prospect/{prospectId}";
	String CHART_PROSPECT = "employee/{employeeId}/prospects/chart/{type}";

	// AROpportunitys
	String CREATE_OPPORTUNITY = "employee/{employeeId}/opportunity";
	String GET_OPPORTUNITY_BY_ID = "employee/{employeeId}/opportunity/{opportunityId}";
	String EDIT_OPPORTUNITY = "employee/{employeeId}/opportunity/edit";
	String DELETE_OPPORTUNITY = "employee/{employeeId}/opportunity/{opportunityId}/delete";
	String DELETE_OPPORTUNITY_LIST = "employee/{employeeId}/opportunities/delete";
	String SEARCH_OPPORTUNITY = "employee/{employeeId}/opportunities/search/{pageNumber}/{pageSize}";
	String SEARCH_OPPORTUNITY_BY_NAME = "employee/{employeeId}/opportunities";
	String FILTER_OPPORTUNITY = "employee/{employeeId}/opportunities/filter/{pageNumber}/{pageSize}";
	String CHART_OPPORTUNITY = "employee/{employeeId}/opportunities/chart/{type}";
	String GET_OPPORTUNITY_BY_CUSTOMER_ID = "employee/{employeeId}/opportunities/customer/{customerId}/{pageNumber}/{pageSize}";
	String GET_OPPORTUNITY_BY_CONTACT_ID = "employee/{employeeId}/opportunities/contact/{contactId}/{pageNumber}/{pageSize}";

	// ARCampaigns
	String CREATE_CAMPAIGN = "employee/{employeeId}/campaign";
	String GET_CAMPAIGN_BY_ID = "employee/{employeeId}/campaign/{campaignId}";
	String EDIT_CAMPAIGN = "employee/{employeeId}/campaign/edit";
	String DELETE_CAMPAIGN = "employee/{employeeId}/campaign/{campaignId}/delete";
	String DELETE_CAMPAIGN_LIST = "employee/{employeeId}/campaigns/delete";
	String SEARCH_CAMPAIGN = "employee/{employeeId}/campaigns/search/{pageNumber}/{pageSize}";
	String SEARCH_CAMPAIGN_BY_NAME = "employee/{employeeId}/campaigns";
	String FILTER_CAMPAIGN = "employee/{employeeId}/campaigns/filter/{pageNumber}/{pageSize}";
	String CHART_CAMPAIGN = "employee/{employeeId}/campaigns/chart/{type}";

	// ARActivitys
	String CREATE_ACTIVITY = "employee/{employeeId}/activity";
	String GET_ACTIVITY_BY_ID = "employee/{employeeId}/activity/{activityType}/{activityId}";
	String EDIT_ACTIVITY = "employee/{employeeId}/activity/{activityType}/edit";
	String DELETE_ACTIVITY = "employee/{employeeId}/activity/{activityType}/{activityId}/delete";
	String DELETE_ACTIVITY_LIST = "employee/{employeeId}/activities/delete";
	String SEARCH_ACTIVITY = "employee/{employeeId}/activities/search/{pageNumber}/{pageSize}";
	String IMPORT_ACTIVITYS_FROM_EXCEL = "employee/{employeeId}/activities";
	String GET_ACTIVITY_BY_EMPLOYEE_AND_TYPE = "employee/{employeeId}/activities/type/{activityType}/{pageNumber}/{pageSize}";
	String GET_ACTIVITY_BY_OBJECT_TYPE = "employee/{employeeId}/activities/{objectType}/{objectId}/{pageNumber}/{pageSize}";
	String FILTER_ACTIVITY = "employee/{employeeId}/activities/filter/{pageNumber}/{pageSize}";

	// List common
	String GET_COMMON_LIST = "list/{listType}";
	
	// List common
	String GET_COMMON_LIST_CUSTOMER_RESOURCE= "list/customer-resources";
	
	// ARComments
	String CREATE_COMMENT = "employee/{employeeId}/comment";
	String GET_COMMENT_LIST = "employee/{employeeId}/comments/{type}/{objectId}/{pageNumber}/{pageSize}";

	// Audit
	String GET_AUDIT_INFO = "employee/{employeeId}/audits/{objectType}/{objectId}/{pageNumber}/{pageSize}";
	String GET_AUDIT_INFO_BY_USER = "employee/{employeeId}/audits/{pageNumber}/{pageSize}";
	String GET_AUDIT_INFO_BY_USER_WITH_RECORD_NUMBER = "employee/{employeeId}/audits/{recordNumber}";

	// AROpportunityContacts
	String CREATE_OPPORTUNITY_CONTACT = "employee/{employeeId}/opportunity-contact";
	String GET_OPPORTUNITYS_CONTACT_BY_OPPORTUNITY_ID = "employee/{employeeId}/opportunity-contacts/{opportunityId}";

	// HREmployeeGroups
	String CREATE_GROUP = "employee/{employeeId}/group";
	String ADD_EMPLOYEE_TO_GROUP = "employee/{employeeId}/employee-group/{empId}/{groupId}";
	String GET_GROUP_BY_ID = "employee/{employeeId}/group/{groupId}";
	String GET_GROUP_LIST = "employee/{employeeId}/groups";

	// Notification
	String GET_NOTIFICATIONS_BY_EMPLOYEE = "employee/{employeeId}/notifications/{pageNumber}/{pageSize}";
	String CHANGE_READ_NOTIFICATION = "employee/{employeeId}/notification/read/{notificationId}";
	String COUNT_WAIT_NOTIFICATION = "employee/{employeeId}/notification/count";
	String GET_NOTIFICATIONS_BY_START_DATE = "employee/{employeeId}/notifications/{startDate}/{pageNumber}/{pageSize}";

	// Download file
	String DOWNLOAD_FILE = "employee/{employeeId}/downloadFile/{objectType}";

	// Call Coordinator
	String GET_COORDINATOR_BY_NO = "employee/{employeeId}/coordinator";
	
	// Get product
	String GET_PRODUCTS= "employee/{employeeId}/products";
	
	//Call from WordFone
	String CALL_FROM_WORDFONE = "callcenter/manager";

	//Call from FE
	String GET_CURRENT_CALL = "employee/{employeeId}/callcenter/callnumbers";
	
	String GET_CALL_OUTBOUND = "employee/{employeeId}/callcenter/callnumbers/outbound";

	// Search phone number
	String SEARCH_PHONE_NUMBER = "employee/{employeeId}/phone/{phoneNumber}";
	
	String TEST_CALL_STORE = "test/store";
	String TEST_CALL_STORE_CUSTOMER = "test/store/customer";
	String TEST_CALL_STORE_CUSTOMER_LIST = "test/store/customer/list";
	String TEST_CALL_STORE_INIT_BALANCE = "test/store/customer/initbalance";

	// Get product price
	String GET_CUSTOMER_INFOR= "employee/{employeeId}/customerInfor/{customerId}";
	
	// Get invoices
	String GET_INVOICES= "employee/{employeeId}/invoices/{customerId}/{pageNumber}/{pageSize}";

	// Get debt details
	String GET_DEBT_DETAILS= "employee/{employeeId}/debtdetails/{customerId}/{pageNumber}/{pageSize}";

	// Check exist phone number
	String CHECT_EXIST_PHONE_NUMBER = "employee/{employeeId}/branch/{branchId}/check-exist-phone/{phoneNumber}";

	// Get FengShuis infor
	String GET_FENG_SHUIS_INFOR = "employee/{employeeId}/feng-shuis-infor";

	// Get lunar birthday list
	String GET_LUNAR_YEARS_LIST = "employee/{employeeId}/lunar-years";

	// Get lunar year form solar year
	String GET_LUNAR_YEAR_FROM_SOLAR_YEAR = "lunar-year/{solarYear}";
}
