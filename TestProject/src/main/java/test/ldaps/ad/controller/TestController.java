package test.ldaps.ad.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import test.ldaps.ad.service.LdapService;

@Controller
public class TestController {
	
	@Autowired
	private final LdapService ldapService;
	
	
    public TestController(LdapService ldapService) {
        this.ldapService = ldapService;
    }
    
    @GetMapping("/")
    public String testLoginComplete(String sId,Model model) {
    	if(sId == null) {
    		sId = "testuser";
    	}
    	
    	model.addAttribute("sId", sId);
    	return "TestLoginComplete";
    }
    
    @GetMapping("Test")
    public String testHome(String sId, Model model) {
    	model.addAttribute("sId", sId);
    	return "TestPwdChange";
    }
    
	@PostMapping(value = "requestTest")
	public String testRequest(@RequestParam Map<String, String> map, Model model) {
		System.out.println(map);
		String newPwd = map.get("pwd");
		String userId = map.get("id");
		System.out.println("userId = " + userId);
		System.out.println("newPwd = " + newPwd);
		boolean result = ldapService.modifyPassword(userId, newPwd);
		
	    if (!result) {
	        model.addAttribute("alertMessage", "비밀번호 변경에 실패했습니다.");
	        model.addAttribute("redirectUrl", "/ad"); // 메인 페이지 URL
	        return "redirectWithAlert";
	    } else {
	        model.addAttribute("alertMessage", "비밀번호 변경이 성공적으로 완료되었습니다.");
	        model.addAttribute("redirectUrl", "/ad"); // 메인 페이지 URL
	        return "redirectWithAlert";
	    }
		
	}
}
