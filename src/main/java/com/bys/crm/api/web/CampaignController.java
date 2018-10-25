package com.bys.crm.api.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bys.crm.app.dto.CampaignDto;
import com.bys.crm.app.dto.ResponseDto;
import com.bys.crm.app.facade.CampaignFacade;

@RestController
public class CampaignController extends BaseController {
	@Autowired
	private CampaignFacade facade;

	@RequestMapping(method = RequestMethod.POST, value = RestURL.CREATE_CAMPAIGN)
	public ResponseDto createCampaign(@PathVariable(value = "employeeId") int employeeId,
			@RequestBody @Valid CampaignDto dto) {
		return new ResponseDto(facade.createCampaign(dto, employeeId));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_CAMPAIGN_BY_ID)
	public ResponseDto getCampaignById(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "campaignId") long campaignId) {
		return new ResponseDto(facade.getCampaignById(employeeId, campaignId));
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.EDIT_CAMPAIGN)
	public ResponseDto editCampaign(@PathVariable(value = "employeeId") int employeeId,
			@RequestBody @Valid CampaignDto dto) {
		return new ResponseDto(facade.editCampaign(dto, employeeId));
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.DELETE_CAMPAIGN)
	public ResponseDto deleteCampaign(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "campaignId") long campaignId) {
		return new ResponseDto(facade.deleteCampaign(campaignId, employeeId));
	}

	@RequestMapping(method = RequestMethod.POST, value = RestURL.DELETE_CAMPAIGN_LIST)
	public ResponseDto deleteCampaignList(@PathVariable(value = "employeeId") int employeeId,
			@RequestBody List<Long> idList) {
		return new ResponseDto(facade.deleteCampaignList(idList, employeeId));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.SEARCH_CAMPAIGN)
	public ResponseDto searchCampaign(@PathVariable(value = "employeeId") int employeeId,
			@RequestParam(value = "search", required = false) String searchKey,
			@PathVariable(value = "pageNumber") Integer pageNumber, @PathVariable(value = "pageSize") Integer pageSize,
			@RequestParam(value = "sort", required = false) String sortBy,
			@RequestParam(value = "direct", required = false) String direct) {
		return new ResponseDto(facade.searchCampaign(employeeId, searchKey, pageNumber, pageSize, sortBy, direct));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.SEARCH_CAMPAIGN_BY_NAME)
	public ResponseDto searchCampaignByName(@PathVariable(value = "employeeId") int employeeId,
			@RequestParam(value = "campaignName", required = false) String campaignName) {
		return new ResponseDto(facade.searchCampaignByName(employeeId, campaignName));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.FILTER_CAMPAIGN)
	public ResponseDto campaignFilter(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "pageNumber") Integer pageNumber, @PathVariable(value = "pageSize") Integer pageSize,
			@RequestParam(value = "search", required = false) String searchKey,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "campaignStatus", required = false) String campaignStatus,
			@RequestParam(value = "sort", required = false) String sortBy,
			@RequestParam(value = "direct", required = false) String direct,
			@RequestParam(value = "fromDate", required = false) Long fromDate,
			@RequestParam(value = "toDate", required = false) Long toDate) {
		return new ResponseDto(facade.campaignFilter(employeeId, searchKey, type, campaignStatus, pageNumber, pageSize,
				sortBy, direct, fromDate, toDate));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.CHART_CAMPAIGN)
	public ResponseDto getCampaignChart(@PathVariable(value = "employeeId") Long employeeId,
			@PathVariable(value = "type") String type, @RequestParam(value = "from", required = true) Long from,
			@RequestParam(value = "to", required = true) Long to) {
		return new ResponseDto(facade.getCampaignChart(employeeId, from, to, type));
	}
}
