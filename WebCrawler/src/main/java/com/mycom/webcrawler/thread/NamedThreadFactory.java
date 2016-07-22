package com.mycom.webcrawler.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {
	private final AtomicInteger _threadIdx = new AtomicInteger(1);
	private final String _prefix;
	private final boolean _daemo;

	public NamedThreadFactory(String prefix) {
		this(prefix, true);
	}

	public NamedThreadFactory(String prefix, boolean daemo) {
		_prefix = prefix;
		_daemo = daemo;
	}

	public Thread newThread(Runnable runnable) {
		String name = _prefix + _threadIdx.getAndIncrement();
		Thread t = new Thread(runnable, name);
		t.setDaemon(_daemo);
		return t;
	}

}