package com.lvmama.vst.demo.common.jobs;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.quartz.JobDataMap;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * JOB调度基类
 * 
 */
public abstract class AbstractJob implements Runnable {

	ThreadPoolTaskExecutor threadPoolTaskExecutor;

	/**
	 * 
	 * @param jobDataMap
	 * @throws Exception
	 */
	public void valueInit(Map<String, Object> jobDataMap) throws Exception {

		if (jobDataMap != null) {
			Class<?> cls = this.getClass();
			Method methodlist[] = cls.getMethods();

			Map<String, Method> methodMap = new HashMap<String, Method>();
			for (Method method : methodlist) {
				methodMap.put(method.getName(), method);
			}

			Iterator<Entry<String, Object>> it = jobDataMap.entrySet().iterator();
			while (it.hasNext()) {
				JobDataMap.Entry<String, Object> entry = (JobDataMap.Entry<String, Object>) it.next();
				if (entry.getKey() != null && !"".equals(entry.getKey()) && entry.getKey().length() > 0) {
					String key = entry.getKey();
					String methodName = "set" + key.substring(0, 1).toUpperCase();
					if (key.length() > 1) {
						methodName += key.substring(1);
					}
					try {
						if (methodMap.containsKey(methodName)) {
							Method method = methodMap.get(methodName);
							Object value = null;
							Class<?>[] classes = method.getParameterTypes();
							if (classes.length > 0) {
								Class<?> class1 = classes[0];
								if (!class1.isAssignableFrom(String.class)) {
									// 类型转换
									if (class1.isAssignableFrom(Integer.class)) {
										value = Integer.parseInt(String.valueOf(entry.getValue()));
									} else if (class1.isAssignableFrom(Date.class)) {
										Date date = new Date();
										if (entry.getValue() instanceof Long) {
											date.setTime((Long) entry.getValue());
										} else if (entry.getValue() instanceof String) {
											if (entry.getValue().toString().equalsIgnoreCase("sysdate")) {
												date.setTime(new Date().getTime());
											} else {
												date.setTime(Long.parseLong(String.valueOf(entry.getValue())));
											}
										} else {
											date.setTime(Long.parseLong(String.valueOf(entry.getValue())));
										}
										value = date;
									} else {
										value = entry.getValue();
									}
								} else {
									value = entry.getValue();
								}
								method.invoke(this, value);
							}
						}
					} catch (Exception e) {
						throw e;
					}
				}
			}
		}

	}

	public ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
		return threadPoolTaskExecutor;
	}

	public void setThreadPoolTaskExecutor(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
		this.threadPoolTaskExecutor = threadPoolTaskExecutor;
	}

}
