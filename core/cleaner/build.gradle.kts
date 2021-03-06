plugins {
    id("com.cognifide.aet.java-conventions")
    id("com.cognifide.aet.test-coverage")
    id("biz.aQute.bnd.builder")
}

configurations {
    testCompile {
        extendsFrom(configurations.compileOnly.get())
    }
}

dependencies {
    testImplementation("junit:junit:4.13.1")
    testImplementation("org.mockito:mockito-all:1.9.5")
    testImplementation("org.hamcrest:hamcrest-all:1.3")
    testImplementation("com.googlecode.zohhak:zohhak:1.1.1")
    testImplementation("com.google.code.gson:gson:2.8.5")
    projectCompile(project(":communication-api"))
    projectCompile(project(":datastorage-api"))
    projectCompile(project(":validation-api"))
    projectCompile(project(":datastorage"))
    projectCompile(project(":validation"))
    compileOnly("org.osgi:org.osgi.service.component.annotations:1.3.0")
    compileOnly("org.osgi:org.osgi.annotation:6.0.0")
    compileOnly("org.osgi:org.osgi.service.metatype.annotations:1.3.0")
    compileOnly("com.google.guava:guava:25.1-jre")
    compileOnly("org.apache.commons:commons-lang3:3.7")
    compileOnly("org.apache.activemq:activemq-osgi:5.15.2")
    compileOnly("org.apache.servicemix.bundles:org.apache.servicemix.bundles.quartz:2.3.0_2")
    compileOnly("org.apache.camel:camel-core:2.24.0")
    compileOnly("com.google.code.findbugs:jsr305:3.0.2")
}

tasks.jar {
    manifest {
        attributes(
            Pair("Bundle-Vendor", "Cognifide Ltd."),
            Pair("Import-Package", "javax.annotation;resolution:=optional,*"),
            Pair("Export-Package", "com.cognifide.aet.cleaner.*")
        )
    }
}

description = "AET :: Core :: Cleaner"
