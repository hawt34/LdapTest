package test.ldaps.ad.service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.stereotype.Service;

@Service
public class LdapService {
	
	private final LdapTemplate ldapTemplate;

	
    public LdapService(LdapTemplate ldapTemplate) {
    	ldapTemplate.setIgnorePartialResultException(true);
        this.ldapTemplate = ldapTemplate;
    }
    
    public Object test222() {
        System.err.println("test222");
        try {
            return ldapTemplate.lookup("CN=testuser,OU=OU1");
        } catch (Exception e) {
            System.err.println("LDAP Lookup Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    
    public List<String> searchUsers() {
    	System.out.println("searchuser 들어옴111111111111");
    	LdapQuery query = LdapQueryBuilder.query()
                .where("objectclass").is("person"); // 사용자 객체 검색
    	System.out.println("query : " + query.toString());
    	ldapTemplate.setIgnorePartialResultException(true);
    	List<String> result = ldapTemplate.search(query, (AttributesMapper<String>) attributes -> 
        attributes.get("cn").get().toString());
    	
    	System.out.println("result : " + result);
        return result;// CN 속성 반환
    }
    
    
    public void changePassword(String userId, String newPassword) {
        // Base DN 설정
//        String baseDn = "DC=jh,DC=com";
        // 사용자 DN 생성
//        String userDn = String.format("CN=%s,OU=OU1,%s", userId, baseDn);
        String userDn = String.format("CN=" + userId + ",OU=OU1");

        // 비밀번호를 유니코드 형식으로 인코딩
        String unicodePwd = encodePassword(newPassword);

        // 비밀번호 변경 요청
        ModificationItem[] modificationItems = new ModificationItem[] {
            new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("unicodePwd", unicodePwd))
        };

        // LDAP 서버에 비밀번호 변경 요청 실행
        ldapTemplate.modifyAttributes(userDn, modificationItems);
    }
    

    public boolean modifyPassword(String userId, String newPassword) {
        System.out.println("User ID: " + userId);
        System.out.println("New Password: " + newPassword);
        
        String dn = getUserDistinguishedName(userId);
        System.out.println("dn값은 : " + dn);
        System.out.println("Trying to lookup DN: " + dn);
        
        try {
            DirContextOperations context = ldapTemplate.lookupContext(dn);
            System.out.println("Found context DN: " + context.getDn());
            
            String newQuotedPassword = "\"" + newPassword + "\"";
            byte[] newUnicodePassword = newQuotedPassword.getBytes(StandardCharsets.UTF_16LE);

            ModificationItem[] mods = new ModificationItem[1];
            mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, 
                                           new BasicAttribute("unicodePwd", newUnicodePassword));

            ldapTemplate.modifyAttributes(context.getDn(), mods);
            System.out.println("Password modified successfully for DN: " + dn);
            
            return true;
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    

	public String getUserDistinguishedName(String userId) {
	    LdapQuery query = LdapQueryBuilder.query()
	        .where("sAMAccountName").is(userId); // 사용자 ID로 필터링 (sAMAccountName은 사용자 ID를 의미)
	
	    System.out.println("query : " + query);
	
	    try {
	        List<String> results = ldapTemplate.search(query, (AttributesMapper<String>) attributes -> 
	            attributes.get("distinguishedName").get().toString());
	        
	        if (!results.isEmpty()) {
	            String fullDn = results.get(0);
	            return fullDn.replace(",DC=jh,DC=com", "");
	        } else {
	            System.err.println("사용자를 찾을 수 없습니다: " + userId);
	            return null;
	        }
	    } catch (Exception e) {
	        System.err.println("getUserDistinguishedName 예외 발생: " + e.getMessage());
	        return null; 
	    }
	}
    
    
    
    
    // AD용 비밀번호 유니코드 인코딩 메서드
    private String encodePassword(String password) {
        return "\"" + password + "\"";
    }
    
    
    
    
    
    
}
