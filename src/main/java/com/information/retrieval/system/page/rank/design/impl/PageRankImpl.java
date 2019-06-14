package com.information.retrieval.system.page.rank.design.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.information.retrieval.system.page.rank.design.PageRank;
import com.information.retrieval.system.page.rank.domains.RankUrl;

/**
 * The Class PageRankImpl.
 */
public class PageRankImpl implements PageRank {

	/** The urls. */
	private ArrayList<String> urls;

	/* (non-Javadoc)
	 * @see com.information.retrieval.system.page.rank.design.PageRank#getPageRank(java.util.Set[], float, int)
	 */
	@Override
	public ArrayList<RankUrl> getPageRank(Set<Integer>[] graph, float dampeningFactor, int numberOfIterations)
			throws InterruptedException, MalformedURLException {
		int numberOfNodes = graph.length;
		float pageRank[] = new float[numberOfNodes];
		float pageRankNew[] = new float[numberOfNodes];
		float pageRankPrev[] = new float[numberOfNodes];

		for (int i = 0; i < numberOfNodes; i++) {
			pageRank[i] = 1 / (float) numberOfNodes;
		}


		int currentL1NormalizedIterationNumber = 1;
		if (numberOfIterations == -1) {
			while (currentL1NormalizedIterationNumber < 5) {
				if (checkIfNormalised(pageRank, pageRankPrev) < 0.001) {
					currentL1NormalizedIterationNumber++;
				}
				pageRankNew = calculatePageRank(graph, pageRank, dampeningFactor);
				for (int k = 0; k < numberOfNodes; k++) {
					pageRankPrev[k] = pageRank[k];
					pageRank[k] = pageRankNew[k];
				}
			}
		} else {
			for (int i = 0; i < numberOfIterations; i++) {
				pageRankNew = calculatePageRank(graph, pageRank, dampeningFactor);
				for (int k = 0; k < numberOfNodes; k++) {
					pageRankPrev[k] = pageRank[k];
					pageRank[k] = pageRankNew[k];
				}
			}
		}

		ArrayList<RankUrl> pageRankUrlList = new ArrayList<RankUrl>();

		for (int i = 0; i < numberOfNodes; i++) {
			pageRankUrlList.add(new RankUrl(pageRank[i], getDocIdFromUrl(urls.get(i))));
		}

		RankUrl temp = null;
		for (int i = 0; i < pageRankUrlList.size() - 1; i++) {
			for (int j = 0; j < pageRankUrlList.size() - i - 1; j++) {
				if (pageRankUrlList.get(j).getPageRank() < pageRankUrlList.get(j + 1).getPageRank()) {
					temp = pageRankUrlList.get(j);
					pageRankUrlList.set(j, pageRankUrlList.get(j + 1));
					pageRankUrlList.set(j + 1, temp);
				}
			}
		}

		return pageRankUrlList;
	}

	/**
	 * Calculate page rank.
	 *
	 * @param graph the graph
	 * @param pageRank the page rank
	 * @param dampeningFactor the dampening factor
	 * @return the float[]
	 * @throws InterruptedException the interrupted exception
	 */
	private float[] calculatePageRank(Set<Integer>[] graph, float[] pageRank, float dampeningFactor)
			throws InterruptedException {
		int numberOfNodes = graph.length;
		float pageRankNew[] = new float[numberOfNodes];
		int sinkNodes[] = getSinkNodes(graph, numberOfNodes);

		int outLinks[] = getOutLinks(graph, numberOfNodes);
		float sinkPR = 0.0f;

		sinkPR = 0;

		for (int i = 0; i < sinkNodes.length; i++) {
			if (sinkNodes[i] == 1)
				sinkPR += pageRank[i];
		}
		for (int i = 0; i < numberOfNodes; i++) {
			pageRankNew[i] = (float) (1 - dampeningFactor) / (float) numberOfNodes;
			pageRankNew[i] += dampeningFactor * sinkPR / (float) numberOfNodes;
			for (int j : graph[i]) {
				pageRankNew[i] += dampeningFactor * pageRank[j] / (float) outLinks[j];
			}
		}
		double sum = 0.0f;
		for(int i =0 ; i < pageRankNew.length ; i++) {
			sum += pageRankNew[i];
		}
		System.out.println("Page Rank New Sum:" + sum);
		return pageRankNew;
	}

	/**
	 * Check if normalised.
	 *
	 * @param pageRank the page rank
	 * @param pageRankNew the page rank new
	 * @return the float
	 */
	private float checkIfNormalised(float[] pageRank, float[] pageRankNew) {
		float l1Norm = 0.0f;
		for (int i = 0; i < pageRank.length; i++) {
			l1Norm += Math.abs(pageRank[i] - pageRankNew[i]);
		}
		System.out.println("L1 norm : " + l1Norm);
		return l1Norm;
	}

	/**
	 * Gets the out links.
	 *
	 * @param graph the graph
	 * @param numberOfNodes the number of nodes
	 * @return the out links
	 */
	private int[] getOutLinks(Set<Integer>[] graph, int numberOfNodes) {
		int[] outlinks = new int[numberOfNodes];
		for (int i = 0; i < numberOfNodes; i++) {
			for (int j = 0; j < numberOfNodes; j++) {
				if (graph[j].contains(i)) {
					outlinks[i] = outlinks[i] + 1;
				}
			}

		}
		
		return outlinks;
	}

