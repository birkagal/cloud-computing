package user;

import exception.InvalidInputException;

public class UserName {

	private String first;
	private String last;

	public UserName() {
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		if (first.isEmpty())
			throw new InvalidInputException("First name cannot be empty.");

		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		if (last.isEmpty())
			throw new InvalidInputException("Last name cannot be empty.");

		this.last = last;
	}

	@Override
	public String toString() {
		return "UserName [first=" + first + ", last=" + last + "]";
	}
}
