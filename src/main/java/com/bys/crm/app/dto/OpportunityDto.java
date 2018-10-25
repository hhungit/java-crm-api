package com.bys.crm.app.dto;

import java.math.BigDecimal;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.joda.time.DateTime;

public class OpportunityDto {
	private Long id;

	private DateTime createdDate;

	private DateTime updatedDate;

	@Size(max = 50, message = "name should not exceed 50 characters")
	private String name;

	@Valid
	private BranchSummaryDto branch;

//	@Valid
//	private CustomerContactSummaryDto customerContact;

	@Valid
	private CustomerSummaryDto customer;

	private String classify;

	private String potentialSources;

	private BigDecimal amount;

	private DateTime completionDate;

	private String step;

	private BigDecimal probability;

//	private String strategy;

	@Valid
	private CampaignSummaryDto campaign;

	private BigDecimal expectedValue;

	private String assignedTo;

	private String description;

	private String customerNm;

	private String contactName;

	@Valid
	private EmployeeSummaryDto employee;

	@Valid
	private GroupSummaryDto employeeGroup;
	
	private Set<CustomerContactDto> contacts;
	
	private Set<OpportunityContactGroupDto> opportunityContactGroups;
	
	private Boolean isShare;
	
	private CustomerResourcesDto customerResource;

	private Boolean isAssigned;

	public Boolean getIsAssigned() {
		return isAssigned;
	}

	public void setIsAssigned(Boolean isAssigned) {
		this.isAssigned = isAssigned;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(DateTime createdDate) {
		this.createdDate = createdDate;
	}

	public DateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(DateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BranchSummaryDto getBranch() {
		return branch;
	}

	public void setBranch(BranchSummaryDto branch) {
		this.branch = branch;
	}

//	public CustomerContactSummaryDto getCustomerContact() {
//		return customerContact;
//	}
//
//	public void setCustomerContact(CustomerContactSummaryDto customerContact) {
//		this.customerContact = customerContact;
//	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	public String getPotentialSources() {
		return potentialSources;
	}

	public void setPotentialSources(String potentialSources) {
		this.potentialSources = potentialSources;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public DateTime getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(DateTime completionDate) {
		this.completionDate = completionDate;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public BigDecimal getProbability() {
		return probability;
	}

	public void setProbability(BigDecimal probability) {
		this.probability = probability;
	}

//	public String getStrategy() {
//		return strategy;
//	}
//
//	public void setStrategy(String strategy) {
//		this.strategy = strategy;
//	}

	public BigDecimal getExpectedValue() {
		return expectedValue;
	}

	public void setExpectedValue(BigDecimal expectedValue) {
		this.expectedValue = expectedValue;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CustomerSummaryDto getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerSummaryDto customer) {
		this.customer = customer;
	}

	public String getCustomerNm() {
		return customerNm;
	}

	public void setCustomerNm(String customerNm) {
		this.customerNm = customerNm;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public EmployeeSummaryDto getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeSummaryDto employee) {
		this.employee = employee;
	}

	public GroupSummaryDto getEmployeeGroup() {
		return employeeGroup;
	}

	public void setEmployeeGroup(GroupSummaryDto employeeGroup) {
		this.employeeGroup = employeeGroup;
	}

	public CampaignSummaryDto getCampaign() {
		return campaign;
	}

	public void setCampaign(CampaignSummaryDto campaign) {
		this.campaign = campaign;
	}

	public Set<CustomerContactDto> getContacts() {
		return contacts;
	}

	public void setContacts(Set<CustomerContactDto> contacts) {
		this.contacts = contacts;
	}

	public Set<OpportunityContactGroupDto> getOpportunityContactGroups() {
		return opportunityContactGroups;
	}

	public void setOpportunityContactGroups(Set<OpportunityContactGroupDto> opportunityContactGroups) {
		this.opportunityContactGroups = opportunityContactGroups;
	}

	public Boolean getIsShare() {
		return isShare;
	}

	public void setIsShare(Boolean isShare) {
		this.isShare = isShare;
	}

	public CustomerResourcesDto getCustomerResource() {
		return customerResource;
	}

	public void setCustomerResource(CustomerResourcesDto customerResource) {
		this.customerResource = customerResource;
	}
}