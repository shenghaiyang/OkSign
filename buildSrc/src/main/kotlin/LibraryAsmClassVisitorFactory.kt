import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.android.build.api.instrumentation.InstrumentationParameters.None
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes


private const val stubClassName = "com.shenghaiyang.oksign.ui.library.LibraryProvider"


data class Library(
    val name: String,
    val url: String,
    val licenseName: String,
    val licenseContent: String,
)

abstract class LibraryAsmClassVisitorFactory : AsmClassVisitorFactory<None> {

    override fun createClassVisitor(
        classContext: ClassContext,
        nextClassVisitor: ClassVisitor,
    ): ClassVisitor {
        return LibraryProviderClassVisitor(Opcodes.ASM8, nextClassVisitor)
    }


    override fun isInstrumentable(classData: ClassData): Boolean {
        return classData.className == stubClassName
    }
}