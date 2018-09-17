package deepthinking.model;

public class SubTitle {
	private String category;//所属分类
    private String content;//二级标题
    private int index;//起始位置
    private String sourceText;//内容
	
	public SubTitle() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SubTitle(String category, String content, int index, String sourceText) {
		super();
		this.category = category;
		this.content = content;
		this.index = index;
		this.sourceText = sourceText;
	}

	public String getSourceText() {
		return sourceText;
	}

	public void setSourceText(String sourceText) {
		this.sourceText = sourceText;
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
