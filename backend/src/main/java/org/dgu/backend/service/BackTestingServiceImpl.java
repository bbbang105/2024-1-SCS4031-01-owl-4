package org.dgu.backend.service;

import lombok.RequiredArgsConstructor;
import org.dgu.backend.domain.Candle;
import org.dgu.backend.domain.CandleInfo;
import org.dgu.backend.dto.BackTestingDto;
import org.dgu.backend.repository.CandleInfoRepository;
import org.dgu.backend.repository.CandleRepository;
import org.dgu.backend.util.BackTestingUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BackTestingServiceImpl implements BackTestingService {
    private final BackTestingUtil backTestingUtil;
    private final CandleInfoRepository candleInfoRepository;
    private final CandleRepository candleRepository;

    // 백테스팅 실행
    @Override
    public BackTestingDto.BackTestingResponse runBackTesting(String authorizationHeader, BackTestingDto.StepInfo stepInfo) {
        Candle candle = candleRepository.findByName(stepInfo.getCandleName());

        // 캔들 ID에 해당하는 모든 CandleInfo 가져옴
        List<CandleInfo> allCandleInfoList = candleInfoRepository.findAllByCandleId(candle.getId());

        // 설정한 기간에 맞는 차트만 필터링
        List<CandleInfo> filteredCandleInfoList = backTestingUtil.getFilteredCandleInfoList(allCandleInfoList, stepInfo.getStartDate(), stepInfo.getEndDate());

        // N일 EMA 계산
        List<BackTestingDto.EMAInfo> nDateEMAs = backTestingUtil.calculateEMA(filteredCandleInfoList, stepInfo.getNDate());
        // M일 EMA 계산
        List<BackTestingDto.EMAInfo> mDateEMAs = backTestingUtil.calculateEMA(filteredCandleInfoList, stepInfo.getMDate());

        // 골든 크로스 지점 찾기
        List<LocalDateTime> goldenCrossPoints = backTestingUtil.findGoldenCrossPoints(nDateEMAs, mDateEMAs);

        // 백테스팅 시작
        List<BackTestingDto.BackTestingResult> backTestingResults = backTestingUtil.run(filteredCandleInfoList, stepInfo, goldenCrossPoints);

        // 백테스팅 결과 도출
        BackTestingDto.BackTestingResponse backTestingResponse = backTestingUtil.collectResults(backTestingResults, stepInfo.getInitialCapital());

        return backTestingResponse;
    }
}