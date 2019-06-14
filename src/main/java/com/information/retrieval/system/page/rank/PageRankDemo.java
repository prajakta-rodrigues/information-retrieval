package com.information.retrieval.system.page.rank;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import com.information.retrieval.system.page.rank.design.PageRank;
import com.information.retrieval.system.page.rank.design.impl.PageRankImpl;
import com.information.retrieval.system.page.rank.domains.RankUrl;


public class PageRankDemo {

	public static void main(String args[]) throws InterruptedException, FileNotFoundException, IOException {

		PageRank pageRankUnfocused = new PageRankImpl();
		String unfocusedFileName = System.getProperty("user.dir") + "/resources/FirstCrawlUrls.txt";
		String unfocusedOutputFileName = System.getProperty("user.dir") + "/resources/ResultUnFocusedCrawlGraph.txt";

		List<RankUrl> pageRankGraph1 = null;
		List<RankUrl> pageRankGraph2 = null;
		
		/* Task 1 - Generating web graph from unfocused crawl urls */
		Set[] G1 = pageRankUnfocused.getWebGraphFromUrls(unfocusedFileName, unfocusedOutputFileName);
		
		System.out.println("The max in degree for graph one(G1) is :" + pageRankUnfocused.getMaxInDegree(G1));
		System.out.println("The max in degree for graph one is(G1) :" + pageRankUnfocused.getMaxOutDegree(G1));
		System.out.println("The number of pages with no inlinks(G1) :" +  pageRankUnfocused.getSources(G1));
		System.out.println("The number of pages with no outlinks(G1) : " + pageRankUnfocused.getSinkNodes(G1, G1.length).length);
		
		/*
		 * Task 2D - Unfocused crawling urls page rank iterating till 4
		 * consecutive L1 norms are lesser than 0.001 i.e pank rank has
		 * converged (-1 iterations indicates iterate till it converges) for dampening factor 0.85.
		 */
		System.out.println("For graph 1 :");
		pageRankGraph1 = pageRankUnfocused.getPageRank(G1, 0.85f, -1);

		saveToFile(System.getProperty("user.dir") + "/resources/SortedByPageRankTask2Graph1.txt", pageRankGraph1);

		/*
		 * Task 3 - A.1 Unfocused crawling urls page rank iterating till 4
		 * consecutive L1 norms are lesser than 0.001 i.e pank rank has
		 * converged (-1 iterations indicates iterate till it converges) for dampening factor 0.5.
		 */
		pageRankGraph1 = pageRankUnfocused.getPageRank(G1, 0.5f, -1);

		saveToFile(System.getProperty("user.dir") + "/resources/SortedByPageRankTask3-A-1-0.5Graph1.txt", pageRankGraph1);
		
		
		/*
		 * Task 3 - A.1 Unfocused crawling urls page rank iterating till 4
		 * consecutive L1 norms are lesser than 0.001 i.e pank rank has
		 * converged (-1 iterations indicates iterate till it converges) for dampening factor 0.65.
		 */
		pageRankGraph1 = pageRankUnfocused.getPageRank(G1, 0.65f, -1);

		saveToFile(System.getProperty("user.dir") + "/resources/SortedByPageRankTask3-A-1-0.65Graph1.txt", pageRankGraph1);
		
		
		
		/*
		 * Task 3 - A.2 Unfocused crawling page rank iterate for 4 consecutive
		 * iterations for G1
		 */

		pageRankGraph1 = pageRankUnfocused.getPageRank(G1, 0.85f, 4);

		saveToFile(System.getProperty("user.dir") + "/resources/SortedByPageRankTask3Graph1.txt", pageRankGraph1);

		
		
		
		
		PageRank pageRankFocused = new PageRankImpl();
		String focusedFileName = System.getProperty("user.dir") + "/resources/FocussedCrawlUrls.txt";
		String focusedOutputFileName = System.getProperty("user.dir") + "/resources/ResultFocusedCrawlGraph.txt";

		/* Task 1 - Generating web graph from focused crawl urls */
		Set[] G2 = pageRankFocused.getWebGraphFromUrls(focusedFileName, focusedOutputFileName);

		System.out.println("The max in degree for graph one(G2) is :" + pageRankUnfocused.getMaxInDegree(G2));
		System.out.println("The max in degree for graph one is(G2) :" + pageRankUnfocused.getMaxOutDegree(G2));
		System.out.println("The number of pages with no inlinks(G2) :" +  pageRankUnfocused.getSources(G2));
		System.out.println("The number of pages with no outlinks(G2) : " + pageRankUnfocused.getSinkNodes(G2, G2.length).length);
		
		/*
		 * Task 2D - Unfocused crawling urls page rank iterating till 4
		 * consecutive L1 norms are lesser than 0.001 i.e pank rank has
		 * converged (-1 iterations indicates iterate till it converges) for dampening factor 0.85
		 */
		System.out.println("For graph 2 :");
		pageRankGraph2 = pageRankFocused.getPageRank(G2, 0.85f, -1);

		saveToFile(System.getProperty("user.dir") + "/resources/SortedByPageRankTask2Graph2.txt", pageRankGraph2);

		
		/*
		 * Task 2D - Unfocused crawling urls page rank iterating till 4
		 * consecutive L1 norms are lesser than 0.001 i.e page rank has
		 * converged (-1 iterations indicates iterate till it converges) for dampening factor 0.5
		 */
		pageRankGraph2 = pageRankFocused.getPageRank(G2, 0.5f, -1);

		saveToFile(System.getProperty("user.dir") + "/resources/SortedByPageRankTask3-A-1-0.5Graph2.txt", pageRankGraph2);
		
		
		/*
		 * Task 2D - Unfocused crawling urls page rank iterating till 4
		 * consecutive L1 norms are lesser than 0.001 i.e pank rank has
		 * converged (-1 iterations indicates iterate till it converges) for dampening factor 0.65
		 */
		pageRankGraph2 = pageRankFocused.getPageRank(G2, 0.65f, -1);

		saveToFile(System.getProperty("user.dir") + "/resources/SortedByPageRankTask3-A-1-0.65Graph2.txt", pageRankGraph2);
		
		
		/*
		 * Task 3 - A.2 Unfocused crawling page rank iterate for 4 consecutive
		 * iterations for G2
		 */
		pageRankGraph2 = pageRankFocused.getPageRank(G2, 0.85f, 4);

		saveToFile(System.getProperty("user.dir") + "/resources/SortedByPageRankTask3Graph2.txt", pageRankGraph2);
		
		
		
		/*Sorting documents based on inlink counts*/
		List<RankUrl> inlinksGraph1 = pageRankUnfocused.sortByRawInlinksCount(G1);
		
		saveToFile(System.getProperty("user.dir") + "/resources/SortedByInLinkCountGraph1.txt", inlinksGraph1);
		
		List<RankUrl> inlinksGraph2 = pageRankFocused.sortByRawInlinksCount(G2);
		
		saveToFile(System.getProperty("user.dir") + "/resources/SortedByInLinkCountGraph2.txt", inlinksGraph2);

	}
	

	private static void saveToFile(String fileName, List<RankUrl> pageRankGraph1) throws FileNotFoundException {
		PrintWriter pr = new PrintWriter(new File(fileName));
		for (RankUrl result : pageRankGraph1) {
			pr.println(result.getUrl() + " " + result.getPageRank());
		}
		pr.flush();
		pr.close();

	}
}
