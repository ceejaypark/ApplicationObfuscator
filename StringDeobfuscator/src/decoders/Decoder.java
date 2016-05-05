package decoders;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Decoder {

	public String Base64Decode(String input) {

		String decoded = "";
		if (input
				.matches("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$")) {
			decoded = new String(Base64.getDecoder().decode(input));
		}

		if (decoded.equals("")) {
			return null;
		}

		return decoded;
	}

	public String AESDecode(String input, String keyString) {
		try {
			SecretKeySpec key = new SecretKeySpec(Arrays.copyOf(MessageDigest
					.getInstance("SHA-1").digest(keyString.getBytes("UTF-8")),
					16), "AES");

			System.out.println(Base64.getEncoder().encodeToString(
					key.getEncoded()));

			Cipher instance = Cipher.getInstance("AES");
			instance.init(Cipher.DECRYPT_MODE, key);

			return new String(instance.doFinal(Base64Decode(input).getBytes()));
		} catch (Exception e) {

		}

		return null;
	}

	public String G3Decode(String input) {
		String key = "abcDDsAS";

		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < input.length(); i++) {
			stringBuilder.append((char) (input.charAt(i) ^ key.charAt(i
					% key.length())));
		}
		if(stringBuilder.toString().equals("")){
			return null;
		}
		
		return stringBuilder.toString();
	}
}