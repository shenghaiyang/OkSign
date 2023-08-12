import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import java.util.concurrent.atomic.AtomicInteger


class GetLibrariesMethodVisitor(
    api: Int,
    private val nextVisitor: MethodVisitor,
    private val libraries: List<Library>,
) : MethodVisitor(api, nextVisitor) {

    private var startLineNum = AtomicInteger(0)

    override fun visitLabel(label: Label?) {
        super.visitLabel(label)
    }

    override fun visitLineNumber(line: Int, start: Label) {
        super.visitLineNumber(line, start)
        startLineNum.compareAndSet(0, line)
        println("visitLineNumber startLineNum:$startLineNum, line:$line")
    }

    override fun visitCode() {
        super.visitCode()
        mv = null
    }

    override fun visitEnd() {
        mv = nextVisitor
        mv.insertLibraCode(libraries, startLineNum.get())
        super.visitEnd()
    }

    private fun insertCode() {

    }
}

private fun MethodVisitor.insertLibraCode(libraries: List<Library>, lineNum: Int) {
    var line = lineNum
    println("line start:$line")
    val startLabel = Label().also { label ->
        visitLabel(label)
        visitLineNumber(line, label)
        visitTypeInsn(Opcodes.NEW, "java/util/ArrayList")
        visitInsn(Opcodes.DUP)
        visitMethodInsn(
            Opcodes.INVOKESPECIAL,
            "java/util/ArrayList",
            "<init>",
            "()V",
            false
        )
        visitIntInsn(Opcodes.ASTORE, 1)
    }

    libraries.forEach { library ->
        line++
        Label().also { label ->
            visitLabel(label)
            visitLineNumber(line, label)

            visitIntInsn(Opcodes.ALOAD, 1)
            visitTypeInsn(
                Opcodes.NEW,
                "com/shenghaiyang/oksign/ui/library/Library"
            )
            visitInsn(Opcodes.DUP)
            visitLdcInsn(library.name)
            visitLdcInsn(library.url)
            visitLdcInsn(library.licenseName)
            visitLdcInsn(library.licenseContent)
            visitMethodInsn(
                Opcodes.INVOKESPECIAL,
                "com/shenghaiyang/oksign/ui/library/Library",
                "<init>",
                "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V",
                false
            )
            visitMethodInsn(
                Opcodes.INVOKEVIRTUAL,
                "java/util/ArrayList",
                "add",
                "(Ljava/lang/Object;)Z",
                false
            )
            visitInsn(Opcodes.POP)
        }
    }
    line++
    Label().also { label ->
        visitLabel(label)
        visitLineNumber(line, label)
        visitIntInsn(Opcodes.ALOAD, 1)
        visitTypeInsn(Opcodes.CHECKCAST, "java/util/List")
        visitInsn(Opcodes.ARETURN)
    }

    Label().also { label ->
        visitLabel(label)
        visitLocalVariable("list", "Ljava/util/ArrayList;", "", startLabel, label, 0)
        visitMaxs(7, 1)
    }
}