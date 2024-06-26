package org.dgu.backend.util;

import lombok.RequiredArgsConstructor;
import org.dgu.backend.service.CandleInfoService;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.google.common.util.concurrent.RateLimiter;

@Component
@RequiredArgsConstructor
public class CandleDataCollector {
    private final CandleInfoService candleInfoService;
    private final CandleUtil candleUtil;
    private final int batchSize = 200;
    private final RateLimiter rateLimiter = RateLimiter.create(10.0); // 초당 요청 허용량 10개로 제한
    private final long retryDelayMillis = 100; // 재시도 대기 시간 (0.1초)

    public void collectCandleData(String koreanName, String candleName, LocalDateTime startDate, LocalDateTime endDate) {
        // 캔들을 분 기준으로 변환
        int candleInterval = candleUtil.calculateCandleInterval(candleName);

        // 시작 시간부터 종료 시간까지의 총 분 수 계산
        long totalMinutes = Duration.between(startDate, endDate).toMinutes();

        // 한 번의 API 요청에서 지나는 시간
        long oneAPI = (long) candleInterval * batchSize;

        // 반복 횟수 계산
        long numIterations = (long) Math.ceil((double) totalMinutes / oneAPI);

        CompletableFuture<Void>[] futures = new CompletableFuture[(int) numIterations];

        for (int i = 0; i < numIterations; i++) {
            LocalDateTime currentStartTime = startDate.plusMinutes((long) i * oneAPI);
            LocalDateTime currentEndTime = currentStartTime.plusMinutes(oneAPI);

            // 종료 시간이 endDate을 넘어가면 endDate으로 설정
            if (currentEndTime.isAfter(endDate)) {
                currentEndTime = endDate;
            }

            final LocalDateTime intervalEnd = currentEndTime;

            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                // candleInfoService를 사용하여 데이터 수집
                boolean requestSuccess = false;
                while (!requestSuccess) {
                    // 초당 요청 허용량을 초과하지 않을 때까지 대기
                    rateLimiter.acquire();

                    try {
                        candleInfoService.getCandleInfo(koreanName, intervalEnd, batchSize, candleName);
                        requestSuccess = true; // 성공적으로 완료됨
                    } catch (Exception e) {
                        // 오류 발생 시 재시도
                        System.out.println("재시도 중...");
                        try {
                            Thread.sleep(retryDelayMillis); // 0.1초 대기
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });

            futures[i] = future;
        }

        // 모든 CompletableFuture가 완료될 때까지 대기
        CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(futures);
        try {
            allOfFuture.get(); // 모든 작업이 완료될 때까지 대기
            System.out.println("모든 작업이 완료되었습니다.");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}