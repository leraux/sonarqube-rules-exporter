package in.flyspark.sonarqube.rulesexporter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.flyspark.sonarqube.rulesexporter.entities.Rule;
import in.flyspark.sonarqube.rulesexporter.rest.RestParser;
import in.flyspark.sonarqube.rulesexporter.service.ExcelService;

public class RulesExporter {
	private static final Logger logger = LoggerFactory.getLogger(RulesExporter.class.getSimpleName());

	public static void main(String[] args) {
		try {
			if (args.length < 1) {
				logger.info("\nInput Sequence : SonarQubeURL(ex. http://localhost:9000/)");
				return;
			}

			String sonarQubeURL = args[0];
			List<Rule> rules = RestParser.getRule(sonarQubeURL);
			rules.sort((Rule r1, Rule r2) -> r1.getLang().compareTo(r2.getLang()));
			Map<String, List<Rule>> rulesMap = rules.stream().collect(Collectors.groupingBy(w -> w.getLang()));

			String fileName = "RULE_SET.xlsx";
			ExcelService.exportExcel(rulesMap, fileName);

			logger.debug("Generated File - {}", fileName);
		} catch (Exception ex) {
			logger.error("Main : ", ex);
		}
	}
}
