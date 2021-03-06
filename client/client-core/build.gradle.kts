plugins {
    id("com.cognifide.aet.java-conventions")
    id("com.cognifide.aet.test-coverage")
}

dependencies {
    projectCompile(project(":communication-api"))
    implementation("org.codehaus.plexus:plexus-utils:3.0.8")
    implementation("com.google.guava:guava:30.1.1-jre")
    implementation("org.apache.commons:commons-lang3:3.3.2")
    implementation("org.apache.httpcomponents:fluent-hc:4.5.2")
    implementation("org.apache.httpcomponents:httpmime:4.5.2")
    implementation("commons-io:commons-io:2.4")
    implementation("com.jcabi:jcabi-log:0.12.2")
    implementation("org.slf4j:slf4j-api:1.7.10")
    implementation("org.slf4j:slf4j-log4j12:1.7.10")
    implementation("com.google.code.gson:gson:2.6.1")
    implementation("org.simpleframework:simple-xml:2.7.1")
    testImplementation("junit:junit:4.13.1")
    testImplementation("org.hamcrest:hamcrest-all:1.3")
    testImplementation("org.mockito:mockito-all:1.9.5")
}

description = "AET :: Client :: Client Core"
