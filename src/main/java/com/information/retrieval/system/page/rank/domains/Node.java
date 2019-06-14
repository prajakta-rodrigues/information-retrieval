package com.information.retrieval.system.page.rank.domains;


/**
 * The Class Node.
 */
public class Node {
	
	/** The id. */
	private final int id;
	
	/** The url. */
	private final String url;
	
	/** The doc id. */
	private final String docId;

	/**
	 * Instantiates a new node.
	 *
	 * @param id the id
	 * @param url the url
	 * @param docId the doc id
	 */
	public Node(int id, String url, String docId) {
		super();
		this.id = id;
		this.url = url;
		this.docId = docId;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Gets the doc id.
	 *
	 * @return the doc id
	 */
	public String getDocId() {
		return docId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (object instanceof Node)
			return this.id == ((Node) object).id;
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return id;
	}

}
