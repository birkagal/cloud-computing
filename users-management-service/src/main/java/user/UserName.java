package user;

public class UserName {
	private String first;
	private String last;

	public UserName() {
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	@Override
	public String toString() {
		return "UserName [first=" + first + ", last=" + last + "]";
	}
}
