package com.sensing.core.utils.kafka;

import java.util.concurrent.atomic.AtomicLong;

public class Metric {

	private AtomicLong readCount = new AtomicLong(0);
	private AtomicLong writeCount = new AtomicLong(0);
	private AtomicLong readBytes = new AtomicLong(0);

	private long readerStartTime;
	private long writerStartTime;

	public Metric(long readerStartTime) {
		this.readerStartTime = readerStartTime;
	}
	public Metric() {

	}

	public AtomicLong getReadCount() {
		return readCount;
	}

	public void setReadCount(AtomicLong readCount) {
		this.readCount = readCount;
	}

	public AtomicLong getWriteCount() {
		return writeCount;
	}

	public void setWriteCount(AtomicLong writeCount) {
		this.writeCount = writeCount;
	}

	public long getReaderStartTime() {
		return readerStartTime;
	}

	public void setReaderStartTime(long readerStartTime) {
		this.readerStartTime = readerStartTime;
	}


	public long getWriterStartTime() {
		return writerStartTime;
	}

	public void setWriterStartTime(long writerStartTime) {
		this.writerStartTime = writerStartTime;
	}

	public AtomicLong getReadBytes() {
		return readBytes;
	}

	public void setReadBytes(AtomicLong readBytes) {
		this.readBytes = readBytes;
	}

	public long getSpeed() {
		long distance = (System.currentTimeMillis() - this.readerStartTime) / 1000;
		if (distance == 0) {
			return 0;
		}
		return this.readBytes.get() / distance;
	}
}
