package com.firstApp.tamaroas.controller;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Controleur 
{
	@GetMapping("/")
	public String acceuil() {
		
		return "acceuil";
	}
	
	
	@PostMapping("/home")
	public String home(@RequestParam(required = true) String nom, ModelMap modelmap) {
		
		
		modelmap.put("name", nom);
		return "home";
	}
	

	


}
