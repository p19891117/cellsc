package feiqq.bean;

public class Group {

	/** 群组Id */
	private String id;
	/** 群组名称 */
	private String name;
	
	public Group(String name) {
		this.name = name;
	}
	
	public Group(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
