jdbcProps = {
	scheduler.instanceName = "test_quartz"
	scheduler.instanceId = "AUTO"

	threadPool.class = "org.quartz.simpl.SimpleThreadPool"
	threadPool.threadCount = 15
	threadPool.threadPriority = 5

	jobStore.misfireThreshold = 60000

	jobStore.class = "org.quartz.impl.jdbcjobstore.JobStoreTX"
	jobStore.driverDelegateClass = "org.quartz.impl.jdbcjobstore.StdJDBCDelegate"

	jobStore.useProperties = false
	jobStore.tablePrefix = "QRTZ_"
	jobStore.isClustered = true
	jobStore.clusterCheckinInterval = 10000

	plugin.shutdownhook.class = "org.quartz.plugins.management.ShutdownHookPlugin"
	plugin.shutdownhook.cleanShutdown = true

}
quartz {
	autoStartup = false
	jdbcStore = false
	waitForJobsToCompleteOnShutdown = false
	exposeSchedulerInRepository = false
}

environments {
	development {
		quartz {
			//Toggle scheduled tasks for local "run-app" environment
			autoStartup = true
			jdbcStore = true
			waitForJobsToCompleteOnShutdown = true
			exposeSchedulerInRepository = true
			props(jdbcProps)
		}
	}
	production {
		quartz {
			jdbcStore = true
			autoStartup = true
			waitForJobsToCompleteOnShutdown = true
			exposeSchedulerInRepository = true
			props(jdbcProps)
		}
	}
}
