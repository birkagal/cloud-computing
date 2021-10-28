package user;

public class KeyUserPairBoundary {
	private String key;
	private User value;

	public KeyUserPairBoundary() {
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public User getValue() {
		return value;
	}

	public void setValue(User value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "KeyUserPairBoundary [key=" + key + ", value=" + value + "]";
	}
}
