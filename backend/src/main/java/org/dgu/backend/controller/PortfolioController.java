package org.dgu.backend.controller;

import lombok.RequiredArgsConstructor;
import org.dgu.backend.common.ApiResponse;
import org.dgu.backend.common.constant.SuccessStatus;
import org.dgu.backend.dto.PortfolioDto;
import org.dgu.backend.service.PortfolioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/portfolios")
@RequiredArgsConstructor
public class PortfolioController {
    private final PortfolioService portfolioService;

    // 포트폴리오 전체 조회 API
    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getPortfolios(
            @RequestHeader("Authorization") String authorizationHeader) {

        List<PortfolioDto.PortfolioInfos> portfolioInfoGroups = portfolioService.getPortfolios(authorizationHeader);
        return ApiResponse.onSuccess(SuccessStatus.SUCCESS_GET_PORTFOLIOS, portfolioInfoGroups);
    }

    // 포트폴리오 상세 조회 API
    @GetMapping("/detail")
    public ResponseEntity<ApiResponse<PortfolioDto.PortfolioDetailInfos>> getPortfolioDetails(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam("portfolio_id") String portfolioId) {

        PortfolioDto.PortfolioDetailInfos portfolioDetailInfos = portfolioService.getPortfolioDetails(authorizationHeader, portfolioId);
        return ApiResponse.onSuccess(SuccessStatus.SUCCESS_GET_PORTFOLIO_DETAILS, portfolioDetailInfos);
    }

    // 포트폴리오 삭제 API
    @DeleteMapping
    public ResponseEntity<ApiResponse<Object>> removePortfolio(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam("portfolio_id") String portfolioId) {

        portfolioService.removePortfolio(authorizationHeader, portfolioId);
        return ApiResponse.onSuccess(SuccessStatus.SUCCESS_DELETE_PORTFOLIO);
    }

    // 포트폴리오 정보 수정 API
    @PatchMapping
    public ResponseEntity<ApiResponse<PortfolioDto.EditPortfolioResponse>> editPortfolio(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody PortfolioDto.EditPortfolioRequest editPortfolioRequest) {

        PortfolioDto.EditPortfolioResponse editPortfolioResponse = portfolioService.editPortfolio(authorizationHeader, editPortfolioRequest);
        return ApiResponse.onSuccess(SuccessStatus.SUCCESS_EDIT_PORTFOLIO, editPortfolioResponse);
    }

    // 포트폴리오 즐겨찾기 추가 API
    @PostMapping("/bookmark")
    public ResponseEntity<ApiResponse<Object>> addPortfolioBookMark(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam("portfolio_id") String portfolioId) {

        portfolioService.addPortfolioBookMark(authorizationHeader, portfolioId);
        return ApiResponse.onSuccess(SuccessStatus.SUCCESS_ADD_PORTFOLIO_SCRAP);
    }

    // 포트폴리오 즐겨찾기 삭제 API
    @DeleteMapping("/bookmark")
    public ResponseEntity<ApiResponse<Object>> removePortfolioBookMark(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam("portfolio_id") String portfolioId) {

        portfolioService.removePortfolioBookMark(authorizationHeader, portfolioId);
        return ApiResponse.onSuccess(SuccessStatus.SUCCESS_DELETE_PORTFOLIO_SCRAP);
    }
}