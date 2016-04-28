package com.mycom.webcrawler.urlholder;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

public class ConcurrentUrlHolder extends AbstractUrlHolder{
	private BlockingQueue<String> urlQueue;
	private Set<String> urlSet = new LinkedHashSet<>();

	public synchronized void addUrl(String url){
		if(urlSet.contains(url)) return;
		else{
			urlQueue.add(url);
			urlSet.add(url);
		}		
	}

	public BlockingQueue<String> getUrlQueue() {
		return urlQueue;
	}

	public void setUrlQueue(BlockingQueue<String> urlQueue) {
		this.urlQueue = urlQueue;
	}

	public Set<String> getUrlSet() {
		return urlSet;
	}

	
	
}
