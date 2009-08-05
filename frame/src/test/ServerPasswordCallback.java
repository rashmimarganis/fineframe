package test;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.ws.security.WSPasswordCallback;

public class ServerPasswordCallback implements CallbackHandler {

	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {
		
		WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
		System.out.println("User:"+pc.getIdentifer()+" Password:"+pc.getPassword());
		if (pc.getIdentifer().equals("admin")) {
			if (!pc.getPassword().equals("admin")) {
				throw new SecurityException("wrong password");
			}
		} else {
			throw new SecurityException("wrong username");
		}
	}

}