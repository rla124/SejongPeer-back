package com.sejong.sejongpeer.domain.buddy.scheduler;

import com.sejong.sejongpeer.domain.buddy.entity.buddy.Buddy;
import com.sejong.sejongpeer.domain.buddy.entity.buddy.type.BuddyStatus;
import com.sejong.sejongpeer.domain.buddy.entity.buddymatched.BuddyMatched;
import com.sejong.sejongpeer.domain.buddy.entity.buddymatched.type.BuddyMatchedStatus;
import com.sejong.sejongpeer.domain.buddy.repository.BuddyRepository;
import com.sejong.sejongpeer.domain.buddy.service.BuddyMatchingService;
import com.sejong.sejongpeer.domain.buddy.service.MatchingService;
import com.sejong.sejongpeer.infra.sms.service.SmsText;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MatchingScheduler {
	private static final int NO_RESPONSE_HOUR_LIMIT = 24;

	private final BuddyRepository buddyRepository;
    private final MatchingService matchingService;
	private final BuddyMatchingService buddyMatchingService;

    // 매 1시간마다 실행
    @Scheduled(cron = "0 0 0/1 * * *")
    public void executeMatchingPeriodically() {
        matchingService.executeMatching();
    }

	@Scheduled(cron = "0 0 0 */1 * *")
	public void updateBuddyStatusAutomatically() {
		List<Buddy> foundBuddies = buddyRepository.findAllByStatus(BuddyStatus.FOUND_BUDDY);
		LocalDateTime currentTime = LocalDateTime.now();

		for (Buddy NoResposeBuddy : foundBuddies) {
			LocalDateTime updatedTime = NoResposeBuddy.getUpdatedAt();
			long hoursElapsed = ChronoUnit.HOURS.between(updatedTime, currentTime);

			if (hoursElapsed >= NO_RESPONSE_HOUR_LIMIT) {
				NoResposeBuddy.changeStatus(BuddyStatus.REJECT);
				buddyMatchingService.sendMatchingFailurePenaltyMessage(NoResposeBuddy, SmsText.MATCHING_AUTO_FAILED_REJECT);

				BuddyMatched progressMatch = buddyMatchingService.getInProgressBuddyMatchedByBuddy(NoResposeBuddy);

				Buddy partnerBuddy = buddyMatchingService.getOtherBuddyInBuddyMatched(progressMatch, NoResposeBuddy);
				partnerBuddy.changeStatus(BuddyStatus.DENIED);
				buddyMatchingService.sendMatchingFailurePenaltyMessage(partnerBuddy, SmsText.MATCHING_AUTO_FAILED_DENIED);

				progressMatch.changeStatus(BuddyMatchedStatus.MATCHING_FAIL);


			}
		}
	}

}
