package com.lvmama.vst.demo.quartz.test.example;

import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class JDBCJobStoreRunner {

	public void run() {
		try {

			SchedulerFactory schedulerFactory = new StdSchedulerFactory();

			Scheduler scheduler = schedulerFactory.getScheduler();

			// ①获取调度器中所有的触发器组

			String[] triggerGroups = scheduler.getTriggerGroupNames();

			// ②重新恢复在tgroup1组中，名为trigger1_1触发器的运行

			for (int i = 0; i < triggerGroups.length; i++) {

				String[] triggers = scheduler.getTriggerNames(triggerGroups[i]);

				for (int j = 0; j < triggers.length; j++) {

					Trigger tg = scheduler.getTrigger(triggers[j], triggerGroups[i]);

					// ②-1:根据名称判断
					if (tg instanceof SimpleTrigger && tg.getFullName().equals("tgroup1.trigger1_1")) {
						// ②-1:恢复运行
						scheduler.rescheduleJob(triggers[j], triggerGroups[i], tg);
					}

				}

			}

			scheduler.start();

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	public static void main(String[] args) {
		new JDBCJobStoreRunner().run();
	}
}
