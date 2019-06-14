package com.information.retrieval.system.page.rank.design;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;
import com.information.retrieval.system.page.rank.domains.RankUrl;


/**
 * The Interface PageRank.
 */
public interface PageRank {


/**
 * Gets the page rank.
 *
 * @param graph the graph
 * @param dampeningFactor the dampening factor
 * @param numberOfIterations the number of iterations
 * @return the page rank
 * @throws InterruptedException the interrupted exception
 * @throws MalformedURLException the malformed URL exception
 */
public List<RankUrl> getPageRank(Set<Integer>[] graph, float dampeningFactor, int numberOfIterations) throws InterruptedException, MalformedURLException;

/**
 * Gets the web graph from urls.
 *
 * @param fileName the file name
 * @param outputFileName the output file name
 * @return the web graph from urls
 * @throws FileNotFoundException the file not found exception
 * @throws IOException Signals that an I/O exception has occurred.
 */
public Set[] getWebGraphFromUrls(String fileName, String outputFileName) throws FileNotFoundException, IOException;

/**
 * Sort by raw inlinks count.
 *
 * @param g1 the g 1
 * @return the array list
 * @throws MalformedURLException the malformed URL exception
 */
public List<RankUrl> sortByRawInlinksCount(Set[] g1) throws MalformedURLException;

/**
 * Gets the sink nodes.
 *
 * @param graph the graph
 * @param numberOfNodes the number of nodes
 * @return the sink nodes
 */
public int[] getSinkNodes(Set<Integer>[] graph, int numberOfNodes);

/**
 * Gets the max in degree.
 *
 * @param g1 the g 1
 * @return the max in degree
 */
public String getMaxInDegree(Set[] g1);

/**
 * Gets the max out degree.
 *
 * @param g1 the g 1
 * @return the max out degree
 */
public String getMaxOutDegree(Set[] g1);

/**
 * Gets the sources.
 *
 * @param g1 the g 1
 * @return the sources
 */
public String getSources(Set[] g1);
}
