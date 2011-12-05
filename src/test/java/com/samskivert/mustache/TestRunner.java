package com.samskivert.mustache;

import com.samskivert.mustache.MustacheTest;

public class TestRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MustacheTest test = new MustacheTest();
		System.out.println("running tests");
		test.testSimpleVariable();

//		test.testCallSiteChange();
//		test.testCompoundVariable();
//		test.testFieldVariable();
//		test.testEscapeHTML();
		test.testOneShotSection();
//		test.testCallSiteReuse();
		
	}

}
