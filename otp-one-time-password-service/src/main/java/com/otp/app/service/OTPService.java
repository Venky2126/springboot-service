package com.otp.app.service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * This class is used to provide service for OTP generation and validation In
 * this class we will the OTP Expire time to 4 minutes.
 */
@Service
public class OTPService {

	private static final int EXPIRATION = 4;
	private LoadingCache<String, Integer> otpCache;
	private static final Random RANDOM = new Random(); // Static and final


	public OTPService() {
		super();
		otpCache = CacheBuilder.newBuilder().expireAfterWrite(EXPIRATION, TimeUnit.MINUTES)
				.build(new CacheLoader<String, Integer>() {
					public Integer load(String key) {
						return 0;
					}
				});
	}

	/**
	 * This method is used to generate the 6 digit OTP number.
	 * 
	 * @return
	 */

	public int generateOTP(String key) {
		int otp = 100000 + RANDOM.nextInt(900000);
		otpCache.put(key, otp);
		return otp;
	}

	/**
	 * This method is used to return the OTP number against Key->Key values is
	 * email.
	 * 
	 * @return
	 */
	public int getOtp(String key) {
		try {
			return otpCache.get(key);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * This method is used to clear the OTP catched already.
	 */

	public void clearOTP(String key) {
		otpCache.invalidate(key);
	}

}
