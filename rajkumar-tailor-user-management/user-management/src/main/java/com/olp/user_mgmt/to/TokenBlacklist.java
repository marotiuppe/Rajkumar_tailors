package com.olp.user_mgmt.to;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TokenBlacklist {
	private static final int MAX_TOKENS = 100;
	private static final Set<String> revokedTokens = new LinkedHashSet<>();
	private static final ConcurrentLinkedQueue<String> tokenQueue = new ConcurrentLinkedQueue<>();

	public static boolean isTokenRevoked(String token) {
		return revokedTokens.contains(token);
	}

	public static void revokeToken(String token) {
		if (revokedTokens.size() >= MAX_TOKENS) {
			String oldestToken = tokenQueue.poll();
			revokedTokens.remove(oldestToken);
		}

		revokedTokens.add(token);
		tokenQueue.offer(token);
	}
}
