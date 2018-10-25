package com.bys.crm.app.mapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bys.crm.app.dto.CommentDto;
import com.bys.crm.domain.erp.model.ARComments;
import com.bys.crm.domain.erp.repository.ARCommentsRepository;

@Component
public class CommentMapper extends BaseMapper<CommentDto, ARComments> {
	@Autowired
	private ARCommentsRepository commentsRepository;

	@Override
	public CommentDto buildDto(ARComments entity) {
		CommentDto dto = super.buildDto(entity);
		if (dto == null) {
			return null;
		}

		return dto;
	}

	@Override
	public ARComments buildEntity(CommentDto dto) {
		ARComments entity = super.buildEntity(dto);
		if (dto.getId() == null) {
			entity.setId(keyGenerationService.findMaxId(commentsRepository).incrementAndGet());
		}

		return entity;
	}
}
