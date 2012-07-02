grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
//grails.project.war.file = "target/${appName}-${appVersion}.war"
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
	repositories {
        grailsCentral()
        grailsRepo "http://grails.org/plugins"

        mavenCentral()
        mavenRepo "http://nexus.nmcdsp.org/content/repositories/releases"
        mavenRepo "http://repository.springsource.com/maven/bundles/release"
        mavenRepo "http://repository.springsource.com/maven/bundles/external"
        mavenRepo "http://repository.springsource.com/maven/libraries/release"
        mavenRepo "http://repository.springsource.com/maven/libraries/external"
    }
	plugins {
		build( ":tomcat:$grailsVersion",
				":release:latest.release",
                ":rest-client-builder:latest.release"
		) {
			// plugin only plugin, should not be transitive to the application
			export = false
		}
	}
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
    }
}
