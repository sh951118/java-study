package prob3;

public class Duck extends Bird{

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	protected void fly() {
		System.out.println("오리(" + name + ")는 날지 않습니다.");
	}

	@Override
	protected void sing() {
		System.out.println("오리(" + name + ")가 소리내어 웁니다.");
	}
	
	public String toString() {
		return "오리의 이름은 " + getName() + " 입니다.";
	}
	
}
