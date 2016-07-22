package com.mycom.webcrawler.urlholder;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentUrlHolder extends AbstractUrlHolder {
	private BlockingQueue<String> urlQueue;
	private Set<String> urlSet = new HashSet<>();
	private Lock lock = new ReentrantLock();

	public void addUrl(String url) {
		lock.lock();
		try {
			if (urlSet.contains(url))
				return;
			else {
				urlQueue.add(url);
				urlSet.add(url);
			}
		} finally {
			lock.unlock();
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
