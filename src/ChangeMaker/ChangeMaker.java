package ChangeMaker;

import Denominations.Bill;
import Denominations.Coin;
import Denominations.Denomination;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ChangeMaker {
	public static void main(String[] args) {
		runMenu();
	}
	
	/*
	 * Greedy Algorithm that subtracts the largest possible value each time to make change
	 * Takes in an arraylist of denominations and amount to make change for
	 * Returns an arraylist of ChangeContainers with a denomination and its count 
	 * Always works for basic us currency but not for some other examples
	 */
	public ArrayList<ChangeContainer> greedyChangeMaker(ArrayList<Denomination> allCurrency, BigDecimal amount){
		Collections.sort(allCurrency,Collections.reverseOrder());
		ArrayList<ChangeContainer> billsAndCoins = new ArrayList<>();
		for(Denomination d : allCurrency) {
			BigDecimal denominationCount = amount.divide(d.getValue(),MathContext.DECIMAL128).setScale(0,RoundingMode.FLOOR);
			switch(denominationCount.compareTo(BigDecimal.valueOf(1))) {
				case(1):
					billsAndCoins.add(new ChangeContainer(d,denominationCount));
					amount = amount.subtract(d.getValue().multiply(denominationCount));
					break;
				case(0):
					billsAndCoins.add(new ChangeContainer(d,denominationCount));
					amount = amount.subtract(d.getValue().multiply(denominationCount));
					break;
				case(-1):
					//Do Nothing 
					break;
			}
		}
		return billsAndCoins;
	}
	
	/*
	 * Brute Force Algorithm that makes change with each subset of denominations 
	 * Takes in an arraylist of denominations and amount to make change for 
	 * Returns an arraylist of ChangeContainers with a denomination and its count
	 * Works for any group of denominations
	 */
	public ArrayList<ChangeContainer> bruteForceChangeMaker(ArrayList<Denomination> allCurrency, BigDecimal amount){
		Collections.sort(allCurrency,Collections.reverseOrder());
		ArrayList<ChangeContainer> correctChange = null;
		int correctChangeCount = Integer.MAX_VALUE;
		for(int i = 0; i < allCurrency.size(); i++) {
			BigDecimal tempAmount = amount;
			ArrayList<ChangeContainer> tempChange = new ArrayList<>();
			for(int j = i; j < allCurrency.size(); j++) {
				Denomination d = allCurrency.get(j);
				BigDecimal denominationCount = tempAmount.divide(d.getValue(),MathContext.DECIMAL128).setScale(0,RoundingMode.FLOOR);
				switch(denominationCount.compareTo(BigDecimal.valueOf(1))) {
					case(1):
						tempChange.add(new ChangeContainer(d,denominationCount));
						tempAmount = tempAmount.subtract(d.getValue().multiply(denominationCount));
						break;
					case(0):
						tempChange.add(new ChangeContainer(d,denominationCount));
						tempAmount = tempAmount.subtract(d.getValue().multiply(denominationCount));
						break;
					case(-1):
						//Do Nothing 
						break;
				}
			}
			if(countDenominationAmount(tempChange) < correctChangeCount && tempAmount.intValue() == 0) {
				correctChangeCount = countDenominationAmount(tempChange);
				correctChange = tempChange;
			}
		}
		return correctChange;
	}
	
	/*
	 * Prints change given an arraylist of ChangeContainers
	 */
	public void printChange(ArrayList<ChangeContainer> correctChange) {
		System.out.println("Change is\n>----------------<");
		for(ChangeContainer c: correctChange) {
			switch(c.getCount().compareTo(BigDecimal.valueOf(1))) {
				case(1):
					System.out.println(c.getCount() + " " +  c.getDenomination().getPlural());
				break;
				case(0):
					System.out.println(c.getCount() + " " +  c.getDenomination().getName());
				break;
			}
		}
		System.out.println(">----------------<");
	}
	
	/*
	 * Returns an arraylist containing all common us denominations
	 */
	public ArrayList<Denomination> giveCommonUSADenominations(){
		ArrayList<Denomination> usaDenominations = new ArrayList<>();
		//Add all bills
		usaDenominations.add(new Bill(BigDecimal.valueOf(100),"100 Dollar Bill","100 Dollar Bills"));
		usaDenominations.add(new Bill(BigDecimal.valueOf(50) ,"50 Dollar Bill","50 Dollar Bills"));
		usaDenominations.add(new Bill(BigDecimal.valueOf(20) ,"20 Dollar Bill","20 Dollar Bills"));
		usaDenominations.add(new Bill(BigDecimal.valueOf(10) ,"10 Dollar Bill","10 Dollar Bills"));
		usaDenominations.add(new Bill(BigDecimal.valueOf(5) ,"5 Dollar Bill","5 Dollar Bills"));
		usaDenominations.add(new Bill(BigDecimal.valueOf(1) ,"1 Dollar Bill","1 Dollar Bills"));
		//Add all coins
		usaDenominations.add(new Coin(BigDecimal.valueOf(0.1),"Dime","Dimes"));
		usaDenominations.add(new Coin(BigDecimal.valueOf(0.05) ,"Nickel","Nickels"));
		usaDenominations.add(new Coin(BigDecimal.valueOf(0.25),"Quarter","Quarters"));
		usaDenominations.add(new Coin(BigDecimal.valueOf(0.01),"Penny","Pennies"));		
		return usaDenominations;
	}
	
	/*
	 * Runs interactive menu and takes console input
	 */
	private static void runMenu() {
		ChangeMaker changeMaker = new ChangeMaker();
		ArrayList<Denomination> denoms = changeMaker.giveCommonUSADenominations();
		Scanner inputScanner = new Scanner(System.in);
		while(true) {
			printMenu();
			int input = takeInput(1,2,inputScanner);
			switch(input) {
				case 1:
					inputScanner.close();
					return;
				case 2:
					System.out.println("Enter An Amount To Make Change For");
					BigDecimal amount = BigDecimal.valueOf(takeInput(Double.valueOf(0),Double.valueOf(1000),inputScanner)).setScale(2,RoundingMode.FLOOR);
					changeMaker.printChange(changeMaker.greedyChangeMaker(denoms,amount));
			}
		}
	}
	
	/*
	 * Method to add a bill or coin to an arrayList of denominations
	 * type is a reference to bill or coin where type = 0 is coin and type = 1 is bill 
	 */
	public void addDenomination(ArrayList<Denomination> denominations, Denomination denomination, int type) {
		switch(type) {
			//add coin
			case(0):
				denominations.add(denomination);
			break;
			//add bill
			case(1):
				denominations.add(denomination);
			break;
		}
	}
	
	/*
	 * Take an integer input
	 * Input must fall between min and max inclusive
	 */
	private static int takeInput(int min, int max, Scanner inputScanner) {
		int input = 0;
		while(true) {
			try {
				input = Integer.parseInt(inputScanner.nextLine());
				if(input >= min && input <= max) {
					break;
				}else {
					System.out.println("Enter " + min + "-" + max);
				}
			}catch(NumberFormatException e){
				System.out.println("Please Enter a Number");
			}
		}
		return input;
	}
	
	/*
	 * Take a double input
	 * Input must fall between min and max inclusive
	 */
	private static Double takeInput(Double min, Double max,Scanner inputScanner) {
		Double input = Double.valueOf(0);
		while(true) {
			try {
				input = Double.parseDouble(inputScanner.nextLine());
				if(input >= min && input <= max) {
					break;
				}else {
					System.out.println("Enter " + min + "-" + max);
				}
			}catch(NumberFormatException e){
				System.out.println("Please Enter a Number");
			}
		}
		return input;
	}
	
	/*
	 * Print base menu
	 */
	private static void printMenu() {
		System.out.println("1. Quit\n2. Enter A Number To Make Change For");
	}
	
	/*
	 * returns the number of individual currency object total in a a given arraylist of change containers
	 */
	public int countDenominationAmount(ArrayList<ChangeContainer> changeList) {
		int count = 0;
		for(ChangeContainer c: changeList) {
			count+= c.getCount().intValue();
		}
		return count;
	}
}
