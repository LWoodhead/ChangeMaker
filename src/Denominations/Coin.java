package Denominations;

import java.math.BigDecimal;

public class Coin extends Denomination{
	//Constructors
	public Coin(BigDecimal value, String name, String plural) {
		super(value, name, plural);
	}
	
	public Coin(Float value, String name, String plural) {
		super(BigDecimal.valueOf(value), name, plural);
	}
}
