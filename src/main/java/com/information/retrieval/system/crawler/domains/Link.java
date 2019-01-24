package com.information.retrieval.system.crawler.domains;


/**
 * The Class Link defines attributes like url, depth , a unique hash and parent depth of the url.
 */
public class Link {
	
	private final String url;
	
	private final int depth;
	
	/** The hash generated for url. */
	private final String hash;
	
	/** The parent depth of url. */
	private final int parentDepth;
	
	/**
	 * Instantiates a new link.
	 *
	 * @param url the url
	 * @param hash the hash of url
	 * @param depth the depth level of the url from seed url
	 * @param parentDepth the parent depth of the url
	 */
	public Link(String url, String hash, int depth, int parentDepth) {
		super();
		this.url = url;
		this.depth = depth;
		this.hash = hash;
		this.parentDepth = parentDepth;
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
	 * Gets the depth.
	 *
	 * @return the depth
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * Gets the hash.
	 *
	 * @return the hash
	 */
	public String getHash() {
		return hash;
	}

	/**
	 * Gets the parent depth.
	 *
	 * @return the parent depth
	 */
	public int getParentDepth() {
		return parentDepth;
	}
	
	
	

}
