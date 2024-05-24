package com.sejong.sejongpeer.domain.study.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sejong.sejongpeer.domain.externalactivity.entity.ExternalActivity;
import com.sejong.sejongpeer.domain.externalactivity.repository.ExternalActivityRepository;
import com.sejong.sejongpeer.domain.member.entity.Member;
import com.sejong.sejongpeer.domain.member.repository.MemberRepository;
import com.sejong.sejongpeer.domain.study.dto.request.ExternalActivityStudyCreateRequest;
import com.sejong.sejongpeer.domain.study.dto.response.StudyCreateResponse;
import com.sejong.sejongpeer.domain.study.entity.ExternalActivityStudy;
import com.sejong.sejongpeer.domain.study.entity.Study;
import com.sejong.sejongpeer.domain.study.repository.ExternalActivityStudyRepository;
import com.sejong.sejongpeer.domain.study.repository.StudyRepository;
import com.sejong.sejongpeer.domain.study.vo.StudyVo;
import com.sejong.sejongpeer.global.error.exception.CustomException;
import com.sejong.sejongpeer.global.error.exception.ErrorCode;
import com.sejong.sejongpeer.global.util.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ExternalActivityStudyService {
	private final ExternalActivityRepository externalActivityRepository;
	private final ExternalActivityStudyRepository externalActivityStudyRepository;
	private final StudyRepository studyRepository;
	private final MemberRepository memberRepository;

	private final SecurityUtil securityUtil;

	public StudyCreateResponse createStudy(ExternalActivityStudyCreateRequest request) {
		String memberId = securityUtil.getCurrentMemberId();
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

		ExternalActivity externalActivity = externalActivityRepository.findById(request.externalActivityId())
			.orElseThrow(() -> new CustomException(ErrorCode.EXTERNAL_ACTIVITY_NOT_FOUND));

		StudyVo vo = StudyVo.from(request);
		Study study = Study.create(member, vo);
		Study savedStudy = studyRepository.save(study);

		ExternalActivityStudy externalActivityStudy = ExternalActivityStudy.create(externalActivity, savedStudy);
		externalActivityStudyRepository.save(externalActivityStudy);

		return StudyCreateResponse.from(savedStudy);
	}
}