	/* (non-Javadoc)
	 * @see com.information.retrieval.system.page.rank.design.PageRank#getSinkNodes(java.util.Set[], int)
	 */
	public int[] getSinkNodes(Set<Integer>[] graph, int numberOfNodes) {
		int sinkNodes[] = new int[numberOfNodes];
		for (int i = 0; i < numberOfNodes; i++) {
			sinkNodes[i] = 1;
		}

		// find sink nodes
		for (int i = 0; i < numberOfNodes; i++) {
			for (int j = 0; j < numberOfNodes; j++) {
				if (graph[j].contains(i)) {
					sinkNodes[i] = 0;
				}
			}
		}

		return sinkNodes;

	}

	/* (non-Javadoc)
	 * @see com.information.retrieval.system.page.rank.design.PageRank#getWebGraphFromUrls(java.lang.String, java.lang.String)
	 */
	@Override
	public Set[] getWebGraphFromUrls(String fileName, String outputFileName) throws IOException {
		File file = new File(fileName);
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		String url;
		int numberOfNodes = 0;
		ArrayList<String> urls = new ArrayList<String>();
		while ((url = bufferedReader.readLine()) != null) {
			url = url.split(" ")[0];
			urls.add(url);

		}
		this.urls = urls;
		numberOfNodes = urls.size();

		Set<Integer>[] graph = new HashSet[numberOfNodes];
		for (int i = 0; i < numberOfNodes; i++) {
			graph[i] = new HashSet<Integer>();
		}
		int index = 0;

		for (String graphUrl : urls) {
			Document document = Jsoup.connect(graphUrl).get();
			Elements links = document.select("a[href]");

			for (Element link : links) {
				String linkInPage = link.attr("abs:href");
				if (urls.contains(linkInPage)) {
					index = urls.indexOf(linkInPage);
					if (!(linkInPage.equals(graphUrl))) {
						if(index == 0 && urls.indexOf(graphUrl) == 0) {
							System.out.println("In here");
						}
						graph[index].add(urls.indexOf(graphUrl));
						
					}
				}
			}
		}

		PrintWriter printWriter = new PrintWriter(new File(outputFileName));
		
		for( int s : graph[0])
		{
			if(s == 0)
			System.out.println(s);
		}
		for (int i = 0; i < graph.length; i++) {
			printWriter.print(getDocIdFromUrl(urls.get(i)) + " ");
			for (int j = 0; j < graph[i].size(); j++) {
				printWriter.print(getDocIdFromUrl(urls.get(j)) + " ");
			}
			printWriter.println();
		}
		printWriter.flush();
		printWriter.close();
		return graph;
	}

	/**
	 * Gets the doc id from url.
	 *
	 * @param url the url
	 * @return the doc id from url
	 * @throws MalformedURLException the malformed URL exception
	 */
	private String getDocIdFromUrl(String url) throws MalformedURLException {
		URL urlFromString = new URL(url);
		return url.replace("https://en.wikipedia.org/wiki/", "");
	}

	/* (non-Javadoc)
	 * @see com.information.retrieval.system.page.rank.design.PageRank#sortByRawInlinksCount(java.util.Set[])
	 */
	@Override
	public ArrayList<RankUrl> sortByRawInlinksCount(Set[] G1) throws MalformedURLException {
		ArrayList<RankUrl> rankUrl = new ArrayList<RankUrl>();
		int i = 0;
		for(Set graphRow : G1) {
			rankUrl.add(new RankUrl(graphRow.size(), getDocIdFromUrl(urls.get(i++))));
		}
		return rankUrl;
	}

	/* (non-Javadoc)
	 * @see com.information.retrieval.system.page.rank.design.PageRank#getMaxInDegree(java.util.Set[])
	 */
	@Override
	public String getMaxInDegree(Set[] g1) {
		int max = 0;
		for(Set set : g1) {
			if(set.size() > max) {
				max = set.size();
			}
		}
		return Integer.toString(max);
	}

	/* (non-Javadoc)
	 * @see com.information.retrieval.system.page.rank.design.PageRank#getMaxOutDegree(java.util.Set[])
	 */
	@Override
	public String getMaxOutDegree(Set[] g1) {
		int max = 0;
		int[] count = new int[g1.length];
		for(int i = 0; i< g1.length ; i++) {
			
			count [i] = countOccurences(g1 , i);
		}
		for(int i =0;i< g1.length; i++) {
			if(count[i] > max) {
				max = count[i];
			}
		}
		return Integer.toString(max);
	}

	/**
	 * Count occurences.
	 *
	 * @param g1 the g 1
	 * @param i the i
	 * @return the int
	 */
	private int countOccurences(Set[] g1, int i) {
		int count = 0;
		for (Set g: g1) {
			if(g.contains(i)) {		
				count++;
			}
		}
		return count;
	}

	/* (non-Javadoc)
	 * @see com.information.retrieval.system.page.rank.design.PageRank#getSources(java.util.Set[])
	 */
	@Override
	public String getSources(Set[] g1) {
		int count = 0;
		for(Set g : g1) {
			if(g.isEmpty()) {
				count++;
			}
		}
		return Integer.toString(count);
	}

	

}
