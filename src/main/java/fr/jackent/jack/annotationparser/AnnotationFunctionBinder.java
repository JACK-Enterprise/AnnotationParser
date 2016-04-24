/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.jackent.jack.annotationparser;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author Aurelien
 */
@AllArgsConstructor
public abstract class AnnotationFunctionBinder implements AnnotationFunctionBinderInterface, Annotation {
    
    public void run(AnnotatedElement element) {
        
    }
    
    Class<? extends Annotation> getType() {
        return this != null ? this.annotationType() : null;
    }
}
