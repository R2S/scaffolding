package biz.r2s.scaffolding.interceptor;

import java.util.ArrayList;
import java.util.List;

public class ExtratorClassStore {
	static List<Class<? extends ExtratorClass>> classesExtratorClass = new ArrayList<Class<? extends ExtratorClass>>();
	
	public static void setClasse(Class<? extends ExtratorClass> class1){
		classesExtratorClass.add(class1);
	}
	
	public static List<Class> extract(){
		List<Class> classesExtract = new ArrayList<Class>();
		for(Class<? extends ExtratorClass> classExtratorClass:classesExtratorClass){
			ExtratorClass instanceClassExtratorClass;
			try {
				instanceClassExtratorClass = classExtratorClass.newInstance();
				classesExtract.addAll(instanceClassExtratorClass.getDomainClass());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}			
		}
		
		return classesExtract;
	}
	
	public static boolean isScaffold(Class classe){
		boolean scaffold = false;
		for(Class<? extends ExtratorClass> classExtratorClass:classesExtratorClass){
			ExtratorClass instanceClassExtratorClass;
			try {
				instanceClassExtratorClass = classExtratorClass.newInstance();
				if(instanceClassExtratorClass.isScaffold(classe)){
					scaffold = true;
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}			
		}		
		return scaffold;
	}
}
