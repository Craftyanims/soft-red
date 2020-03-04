package test.model;



import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

//import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;


class DataStoreTest {

	private model.DataStore buildDataStore() throws Exception {
		
		model.Researcher harvey = new model.Researcher("Harvey", "p");
		model.Paper harveysPaper = new model.Paper("Harvey likes catnip");
		harveysPaper.author = harvey;
		
		
		model.Researcher emily = new model.Researcher("Emily", "p");
		model.Paper emilysPaper = new model.Paper("Emily likes oranges");
		emilysPaper.author = emily;
		
		model.Journal petsJournal = new model.Journal("Pets");
		
		petsJournal.papers.add(harveysPaper);
		petsJournal.papers.add(emilysPaper);
		
		model.Editor squidward = new model.Editor("Squidward", "p");
		petsJournal.editor = squidward;
		
		
		model.University uni = new model.University("Generic Name");
		uni.addJournal(petsJournal);
		
		model.Administrator admin = new model.Administrator("super admin", "p");
		
		
		
		
		model.DataStore db = new model.DataStore();
		db.university = uni;
		
		return db;
	}
	
	@Test
	void smokeTest() throws Exception {
		model.DataStore db = buildDataStore();
		
		db.serialize();
		
		model.DataStore loadedDB = model.DataStore.load();
		
		
		model.Journal firstJournal = db.university.journals.get(0);
		model.Journal loadedFirstJournal = loadedDB.university.journals.get(0);
		
		model.Paper firstPaper = firstJournal.papers.get(0);
		model.Paper loadedFirstPaper = loadedFirstJournal.papers.get(0);
		
		model.Paper secondPaper = firstJournal.papers.get(1);
		model.Paper loadedSecondPaper = loadedFirstJournal.papers.get(1);
		
		assertTrue(firstPaper.name.equals("Harvey likes catnip"));
		assertTrue(loadedFirstPaper.name.equals("Harvey likes catnip"));
		
		assertTrue(secondPaper.name.equals("Emily likes oranges"));
		assertTrue(loadedSecondPaper.name.equals("Emily likes oranges"));	
	}
	
	@Test
	void backlinkTest() throws Exception {
		model.DataStore db = buildDataStore();
		
		db.serialize();
		
		model.DataStore loadedDB = model.DataStore.load();
		
		model.Journal firstJournal = db.university.journals.get(0);
		model.Journal loadedFirstJournal = loadedDB.university.journals.get(0);
		
		// Test links "up" the hierarchy, instead of just down it
		// This should also test for problems with serializing circular references
		assertTrue(firstJournal.owningUniversity == db.university);
		assertTrue(loadedFirstJournal.owningUniversity == loadedDB.university);
	}
	
	@Test
	void userLogin() throws Exception {
		String password = "a bad password";
		model.User user = new model.User("name", password);
		
		assertFalse(user.checkPassword("the wrong password"));
		assertTrue(user.checkPassword(password));
	}

	

}
