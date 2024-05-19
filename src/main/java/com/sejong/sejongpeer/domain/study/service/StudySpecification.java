package com.sejong.sejongpeer.domain.study.service;

import com.sejong.sejongpeer.domain.study.entity.Study;
import com.sejong.sejongpeer.domain.study.entity.type.RecruitmentStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class StudySpecification {

	public static Specification<Study> biggerThanRecruitmentMin(Integer recruitmentMin) {
		return (root, query, CriteriaBuilder) -> CriteriaBuilder.greaterThanOrEqualTo(root.get("recruitmentCount"), recruitmentMin);
	}

	public static Specification<Study> smallerThanRecruitmentMax(Integer recruitmentMax) {
		return (root, query, CriteriaBuilder) -> CriteriaBuilder.lessThanOrEqualTo(root.get("recruitmentCount"), recruitmentMax);
	}

	public static Specification<Study> afterStartedAt(LocalDateTime recruitmentStartAt) {
		return (root, query, CriteriaBuilder) -> CriteriaBuilder.greaterThanOrEqualTo(root.get("recruitmentStartAt"), recruitmentStartAt);
	}

	public static Specification<Study> beforeClosededAt(LocalDateTime recruitmentEndAt) {
		return (root, query, CriteriaBuilder) -> CriteriaBuilder.lessThanOrEqualTo(root.get("recruitmentEndAt"), recruitmentEndAt);
	}

	public static Specification<Study> equalsRecruitmentStatus(Boolean isRecruiting) {
		if (isRecruiting) {
			return (root, query, CriteriaBuilder) -> CriteriaBuilder.equal(root.get("recruitmentStatus"), RecruitmentStatus.RECRUITING);
		} else {
			return (root, query, CriteriaBuilder) -> CriteriaBuilder.equal(root.get("recruitmentStatus"), RecruitmentStatus.CLOSED);
		}
	}

	public static Specification<Study> equalsTitle(String searchWord) {
		return (root, query, CriteriaBuilder) -> CriteriaBuilder.like(root.get("title"), searchWord);
	}

	public static Specification<Study> equalsContent(String searchWord) {
		return (root, query, CriteriaBuilder) -> CriteriaBuilder.like(root.get("content"), searchWord);
	}
}
