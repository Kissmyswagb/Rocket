package rocket.task;

import java.util.concurrent.ScheduledFuture;

public abstract class Task implements Runnable {
	private volatile static long counter = 0x1;
	private long id;
	private int lifespan;
	private ScheduledFuture<?> future;

	public Task() {
		this.id = counter++;
	}

	public void tick() {
		if (lifespan > 0) {
			lifespan--;
		}
		if (lifespan == 0 || future.isDone()) {
			stop();
		}
		run();
	}

	public void stop() {
		if (future != null && future.isCancelled()) {
			throw new IllegalStateException("Task has already stopped!");
		}
		future.cancel(false);
	}

	public long getId() {
		return id;
	}

	public void setLifespan(int lifespan) {
		this.lifespan = lifespan;
	}

	public void setFuture(ScheduledFuture<?> future) {
		this.future = future;
	}

	public boolean isRunning() {
		return (future != null && !future.isCancelled());
	}

	@Override
	public String toString() {
		return "[Task:" + id + "] [Life span:" + lifespan + "] [State: " + (isRunning() ? "Running" : "Not Running" + "]");
	}
}