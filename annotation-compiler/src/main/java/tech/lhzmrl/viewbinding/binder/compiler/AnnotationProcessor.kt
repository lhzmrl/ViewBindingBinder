package tech.lhzmrl.viewbinding.binder.compiler

import com.google.auto.service.AutoService
import tech.lhzmrl.bind.viewbinding.compiler.util.isSubtypeOfType
import tech.lhzmrl.viewbinding.binder.annotation.ViewBinding
import java.util.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeMirror
import javax.tools.Diagnostic

@AutoService(Processor::class)
class AnnotationProcessor: AbstractProcessor() {

    private lateinit var mMessager: Messager

    override fun init(processingEnv: ProcessingEnvironment?) {
        super.init(processingEnv)
        mMessager = processingEnv!!.messager
    }

    private fun getSupportedAnnotations(): Set<Class<out Annotation?>> {
        val annotations: MutableSet<Class<out Annotation?>> = LinkedHashSet()
        annotations.add(ViewBinding::class.java)
        return annotations
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        val types: MutableSet<String> = LinkedHashSet()
        for (annotation in getSupportedAnnotations()) {
            types.add(annotation.canonicalName)
        }
        return types
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.RELEASE_8
    }

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        if (roundEnv!!.processingOver()) {
            return true
        }

        val methodSourceAnnotations: Set<Element> = roundEnv.getElementsAnnotatedWith(
            ViewBinding::class.java
        )
        for (element in methodSourceAnnotations) {
            parseViewBindingAnnotation(element)
        }

        return true
    }

    private fun parseViewBindingAnnotation(element: Element) {
        note("parse ViewBinding", element)
        val enclosingElements = element.enclosingElement
        note("      enclosingElements: ${enclosingElements.kind}", enclosingElements)
        if (enclosingElements.kind != ElementKind.CLASS) {
            throw IllegalStateException(
                String.format(
                    "ViewBinding annotation must be on a member variable.",
                )
            )
        }
        val typeElement = enclosingElements as TypeElement
        val typeMirror: TypeMirror = typeElement.asType()

        if (typeMirror.isSubtypeOfType(ACTIVITY_TYPE)) {
            processViewBindingInActivity(element)
        } else if (typeMirror.isSubtypeOfType(FRAGMENT_TYPE)
            || typeMirror.isSubtypeOfType(FRAGMENT_V4_TYPE)
            || typeMirror.isSubtypeOfType(FRAGMENT_X_TYPE)) {
            processViewBindingInFragment(element)
        } else if (typeMirror.isSubtypeOfType(VIEW_HOLDER_TYPE)) {
            processViewBindingInViewHolder(element)
        }
//        val isDialog = typeMirror.isSubtypeOfType(DIALOG_TYPE)
//        val isViewHolder = typeMirror.isSubtypeOfType(VIEW_HOLDER_TYPE)
    }

    private fun processViewBindingInActivity(element: Element) {
        val typeElement = element.enclosingElement as TypeElement
    }

    private fun processViewBindingInFragment(element: Element) {
        // TODO Not yet implemented
    }

    private fun processViewBindingInViewHolder(element: Element) {
        // TODO Not yet implemented
    }

    private fun note(message: CharSequence) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, message.toString() + "\r")
    }

    private fun note(message: CharSequence, element: Element) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, message, element)
        mMessager.printMessage(Diagnostic.Kind.NOTE, "\r")
    }

    companion object {
        const val ACTIVITY_TYPE = "android.app.Activity"
        const val FRAGMENT_TYPE = "android.app.Fragment"
        const val FRAGMENT_V4_TYPE = "android.support.v4.app.Fragment"
        const val FRAGMENT_X_TYPE = "androidx.fragment.app.Fragment"
        const val DIALOG_TYPE = "android.app.Dialog"
        const val VIEW_HOLDER_TYPE = "androidx.recyclerview.widget.RecyclerView.ViewHolder"
    }

}