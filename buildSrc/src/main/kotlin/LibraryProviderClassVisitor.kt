import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor

private const val stubMethodName = "getLibraries"
private const val stubMethodDescriptor = "()Ljava/util/List;"
private const val stubMethodSignature =
    "()Ljava/util/List<Lcom/shenghaiyang/oksign/ui/library/Library;>;"


class LibraryProviderClassVisitor(
    api: Int,
    classVisitor: ClassVisitor,
) : ClassVisitor(api, classVisitor) {

    @OptIn(ExperimentalStdlibApi::class)
    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val mv = super.visitMethod(access, name, descriptor, signature, exceptions)
        if (name != stubMethodName || descriptor != stubMethodDescriptor || signature != stubMethodSignature) {
            return mv
        }
        println(">>>>>>>> visitMethod descriptor:$descriptor,signature:$signature")
        return GetLibrariesMethodVisitor(api, mv, getLibraries())
    }


}