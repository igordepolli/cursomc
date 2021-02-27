package com.depolliigor.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.depolliigor.cursomc.domain.PaymentWithSlip;

@Service
public class SlipService {

	public void fillPaymentWithSlip(PaymentWithSlip payment, Date orderInstant) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(orderInstant);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		payment.setDueDate(cal.getTime());
	}
}
