package org.dgu.backend.controller;

import lombok.RequiredArgsConstructor;
import org.dgu.backend.common.ApiResponse;
import org.dgu.backend.common.constant.SuccessStatus;
import org.dgu.backend.dto.TradingDto;
import org.dgu.backend.service.TradingService;
import org.dgu.backend.service.UpbitAutoTrader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/trading")
@RequiredArgsConstructor
public class TradingController {
    private final TradingService tradingService;
    private final UpbitAutoTrader upbitAutoTrader;

    // 자동매매 등록 API
    @PostMapping
    public ResponseEntity<ApiResponse<Object>> registerAutoTrading(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody TradingDto.AutoTradingRequest autoTradingRequest) {

        tradingService.registerAutoTrading(authorizationHeader, autoTradingRequest);
        return ApiResponse.onSuccess(SuccessStatus.SUCCESS_START_TRADING);
    }

    // 자동매매 삭제 API
    @DeleteMapping
    public ResponseEntity<ApiResponse<Object>> removeAutoTrading(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam("portfolio_id") String portfolioId) {

        tradingService.removeAutoTrading(authorizationHeader, portfolioId);
        return ApiResponse.onSuccess(SuccessStatus.SUCCESS_DELETE_TRADING);
    }

    // 자동매매 수동 테스트 API
    @GetMapping("/test")
    public void test() {
        upbitAutoTrader.performAutoTrading();
    }
}