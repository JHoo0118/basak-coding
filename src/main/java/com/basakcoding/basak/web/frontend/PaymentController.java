package com.basakcoding.basak.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentController {

	@GetMapping("/orders/payments/{course_id}")
	public String payment() {
		return "frontend/payment";
	}
	
	@GetMapping("/orders/complete")
	public String paymentResult() {
		return "frontend/paymentConfirm";
	}
	
	@GetMapping("/orders/payment")
	public String paymentP() {
		return "frontend/payment";
	}
	
	
}
