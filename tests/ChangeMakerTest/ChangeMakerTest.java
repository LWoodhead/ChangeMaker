package ChangeMakerTest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.Test;

import ChangeMaker.ChangeContainer;
import ChangeMaker.ChangeMaker;
import Denominations.Bill;
import Denominations.Coin;
import Denominations.Denomination;

public class ChangeMakerTest {
	
	ChangeMaker testChangeMaker = new ChangeMaker();
	//Create denomination objects
	private Coin penny = new Coin(BigDecimal.valueOf(0.01),"Penny","Pennies");
	private Coin nickel = new Coin(BigDecimal.valueOf(0.05) ,"Nickel","Nickels");
	private Coin dime = new Coin(BigDecimal.valueOf(0.1),"Dime","Dimes");
	private Coin quarter = new Coin(BigDecimal.valueOf(0.25),"Quarter","Quarters");
	private Bill dollar1 = new Bill(BigDecimal.valueOf(1) ,"1 Dollar Bill","1 Dollar Bills");
	private Bill dollar4 = new Bill(BigDecimal.valueOf(4) ,"4 Dollar Bill","4 Dollar Bills");
	private Bill dollar7 = new Bill(BigDecimal.valueOf(7) ,"7 Dollar Bill","7 Dollar Bills");
	private Bill dollar5 = new Bill(BigDecimal.valueOf(5) ,"5 Dollar Bill","5 Dollar Bills");
	private Bill dollar10 = new Bill(BigDecimal.valueOf(10) ,"10 Dollar Bill","10 Dollar Bills");
	private Bill dollar20 = new Bill(BigDecimal.valueOf(20) ,"20 Dollar Bill","20 Dollar Bills");
	private Bill dollar50 = new Bill(BigDecimal.valueOf(50) ,"50 Dollar Bill","50 Dollar Bills");
	private Bill dollar100 = new Bill(BigDecimal.valueOf(100),"100 Dollar Bill","100 Dollar Bills");
	
	@Test
	public void AddDenominationTest() {
		ArrayList<Denomination> testDenoms = new ArrayList<>();
		//Check that list is empty
		assertNotEquals(testDenoms.contains(quarter),true);
		testChangeMaker.addDenomination(testDenoms,quarter,1);
		//Check that list contains 1 quarter
		assertEquals(testDenoms.contains(quarter),true);
		testChangeMaker.addDenomination(testDenoms,nickel,2);
		//Assert that a type other than 1 or 2 adds nothing
		assertNotEquals(testDenoms.contains(nickel),true);
		testChangeMaker.addDenomination(testDenoms, dollar1, 0);
		//Test adding a bill
		assertEquals(testDenoms.contains(dollar1),true);
	}
	
	@Test
	public void bruteForceChangerMakerTest(){
		ArrayList<Denomination> UScurrency = new ArrayList<>();
		//Add all us currency
		UScurrency.add(penny);
		UScurrency.add(dime);
		UScurrency.add(quarter);
		UScurrency.add(nickel);
		UScurrency.add(dollar1);
		UScurrency.add(dollar5);
		UScurrency.add(dollar10);
		UScurrency.add(dollar20);
		UScurrency.add(dollar50);
		UScurrency.add(dollar100);
		//Create correct change for 327.82 should be 3 100s + 1 20s + 1 5s + 2 1s + 3 quarters + 1 nickel + 2 pennies 
		ArrayList<ChangeContainer> correctChange = new ArrayList<>();
		correctChange.add(new ChangeContainer(dollar100,BigDecimal.valueOf(3)));
		correctChange.add(new ChangeContainer(dollar20,BigDecimal.valueOf(1)));
		correctChange.add(new ChangeContainer(dollar5,BigDecimal.valueOf(1)));
		correctChange.add(new ChangeContainer(dollar1,BigDecimal.valueOf(2)));
		correctChange.add(new ChangeContainer(quarter,BigDecimal.valueOf(3)));
		correctChange.add(new ChangeContainer(nickel,BigDecimal.valueOf(1)));
		correctChange.add(new ChangeContainer(penny,BigDecimal.valueOf(2)));
		//Test correct change for 327.82
		assertArrayEquals(correctChange.toArray(),testChangeMaker.bruteForceChangeMaker(UScurrency, BigDecimal.valueOf(327.82)).toArray());
		ArrayList<Denomination> nonGreedyCurrency = new ArrayList<>();
		nonGreedyCurrency.add(dollar1);
		nonGreedyCurrency.add(dollar4);
		nonGreedyCurrency.add(dollar5);
		nonGreedyCurrency.add(dollar7);
		correctChange.clear();
		//Add correct change for 9 dollars using 7,5,4,1 which is 1 4s and 1 5s
		correctChange.add(new ChangeContainer(dollar5,BigDecimal.valueOf(1)));
		correctChange.add(new ChangeContainer(dollar4,BigDecimal.valueOf(1)));
		//Test correct change for 9 with alternate denominations
		assertArrayEquals(correctChange.toArray(),testChangeMaker.bruteForceChangeMaker(nonGreedyCurrency, BigDecimal.valueOf(9)).toArray());
	}
	
	@Test
	public void greedyChangeMakerTest() {
		ArrayList<Denomination> UScurrency = new ArrayList<>();
		//Add all us currency
		UScurrency.add(penny);
		UScurrency.add(dime);
		UScurrency.add(quarter);
		UScurrency.add(nickel);
		UScurrency.add(dollar1);
		UScurrency.add(dollar5);
		UScurrency.add(dollar10);
		UScurrency.add(dollar20);
		UScurrency.add(dollar50);
		UScurrency.add(dollar100);
		//Create correct change for 327.82 should be 3 100s + 1 20s + 1 5s + 2 1s + 3 quarters + 1 nickel + 2 pennies 
		ArrayList<ChangeContainer> correctChange = new ArrayList<>();
		correctChange.add(new ChangeContainer(dollar100,BigDecimal.valueOf(3)));
		correctChange.add(new ChangeContainer(dollar20,BigDecimal.valueOf(1)));
		correctChange.add(new ChangeContainer(dollar5,BigDecimal.valueOf(1)));
		correctChange.add(new ChangeContainer(dollar1,BigDecimal.valueOf(2)));
		correctChange.add(new ChangeContainer(quarter,BigDecimal.valueOf(3)));
		correctChange.add(new ChangeContainer(nickel,BigDecimal.valueOf(1)));
		correctChange.add(new ChangeContainer(penny,BigDecimal.valueOf(2)));
		//Test correct change for 327.82
		assertArrayEquals(correctChange.toArray(),testChangeMaker.greedyChangeMaker(UScurrency, BigDecimal.valueOf(327.82)).toArray());
	}
	
	@Test
	public void countDenominationTest() {
		ArrayList<ChangeContainer> changeCount = new ArrayList<>();
		changeCount.add(new ChangeContainer(dollar1,BigDecimal.valueOf(2)));
		changeCount.add(new ChangeContainer(quarter,BigDecimal.valueOf(3)));
		changeCount.add(new ChangeContainer(nickel,BigDecimal.valueOf(1)));
		changeCount.add(new ChangeContainer(penny,BigDecimal.valueOf(2)));
		//Test the correct count is returned 
		assertEquals(testChangeMaker.countDenominationAmount(changeCount),8);
	}
}
