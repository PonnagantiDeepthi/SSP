/**
 * 
 */
package ug.daes.onboarding.dto;

/**
 * @author Raxit Dubey
 *
 */
public class Token {
	
	private String token;

    public Token() { }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Token{" +
                "token='" + token + '\'' +
                '}';
    }

}
