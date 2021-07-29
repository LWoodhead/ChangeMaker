package Denominations;

import java.math.BigDecimal;

public class Bill extends Denomination{
	//Constructors
	public Bill(BigDecimal value, String name,String plural) {
		super(value,name,plural);
	}
	
	public Bill(Float value, String name, String plural) {
		super(BigDecimal.valueOf(value), name, plural);
	}
}
