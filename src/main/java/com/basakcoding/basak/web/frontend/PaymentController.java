package com.basakcoding.basak.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
}
