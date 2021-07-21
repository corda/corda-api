package net.corda.gradle

import com.google.cloud.tools.jib.image.Image
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import com.google.cloud.tools.jib.api.*
import com.google.cloud.tools.jib.api.buildplan.AbsoluteUnixPath
import java.nio.file.Paths
import java.time.Instant

// TODO: could move into an external library and make more generic
class DevNetworkJibTask extends DefaultTask {

    @PathSensitive(PathSensitivity.RELATIVE)
    @InputFile
    final RegularFileProperty overrideFile = project.objects.fileProperty()

    @Input
    final Property<String> registryUsername = project.objects.property(String)

    @Input
    final Property<String> registryPassword = project.objects.property(String)

    @Input
    final Property<Boolean> remotePublish =
            project.objects.property(Boolean).convention(false)

    @Input
    final Property<String> baseImageName =
            project.objects.property(String).convention('engineering-docker.software.r3.com/corda-bootstrapper-base')

    @Input
    final Property<String> targetImageName =
            project.objects.property(String).convention('engineering-docker.software.r3.com/corda-bootstrapper')

    @Input
    final Property<String> targetImageTag =
            project.objects.property(String).convention('latest')

    DevNetworkJibTask() {
        description = 'Creates a new "corda-bootstrapper" image with the file specified in "overrideFilePath".'
        group = 'publishing'
    }

    @TaskAction
    def updateImage() {
        // NOTE: this isn't the way JIB is intended to use, we should build the image directly rather than copy the fat jar
        //  however, while we have capsule, this may make sense.

        String overrideFilePath = overrideFile.getAsFile().get().getPath()
        logger.quiet("Publishing '${targetImageName.get()}:${targetImageTag.get()}' ${remotePublish.get() ? "remotely" : "locally"} with '$overrideFilePath', from base '${baseImageName.get()}'")

        RegistryImage baseImage = RegistryImage.named("${baseImageName.get()}:latest")
                .addCredential(registryUsername.get(), registryPassword.get())

        JibContainerBuilder builder = Jib.from(baseImage)
            .setCreationTime(Instant.now())
            .addLayer(Arrays.asList(Paths.get(overrideFilePath)), AbsoluteUnixPath.get("/opt/override/"))

        if (remotePublish.get()) {
            builder.containerize(
                    Containerizer.to(RegistryImage.named("${targetImageName.get()}:${targetImageTag.get()}")
                            .addCredential(registryUsername.get(), registryPassword.get()))
            )
        } else {
            builder.containerize(
                    Containerizer.to(DockerDaemonImage.named("${targetImageName.get()}:${targetImageTag.get()}"))
            )
        }
    }
}