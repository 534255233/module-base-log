package com.zlp.test;

import org.junit.Test;

import com.zlp.log.LoggerFactory;

import junit.framework.TestCase;

public class TestLog extends TestCase {

	@Test
	public void test1() {
		System.out.println(123);
		LoggerFactory.getLogger(this.getClass());
		System.out.println(456);
	}
	
	
}
