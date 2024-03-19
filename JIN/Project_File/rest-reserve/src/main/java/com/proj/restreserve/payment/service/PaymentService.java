package com.proj.restreserve.payment.service;

import com.proj.restreserve.payment.dto.PaymentMenusDto;
import com.proj.restreserve.payment.entity.PaymentMenu;
import com.proj.restreserve.payment.repository.PaymentMenuRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentMenuRepository paymentMenuRepository;
    private final ModelMapper modelMapper;

    public List<PaymentMenusDto> paymentMenusSet (String paymentid){
        List<PaymentMenu> paymentMenus = this.paymentMenuRepository.findByPaymentid_Paymentid(paymentid);

        List<PaymentMenusDto> paymentMenusDto = new ArrayList<>();//총 메뉴 저장

        for (PaymentMenu menu : paymentMenus) {
            PaymentMenusDto paymentMenuDto = modelMapper.map(menu, PaymentMenusDto.class);//DTO로 변환
            paymentMenusDto.add(paymentMenuDto);
        }
        return paymentMenusDto;
    }
}
