package rocket.task;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public final class TaskHandler {
	private static final Logger logger = Logger.getLogger(TaskHandler.class.getSimpleName());
	private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	private final Map<Long, Task> tasks;
	private final int time;

	private TaskHandler() {
		this.tasks = Collections.synchronizedMap(new HashMap<>());
		this.time = 1;
	}

	private TaskHandler(int time) {
		if (time <= 0) {
			throw new IllegalArgumentException("Cannot set time: " + time + " as it needs to be greater than 0");
		}
		this.tasks = Collections.synchronizedMap(new HashMap<>());
		this.time = time;
	}

	public void schedule(Task task) {
		schedule(task, 0, -1);
	}

	public void schedule(Task task, long delay) {
		schedule(task, delay, -1);
	}

	public void schedule(final Task task, long delay, int lifespan) {
		if (task == null) {
			throw new NullPointerException("Error: Task cannot be null.");
		}
		task.setLifespan(lifespan);
		ScheduledFuture<?> future = executor.scheduleAtFixedRate(() -> {
			try {
				task.tick();
			} catch (Exception e) {
				task.stop();
				tasks.remove(task);
				logger.severe("Error executing task: " + task.getId() + ".\n" + e.getMessage());
			}
		}, delay, time, TimeUnit.MILLISECONDS);
		task.setFuture(future);
		tasks.put(task.getId(), task);
	}

	public Task lookup(long id) {
		if (!tasks.containsKey(id)) {
			throw new IllegalArgumentException("Error: " + id + " not found!!");
		}
		return tasks.get(id);
	}
	
	public static TaskHandler create() {
		return new TaskHandler();
	}
	
	public static TaskHandler createWithTime(int time) {
		return new TaskHandler(time);
	}
}