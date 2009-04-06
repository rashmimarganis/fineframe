package test;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.ws.security.WSPasswordCallback;

public class ClientPasswordCallback implements CallbackHandler {

	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {
		WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];

		int usage = pc.getUsage();

		System.out.println("identifier: " + pc.getIdentifer());
		System.out.println("usage: " + pc.getUsage());
		if (usage == WSPasswordCallback.USERNAME_TOKEN) {
			pc.setPassword("admin");

		} else if (usage == WSPasswordCallback.SIGNATURE) {
			// set the password for client's keystore.keyPassword
			pc.setPassword("admin");
		}
	}

}