package com.mycom.webcrawler;

import static org.junit.Assert.*;

import org.junit.Test;

public class LaucherTest {

	@Test
	public void testStartWith() {
		String s = "/abc/p1cd";
		boolean ok = s.startsWith("/abc/p[0-9]+");
		System.out.println(ok);
	}

}
