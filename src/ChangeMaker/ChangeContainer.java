package ChangeMaker;

import java.math.BigDecimal;

import Denominations.Denomination;

public class ChangeContainer {
	//Instance Variables
	private Denomination denomination;
	private BigDecimal count;
	//Constructor
	public ChangeContainer(Denomination denomination, BigDecimal count) {
		this.denomination = denomination;
		this.count = count;
	}
	//Getters and Setters
	public Denomination getDenomination() {
		return denomination;
	}
	public void setDenomination(Denomination denomination) {
		this.denomination = denomination;
	}
	public BigDecimal getCount() {
		return count;
	}
	public void setCount(BigDecimal count) {
		this.count = count;
	}
	//ToString
	@Override
	public String toString() {
		return "ChangeContainer [denomination=" + denomination + ", count=" + count + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((count == null) ? 0 : count.hashCode());
		result = prime * result + ((denomination == null) ? 0 : denomination.hashCode());
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
		ChangeContainer other = (ChangeContainer) obj;
		if (count == null) {
			if (other.count != null)
				return false;
		} else if (!count.equals(other.count))
			return false;
		if (denomination == null) {
			if (other.denomination != null)
				return false;
		} else if (!denomination.equals(other.denomination))
			return false;
		return true;
	}
	
}
