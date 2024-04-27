package com.proj.restreserve.detailpage.service;

import com.proj.restreserve.payment.entity.Payment;
import com.proj.restreserve.payment.repository.PaymentRepository;
import com.proj.restreserve.user.entity.User;
import com.proj.restreserve.user.repository.UserRepository;
import com.proj.restreserve.visit.entity.Visit;
import com.proj.restreserve.visit.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SelectReservation {
    private final PaymentRepository paymentRepository;
    private final VisitRepository visitRepository;
    private final UserRepository userRepository;
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 현재 로그인한 사용자의 인증 정보를 가져옵니다.
        String useremail = authentication.getName();
        return userRepository.findByUseremail(useremail); // 로그인한 사용자의 이메일을 사용하여 사용자 정보를 조회합니다.
    }

    public Page<Object> showReservation(int page){//예약중인 방문,포장 조회
        Pageable pageable = PageRequest.of(page-1, 10);
        User user = getCurrentUser();
        //결제확인이 안된걸 기준으로 검색
        List<Payment> payments = paymentRepository.findByUserAndPaymentcheckFalse(user);
        List<Visit> visits = visitRepository.findByUserAndVisitcheckFalse(user);

        List<Object> reservation = new ArrayList<>();
        if(!payments.isEmpty()){
            reservation.add(payments);
        }
        if(!visits.isEmpty()){
            reservation.add(visits);
        }
        return new PageImpl<>(reservation,pageable,reservation.size());
    }
}
