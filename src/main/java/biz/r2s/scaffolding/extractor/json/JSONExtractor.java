package biz.r2s.scaffolding.extractor.json;

import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;

import biz.r2s.scaffolding.extractor.scaffolding.ScaffoldingExtractor;
import biz.r2s.scaffolding.interceptor.FileJsonExtratorClass;

public class JSONExtractor extends ScaffoldingExtractor{
	private static Gson gson = new Gson();
	@Override
	protected
	Object getConfigScaffoldind(Class domainClass) {
		StringWriter writer = new StringWriter();
		try {
			String path = domainClass.getName().replace('.', File.separatorChar) + FileJsonExtratorClass.EXT;
			System.out.println(path);
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path);
			System.out.println(inputStream);
			if (inputStream != null) {
				IOUtils.copy(inputStream, writer, Charset.defaultCharset());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String texto = writer.toString();
		if (texto != null && !texto.isEmpty()) {
			try {
				return gson.fromJson(texto, Boolean.class);
			} catch (Exception e) {
				return gson.fromJson(texto, Map.class);
			}
		}
		return null;
	}
	
	public Object getConfig(Class domainClass){
		return this.getConfigScaffoldind(domainClass);
	}
	
	@Override
	public int getOrder() {
		return 2;
	}
}
