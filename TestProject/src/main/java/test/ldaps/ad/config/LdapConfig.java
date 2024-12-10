package test.ldaps.ad.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;


@Configuration
public class LdapConfig {

	
    
    @Bean
    public LdapContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl("ldaps://adpc1.jh.com:636"); // LDAPS URL
        contextSource.setBase("DC=jh,DC=com"); // BaseDN ����
        contextSource.setUserDn("administrator@jh.com"); // ������ ����
        contextSource.setPassword("tlfksndl!@!@3232"); // ������ ��й�ȣ
        contextSource.setPooled(true); // ���� Ǯ Ȱ��ȭ
        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(contextSource());
    }
}