package com.bys.crm.api.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bys.crm.app.dto.CommentDto;
import com.bys.crm.app.dto.ResponseDto;
import com.bys.crm.app.facade.CommentFacade;

@RestController
public class CommentController extends BaseController {
	@Autowired
	private CommentFacade facade;

	@RequestMapping(method = RequestMethod.POST, value = RestURL.CREATE_COMMENT)
	public ResponseDto createProspectComment(@PathVariable(value = "employeeId") int employeeId,
			@RequestBody @Valid CommentDto dto) {
		return new ResponseDto(facade.createComment(dto, employeeId));
	}

	@RequestMapping(method = RequestMethod.GET, value = RestURL.GET_COMMENT_LIST)
	public ResponseDto getCommentList(@PathVariable(value = "employeeId") int employeeId,
			@PathVariable(value = "type") String type, @PathVariable(value = "objectId") long objectId,
			@PathVariable(value = "pageNumber") Integer pageNumber,
			@PathVariable(value = "pageSize") Integer pageSize) {
		return new ResponseDto(facade.getCommentList(employeeId, type, objectId, pageNumber, pageSize));
	}
}
