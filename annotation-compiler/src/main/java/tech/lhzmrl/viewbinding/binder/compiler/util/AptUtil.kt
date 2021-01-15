package tech.lhzmrl.bind.viewbinding.compiler.util

import javax.lang.model.element.TypeElement
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror

fun isTypeEqual(typeMirror: TypeMirror, otherType: String): Boolean {
    return otherType == typeMirror.toString()
}

fun TypeMirror.isSubtypeOfType(otherType: String): Boolean {
    if (isTypeEqual(this, otherType)) {
        return true
    }
    if (this.kind != TypeKind.DECLARED) {
        return false
    }
    val declaredType = this as DeclaredType
    val typeArguments = declaredType.typeArguments
    if (typeArguments.size > 0) {
        val typeString = StringBuilder(declaredType.asElement().toString())
        typeString.append('<')
        for (i in typeArguments.indices) {
            if (i > 0) {
                typeString.append(',')
            }
            typeString.append('?')
        }
        typeString.append('>')
        if (typeString.toString() == otherType) {
            return true
        }
    }
    val element = declaredType.asElement() as? TypeElement ?: return false
    val typeElement = element
    val superType = typeElement.superclass
    if (superType.isSubtypeOfType(otherType)) {
        return true
    }
    for (interfaceType in typeElement.interfaces) {
        if (interfaceType.isSubtypeOfType(otherType)) {
            return true
        }
    }
    return false
}