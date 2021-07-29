package Denominations;

import java.math.BigDecimal;

public abstract class Denomination implements Comparable<Denomination>{
	//Instance Variables
	private BigDecimal value;
	private String name;
	private String plural;
	
	//Getters and Setters
	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlural() {
		return plural;
	}

	public void setPlural(String plural) {
		this.plural = plural;
	}
	
	//Constructors
	public Denomination(BigDecimal value, String name,String plural) {
		this.value = value;
		this.name = name;
		this.plural = plural;
	}

	@Override
	public int compareTo(Denomination o) {
		return this.getValue().compareTo(o.getValue());
	}

	@Override
	public String toString() {
		return "Denomination [value=" + value + ", name=" + name + ", plural=" + plural + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((plural == null) ? 0 : plural.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Denomination other = (Denomination) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (plural == null) {
			if (other.plural != null)
				return false;
		} else if (!plural.equals(other.plural))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	
}
