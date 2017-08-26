package org.karaoke.service;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.karaoke.domain.KaraokeBuild;
import org.karaoke.parser.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class KaraokeServiceImpl implements KaraokeService {
	Logger log = Logger.getLogger(this.getClass());

	Parser parser;

	@Resource(name = "commonParaser")
	public void setParser(Parser parser) {
		this.parser = parser;
	}

	public List<KaraokeBuild> makeKaraokeNumber(String company, String type, String title) {
		// invalidate
		if (company == null || type == null || title == null || "".equals(company) || "".equals(type)
				|| "".equals(title)) {
			return null;
		}
		List<KaraokeBuild> list = null;
		Parser ms = parser.initCompany(company);
		try {
			list = ms.checkType(type, title);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.fatal("url connect error !");
		}
		return list;
	}

}
