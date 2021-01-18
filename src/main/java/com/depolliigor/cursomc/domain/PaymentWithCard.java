package com.depolliigor.cursomc.domain;

import javax.persistence.Entity;

import com.depolliigor.cursomc.domain.enums.PaymentStatus;

@Entity
public class PaymentWithCard extends Payment {
	private static final long serialVersionUID = 1L;
	
	private Integer numberOfInstallments;
	
	public PaymentWithCard() {
		
	}

	public PaymentWithCard(Integer id, PaymentStatus paymentStatus, ClientOrder order, Integer numberOfInstallments) {
		super(id, paymentStatus, order);
		this.numberOfInstallments = numberOfInstallments;
	}

	public Integer getNumberOfInstallments() {
		return numberOfInstallments;
	}

	public void setNumberOfInstallments(Integer numberOfInstallments) {
		this.numberOfInstallments = numberOfInstallments;
	}
	
}
