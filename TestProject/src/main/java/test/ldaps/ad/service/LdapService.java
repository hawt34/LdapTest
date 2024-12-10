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
    	System.out.println("searchuser ����111111111111");
    	LdapQuery query = LdapQueryBuilder.query()
                .where("objectclass").is("person"); // ����� ��ü �˻�
    	System.out.println("query : " + query.toString());
    	ldapTemplate.setIgnorePartialResultException(true);
    	List<String> result = ldapTemplate.search(query, (AttributesMapper<String>) attributes -> 
        attributes.get("cn").get().toString());
    	
    	System.out.println("result : " + result);
        return result;// CN �Ӽ� ��ȯ
    }
    
    
    public void changePassword(String userId, String newPassword) {
        // Base DN ����
//        String baseDn = "DC=jh,DC=com";
        // ����� DN ����
//        String userDn = String.format("CN=%s,OU=OU1,%s", userId, baseDn);
        String userDn = String.format("CN=" + userId + ",OU=OU1");

        // ��й�ȣ�� �����ڵ� �������� ���ڵ�
        String unicodePwd = encodePassword(newPassword);

        // ��й�ȣ ���� ��û
        ModificationItem[] modificationItems = new ModificationItem[] {
            new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("unicodePwd", unicodePwd))
        };

        // LDAP ������ ��й�ȣ ���� ��û ����
        ldapTemplate.modifyAttributes(userDn, modificationItems);
    }
    

    public boolean modifyPassword(String userId, String newPassword) {
        System.out.println("User ID: " + userId);
        System.out.println("New Password: " + newPassword);
        
        String dn = getUserDistinguishedName(userId);
        System.out.println("dn���� : " + dn);
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
	        .where("sAMAccountName").is(userId); // ����� ID�� ���͸� (sAMAccountName�� ����� ID�� �ǹ�)
	
	    System.out.println("query : " + query);
	
	    try {
	        List<String> results = ldapTemplate.search(query, (AttributesMapper<String>) attributes -> 
	            attributes.get("distinguishedName").get().toString());
	        
	        if (!results.isEmpty()) {
	            String fullDn = results.get(0);
	            return fullDn.replace(",DC=jh,DC=com", "");
	        } else {
	            System.err.println("����ڸ� ã�� �� �����ϴ�: " + userId);
	            return null;
	        }
	    } catch (Exception e) {
	        System.err.println("getUserDistinguishedName ���� �߻�: " + e.getMessage());
	        return null; 
	    }
	}
    
    
    
    
    // AD�� ��й�ȣ �����ڵ� ���ڵ� �޼���
    private String encodePassword(String password) {
        return "\"" + password + "\"";
    }
    
    
    
    
    
    
}
