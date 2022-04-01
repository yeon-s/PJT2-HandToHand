package com.ssafy.handtohand.domain.service.donation;

import com.ssafy.handtohand.domain.model.dto.donation.request.RequestDonationInfo;
import com.ssafy.handtohand.domain.model.dto.donation.request.RequestDonationStatus;
import com.ssafy.handtohand.domain.model.dto.donation.response.ResponseDonation;
import com.ssafy.handtohand.domain.model.entity.donation.Donation;
import com.ssafy.handtohand.domain.model.entity.donation.DonationStatusType;
import com.ssafy.handtohand.domain.model.entity.user.User;
import com.ssafy.handtohand.domain.repository.user.UserRepository;
import com.ssafy.handtohand.domain.repository.donation.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 기부 관련 기능 Service
 *
 * @author Eunee Chung
 * created on 2022-03-30
 */

@Service
@Transactional
public class DonationService {
    private final DonationRepository donationRepository;
    private final UserRepository userRepository;

    @Autowired
    public DonationService(DonationRepository donationRepository, UserRepository userRepository) {
        this.donationRepository = donationRepository;
        this.userRepository = userRepository;
    }

    public int insertDonationHistory(RequestDonationInfo request) {
        try {
            Donation donation = Donation.builder()
                    .transactionHash(request.getTransactionHash())
                    .amount(request.getAmount())
                    .createdDate(new Date())
                    .build();
            User user = userRepository.findUserByWalletAddress(request.getWalletAddress());
            donation.setUser(user);
            donationRepository.save(donation);
            return 1;
        } catch (NullPointerException e) {

            return 0;
        }
    }

    public List<ResponseDonation> getDonations(String address) {
        User user = userRepository.findUserByWalletAddress(address);
        List<Donation> donationList = donationRepository.findDonationsByUserOrderByCreatedDateDesc(user);
        List<ResponseDonation> responseDonationList = new ArrayList<>();
        for (Donation d : donationList) {
            responseDonationList.add(ResponseDonation.convertToDto(d));
        }
        return responseDonationList;
    }

    public String updateDonations(RequestDonationStatus request) {
        try {
            Donation donation = donationRepository.findDonationByTransactionHash(request.getTransactionHash());
            donation.setType(DonationStatusType.values()[request.getType()]);
            donationRepository.save(donation);

            return "success";
        } catch (Exception e) {
            return "error";
        }
    }
}
