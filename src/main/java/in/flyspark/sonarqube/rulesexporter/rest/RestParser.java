package in.flyspark.sonarqube.rulesexporter.rest;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

import in.flyspark.sonarqube.rulesexporter.entities.Rule;
import in.flyspark.sonarqube.rulesexporter.exception.ServiceUnavailableException;
import in.flyspark.sonarqube.rulesexporter.util.Utils;

public class RestParser {
	private RestParser() {
	}

	private static final int PAGE_SIZE = 500;

	public static List<Rule> getRule(String host) throws ServiceUnavailableException {
		List<Rule> issues = new ArrayList<Rule>();
		Rule.Resp respObject = fetchRule(host, 1);
		if (respObject != null) {
			if (respObject.rules != null) {
				issues.addAll(respObject.rules);
				int total = respObject.total;
				if (total > PAGE_SIZE) {
					int max = total / PAGE_SIZE + 2;
					for (int i = 2; i < max; ++i) {
						respObject = fetchRule(host, i);
						if (respObject != null) {
							issues.addAll(respObject.rules);
						}
					}
				}
			}
		}
		return issues;
	}

	private static Rule.Resp fetchRule(String host, int page) throws ServiceUnavailableException {
		String url = getRuleURL(host, page);
		return JSON.parseObject(RestServices.getComponents(url), Rule.Resp.class);
	}

	private static String getRuleURL(String host, int page) {
		return Utils.makeValidURL(host + "api/rules/search?f=isTemplate%2Cname%2Clang%2ClangName%2Cseverity%2Cstatus%2CsysTags%2Ctags%2CtemplateKey&ps=" + PAGE_SIZE + "&s=name&p=" + page);
	}
}
