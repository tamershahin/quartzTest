grails.servlet.version = '3.0' // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = 'target/classes'
grails.project.test.class.dir = 'target/test-classes'
grails.project.test.reports.dir = 'target/test-reports'
grails.project.work.dir = 'target/work'
grails.project.target.level = 1.7
grails.project.source.level = 1.7

grails.project.war.file = "target/${appName}-${grails.util.Environment.current.name}-${appVersion}.war"
grails.war.resources = { stagingDir ->
	delete { fileset(dir: "${stagingDir}", includes: '**/.gitignore') }
}

// uncomment (and adjust settings) to fork the JVM to isolate classpaths
//grails.project.fork = [
//   run: [maxMemory:1024, minMemory:64, debug:false, maxPerm:256]
//]

grails.project.dependency.resolver = "maven"
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        mavenLocal()
        grailsCentral()
        mavenCentral()
        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }

    dependencies {
        test "org.grails:grails-datastore-test-support:1.0.2-grails-2.4"

	    // for configuring cloud foundry services
	    compile 'org.springframework.cloud:spring-cloud-cloudfoundry-connector:1.0.0.RELEASE'
	    compile 'org.springframework.cloud:spring-cloud-spring-service-connector:1.0.0.RELEASE'

	    runtime 'mysql:mysql-connector-java:5.1.29'
    }

    plugins {
        // plugins for the build system only
        build ":tomcat:7.0.55"

	    compile ':database-migration:1.4.1-SNAPSHOT'
	    compile ':hibernate4:4.3.6.1'

	    compile ':quartz:1.0.2'
    }
}
