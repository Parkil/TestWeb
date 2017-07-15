package gen_template.tree;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * tree node
 */
public class Node implements Comparable<Node>,Serializable{

	private static final long serialVersionUID = -63390432191047878L;
	
	private String identifier; //식별자
	private String parent_identifier; //부모식별자
	private int level ; //level
	private ArrayList<String> children; //자식 노드
	private Object attach; //부가적으로 붙일수 있는 객체

	public Node(String identifier) {
		this.identifier = identifier;
		children = new ArrayList<String>();
	}
	
	public Node(String identifier, String parent_identifier) {
		this.identifier = identifier;
		this.parent_identifier = parent_identifier;
		children = new ArrayList<String>();
	}
	
	public Node(String identifier, String parent_identifier, int level) {
		this.identifier = identifier;
		this.parent_identifier = parent_identifier;
		this.level = level;
		children = new ArrayList<String>();
	}
	
	public Node(String identifier, String parent_identifier, int level, Object attach) {
		this.identifier = identifier;
		this.parent_identifier = parent_identifier;
		this.level = level;
		this.attach = attach;
		children = new ArrayList<String>();
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getParent_identifier() {
		return parent_identifier;
	}

	public void setParent_identifier(String parent_identifier) {
		this.parent_identifier = parent_identifier;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public ArrayList<String> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<String> children) {
		this.children = children;
	}

	public Object getAttach() {
		return attach;
	}

	public void setAttach(Object attach) {
		this.attach = attach;
	}
	
	// Public interface
	public void addChild(String identifier) {
		children.add(identifier);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * 첫번째로 level을 비교하고 level이 같으면 문자열을 비교하도록 처리
	 */
	@Override
	public int compareTo(Node other_node) {
		
		if(level != other_node.getLevel()) {
			return level - other_node.getLevel();
		}
		
		return identifier.compareTo(other_node.getIdentifier());
	}
}
