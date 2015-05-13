import org.springframework.cloud.CloudFactory

def cloud = null
try {
	cloud = new CloudFactory().cloud
} catch (CloudException) {
}

dataSource {
	pooled = true
	jmxExport = true
}
hibernate {
	cache.use_second_level_cache = false
	cache.use_query_cache = false
	cache.region.factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory' // Hibernate 4
	singleSession = true // configure OSIV singleSession mode TODO in dmg was false, not supported in hibernate 4
	flush.mode = 'manual' // OSIV session flush mode outside of transactional context
}

// environment specific settings
environments {
	development {
		dataSource {
			driverClassName = 'com.mysql.jdbc.Driver'
			dialect = 'org.hibernate.dialect.MySQL5InnoDBDialect'
			username = 'grails'
			password = 'server'
			url = 'jdbc:mysql://localhost:9101/test?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true'
		}
	}
	test {
		dataSource {
			username = 'sa'
			password = ''
			driverClassName = 'org.h2.Driver'
			dialect = 'org.hibernate.dialect.H2Dialect'
			url = 'jdbc:h2:file:~/h2/developDbTest.db:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE'
		}
	}
	production {
		dataSource {
			def dbInfo = cloud?.getServiceInfo('mysql-stats')
			username = dbInfo?.userName
			password = dbInfo?.password
			url = dbInfo?.jdbcUrl

			driverClassName = 'com.mysql.jdbc.Driver'
			dialect = 'org.hibernate.dialect.MySQL5InnoDBDialect'

			properties { // TODO adapt to CF
				// See http://grails.org/doc/latest/guide/conf.html#dataSource for documentation
				jmxEnabled = true
				initialSize = 5
				maxActive = 50
				minIdle = 5
				maxIdle = 25
				maxWait = 10000
				maxAge = 10 * 60000
				timeBetweenEvictionRunsMillis = 5000
				minEvictableIdleTimeMillis = 60000
				validationQuery = 'SELECT 1'
				validationQueryTimeout = 3
				validationInterval = 15000
				testOnBorrow = true
				testWhileIdle = true
				testOnReturn = false
				jdbcInterceptors = 'ConnectionState'
				defaultTransactionIsolation = java.sql.Connection.TRANSACTION_READ_COMMITTED
			}
		}
	}
}
