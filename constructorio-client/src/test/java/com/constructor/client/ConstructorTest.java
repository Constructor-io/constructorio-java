package com.constructor.client;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MyTests {

	// test encodes params
	// test creates urls correctly
	// test api token is set
	// test ac key is set
	// test ac query
	// test add
	// test add w/ params
	// test remove
	// test remove w/ params
	// test modify
	// test modify w/ params
	// test conversion
	// test conversion w/ params
	// test search
	// test search w/ params
	// test clickthrough
	// test clickthrough w/ params

  @Test
  public void multiplicationOfZeroIntegersShouldReturnZero() {

    // MyClass is tested
    MyClass tester = new MyClass();

    // assert statements
    assertEquals("10 x 0 must be 0", 0, tester.multiply(10, 0));
    assertEquals("0 x 10 must be 0", 0, tester.multiply(0, 10));
    assertEquals("0 x 0 must be 0", 0, tester.multiply(0, 0));
  }

} 


//import junit.framework.Test;
//import junit.framework.TestCase;
//import junit.framework.TestSuite;
//
///**
// * Unit test for simple App.
// */
//public class ConstructorTest 
//    extends TestCase
//{
//    /**
//     * Create the test case
//     *
//     * @param testName name of the test case
//     */
//    public ConstructorTest( String testName )
//    {
//        super( testName );
//    }
//
//    /**
//     * @return the suite of tests being tested
//     */
//    public static Test suite()
//    {
//        return new TestSuite( AppTest.class );
//    }
//
//    public void testApp()
//    {
//        assertTrue( true );
//    }
//}
