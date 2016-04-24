package fr.jackent.jack.annotationparser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Aurelien
 */
@AllArgsConstructor
@Slf4j
public class AnnotationParser {
    
    private Class target;
    
    private static final String NULL_TARGET_CLASS_MESSAGE = "Given class could not be parsed";
    
    public List<AnnotationBinder> parse() {
        if(this.target == null)
        {
            log.error(NULL_TARGET_CLASS_MESSAGE);
            throw new NullPointerException(NULL_TARGET_CLASS_MESSAGE);
        }
        
        ClassTree tree = new ClassTreeFactory(this.target).parse();
        
        return recurse_parse(tree);
    }
    
    private List<AnnotationBinder> recurse_parse(ClassTree tree) {
        List<AnnotationBinder> binder = new ArrayList<AnnotationBinder>();
        
        if(tree != null)
        {
            List<ClassTree> parents = tree.getParents();
            Class target = tree.getNode();
            for(ClassTree parent : parents) {
                binder.addAll(recurse_parse(parent));
            }
            AnnotationBinder a = new AnnotationBinder(target, getClassAnnotations(target));
            System.out.println(a.getElement() + " / " + a.getAnnotations());
            binder.add(a);
            binder.addAll(getClassMethodsAnnotations(target));
            binder.addAll(getClassFieldsAnnotations(target));
        }
        
        return binder;
    }
    
    private List<Annotation> getClassAnnotations(Class target){
        Annotation[] annotations = null;
        List<Annotation> output = new ArrayList<Annotation>();
        if(target != null)
        {
            annotations = target.getAnnotations();
            System.out.println( annotations.length);
            if(annotations != null)
            {
                output.addAll(Arrays.asList(annotations));
            }
        }
        return output;
    }
    
    private List<Annotation> getClassMethodAnnotations(Method target){
        Annotation[] annotations = null;
        List<Annotation> output = new ArrayList<Annotation>();
        if(target != null)
        {
            annotations = target.getAnnotations();
            System.out.println( annotations.length);
            if(annotations != null)
            {
                output.addAll(Arrays.asList(annotations));
            }
        }
        return output;
    }
    
    private List<AnnotationBinder> getClassMethodsAnnotations(Class target){
        List<AnnotationBinder> output = new ArrayList<AnnotationBinder>();
        
        if(target != null)
        {
            Method[] methods = target.getMethods();
            Annotation[] annotations = null;
            for(Method method : methods) {
                output.add(new AnnotationBinder(method, getClassMethodAnnotations(method)));
            }
        }
        return output;
    }
    
    private List<Annotation> getClassFieldAnnotations(Field target){
        Annotation[] annotations = null;
        List<Annotation> output = new ArrayList<Annotation>();
        if(target != null)
        {
            annotations = target.getAnnotations();
            System.out.println( annotations.length);
            if(annotations != null)
            {
                output.addAll(Arrays.asList(annotations));
            }
        }
        return output;
    }
    
    private List<AnnotationBinder> getClassFieldsAnnotations(Class target){
        List<AnnotationBinder> output = new ArrayList<AnnotationBinder>();
        
        if(target != null)
        {
            Field[] fields = target.getFields();
            Annotation[] annotations = null;
            for(Field field : fields) {
                output.add(new AnnotationBinder(field, getClassFieldAnnotations(field)));
            }
        }
        return output;
    }
    
    
}