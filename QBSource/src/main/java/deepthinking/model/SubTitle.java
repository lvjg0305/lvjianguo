package deepthinking.model;

public class SubTitle {
	private String category;//所属分类
    private String content;
    private int index;
	
	public SubTitle() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SubTitle(String category, String content, int index) {
		this.category = category;
		this.content = content;
		this.index = index;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
    
    
}
