
package com.goldenhandshake.demo;

import com.goldenhandshake.demo.entities.User;
import java.util.Arrays;
import java.util.Base64;
import java.util.LinkedHashMap;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author binod
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class ITUserController {
    
    private static final String BASE_URI="http://localhost:8080/";
    private static final String TOKEN_URI="oauth/token?grant_type=password&username=admin&password=admin";
    
     @Test
    public  void testIt(){
      User user=  getUSer(sendTokenRequest());
       assertThat(user.getUsername(),is(equalTo("admin")));
    }
    
    /**
     * prepare http headers
     * @return 
     */
    private  HttpHeaders getHeaders(){
        HttpHeaders headers=new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }
    private  HttpHeaders getHeadersWithClientCredentials(){
        String plainClientCredentials="my-trusted-client:secret";
        String base64ClientCredentials=Base64.getEncoder().encodeToString(plainClientCredentials.getBytes());
        HttpHeaders headers=getHeaders();
        headers.add("Authorization", "Basic "+base64ClientCredentials);
        return headers;
    }
    
    private  AuthTokenInfo sendTokenRequest(){
        AuthTokenInfo tokenInfo=null;
        RestTemplate restTemplate=new RestTemplate();
        HttpEntity<String> request =new HttpEntity<>(getHeadersWithClientCredentials());
        ResponseEntity<Object> response= restTemplate.exchange(BASE_URI+TOKEN_URI, HttpMethod.POST, request, Object.class);
        LinkedHashMap<String,Object> map = (LinkedHashMap<String,Object>) response.getBody();
    if(map!=null){
        tokenInfo=new AuthTokenInfo();
        tokenInfo.setAccessToken( map.get("access_token").toString());
        tokenInfo.setRefreshToken( map.get("refresh_token").toString());
        tokenInfo.setExpireIn((int) map.get("expires_in"));
        tokenInfo.setScope(map.get("scope").toString());
        tokenInfo.setTokenType(map.get("token_type").toString());
        
    }
    return tokenInfo;
    }
    
    private User getUSer(AuthTokenInfo tokenInfo){
         RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(getHeaders());
        ResponseEntity<User> response = restTemplate.exchange(BASE_URI+"user?access_token="+tokenInfo.getAccessToken(),
                HttpMethod.GET, request, User.class);
        User user = response.getBody();
        System.out.println("username= "+user.getUsername());
        return user;
    }
   
    
    
}
