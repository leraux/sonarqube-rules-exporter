package in.flyspark.sonarqube.rulesexporter.entities;

import java.util.List;

public class Rule {
	String key;
	String name;
	String severity;
	boolean isTemplate;
	String[] tags;
	String[] sysTags;
	String lang;
	String langName;
	String type;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public boolean isTemplate() {
		return isTemplate;
	}

	public void setTemplate(boolean isTemplate) {
		this.isTemplate = isTemplate;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public String[] getSysTags() {
		return sysTags;
	}

	public void setSysTags(String[] sysTags) {
		this.sysTags = sysTags;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getLangName() {
		return langName;
	}

	public void setLangName(String langName) {
		this.langName = langName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static class Resp {
		public int total;
		public List<Rule> rules;
	}
}
