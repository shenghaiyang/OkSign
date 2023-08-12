import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project


class LibraryPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val androidComponents = project.extensions.getByType(AndroidComponentsExtension::class.java)

        androidComponents.onVariants {
            it.instrumentation.transformClassesWith(
                LibraryAsmClassVisitorFactory::class.java,
                InstrumentationScope.PROJECT,
            ) {

            }
        }
    }

}
