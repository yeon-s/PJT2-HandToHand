package com.ssafy.handtohand.domain.model.dto.donation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel("기부 내역 추가 요청 정보")
@Getter
@Builder
public class RequestDonationInfo {
    @ApiModelProperty(value = "기부자 지갑 주소",required = true)
    private final String walletAddress;

    @ApiModelProperty(value = "기부 거래 트랜잭션 해시",required = true)
    private final String transactionHash;

    @ApiModelProperty(value = "기부금액",required = true)
    private final double amount;
}