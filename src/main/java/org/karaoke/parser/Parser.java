package org.karaoke.parser;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.karaoke.domain.Argument;
import org.karaoke.domain.Karaoke;
import org.karaoke.domain.KaraokesWrapper;
import org.sonarsource.scanner.api.internal.shaded.minimaljson.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@EnableRedisRepositories
public abstract class Parser {

    private static final int NUMBER_INDEX = 0;
    private static final int TITLE_INDEX = 1;
    private static final int SINGER_INDEX = 2;
    private static final int CONNECT_TIMEOUT = 5000;

    public abstract List<Karaoke> extract(Argument argument) throws IOException;

    protected List<Karaoke> extractKaraokes(Elements elements) {
        if (elements == null) {
            return null;
        }
        List<Karaoke> list = elements.stream()
                .filter(e -> e.children().size() > 1) // if result is 0, not operate.
                .map(this::populateKaraoke)
                .collect(Collectors.toList());
        //redisTemplate.opsForList().leftPush()
        return list;
    }

    private Karaoke populateKaraoke(Element e) {
        return new Karaoke()
                .setNumber(e.child(NUMBER_INDEX).text())
                .setTitle(e.child(TITLE_INDEX).text())
                .setSinger(e.child(SINGER_INDEX).text());
    }

    /**
     * TODO 2018.05.01
     * before parse the Document, I should check the resource using the head method.
     * If resource is not present, It is occur the connect time out.
     * @param str
     * @param docQuery
     * @return
     * @throws IOException
     */
    protected Elements fetchDOM(String str, String docQuery) throws IOException {
        return Jsoup.connect(str).timeout(CONNECT_TIMEOUT).get().select(docQuery);
    }
}
