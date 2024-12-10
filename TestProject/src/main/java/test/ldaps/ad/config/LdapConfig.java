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
        contextSource.setBase("DC=jh,DC=com"); // BaseDN 지정
        contextSource.setUserDn("administrator@jh.com"); // 관리자 계정
        contextSource.setPassword("tlfksndl!@!@3232"); // 관리자 비밀번호
        contextSource.setPooled(true); // 연결 풀 활성화
        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(contextSource());
    }
}