package com.company.conference.test;

import org.junit.Test;

import com.company.conference.InputFileParser;
import com.company.conference.Talk;
import com.company.conference.TalkException;

import org.junit.Ignore;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class TestInputParser {

	@Test
	public void testFileContentReader() throws FileNotFoundException {
		InputFileParser parser = new InputFileParser(
				"E:\\Gaurav\\programming\\workspace\\conference\\src\\com\\thoughtworks\\conference\\test\\testFileContents.txt");
		ArrayList<String> actual = parser.getFileContentLines();
		ArrayList<String> expected = new ArrayList<String>();

		expected.add("Writing Fast Tests Against Enterprise Rails 60min");
		expected.add("Overdoing it in Python 45min");

		boolean bothSame = actual.containsAll(expected);

		if (!bothSame) {
			assertFalse(true);
		}
	}

	@Test
	public void testTalkListPopulation() throws TalkException, FileNotFoundException {
		InputFileParser parser = new InputFileParser(
				"E:\\Gaurav\\programming\\workspace\\conference\\src\\com\\thoughtworks\\conference\\test\\testFileContents.txt");
		ArrayList<String> al = parser.getFileContentLines();
		ArrayList<Talk> actual = parser.getTalkList(al);

		Talk rails = new Talk("Writing Fast Tests Against Enterprise Rails", 60);
		Talk python = new Talk("Overdoing it in Python", 45);
		ArrayList<Talk> expected = new ArrayList<Talk>();
		expected.add(rails);
		expected.add(python);

		boolean bothSame = actual.containsAll(expected);

		if (!bothSame) {
			assertFalse(true);
		}
	}

	@Test
	public void testValidateTalkForTalkName() throws TalkException, FileNotFoundException {
		InputFileParser parser = new InputFileParser("E:\\Gaurav\\programming\\workspace\\conference\\src\\"
				+ "com\\thoughtworks\\conference\\test\\testFileContents2.txt");
		ArrayList<String> al = parser.getFileContentLines();
		boolean testSucess = false;
		try {
			ArrayList<Talk> actual = parser.getTalkList(al);
		} catch (TalkException e) {
			System.out.println(e);
			testSucess = true;
		}

		if (!testSucess) {
			assertFalse(true);
		}
	}

	@Test
	public void testValidateTalkForDuration() throws TalkException, FileNotFoundException {
		InputFileParser parser = new InputFileParser("E:\\Gaurav\\programming\\workspace\\conference\\src\\"
				+ "com\\thoughtworks\\conference\\test\\testFileContents3.txt");
		ArrayList<String> al = parser.getFileContentLines();
		boolean testSucess = false;
		try {
			ArrayList<Talk> actual = parser.getTalkList(al);
		} catch (TalkException e) {
			System.out.println(e);
			testSucess = true;
		}

		if (!testSucess) {
			assertFalse(true);
		}
	}
	
	@Test
	public void testValidateTalkForTerminator() throws TalkException, FileNotFoundException {
		InputFileParser parser = new InputFileParser("E:\\Gaurav\\programming\\workspace\\conference\\src\\"
				+ "com\\thoughtworks\\conference\\test\\testFileContents4.txt");
		ArrayList<String> al = parser.getFileContentLines();
		boolean testSucess = false;
		try {
			ArrayList<Talk> actual = parser.getTalkList(al);
		} catch (TalkException e) {
			System.out.println(e);
			testSucess = true;
		}

		if (!testSucess) {
			assertFalse(true);
		}
	}
	
	@Test
	public void testValidateTalkForEmptyFile() throws TalkException, FileNotFoundException {
		InputFileParser parser = new InputFileParser("E:\\Gaurav\\programming\\workspace\\conference\\src\\"
				+ "com\\thoughtworks\\conference\\test\\testFileContents5.txt");
		ArrayList<String> al = parser.getFileContentLines();
		boolean testSucess = false;
		try {
			ArrayList<Talk> actual = parser.getTalkList(al);
		} catch (TalkException e) {
			System.out.println(e);
			testSucess = true;
		}

		if (!testSucess) {
			assertFalse(true);
		}
	}
	

	@Test
	public void testFileNotFound() throws TalkException, FileNotFoundException {
		InputFileParser parser = new InputFileParser("E:\\Gaurav\\programming\\workspace\\conference\\src\\"
				+ "com\\thoughtworks\\conference\\test\\asdasdasd.txt");
		
		boolean testSucess = false;
		try {
			ArrayList<String> al = parser.getFileContentLines();
			ArrayList<Talk> actual = parser.getTalkList(al);
		} catch (FileNotFoundException e) {
			System.out.println(e);
			testSucess = true;
		}

		if (!testSucess) {
			assertFalse(true);
		}
	}
}
