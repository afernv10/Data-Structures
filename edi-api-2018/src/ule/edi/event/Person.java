package ule.edi.event;

public class Person {

	public String name;
	
	public int age;
	
	public String occupation;

	public Person(String name, int age, String occupation) {

		this.name = name;
		this.age = age;
		this.occupation = occupation;
	}

	public Person(String name, int age) {

		this.name = name;
		this.age = age;
		
		this.occupation = null;
	}

	@Override
	public String toString() {
		return "{'Name':'" + name + "', 'Age':" + age + ", 'Occupation':'" + (occupation == null ? "n/a" : occupation) + "'}";
	}
	
}
