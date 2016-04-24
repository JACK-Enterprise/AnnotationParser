/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.jackent.jack.annotationparser;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.List;
import lombok.AllArgsConstructor;

/**
 *
 * @author Aurelien
 */
@AllArgsConstructor
public class AnnotationInterpreter {
    private List<AnnotationBinder> objectBinders;
    private List<AnnotationFunctionBinder> functionBinders;
    
    public void run() {
        if(objectBinders != null && functionBinders != null)
        {   
            System.out.println("1st");
            for (AnnotationBinder objectBinder : objectBinders) {
                System.out.println("2nd");
                parseAndRunElement(objectBinder);
            }
        }
    }
    
    private void parseAndRunElement(AnnotationBinder objectBinder) {
        if(objectBinder != null) {
            List<Annotation> annotations = objectBinder.getAnnotations();
            AnnotatedElement targetElement = objectBinder.getElement();
            if(annotations != null) {
                System.out.println("3rd");
                for(Annotation annotation : annotations) {
                    System.out.println("4th");
                    findMatchingAnnotationAndRun(targetElement, annotation);
                }
            }
        }
    }
    
    private void findMatchingAnnotationAndRun(AnnotatedElement targetElement, 
                                                Annotation annotation) {
        if(annotation != null) {
            Class annotationType = annotation.annotationType();
            for(AnnotationFunctionBinder functionBinder : functionBinders) {
                runCorrespondingCode(functionBinder, annotationType, targetElement, annotation);
            }
        }
    }
    
    private void runCorrespondingCode(AnnotationFunctionBinder functionBinder, 
                                        Class annotationType, 
                                        AnnotatedElement targetElement,
                                        Annotation annotation) {
        if(functionBinder != null) {
            Class functionAnnotationType = functionBinder.getType();

            if(annotationType != null && annotationType.equals(functionAnnotationType))
            {
                functionBinder.run(targetElement);
            }
        }
    }
}
