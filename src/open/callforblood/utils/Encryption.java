package open.callforblood.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {

	private static String passPhrase = "callforblood";
	private static Cipher aes;
	private static SecretKeySpec key;
	private static byte[] ciphertext;

	public Encryption() {
		setKey();
	}

	public String getPassPhrase() {
		return passPhrase;
	}

	public static String AESEncryption(String text) {
		setKey();
		try {
			aes.init(Cipher.ENCRYPT_MODE, key);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		ciphertext = null;

		try {
			ciphertext = aes.doFinal(text.getBytes());
		} catch (IllegalBlockSizeException e) {

		} catch (BadPaddingException e) {

		}

		return new String(ciphertext);
	}

	public static String AESDecryption(byte[] ciphertext) {
		try {
			aes.init(Cipher.DECRYPT_MODE, key);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		String cleartext = null;

		try {
			cleartext = new String(aes.doFinal(ciphertext));
		} catch (IllegalBlockSizeException e) {

		} catch (BadPaddingException e) {

		}

		return cleartext;
	}

	private static void setKey() {

		try {
			aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
		} catch (NoSuchAlgorithmException e1) {

		} catch (NoSuchPaddingException e1) {

		}

		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
		}
		digest.update(passPhrase.getBytes());
		key = new SecretKeySpec(digest.digest(), 0, 16, "AES");
	}

	public static String md5Encryption(String message) {
		String digest = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hash = md.digest(message.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder(2 * hash.length);
			for (byte b : hash) {
				sb.append(String.format("%02x", b & 0xff));
			}
			digest = sb.toString();
		} catch (UnsupportedEncodingException ex) {

		} catch (NoSuchAlgorithmException ex) {

		}
		return digest;
	}

}
