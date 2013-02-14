package org.tp.soa.client;

import org.tp.soa.client.twitter.TwitterApi;

public class Main {
	
	/**
	 * Lancement du client
	 * @param args
	 */
	public static void main(String[] args) {
		TwitterApi twitter = new TwitterApi();
		System.out.println(twitter.getUserTimeline("1", "remixjobs"));
	}
}
